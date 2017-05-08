package com.example.naver.lec9.prac18.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.example.naver.lec9.R;
import com.example.naver.lec9.prac18.Fragments.AddFragment;
import com.example.naver.lec9.prac18.Fragments.ListFramgment;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class MainActivity extends AppCompatActivity {
    final int FOCUS_ON_ADD_FRAGMENT = 0;
    final int FOCUS_ON_LIST_FRAMGENT = 1;

    Fragment mAddItemFrament;
    Fragment mListItemFragment;

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
    }
}


