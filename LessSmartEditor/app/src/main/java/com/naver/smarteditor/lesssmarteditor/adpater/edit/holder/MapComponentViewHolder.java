package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.component.MapComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;

/**
 * Created by NAVER on 2017. 5. 22..
 */

public class MapComponentViewHolder extends ComponentViewHolder{
    private TextView placeTextInfo;
    private ImageView placeMapImg;
    private LinearLayout placeLinearLayout;
    private RequestManager requestManager;

    public MapComponentViewHolder(View itemView, RequestManager requestManager) {
        super(itemView);
        this.placeLinearLayout = (LinearLayout) itemView;
        this.requestManager = requestManager;

        placeMapImg = (ImageView) placeLinearLayout.getChildAt(0);
        placeTextInfo = (TextView) placeLinearLayout.getChildAt(1);
    }


    @Override
    public void setDataPositionOnAdapter(int position) {
        this.position = position;
    }

    @Override
    public int getDataPositionOnAdapter(){
        return this.position;
    }


    @Override
    public void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener) {
        this.onComponentLongClickListener = onComponentLongClickListener;
    }

    @Override
    public void bindView(Object object) {
        MapComponent thisMapComponent = (MapComponent) object;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.placeTextInfo.setText(Html.fromHtml("<b>" + thisMapComponent.getPlaceName() + "</b>", Html.FROM_HTML_MODE_COMPACT));
            this.placeTextInfo.append("\n" + thisMapComponent.getPlaceAddress());
        } else {
            this.placeTextInfo.setText(Html.fromHtml("<b>" + thisMapComponent.getPlaceName() + "</b>"));
            this.placeTextInfo.append("\n" + thisMapComponent.getPlaceAddress());
        }
        requestManager.load(thisMapComponent.getPlaceMapImgUri())
                .override(600, 600)
                .into(this.placeMapImg);
    }
}
