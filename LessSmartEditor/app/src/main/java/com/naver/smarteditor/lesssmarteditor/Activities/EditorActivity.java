package com.naver.smarteditor.lesssmarteditor.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.Adapter.EditorComponentAdapter;
import com.naver.smarteditor.lesssmarteditor.Database.DatabaseHelper;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.Views.CustomDialog;

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
    private CustomDialog mCustomDialog;

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

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();


        mComponentList = new ArrayList<Integer>();

        initListener();
        mCustomDialog = new CustomDialog(this, txtButtonListener, imgButtonListener, mapButtonListener);

        mAddComponentButton = (Button) findViewById(R.id.editor_bt_addcomponent);
        mAddComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.show();
            }
        });

        mComponentAdpater = new EditorComponentAdapter(this, mComponentList);
        mEditorRecyclerView = (RecyclerView) findViewById(R.id.editor_recyclerview);
        mEditorRecyclerView.setHasFixedSize(true);
        mEditorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditorRecyclerView.setAdapter(mComponentAdpater);
    }

    public void initListener(){
        txtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditorActivity.this, "TXT", Toast.LENGTH_SHORT).show();
                addEditTextView();
                mCustomDialog.dismiss();
            }
        };

        imgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditorActivity.this, "IMG", Toast.LENGTH_SHORT).show();
                mCustomDialog.dismiss();
            }
        };

        mapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditorActivity.this, "MAP", Toast.LENGTH_SHORT).show();
                mCustomDialog.dismiss();
                finish();
            }
        };
    }

    public void addEditTextView(){
        MyApplication.LogController.makeLog(TAG, "make list", localLogPermission);
        mComponentList.add(TEXT_MODE);
        mComponentAdpater.setComponentList(mComponentList);
        mComponentAdpater.notifyDataSetChanged();
        mEditorRecyclerView.setAdapter(mComponentAdpater);
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
