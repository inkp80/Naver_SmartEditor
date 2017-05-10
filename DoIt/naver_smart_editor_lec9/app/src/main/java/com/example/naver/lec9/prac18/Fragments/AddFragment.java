package com.example.naver.lec9.prac18.Fragments;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naver.lec9.R;
import com.example.naver.lec9.prac18.Objects.BookInfoBuilder;
import com.example.naver.lec9.prac18.Objects.BookItem;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class AddFragment extends Fragment {

    AddFragmentCallbacks mCallbacks;

    EditText mTitle;
    EditText mAuthor;
    EditText mDescription;

    Button mSaveBt;

    public void test(){

    }

    public interface AddFragmentCallbacks{
        void saveObjectToDB(BookItem item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.prac18_main_add_fragment, container, false);

        mTitle = (EditText) rootView.findViewById(R.id.et_title);
        mAuthor = (EditText) rootView.findViewById(R.id.et_author);
        mDescription = (EditText) rootView.findViewById(R.id.et_description);
        mSaveBt = (Button) rootView.findViewById(R.id.bt_save);

        mSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                String author = mAuthor.getText().toString();
                String description = mDescription.getText().toString();
                BookInfoBuilder bookInfoBuilder = new BookInfoBuilder();

                BookItem item = bookInfoBuilder
                        .setTitle(title)
                        .setAuthor(author)
                        .setDescriprion(description)
                        .setBookImg(R.drawable.effective_java)
                        .Build();


                mCallbacks.saveObjectToDB(new BookItem(title, author, description, R.drawable.effective_java));
            }
        });
        return rootView;
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddFragmentCallbacks) {
            mCallbacks = (AddFragmentCallbacks) activity;
        }
    }
}
