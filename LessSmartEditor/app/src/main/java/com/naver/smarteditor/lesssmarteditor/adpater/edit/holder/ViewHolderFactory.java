package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.SmartEditText;

/**
 * Created by NAVER on 2017. 5. 31..
 */

public class ViewHolderFactory {
    private Context mContext;
    private RequestManager requestManager;
    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    public ViewHolderFactory(Context context, RequestManager requestManager,  OnEditTextComponentChangeListener onEditTextComponentChangeListener){
        this.mContext = context;
        this.requestManager = requestManager;
        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;
    }

    public ComponentViewHolder createViewHolder(BaseComponent.Type type) {

        switch (type) {
            case TEXT:
                return new TextComponentViewHolder(createItemView(type), onEditTextComponentChangeListener);
            case IMG:
                return new ImgComponentViewHolder(createItemView(type), requestManager);
            case MAP:
                return new MapComponentViewHolder(createItemView(type), requestManager);
            case TITLE:
                return new TitleComponentViewHolder(createItemView(type), onEditTextComponentChangeListener);
            default:
                LogController.makeLog("ViewHolderFactroy", "INVALID TYPE", true);
                return null;
        }
    }

    private View createItemView(BaseComponent.Type type){

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (type){
            case TEXT:
                SmartEditText editTextItemView = new SmartEditText(mContext);
                editTextItemView.setLayoutParams(lp);
                return editTextItemView;
            case IMG:
                ImageView imageItemView = new ImageView(mContext);
                imageItemView.setLayoutParams(lp);
                return imageItemView;
            case MAP:
                TextView placeName = new TextView(mContext);
                ImageView mapImg = new ImageView(mContext);
                placeName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                LinearLayout mapItemView = new LinearLayout(mContext);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.gravity = Gravity.CENTER;
                placeName.setLayoutParams(lp);
                placeName.setGravity(Gravity.CENTER_HORIZONTAL);
                mapImg.setLayoutParams(lp);

                mapItemView.setOrientation(LinearLayout.VERTICAL);
                mapItemView.setLayoutParams(llp);
                mapItemView.addView(mapImg);
                mapItemView.addView(placeName);
                return mapItemView;
            case TITLE:
                SmartEditText editTextTitleItemView = new SmartEditText(mContext);

                editTextTitleItemView.setLayoutParams(lp);
                return editTextTitleItemView;
            default:
                return null;
        }

    }

}
