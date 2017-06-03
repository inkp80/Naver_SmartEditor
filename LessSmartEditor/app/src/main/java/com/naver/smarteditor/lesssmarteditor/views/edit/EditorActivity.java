package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchItemHelperCallback;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TitleComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.dialog.SelectComponentDialog;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditContract;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditPresenter;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.ClearCachesTask;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.TitleFilter;
import com.naver.smarteditor.lesssmarteditor.views.main.DocumentListActivity;
import com.naver.smarteditor.lesssmarteditor.views.map.SearchPlaceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_ID;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.NEW_DOCUMENT_MODE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.MAPINFO_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_ADD_DOCUMENT;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_MOV2_DOCLIST;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_MOV2_GALLERY;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.REQ_MOV2_SEARCH_PLACE;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorActivity extends AppCompatActivity implements EditContract.View {
    private final String TAG = "EditorActivity";
    private boolean localLogPermission = true;
    private long backKeyTime = 0;

    private InputMethodManager inputMethodManager;

    private EditContract.Presenter mPresenter;
    private EditComponentAdapter mAdapter;

    private int focusingComponentIndex;
    private View focusingComponentView;


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

    private Rect focusedRange;
    private boolean isFocused = false;
    private ActivityToViewHolder activity2ViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        mAdapter = new EditComponentAdapter(this);
        activity2ViewHolder = mAdapter.getA2V();
        mAdapter.setV2A(new ViewHolderToActivity() {
            @Override
            public void focusing(Rect rect) {
                focusedRange = rect;
                isFocused = true;
            }
        });

        initPresenter();

        initRecyclerView();

        initEditorMenu();

        initSelectComponentDialog();

        initDocument();

        setTitleLengthLimit(30);

        initComponentOption();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clearCurrentDocument();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_MOV2_GALLERY) {
                try {
                    Uri selectedImgUri = data.getData();
                    mPresenter.addComponentToDocument(new ImgComponent(selectedImgUri.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQ_MOV2_SEARCH_PLACE) {
                try {
                    Bundle bundle = data.getExtras();
                    PlaceItemParcelable passer = bundle.getParcelable(MAPINFO_PARCEL);
                    mPresenter.addComponentToDocument(new MapComponent(passer.getPlaceName(), passer.getPlaceAddress(), passer.getPlaceCoords(), passer.getPlaceUri()));

                } catch (Exception e) {
                    LogController.makeLog(TAG, "ERROR", localLogPermission);
                    e.printStackTrace();
                }
            } else if (requestCode == REQ_MOV2_DOCLIST) {
                try {
                    int documentId = getIntent().getIntExtra(DOCUMENT_ID, -1);
                    mPresenter.getDocument(documentId);
                } catch (Exception e) {

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

        mAdapter.setOnEditTextComponentChangeListener(new OnEditTextComponentChangeListener() {
            @Override
            public void onEditTextComponentTextChange(BaseComponent baseComponent, int position) {
                mPresenter.updateComponentInDocument(baseComponent, position);
                //update요청
            }
        });


        ItemTouchHelper.Callback callback = new ComponentTouchItemHelperCallback(mAdapter, new ComponentTouchEventListener() {
            @Override
            public boolean OnComponentMove(int from, int to) {
                LogController.makeLog(TAG, "onComponentMov", localLogPermission);
                mPresenter.swapComponent(from, to);
                return true;
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mEditorRecyclerView);
    }

    private void initPresenter() {
        mPresenter = new EditPresenter();
        mPresenter.attachView(this);
        mPresenter.setComponentAdapterView(mAdapter);
        mPresenter.setComponentAdatperModel(mAdapter);
        mPresenter.setComponentDataSource(DocumentRepository.getInstance(this));

        mPresenter.addComponentToDocument(new TitleComponent(null, null));
    }

    private void initSelectComponentDialog() {

        View.OnClickListener dialogAddTxtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TEXT COMPONENT TO EDITOR
                mPresenter.addComponentToDocument(new TextComponent(""));
                mSelectComponentDialog.dismiss();
            }
        };

        View.OnClickListener dialogImgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImgSrcFromGallery();
                mSelectComponentDialog.dismiss();
            }
        };

        View.OnClickListener dialogMapButtonListener = new View.OnClickListener() {
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
    ////////////////////////////////////////////////////////////////////////////////////


    //View action
    @Override
    public void showProgressBar() {
        //TODO : list on wait
        ProgressDialog dialog = ProgressDialog.show(EditorActivity.this, "", "잠시만 기다려 주세요 ...", true);
        dialog.show();
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
                startActivityForResult(intent, REQ_MOV2_DOCLIST);
            }
        });

        mBtSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogController.makeLog(TAG, "save bt pushed", localLogPermission);
                mPresenter.updateDocument();
                new ClearCachesTask(getBaseContext(), true, true).execute();
            }
        });


        mBtNewDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(EditorActivity.this);
                ab.setMessage("편집 중인 문서를 초기화 하시겠습니까?");
                ab.setNegativeButton("NO", null)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.clearCurrentDocument();
                                mPresenter.addComponentToDocument(new TitleComponent(null, null));
                                activity2ViewHolder.clearFocus();
                            }
                        });
                ab.show();
            }
        });
    }

    private void setTitleLengthLimit(int lengthLimit) {
        InputFilter[] f = new InputFilter[]{
                new TitleFilter(getBaseContext(), lengthLimit)
        };

        mTxtTitle.setFilters(f);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {


        if (System.currentTimeMillis() > backKeyTime + 2000) {
            activity2ViewHolder.clearFocus();
            backKeyTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyTime + 2000) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private DocumentParcelable getDocumentDataFromParcelable(Intent intent) {
        Bundle bundle = intent.getExtras();
        DocumentParcelable documentParcelable = bundle.getParcelable(DOCUMENT_PARCEL);
        return documentParcelable;
    }
    ////////////////////////////////////////////////////////////////////////////////////



    //componentOption : Sliding menu which showing in View when DocumentComponent selected by long click
    private void initComponentOption() {
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
                removeFocusFromCurrentComponent();
                hideComponentOption();
            }
        });
    }

    private void showComponentOption() {
        mComponentMenu.setVisibility(View.VISIBLE);
    }

    private void hideComponentOption() {
        mComponentMenu.setVisibility(GONE);
    }

    @Override
    public void setFocusForSelectedComponent(int componentIndex, View selectedComponent) {

        inputMethodManager.toggleSoftInput(0, 0);

        if (selectedComponent == focusingComponentView) {
            return;
        }

        LogController.makeLog(TAG, String.valueOf(componentIndex), localLogPermission);
        removeFocusFromCurrentComponent();
        focusingComponentIndex = componentIndex;
        focusingComponentView = selectedComponent;
        focusingComponentView.setBackgroundColor(Color.LTGRAY);
        showComponentOption();
    }

    private void removeFocusFromCurrentComponent() {
        hideComponentOption();
        if (focusingComponentView != null) {
            focusingComponentView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        focusingComponentView = null;
        focusingComponentIndex = -1;
    }

    private boolean checkComponentIsFocused() {
        if (mComponentMenu.getVisibility() == View.VISIBLE)
            return true;
        else
            return false;
    }

    @Override
    public void scrollToNewComponent(int componentPosition) {
        mEditorRecyclerView.smoothScrollToPosition(componentPosition);
        mEditorRecyclerView.requestChildFocus(mEditorRecyclerView.getChildAt(componentPosition), getCurrentFocus());
    }
    ////////////////////////////////////////////////////////////////////////////////////




    //TODO : 구조 개선, 작명
    //doc List Activity => View로 => Presenter로 => Model 가공 => View로
    private void initDocument() {
        Intent intent = getIntent();
        if(intent.getExtras() == null){
            return;
        }
        //if document need to load, need to clear current document before load document
        mPresenter.clearCurrentDocument();

        Bundle bundle = intent.getExtras();
        DocumentParcelable documentParcelable = bundle.getParcelable(DOCUMENT_PARCEL);


        mTxtTitle.setText(documentParcelable.getTitle());
        requestDocumentBody(documentParcelable);
    }


    private void requestDocumentBody(DocumentParcelable documentParcelable) {
//        mPresenter.convertParcelToDocumentComponents(documentParcelable);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(inputMethodManager.isAcceptingText())
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            if (isFocused) {
                if (!focusedRange.contains((int)event.getRawX(), (int)event.getRawY())) {
                    focusedRange = null;
                    isFocused = false;
                    activity2ViewHolder.clearFocus();
                    //굳이 이럴 필요가 있나??
                    //다이렉트로 뿌리자.


                } else{
                    return false;
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
