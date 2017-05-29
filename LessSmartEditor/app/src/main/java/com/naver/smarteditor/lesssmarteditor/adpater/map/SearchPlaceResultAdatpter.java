package com.naver.smarteditor.lesssmarteditor.adpater.map;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.SearchResultOnClickListener;
import com.naver.smarteditor.lesssmarteditor.data.api.naver_map.PlaceItem;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class SearchPlaceResultAdatpter extends RecyclerView.Adapter<SearchPlaceResultAdatpter.PlaceViewHolder> {


    List<PlaceItem> places;
    Context mContext;
    SearchResultOnClickListener mSearchResultItemOnClickListener;

    public SearchPlaceResultAdatpter(Context context){
        mContext = context;
    }


    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_place, parent, false);
        PlaceViewHolder viewHolder = new PlaceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.placeName.setText(Html.fromHtml(places.get(position).getPlaceName(), Html.FROM_HTML_MODE_COMPACT));
        }
        else{
            holder.placeName.setText(Html.fromHtml(places.get(position).getPlaceName()));
        }

        holder.placeAddress.setText(places.get(position).getPlaceAddress());
        holder.katechMapX = places.get(position).getKatechMapX();
        holder.katechMapY = places.get(position).getKatechMapY();
        holder.postion = position;
    }

    @Override
    public int getItemCount() {
        if(places == null) {
            return 0;
        } else {
            return places.size();
        }
    }

    public void changeData(List<PlaceItem> newData){
        places = newData;
    }

    public void setOnResultClickedListener(SearchResultOnClickListener listener){
        this.mSearchResultItemOnClickListener = listener;
    }


    class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView placeName;
        TextView placeAddress;
        int katechMapX, katechMapY;
        int postion;

        public PlaceViewHolder(final View itemView) {
            super(itemView);

            placeName = (TextView) itemView.findViewById(R.id.viewholder_search_place_name);
            placeAddress = (TextView) itemView.findViewById(R.id.viewholder_search_place_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchResultItemOnClickListener.OnClickListener(itemView, katechMapX, katechMapY, postion);
                }
            });
        }

    }
}

