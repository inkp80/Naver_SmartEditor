package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ImgComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.MapComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.ImgComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements EditComponentAdapterContract.Model, EditComponentAdapterContract.View,
        OnComponentLongClickListener {

    private final String TAG = "EditComponentAdapter";
    private boolean localLogPermission = true;

    private RequestManager requestManager;

    private Context mContext;
    private OnTextChangeListener onTextChangeListener;
    private List<BaseComponent> mComponents;

    private EditText currentFocus;

    private OnComponentLongClickListener onComponentLongClickListener;


    public EditComponentAdapter(Context context) {
        this.mContext = context;
        mComponents = new ArrayList<>();
        requestManager = Glide.with(context);
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        BaseComponent.TypE ViewHolderType = BaseComponent.getType(viewType);


        switch (ViewHolderType) {
            case TEXT:
                return createTextComponentViewholder(lp);
            case IMG:
                return createImageViewholder(lp);
            case MAP:
                return createMapViewHolder(lp);
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {

        BaseComponent.TypE thisComponentType = mComponents.get(position).getComponentType();
        holder.setDataPositionOnAdapter(position);
        holder.setOnComponentLongClickListener(this);

        switch (thisComponentType) {
            case TEXT:
                TextComponent thisTextComponent = (TextComponent) mComponents.get(position);
                TextComponentViewHolder textComponentViewHolder = (TextComponentViewHolder) holder;
                textComponentViewHolder.removeWatcher();
                textComponentViewHolder.setText(thisTextComponent.getText());
                textComponentViewHolder.onBind(position);
                break;
            case IMG:
                ImgComponent thisImgComponent = (ImgComponent) mComponents.get(position);
                ImgComponentViewHolder imgComponentViewHolder = (ImgComponentViewHolder) holder;
                requestManager.load(thisImgComponent.getImgUri())
                        .override(600, 600)
                        .into(imgComponentViewHolder.getImageView());
                break;
            case MAP:
                MapComponent thisMapComponent = (MapComponent) mComponents.get(position);
                MapComponentViewHolder mapComponentViewHolder = (MapComponentViewHolder) holder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mapComponentViewHolder.getTextView().setText(Html.fromHtml("<b>" + thisMapComponent.getPlaceName() + "</b>", Html.FROM_HTML_MODE_COMPACT));
                    mapComponentViewHolder.getTextView().append("\n" + thisMapComponent.getPlaceAddress());
                } else {
                    mapComponentViewHolder.getTextView().setText(Html.fromHtml("<b>" + thisMapComponent.getPlaceName() + "</b>"));
                    mapComponentViewHolder.getTextView().append("\n" + thisMapComponent.getPlaceAddress());
                }
                requestManager.load(thisMapComponent.getPlaceMapImgUri())
                        .override(600, 600)
                        .into(mapComponentViewHolder.getImageView());
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


    //AdapterModel & View
    @Override
    public void setComponent(List<BaseComponent> components) {
        mComponents = new ArrayList<>(components);
    }

    @Override
    public void clearComponent() {
        mComponents.clear();
    }

    @Override
    public void swapComponent(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void setFocus(){
        //....
    }

    //Create ViewHolders
    private ImgComponentViewHolder createImageViewholder(RecyclerView.LayoutParams lp){
        ImageView img = new ImageView(mContext);
        img.setLayoutParams(lp);
        ImgComponentViewHolder imgComponentViewHolder = new ImgComponentViewHolder(img);
        return imgComponentViewHolder;
    }

    private MapComponentViewHolder createMapViewHolder(RecyclerView.LayoutParams lp){
        TextView placeName = new TextView(mContext);
        ImageView mapImg = new ImageView(mContext);
        placeName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.gravity = Gravity.CENTER;
        placeName.setLayoutParams(lp);
        placeName.setGravity(Gravity.CENTER_HORIZONTAL);
        mapImg.setLayoutParams(lp);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(llp);
        linearLayout.addView(mapImg);
        linearLayout.addView(placeName);

        MapComponentViewHolder mapComponentViewHolder = new MapComponentViewHolder(linearLayout);
        return mapComponentViewHolder;
    }

    private TextComponentViewHolder createTextComponentViewholder(RecyclerView.LayoutParams lp){
        EditText et = new EditText(mContext);
        et.setLayoutParams(lp);
        TextComponentViewHolder textComponentViewHolder = new TextComponentViewHolder(et, onTextChangeListener);
        return textComponentViewHolder;
    }


    @Override
    public void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener){
        this.onComponentLongClickListener = onComponentLongClickListener;
    }

    @Override
    public void OnComponentLongClick(int position) {
        onComponentLongClickListener.OnComponentLongClick(position);
    }



}
