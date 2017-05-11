package com.naver.smarteditor.lesssmarteditor.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.naver.smarteditor.lesssmarteditor.Database.DatabaseHelper;
import com.naver.smarteditor.lesssmarteditor.R;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mAddDocumentButton;

    final String TAG = "Database";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        mAddDocumentButton = (FloatingActionButton) findViewById(R.id.fab);
        mAddDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditorActivity.class);
                startActivity(intent);
            }
        });
    }
}
