package com.naver.smarteditor.lesssmarteditor.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.naver.smarteditor.lesssmarteditor.Adapter.EditorComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.Database.DatabaseHelper;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.Dialogs.SelectComponentDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 11..
 */

public class EditorActivity extends AppCompatActivity{
    final String TAG = "Editor";
    boolean localLogPermission = true;

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

    List<Integer> mComponentList;

    EditorComponentAdapter mComponentAdpater;
    RecyclerView mEditorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_view);
        openDatabase();
        initDialog();

        mComponentList = new ArrayList<Integer>();

        mAddComponentButton = (Button) findViewById(R.id.editor_bt_addcomponent);
        mAddComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectComponentDialog.show();
            }
        });

        initRecyclerView();

    }



    public void initDialog(){

        txtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TEXT COMPONENT TO EDITOR
                addComponent(TEXT_MODE);
                mSelectComponentDialog.dismiss();
            }
        };

        imgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComponent(IMG_MODE);
                mSelectComponentDialog.dismiss();
            }
        };

        mapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComponent(MAP_MODE);
                mSelectComponentDialog.dismiss();
                Intent intent = new Intent(EditorActivity.this, SearchPlaceActivity.class);
            }
        };

        mSelectComponentDialog = new SelectComponentDialog(this, txtButtonListener, imgButtonListener, mapButtonListener);
    }

    public void initRecyclerView(){
        mComponentAdpater = new EditorComponentAdapter(this, mComponentList);
        mEditorRecyclerView = (RecyclerView) findViewById(R.id.editor_recyclerview);
        mEditorRecyclerView.setHasFixedSize(true);
        mEditorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditorRecyclerView.setAdapter(mComponentAdpater);
    }


    public void addComponent(int mode){
        MyApplication.LogController.makeLog(TAG, "AddComp mode : " + String.valueOf(mode), localLogPermission);
        mComponentList.add(mode);
        renewingAdapterData();
    }


    public void openDatabase(){
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }


    public void renewingAdapterData(){
        mComponentAdpater.setComponentList(mComponentList);
        mComponentAdpater.notifyDataSetChanged();
    }

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
