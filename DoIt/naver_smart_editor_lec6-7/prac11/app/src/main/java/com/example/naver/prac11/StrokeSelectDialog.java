package com.example.naver.prac11;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by NAVER on 2017. 4. 27..
 */

public class StrokeSelectDialog extends Activity {

    public static OnStrokeSelectedListener listener;

    GridView mGridView;
    Button mCloseButton;
    StrokeDataAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        this.setTitle("선 선택");

        mGridView = (GridView) findViewById(R.id.dialog_gridview);
        mCloseButton = (Button) findViewById(R.id.dialog_bt_close);

        mGridView.setColumnWidth(14);
        mGridView.setBackgroundColor(Color.GRAY);
        mGridView.setVerticalSpacing(4);
        mGridView.setHorizontalSpacing(4);

        mAdapter = new StrokeDataAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(mAdapter.getNumColumns());


        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

class StrokeDataAdapter extends BaseAdapter{

    Context mContext;
    int rowCount;
    int columnCount;

    public StrokeDataAdapter(Context context){
        super();
        mContext = context;
        rowCount = 3;
        columnCount = 7;
    }


    public static final int [] pens = new int[] {
            1,2,3,4,5,
            6,7,8,9,10,
            11,13,15,17,20
    };


    @Override
    public int getCount() {
        return pens.length;
    }

    @Override
    public Object getItem(int position) {
        return pens[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int rowIdx = position / rowCount;
        int columnIdx = position % rowCount;

        GridView.LayoutParams params = new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        int areaWidth = 10;
        int areaHeight = 20;

        Bitmap mBitmap = Bitmap.createBitmap(areaWidth, areaHeight, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mCanvas.drawRect(0, 0, areaWidth, areaHeight, mPaint);
        //배경 드로잉

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth((float)pens[position]);
        mCanvas.drawLine(0, areaHeight/2, areaWidth-1,  areaHeight/2, mPaint);

        BitmapDrawable mBitmapDrawable = new BitmapDrawable(mContext.getResources(), mBitmap);


        Button item = new Button(mContext);

        item.setText("");
        item.setTag(pens[position]);
        item.setBackgroundDrawable(mBitmapDrawable);
        item.setHeight(120);
        item.setPadding(4,4,4,4);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StrokeSelectDialog.listener != null){
                    StrokeSelectDialog.listener.onStrokeSelected(((Integer)v.getTag()).intValue());
                    ((StrokeSelectDialog)mContext).finish();
                }
            }
        });

        return item;
    }

    public int getNumColumns(){
        return columnCount;
    }
}
