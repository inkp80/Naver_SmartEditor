package com.example.naver.lec9.prac18.Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.naver.lec9.R;
import com.example.naver.lec9.prac18.Fragments.AddFragment;
import com.example.naver.lec9.prac18.Fragments.ListFramgment;
import com.example.naver.lec9.prac18.Objects.BookItem;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class MainActivity extends AppCompatActivity implements AddFragment.AddFragmentCallbacks, ListFramgment.ListFragmentCallbacks{
    final String TAG = "Database";

    private static String DATABASE_NAME = "BOOK";
    private static String TABLE_NAME = "book_info";
    private static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    final int FOCUS_ON_ADD_FRAGMENT = 0;
    final int FOCUS_ON_LIST_FRAMGENT = 1;

    public Fragment mAddItemFrament;
    public Fragment mListItemFragment;

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prac18_main_activity);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("입력"));
        mTabLayout.addTab(mTabLayout.newTab().setText("조회"));
        //mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mAddItemFrament = new AddFragment();
        mListItemFragment = new ListFramgment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container, mAddItemFrament).commit();



        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //do something in here

                int posisition = tab.getPosition();

                Fragment selectedFragment = null;
                switch (posisition){
                    case FOCUS_ON_ADD_FRAGMENT:
                        selectedFragment = mAddItemFrament;
                        break;
                    case FOCUS_ON_LIST_FRAMGENT:
                        selectedFragment = mListItemFragment;
                        break;
                    default:
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        openDatabase();
    }

    public void openDatabase(){

        try {
            dbHelper = new DatabaseHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.e("Database", "Error occur during open Database", e);
        }
//        return true;
    }


    //TODO : implement Create, Read
    //TODO : Fragment method를 어떻게 호출해야하지 Interface를 통해.. 한다는데.. 그럼 내부 변수 접근은.



    @Override
    public void saveObjectToDB(BookItem item) {
        try {
            Log.d("from AddFrag value, ","title:"+item.getTitle());

            db.execSQL("insert into " + TABLE_NAME + "(title, author, description, img_resID) " +
                    "values ('"
                    + item.getTitle() + "', '"
                    + item.getAuthor() + "', '"
                    + item.getDescription() + "', "
                    + item.getBookImg() + ");");
        } catch (Exception e){
            Log.e(TAG, "Error : ", e);
        }
        Log.d("Database", "INSERT SUCCESS");

//        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
    }

    @Override
    public Cursor passListItemToAdapter(Cursor cursor) {
        Cursor cursor1 = db.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToNext();
        Log.d("Database", "read process:"+"count = "+cursor.getCount());

        for(int i=0; i<cursor.getCount(); i++){
            Log.d("CURSOR", cursor.getString(1).toString());
            cursor.moveToNext();
        }
        return cursor;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG,"creating table [" + TABLE_NAME + "].");

            try {
                String DROP_SQL = "drop table if exists " + TABLE_NAME;
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "create table " + TABLE_NAME + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " title text, "
                    + " author text, "
                    + " description text, "
                    + " img_resID long)";

            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

        }

        public void onOpen(SQLiteDatabase db) {
            Log.d(TAG, "opened database [" + DATABASE_NAME + "].");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".");

        }
    }
}


