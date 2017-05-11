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
        openDatabase();
        initDialogSelectionListener();

        mComponentList = new ArrayList<Integer>();

        mAddComponentButton = (Button) findViewById(R.id.editor_bt_addcomponent);
        mAddComponentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.show();
            }
        });

        initRecyclerView();

    }

    public void initDialogSelectionListener(){
        txtButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditTextView();
                mCustomDialog.dismiss();
            }
        };

        imgButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.dismiss();
            }
        };

        mapButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.dismiss();
            }
        };

        mCustomDialog = new CustomDialog(this, txtButtonListener, imgButtonListener, mapButtonListener);
    }

    public void addEditTextView(){
        MyApplication.LogController.makeLog(TAG, "AddEditText", localLogPermission);
        mComponentList.add(TEXT_MODE);
        mComponentAdpater.setComponentList(mComponentList);
        mComponentAdpater.notifyDataSetChanged();
        mEditorRecyclerView.setAdapter(mComponentAdpater);
    }

    public void initRecyclerView(){
        mComponentAdpater = new EditorComponentAdapter(this, mComponentList);
        mEditorRecyclerView = (RecyclerView) findViewById(R.id.editor_recyclerview);
        mEditorRecyclerView.setHasFixedSize(true);
        mEditorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditorRecyclerView.setAdapter(mComponentAdpater);
    }

    public void openDatabase(){
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
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
