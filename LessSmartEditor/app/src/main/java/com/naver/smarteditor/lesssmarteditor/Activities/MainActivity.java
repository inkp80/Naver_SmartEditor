package com.naver.smarteditor.lesssmarteditor.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.naver.smarteditor.lesssmarteditor.Database.DatabaseHelper;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.Objects.Comp;
import com.naver.smarteditor.lesssmarteditor.Objects.ImgComp;
import com.naver.smarteditor.lesssmarteditor.Objects.TextComponent;
import com.naver.smarteditor.lesssmarteditor.Objects.TxtComp;
import com.naver.smarteditor.lesssmarteditor.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mAddDocumentButton;


    Button mImage;ImageView image;
    Button mMap;

    final int REQ_CODE_SELECT_IMAGE=100;

    final String TAG = "MainActivity";
    boolean localLogPermission = true;

    private DatabaseHelper dbHelper;
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
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

        mMap = (Button) findViewById(R.id.button_map_access);
        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPlaceActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        mAddDocumentButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.LogController.makeLog(TAG, "item clicked", localLogPermission);
                Intent intent = new Intent(getBaseContext(), EditorActivity.class);
                startActivity(intent);
            }
        });

        ImgComp imgComp = new ImgComp();
        MyApplication.LogController.makeLog("compTest:img", String.valueOf(imgComp.getComponentType()), localLogPermission);

        Comp tmpComp = imgComp;
        MyApplication.LogController.makeLog("compTest:comp", String.valueOf(tmpComp.getComponentType()), localLogPermission);
        MyApplication.LogController.makeLog("compTest:comp2", String.valueOf(Comp.Type.IMAGE.ordinal()), localLogPermission);





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
}
