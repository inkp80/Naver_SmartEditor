package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
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

    public MapComponentViewHolder(View itemView, ComponentFocusListener componentFocusListener, RequestManager requestManager) {
        super(itemView, componentFocusListener);
        this.placeLinearLayout = (LinearLayout) itemView;
        this.requestManager = requestManager;

        placeMapImg = (ImageView) placeLinearLayout.getChildAt(0);
        placeTextInfo = (TextView) placeLinearLayout.getChildAt(1);

    }



    @Override
    public void bindView(BaseComponent baseComponent) {
        MapComponent thisMapComponent = (MapComponent) baseComponent;

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
