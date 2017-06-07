package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.EditComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TitleComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchEventListener;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentTouchItemHelperCallback;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItemParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TitleComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.DocumentRepository;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.listener.TextCursorListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.dialog.SelectComponentDialog;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditContract;
import com.naver.smarteditor.lesssmarteditor.views.edit.presenter.EditPresenter;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.ClearCachesTask;
import com.naver.smarteditor.lesssmarteditor.views.edit.utils.TitleFilter;
import com.naver.smarteditor.lesssmarteditor.views.doclist.DocumentListActivity;
import com.naver.smarteditor.lesssmarteditor.views.map.SearchPlaceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.DOCUMENT_ID;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.MAPINFO_PARCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.NO_TITLE_IMG;
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


    @BindView(R.id.editor_img_map_comp_menu)
    LinearLayout mImgMapComponentMenu;
    @BindView(R.id.editor_bt_delete)
    Button mBtDeleteComponent;
    @BindView(R.id.editor_bt_cancel)
    Button mBtCancelMenu;

    @BindView(R.id.editor_text_span_menu)
    LinearLayout mTextSpanMenu;
    @BindView(R.id.editor_bt_bold)
    Button mBtBold;
    @BindView(R.id.editor_bt_italic)
    Button mBtItalic;
    @BindView(R.id.editor_bt_underline)
    Button mBtUnderline;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        mAdapter = new EditComponentAdapter(this);
        mAdapter.setTextCursorChangeListener(new TextCursorListener() {
            @Override
            public void showSelectedTypes(int styleType) {
                checkSelectedType(styleType);
            }
        });

        initPresenter();

        initRecyclerView();

        initEditorMenu();

        initSelectComponentDialog();

        setTitleLengthLimit(30);

        initComponentOption();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                    int documentId = data.getIntExtra(DOCUMENT_ID, -1);
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
        mEditorRecyclerView.addOnItemTouchListener(new RecyclerViewTouchItemHelper());

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

        mPresenter.addComponentToDocument(new TitleComponent("", NO_TITLE_IMG));
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
                                //TODO : document ID init
                                mPresenter.clearCurrentDocument();
                                mPresenter.addComponentToDocument(new TitleComponent("", NO_TITLE_IMG));

                                if (currentFocusingViewHolder != null) {
                                    currentFocusingViewHolder.dismissHighlight();
                                    currentFocusingViewHolder = null;
                                    hideComponentOption();
                                }
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
            if (currentFocusingViewHolder != null) {
                currentFocusingViewHolder.dismissHighlight();
                currentFocusingViewHolder = null;
                hideComponentOption();
            }
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
    ////////////////////////////////////////////////////////////////////////////////////


    //componentOption : Sliding menu which showing in View when DocumentComponent selected by long click
    private void initComponentOption() {
        mBtCancelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFocusingViewHolder != null) {
                    currentFocusingViewHolder.dismissHighlight();
                    currentFocusingViewHolder = null;
                    hideComponentOption();
                }
            }
        });

        mBtDeleteComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFocusingViewHolder != null) {
                    //TODO : delete
                    mPresenter.deleteComponentFromDocument(currentFucusingPosition);

                    currentFocusingViewHolder.dismissHighlight();
                    currentFocusingViewHolder = null;
                    hideComponentOption();
                }
            }
        });


        mBtBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEditText.editSpannable(StyleSpan.class, Typeface.BOLD);
            }
        });

        mBtItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEditText.editSpannable(StyleSpan.class, Typeface.ITALIC);
            }
        });

        mBtUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEditText.editSpannable(UnderlineSpan.class, 3);
            }
        });
    }

    private void showComponentOption(BaseComponent.Type type) {
        if (type == BaseComponent.Type.IMG || type == BaseComponent.Type.MAP) {
            mImgMapComponentMenu.setVisibility(View.VISIBLE);
        } else if (type == BaseComponent.Type.TEXT || type == BaseComponent.Type.TITLE) {
            if (type == BaseComponent.Type.TEXT)
                currentEditText = ((TextComponentViewHolder) currentFocusingViewHolder).getEditText();
            else if (type == BaseComponent.Type.TITLE)
                currentEditText = ((TitleComponentViewHolder) currentFocusingViewHolder).getEditText();

            mTextSpanMenu.setVisibility(View.VISIBLE);
        }
        mBtSaveButton.setVisibility(GONE);
    }

    private void hideComponentOption() {
        mImgMapComponentMenu.setVisibility(GONE);
        mTextSpanMenu.setVisibility(GONE);
        mBtSaveButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void scrollToNewComponent(int componentPosition) {
        mEditorRecyclerView.smoothScrollToPosition(componentPosition);
    }
    ////////////////////////////////////////////////////////////////////////////////////


    private ComponentViewHolder currentFocusingViewHolder;
    private int currentFucusingPosition;
    private SmartEditText currentEditText;


    private class RecyclerViewTouchItemHelper implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View v = mEditorRecyclerView.findChildViewUnder(event.getX(), event.getY());
                if (v == null) {
                    if (currentFocusingViewHolder != null) {
                        currentFocusingViewHolder.dismissHighlight();
                        currentFocusingViewHolder = null;
                    }
                    if (inputMethodManager.isAcceptingText()) {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                    hideComponentOption();
                    return false;
                }

                ComponentViewHolder temp = (ComponentViewHolder) mEditorRecyclerView.getChildViewHolder(v);

                if (temp == currentFocusingViewHolder) {
                    return false;
                }
                if (inputMethodManager.isAcceptingText()) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                if (currentFocusingViewHolder != null) {
                    currentFocusingViewHolder.dismissHighlight();
                    currentFocusingViewHolder = null;
                    hideComponentOption();
                    return false;
                }

                currentFocusingViewHolder = temp;
                currentFocusingViewHolder.showHighlight();


                currentFucusingPosition = mEditorRecyclerView.getChildAdapterPosition(v);
                BaseComponent.Type type = BaseComponent.getType(mAdapter.getItemViewType(currentFucusingPosition));
                showComponentOption(type);
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public void checkSelectedType(int styleType) {
        mBtItalic.setText("I(X)");
        mBtBold.setText("B(X)");

        for (int i = 0; i < 3; i++) {
            int isChecked = styleType & (1 << i);
            if (isChecked / 2 == 1) {
                mBtBold.setText("B(O)");
                continue;

            } else if (isChecked / 2 == 2) {
                mBtItalic.setText("I(O)");
                continue;
            }
        }
    }
}
