package com.example.naver.lec9.DB;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naver.lec9.R;

import static java.sql.DriverManager.println;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class MainActivity extends AppCompatActivity {


    boolean dataBaseCreated = false;
    boolean tableCreated = false;

    TextView mTvStatus;

    SQLiteDatabase db;
    String databaseName = "database";
    EditText mEditDatabaseName;
    Button mButtonCreateDatabase;


    LinearLayout mTableCreateLayout;
    EditText mEditTableName;
    Button mButtonCreateTable;
    String TableName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_act);

        mTvStatus = (TextView) findViewById(R.id.tv_status);

        mEditDatabaseName = (EditText) findViewById(R.id.et_database_name);
        databaseName = mEditDatabaseName.getText().toString();
        mButtonCreateDatabase = (Button) findViewById(R.id.bt_create_db);

        mTableCreateLayout = (LinearLayout) findViewById(R.id.linear_table);
        mEditTableName = (EditText) findViewById(R.id.et_table_name);
        mButtonCreateTable = (Button) findViewById(R.id.bt_create_talbe);


        mButtonCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseName = mEditDatabaseName.getText().toString();
                Log.d("###", databaseName);
                createDatabase(databaseName);
            }
        });

        mButtonCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableName = mEditTableName.getText().toString();
                createTable(TableName);

                int count = insertRecord(TableName);

                println(count+"records inserted");
            }
        });
    }




    public void createDatabase(String name){
        println("creating database [" + name + "].");

        try {
            db = openOrCreateDatabase(
                    name,
                    MODE_PRIVATE,
                    null);

            dataBaseCreated = true;
            println("database is created.");
        } catch(Exception ex) {
            ex.printStackTrace();
            println("database is not created.");
        }

    }

    public void createTable(String name){
        println("creating table [" + name + "].");

        Log.d("db", db.toString());
        db.execSQL("create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);" );

        tableCreated = true;
    }

    public int insertRecord(String name){
        println("inserting records into table " + name + ".");

        int count = 3;
        db.execSQL( "insert into " + name + "(name, age, phone) values ('John', 20, '010-7788-1234');" );
        db.execSQL( "insert into " + name + "(name, age, phone) values ('Mike', 35, '010-8888-1111');" );
        db.execSQL( "insert into " + name + "(name, age, phone) values ('Sean', 26, '010-6677-4321');" );

        return count;
    }

    private int insertRecordParam(String name) {
        println("inserting records using parameters.");

        int count = 1;
        ContentValues recordValues = new ContentValues();

        recordValues.put("name", "Rice");
        recordValues.put("age", 43);
        recordValues.put("phone", "010-3322-9811");
        int rowPosition = (int) db.insert(name, null, recordValues);

        return count;
    }

    /**
     * update records using parameters
     */
    private int updateRecordParam(String name) {
        println("updating records using parameters.");

        ContentValues recordValues = new ContentValues();
        recordValues.put("age", 43);
        String[] whereArgs = {"Rice"};

        int rowAffected = db.update(name,
                recordValues,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

    /**
     * delete records using parameters
     */
    private int deleteRecordParam(String name) {
        println("deleting records using parameters.");

        String[] whereArgs = {"Rice"};

        int rowAffected = db.delete(name,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

    private void println(String msg) {
        Log.d("MainActivity", msg);
        mTvStatus.append("\n" + msg);

    }
}
