package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


    EditContract.Presenter mPresenter;
    EditComponentAdapter mAdapter;

    private int focusing = -1;
    private InputMethodManager inputMethodManager;


    @BindView(R.id.editor_component_menu)
    LinearLayout mComponentMenu;
    @BindView(R.id.editor_bt_delete)
    Button mBtDeleteComponent;
    @BindView(R.id.editor_bt_cancel)
    Button mBtCancelMenu;

    @BindView(R.id.editor_et_title)
    EditText mTxtTitle;
    @BindView(R.id.editor_bt_newdocument)
    Button mBtNewDocument;
    @BindView(R.id.editor_bt_loaddocument)
    Button mBtLoadDocument;
    @BindView(R.id.editor_bt_save)
    Button mBtSaveButton;
    @BindView(R.id.editor_bt_addcomponent)
    Button mBtAddComponent;
    @BindView(R.id.editor_recyclerview)
    RecyclerView mEditorRecyclerView;

    private SelectComponentDialog mSelectComponentDialog;


    View.OnClickListener dialogAddTxtButtonListener;
    View.OnClickListener dialogImgButtonListener;
    View.OnClickListener dialogMapButtonListener;

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
        setTitleLengthLimit();

        initComponentMenu();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clearComponents();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: handling data
        if (requestCode == REQ_MOV2_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImgUri = data.getData();
                    mPresenter.addComponent(BaseComponent.TypE.IMG, selectedImgUri.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == REQ_MOV2_SEARCH_PLACE) {
            if (resultCode == RESULT_OK) {
                try {
                    //TODO: parcel should declared with final
                    Bundle bundle = data.getExtras();
                    PlaceItemParcelable passer = bundle.getParcelable(MAPINFO_PARCEL);
                    mPresenter.addComponent(BaseComponent.TypE.MAP, passer);

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
                mPresenter.addComponent(BaseComponent.TypE.TEXT, null);
                mSelectComponentDialog.dismiss();
            }
        };

        dialogImgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImgSrcFromGallery();
                mSelectComponentDialog.dismiss();
            }
        };

        dialogMapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMapSrcFromNaverPlaceAPI();
                mSelectComponentDialog.dismiss();
            }
        };

        mSelectComponentDialog = new SelectComponentDialog(this, dialogAddTxtButtonListener, dialogImgButtonListener, dialogMapButtonListener);
    }

    private void getImgSrcFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_MOV2_GALLERY);
    }

    private void getMapSrcFromNaverPlaceAPI() {
        Intent intent = new Intent(this, SearchPlaceActivity.class);
        startActivityForResult(intent, REQ_MOV2_SEARCH_PLACE);
    }
    //---


    //View action
    @Override
    public void waitForDbProcessing() {
        //TODO : list on wait
        //setProgress Bar - Waiting
        //save & add comp bt inactivate

        ProgressDialog dialog = ProgressDialog.show(EditorActivity.this, "", "잠시만 기다려 주세요 ...", true);
        dialog.show();
    }

    @Override
    public void finishActivity(int REQ_CODE) {
        setResult(REQ_CODE);
        finish();
    }

    @Override
    public void setMenuForSelectedComponent(int position) {
        focusing = position;
        showComponentMenu();
    }


    //activity
    @Override
    public void onBackPressed() {
        if(checkComponentMenuIsActive()){
            clearComponentMenu();
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
                ab
                        .setNegativeButton("NO", null)
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

    private void checkEditorMode() {
        Intent intent = getIntent();
        EDITOR_MODE = intent.getIntExtra(MyApplication.EDITOR_MODE, NEW_DOCUMENT_MODE);
        MyApplication.LogController.makeLog(TAG, "Editor Mode :" + String.valueOf(EDITOR_MODE), localLogPermission);

        if (EDITOR_MODE == EDIT_DOCUMENT_MODE) {
            mPresenter.clearComponents();
            DocumentDataParcelable documentDataParcelable = getDocumentDataFromParcelable(intent);
            loadDocument(documentDataParcelable);
        } else if (EDITOR_MODE == NEW_DOCUMENT_MODE) {
            MyApplication.LogController.makeLog(TAG, "Editor Mode :" + String.valueOf(NEW_DOCUMENT_MODE), localLogPermission);
        }
    }

    private void setTitleLengthLimit() {
        InputFilter[] f = new InputFilter[]{
                new TitleFilter(getBaseContext(), 20)
        };

        mTxtTitle.setFilters(f);
    }


    //componentMenu
    private void initComponentMenu() {
        mBtCancelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideComponentMenu();
            }
        });

        mBtDeleteComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteComponent(focusing);
                hideComponentMenu();
                focusing = -1;
            }
        });
    }

    private void clearComponentMenu() {
        hideComponentMenu();
        focusing = -1;
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


    //database
    private DocumentDataParcelable getDocumentDataFromParcelable(Intent intent) {
        Bundle bundle = intent.getExtras();
        DocumentDataParcelable documentDataParcelable = bundle.getParcelable(DOCUMENT_PARCEL);
        return documentDataParcelable;
    }

    private void loadDocument(DocumentDataParcelable documentDataParcelable) {
        mTxtTitle.setText(documentDataParcelable.getTitle());
        currentDocumentId = documentDataParcelable.getDoc_id();
        requestLoadDocumentComponents(documentDataParcelable);
    }

    private void requestLoadDocumentComponents(DocumentDataParcelable documentDataParcelable) {

        String jsonComponent = documentDataParcelable.getComponentsJson();
        mPresenter.getComponentsFromJson(jsonComponent);
    }

    private void createNewDocument() {
        mTxtTitle.setText("");
        mTxtTitle.setHint("Title");
        mPresenter.clearComponents();
        EDITOR_MODE = NEW_DOCUMENT_MODE;
        currentDocumentId = -1;
    }
}
