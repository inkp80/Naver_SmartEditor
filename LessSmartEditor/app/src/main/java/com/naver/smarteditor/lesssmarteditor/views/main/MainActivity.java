package com.naver.smarteditor.lesssmarteditor.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.naver.smarteditor.lesssmarteditor.MyApplication;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorContract;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.EditorDbHelper;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mAddDocumentButton;


    Button mImage;ImageView image;
    final int REQ_CODE_SELECT_IMAGE=100;

    final String TAG = "MainActivity";
    boolean localLogPermission = true;

    private EditorDbHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.img1);
        Glide.with(this).load("http://www.romand.co.kr/file_data/romand/2017/05/11/5d28daee1e35059fe8e24b26f5f89012.jpg").into(image);
        mImage = (Button) findViewById(R.id.button_img_access);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });


        dbHelper = new EditorDbHelper(this);
        db = dbHelper.getWritableDatabase();

        mAddDocumentButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecord();
                getRecords();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


//        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    Uri selectedImg = data.getData();
                    MyApplication.LogController.makeLog(TAG, selectedImg.toString(), localLogPermission);

                    Glide.with(this).load(selectedImg).into(image);

//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }

    public void insertRecord(){

        String insert = "INSERT INTO "+ EditorContract.ComponentEntry.TABLE_NAME + "(" + EditorContract.ComponentEntry.COLUMN_TITLE + "," +EditorContract.ComponentEntry.COLUMN_TIMESTAMP+
                ", "+EditorContract.ComponentEntry.COLUNM_DESCRIPTION_JSON + ") values ('document1', 170524, 'JSON');";
        db.execSQL(insert);
    }

    public void getRecords(){
        Cursor cursor = db.rawQuery("select * from " + EditorContract.ComponentEntry.TABLE_NAME, null);
        cursor.moveToNext();
        MyApplication.LogController.makeLog(TAG, cursor.getString(1), localLogPermission);
    }
}
