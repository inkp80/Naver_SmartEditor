package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ImgComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.MapComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentAdapter extends RecyclerView.Adapter<BasicViewHolder> implements EditComponentAdapterContract.Model, EditComponentAdapterContract.View{

    private final String TAG = "EditComponentAdapter";
    private boolean localLogPermission = true;

    private final int TEXT_COMPONENT = 0;
    private final int IMG_COMPONENT = 1;
    private final int MAP_COMPONENT = 2;

    private RequestManager requestManager;

    private Context mContext;
    private OnTextChangeListener onTextChangeListener;
    private ArrayList<BaseComponent> mComponents;

    public EditComponentAdapter(Context context){
        this.mContext = context;
        mComponents = new ArrayList<>();
        requestManager = Glide.with(context);
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(viewType == TEXT_COMPONENT){
            EditText et = new EditText(mContext);
            et.setLayoutParams(lp);
            TextComponentViewHolder textComponentViewHolder = new TextComponentViewHolder(et, onTextChangeListener);
            return textComponentViewHolder;
        }

        switch (viewType){
            case TEXT_COMPONENT :
                EditText et = new EditText(mContext);
                et.setLayoutParams(lp);
                TextComponentViewHolder textComponentViewHolder = new TextComponentViewHolder(et, onTextChangeListener);
                return textComponentViewHolder;
            case IMG_COMPONENT :
                ImageView img = new ImageView(mContext);
                img.setLayoutParams(lp);
                ImgComponentViewHolder imgComponentViewHolder = new ImgComponentViewHolder(img);
                return imgComponentViewHolder;
            case MAP_COMPONENT :
                TextView text = new TextView(mContext);
                ImageView mapImg = new ImageView(mContext);
                text.setLayoutParams(lp);
                mapImg.setLayoutParams(lp);

                LinearLayout linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(llp);

                linearLayout.addView(mapImg);
                linearLayout.addView(text);

                MapComponentViewHolder mapComponentViewHolder = new MapComponentViewHolder(linearLayout);
                return mapComponentViewHolder;

            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {

        int thisComponentType = mComponents.get(position).getComponentType().getTypeValue();

        switch (thisComponentType){
            case TEXT_COMPONENT :
                TextComponent thisTextComponent = (TextComponent) mComponents.get(position);

                TextComponentViewHolder textComponentViewHolder = (TextComponentViewHolder) holder;
                textComponentViewHolder.removeWatcher();
                textComponentViewHolder.setText(thisTextComponent.getText());
                textComponentViewHolder.onBind(position);
                break;
            case IMG_COMPONENT :
                MyApplication.LogController.makeLog(TAG, "IMG_Comp, Binding", localLogPermission);
                ImgComponent thisImgComponent = (ImgComponent) mComponents.get(position);
                ImgComponentViewHolder imgComponentViewHolder = (ImgComponentViewHolder) holder;
                requestManager.load(thisImgComponent.getImgUri()).into(imgComponentViewHolder.getImageView());
                break;
            case MAP_COMPONENT :
                MapComponent thisMapComponent = (MapComponent) mComponents.get(position);
                MapComponentViewHolder mapComponentViewHolder = (MapComponentViewHolder) holder;
                mapComponentViewHolder.getTextView().setText(thisMapComponent.getPlaceName());
                requestManager.load(thisMapComponent.getPlaceMapImgUri()).into(mapComponentViewHolder.getImageView());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mComponents.size();
    }

    @Override
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    @Override
    public void notifyAdapter() {
        MyApplication.LogController.makeLog(TAG, "notifyadapter", localLogPermission);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        BaseComponent thisComponent = mComponents.get(position);
        return thisComponent.getComponentType().getTypeValue();
    }

    @Override
    public void setComponent(ArrayList<BaseComponent> components) {
        mComponents = new ArrayList<>(components);
    }

    @Override
    public void editComponent(CharSequence s, int position) {
    }

    @Override
    public BaseComponent getComponent(int position) {
        return mComponents.get(position);
    }

    @Override
    public void clearComponent() {
        mComponents.clear();
    }
}
