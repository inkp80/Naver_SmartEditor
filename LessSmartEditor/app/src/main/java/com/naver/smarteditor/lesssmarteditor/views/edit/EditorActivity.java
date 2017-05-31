package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchItemHelperCallback;
import com.naver.smarteditor.lesssmarteditor.data.DocumentDataParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemParcelable;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.EditorComponentRepository;
import com.naver.smarteditor.lesssmarteditor.views.edit.dialog.SelectComponentDialog;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditContract;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditPresenter;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.ClearCachesTask;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.TitleFilter;
import com.naver.smarteditor.lesssmarteditor.views.main.DocumentListActivity;
import com.naver.smarteditor.lesssmarteditor.views.map.SearchPlaceActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.NEW_DOCUMENT_MODE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.EDIT_DOCUMENT_MODE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.MAPINFO_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_MOV2_GALLERY;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_MOV2_SEARCH_PLACE;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorActivity extends AppCompatActivity implements EditContract.View {
    private final String TAG = "EditorActivity";
    private boolean localLogPermission = true;
    private long backKeyTime = 0;

    private int currentDocumentId = -1;
    private int EDITOR_MODE = NEW_DOCUMENT_MODE;


    private EditContract.Presenter mPresenter;
    private EditComponentAdapter mAdapter;

    private int focusingComponentIndex;
    private View focusingComponentView;
    private InputMethodManager inputMethodManager;


    @BindView(R.id.editor_component_menu)
    private LinearLayout mComponentMenu;
    @BindView(R.id.editor_bt_delete)
    private Button mBtDeleteComponent;
    @BindView(R.id.editor_bt_cancel)
    private Button mBtCancelMenu;

    @BindView(R.id.editor_et_title)
    private EditText mTxtTitle;
    @BindView(R.id.editor_bt_newdocument)
    private Button mBtNewDocument;
    @BindView(R.id.editor_bt_loaddocument)
    private Button mBtLoadDocument;
    @BindView(R.id.editor_bt_save)
    private Button mBtSaveButton;
    @BindView(R.id.editor_bt_addcomponent)
    private Button mBtAddComponent;
    @BindView(R.id.editor_recyclerview)
    private RecyclerView mEditorRecyclerView;


    private SelectComponentDialog mSelectComponentDialog;


    private View.OnClickListener dialogAddTxtButtonListener;
    private View.OnClickListener dialogImgButtonListener;
    private View.OnClickListener dialogMapButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        mAdapter = new EditComponentAdapter(this);

        initPresenter();
        initRecyclerView();

        initEditorMenu();
        initDialog();
        checkEditorMode();
        setTitleLengthLimit(10);

        initComponentMenu();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clearCurrentDocument();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: handling data
        if(resultCode == RESULT_OK) {
            if(requestCode == REQ_MOV2_GALLERY){
                try {
                    Uri selectedImgUri = data.getData();
                    mPresenter.addComponentToDocument(BaseComponent.TypE.IMG, selectedImgUri.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if(requestCode == REQ_MOV2_SEARCH_PLACE) {
                try {
                    //TODO: parcel should declared with final
                    Bundle bundle = data.getExtras();
                    PlaceItemParcelable passer = bundle.getParcelable(MAPINFO_PARCEL);
                    mPresenter.addComponentToDocument(BaseComponent.TypE.MAP, passer);

                } catch (Exception e) {
                    MyApplication.LogController.makeLog(TAG, "ERROR", localLogPermission);
                    e.printStackTrace();
                }
            }
        }
    }


    //Init & default setup method for Activity
    private void initRecyclerView() {
        mEditorRecyclerView = (RecyclerView) findViewById(R.id.editor_recyclerview);
        mEditorRecyclerView.setPadding(10, 10, 10, 10);
        mEditorRecyclerView.setHasFixedSize(true);
        mEditorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditorRecyclerView.setAdapter(mAdapter);
        mEditorRecyclerView.setItemViewCacheSize(100);


        ItemTouchHelper.Callback callback = new ComponentTouchItemHelperCallback(mAdapter, (ComponentTouchEventListener) mPresenter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mEditorRecyclerView);
    }

    private void initPresenter() {
        mPresenter = new EditPresenter();
        mPresenter.attachView(this);
        mPresenter.setComponentAdapterView(mAdapter);
        mPresenter.setComponentAdatperModel(mAdapter);
        mPresenter.setComponentDataSource(EditorComponentRepository.getInstance(this));
    }

    private void initDialog() {
        //TODO: rename & restructuring

        dialogAddTxtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TEXT COMPONENT TO EDITOR
                mPresenter.addComponentToDocument(BaseComponent.TypE.TEXT, null);
                mSelectComponentDialog.dismiss();
            }
        };

        dialogImgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImgSrcFromGallery();
                mSelectComponentDialog.dismiss();
            }
        };

        dialogMapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMapSrcFromNaverAPI();
                mSelectComponentDialog.dismiss();
            }
        };

        mSelectComponentDialog = new SelectComponentDialog(this, dialogAddTxtButtonListener, dialogImgButtonListener, dialogMapButtonListener);
    }

    private void requestImgSrcFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_MOV2_GALLERY);
    }

    private void requestMapSrcFromNaverAPI() {
        Intent intent = new Intent(this, SearchPlaceActivity.class);
        startActivityForResult(intent, REQ_MOV2_SEARCH_PLACE);
    }
    //---


    //View action
    @Override
    public void showProgressBar() {
        //TODO : list on wait
        ProgressDialog dialog = ProgressDialog.show(EditorActivity.this, "", "잠시만 기다려 주세요 ...", true);
        dialog.show();
    }



    //TODO: focus 관련 동작 여기서
    @Override
    public void setFocusForSelectedComponent(int componentIndex, View selectedComponent) {
        removeFocusFromCurrentComponent();
        focusingComponentIndex = componentIndex;
        focusingComponentView = selectedComponent;
        showComponentMenu();
    }

    @Override
    public void scrollToNewComponent(int componentPosition) {
        mEditorRecyclerView.smoothScrollToPosition(componentPosition);
        mEditorRecyclerView.requestChildFocus(mEditorRecyclerView.getChildAt(componentPosition), getCurrentFocus());
    }

    //activity
    @Override
    public void onBackPressed() {
        if(checkComponentMenuIsActive()){
            removeFocusFromCurrentComponent();
            backKeyTime = 0;
            return;
        }

        Toast toast;
        if (System.currentTimeMillis() > backKeyTime + 2000) {
            backKeyTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyTime + 2000) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void initEditorMenu() {

        mBtAddComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectComponentDialog.show();
            }
        });

        mBtLoadDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditorActivity.this, DocumentListActivity.class);
                startActivity(intent);
            }
        });

        mBtSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditorActivity.this, String.valueOf(Calendar.getInstance().getTime()) + "\n저장되었습니다.", Toast.LENGTH_SHORT).show();

                if (EDITOR_MODE == NEW_DOCUMENT_MODE) {
                    mPresenter.saveDocumentToDataBase(mTxtTitle.getText().toString());
                } else if (EDITOR_MODE == EDIT_DOCUMENT_MODE) {
                    //TODO : update query, while show progress-bar
                    mPresenter.updateDocumentOnDatabase(mTxtTitle.getText().toString(), currentDocumentId);
                }
                new ClearCachesTask(getBaseContext(), true, true).execute();
            }
        });


        mBtNewDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(EditorActivity.this);
                ab.setMessage("Make a new Document?");
                ab.setNegativeButton("NO", null)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createNewDocument();
                            }
                        });
                ab.show();
            }
        });
    }


    //TODO : Remove Edit-Mode intent data from DocListActivity
    private void checkEditorMode() {
        Intent intent = getIntent();
        EDITOR_MODE = intent.getIntExtra(MyApplication.EDITOR_MODE, NEW_DOCUMENT_MODE);
        MyApplication.LogController.makeLog(TAG, "Editor Mode :" + String.valueOf(EDITOR_MODE), localLogPermission);

        if (EDITOR_MODE == EDIT_DOCUMENT_MODE) {
            mPresenter.clearCurrentDocument();
            DocumentDataParcelable documentDataParcelable = getDocumentDataFromParcelable(intent);
            initDocument(documentDataParcelable);
        } else if (EDITOR_MODE == NEW_DOCUMENT_MODE) {
            MyApplication.LogController.makeLog(TAG, "Editor Mode :" + String.valueOf(NEW_DOCUMENT_MODE), localLogPermission);
        }
    }

    private void setTitleLengthLimit(int lengthLimit) {
        InputFilter[] f = new InputFilter[]{
                new TitleFilter(getBaseContext(), lengthLimit)
        };

        mTxtTitle.setFilters(f);
    }


    //componentMenu
    private void initComponentMenu() {
        mBtCancelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFocusFromCurrentComponent();
            }
        });

        mBtDeleteComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteComponentFromDocument(focusingComponentIndex);
                hideComponentMenu();
            }
        });
    }

    private void removeFocusFromCurrentComponent() {
        hideComponentMenu();
        focusingComponentView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        focusingComponentView = null;
        focusingComponentIndex = -1;
    }

    private void hideComponentMenu() {
        mComponentMenu.setVisibility(GONE);
    }

    private void showComponentMenu() {
        mComponentMenu.setVisibility(View.VISIBLE);
    }

    private boolean checkComponentMenuIsActive(){
        if(mComponentMenu.getVisibility() == View.VISIBLE)
            return true;
        else
            return false;
    }


    private DocumentDataParcelable getDocumentDataFromParcelable(Intent intent) {
        Bundle bundle = intent.getExtras();
        DocumentDataParcelable documentDataParcelable = bundle.getParcelable(DOCUMENT_PARCEL);
        return documentDataParcelable;
    }


    //TODO : 구조 개선, 작명
    //doc List Activity => View로 => Presenter로 => Model 가공 => View로
    private void initDocument(DocumentDataParcelable documentDataParcelable) {
        mTxtTitle.setText(documentDataParcelable.getTitle());
        currentDocumentId = documentDataParcelable.getDoc_id();
        requestDocumentBody(documentDataParcelable);
    }

    private void requestDocumentBody(DocumentDataParcelable documentDataParcelable) {
        String jsonComponent = documentDataParcelable.getComponentsJson();
        mPresenter.getComponentsFromJson(jsonComponent);
    }

    //TODO : createNewDocument, 이미 비어있다면 새로 만들 필요가 없다.. 초기화로는?
    private void createNewDocument() {
        mTxtTitle.setText("");
        mTxtTitle.setHint("Title");
        mPresenter.clearCurrentDocument();
        EDITOR_MODE = NEW_DOCUMENT_MODE;
        currentDocumentId = -1;
    }



}
