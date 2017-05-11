package com.example.naver.prac11;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by NAVER on 2017. 4. 26..
 */

public class ColorSelectDialog extends Activity {

    public static OnColorSelectedListener listener;

    GridView mGridView;
    Button mCloseButton;
    ColorDataAdapter mColorDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        mGridView = (GridView) findViewById(R.id.dialog_gridview);
        mCloseButton = (Button) findViewById(R.id.dialog_bt_close);

        mGridView.setColumnWidth(14);
        mGridView.setBackgroundColor(Color.GRAY);
        mGridView.setVerticalSpacing(4);
        mGridView.setHorizontalSpacing(4);

        mColorDataAdapter = new ColorDataAdapter(this);
        mGridView.setAdapter(mColorDataAdapter);
        mGridView.setNumColumns(mColorDataAdapter.getNumColumns());


        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}


class ColorDataAdapter extends BaseAdapter{
    int rowCount;
    int columnCount;
    Context mContext;

    public ColorDataAdapter(Context context){
        super();

        mContext = context;
        rowCount = 3;
        columnCount = 7;
    }


    public static final int [] colors = new int[] {
            0xff000000,0xff00007f,0xff0000ff,0xff007f00,0xff007f7f,0xff00ff00,0xff00ff7f,
            0xff00ffff,0xff7f007f,0xff7f00ff,0xff7f7f00,0xff7f7f7f,0xffff0000,0xffff007f,
            0xffff00ff,0xffff7f00,0xffff7f7f,0xffff7fff,0xffffff00,0xffffff7f,0xffffffff
    };

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int rowIndex = position / rowCount;
        int columnIndex = position % rowCount;

        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT,
                GridView.LayoutParams.MATCH_PARENT);

        Button item = new Button(mContext);
        item.setText("");
        item.setLayoutParams(params);
        item.setPadding(4,4,4,4);
        item.setHeight(120);
        item.setBackgroundColor(colors[position]);
        item.setTag(colors[position]);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????????
                if(ColorSelectDialog.listener != null){
                    ColorSelectDialog.listener.onColorSelected(((Integer)v.getTag()).intValue());
                }
                ((ColorSelectDialog)mContext).finish();
            }
        });

        return item;
    }
    public int getNumColumns() {
        return columnCount;
    }
}
