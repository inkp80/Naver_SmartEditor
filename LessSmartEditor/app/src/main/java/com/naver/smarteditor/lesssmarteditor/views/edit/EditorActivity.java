package com.naver.smarteditor.lesssmarteditor.views.edit;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

//import com.naver.smarteditor.lesssmarteditor.Activities.SearchPlaceActivity;
//import com.naver.smarteditor.lesssmarteditor.Adapter.EditorComponentAdapter;
//import com.naver.smarteditor.lesssmarteditor.Database.DatabaseHelper;
//import com.naver.smarteditor.lesssmarteditor.Dialogs.SelectComponentDialog;
//import com.naver.smarteditor.lesssmarteditor.MyApplication;
//import com.naver.smarteditor.lesssmarteditor.Objects.Comp;
//import com.naver.smarteditor.lesssmarteditor.Objects.ImgComp;
//import com.naver.smarteditor.lesssmarteditor.Objects.MapComp;
//import com.naver.smarteditor.lesssmarteditor.Objects.MapComponent;
//import com.naver.smarteditor.lesssmarteditor.Objects.PlaceItemPasser;
//import com.naver.smarteditor.lesssmarteditor.Objects.TxtComp;
import com.naver.smarteditor.lesssmarteditor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorActivity extends AppCompatActivity{
    @BindView(R.id.editor_bt_addcomponent)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_view);
        ButterKnife.bind(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add text component;
            }
        });
    }



    /*
    final String TAG = "Editor";
    boolean localLogPermission = true;


    final int REQ_CODE_SELECT_IMAGE = 100;
    final int REQ_CODE_SELECT_MAP = 200;
    Object dataMessenger;

    final int TEXT_MODE = 0;
    final int IMG_MODE = 1;
    final int MAP_MODE = 2;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;


    private Button mAddComponentButton;
    private SelectComponentDialog mSelectComponentDialog;


    View.OnClickListener txtButtonListener;
    View.OnClickListener imgButtonListener;
    View.OnClickListener mapButtonListener;

//    List<Component> mComponentList;
    List<Comp> mCompList;

    EditorComponentAdapter mComponentAdpater;
    RecyclerView mEditorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_view);
        openDatabase();
        initDialog();
        dataMessenger = new Object();


//        mComponentList = new ArrayList<Component>();
        mCompList = new ArrayList<Comp>();

        mAddComponentButton = (Button) findViewById(R.id.editor_bt_addcomponent);
        mAddComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectComponentDialog.show();
            }
        });

        initRecyclerView();

    }



    public void initRecyclerView(){
        mComponentAdpater = new EditorComponentAdapter(this, mCompList);
        mEditorRecyclerView = (RecyclerView) findViewById(R.id.editor_recyclerview);
        mEditorRecyclerView.setHasFixedSize(true);
        mEditorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditorRecyclerView.setAdapter(mComponentAdpater);
        mEditorRecyclerView.setItemViewCacheSize(100);
    }

    public void initDialog(){
        //TODO: rename & restructuring

        txtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TEXT COMPONENT TO EDITOR
                addCompToList(createTextComponent());
                mSelectComponentDialog.dismiss();
            }
        };

        imgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompToList(createImgCompoenent());
                mSelectComponentDialog.dismiss();
            }
        };

        mapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCompToList(createMapComponent());
                mSelectComponentDialog.dismiss();
            }
        };

        mSelectComponentDialog = new SelectComponentDialog(this, txtButtonListener, imgButtonListener, mapButtonListener);
    }

    public TxtComp createTextComponent(){
        TxtComp txtComp = new TxtComp();
        return txtComp;
    }

    public ImgComp createImgCompoenent(){
        ImgComp imgComp = new ImgComp();
        getImgSrcFromDevice();
        String imgSrc = dataMessenger.toString();
        imgComp.setComponentData(imgSrc);
        return imgComp;
    }

    public MapComp createMapComponent(){
        MapComp mapComp = new MapComp();
        getMapSrcFromServer();
        mapComp.setComponentData(dataMessenger);
        return mapComp;
    }

    public void addCompToList(Comp comp){
        MyApplication.LogController.makeLog(TAG, String.valueOf(comp.getComponentType()), localLogPermission);
        mCompList.add(comp);
        renewingAdapterData();
    }

    public void getImgSrcFromDevice(){
        //TODO: 정말 좋지 않은 구조인듯
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
//        return dataMessenger.toString();
        //TODO: 이 부분 개선 필요함
    }

    public void getMapSrcFromServer(){
        Intent intent = new Intent(this, SearchPlaceActivity.class);
        startActivity(intent);

    }

    public MapComponent parsePalceltoObject(PlaceItemPasser data){
        MapComponent mapComponent = new MapComponent(data.getPlaceName(), data.getPlaceAddress(), data.getPlaceCoords(), data.getPlaceUri());
        return mapComponent;
    }



    public void openDatabase(){
        //TODO: default CRUD
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }


    public void renewingAdapterData(){
        mComponentAdpater.setComponentList(mCompList);
        mComponentAdpater.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: handling data
        if(requestCode == REQ_CODE_SELECT_IMAGE) {
            if(resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImgUri = data.getData();
                    MyApplication.LogController.makeLog(TAG, selectedImgUri.toString(), localLogPermission);


//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
                    dataMessenger = selectedImgUri.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == REQ_CODE_SELECT_MAP){
            if(resultCode == RESULT_OK){
                try{
                    Bundle bundle = getIntent().getExtras();

                    PlaceItemPasser passer = (PlaceItemPasser) bundle.getParcelable("passer");
                    dataMessenger = parsePalceltoObject(passer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    */
//    public void saveToSQLite(){
//        try {
//            Log.d("from AddFrag value, ","title:"+item.getTitle());
//
//            db.execSQL("insert into " + TABLE_NAME + "(title, author, description, img_resID) " +
//                    "values ('"
//                    + item.getTitle() + "', '"
//                    + item.getAuthor() + "', '"
//                    + item.getDescription() + "', "
//                    + item.getBookImg() + ");");
//        } catch (Exception e){
//            Log.e(TAG, "Error : ", e);
//        }
//        Log.d("Database", "INSERT SUCCESS");
//    }
//
//    Select from database
//    Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
}
