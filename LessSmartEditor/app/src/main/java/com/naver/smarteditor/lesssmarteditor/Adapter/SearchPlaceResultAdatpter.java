package com.naver.smarteditor.lesssmarteditor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naver.smarteditor.lesssmarteditor.Objects.Place;
import com.naver.smarteditor.lesssmarteditor.R;
import com.naver.smarteditor.lesssmarteditor.SearchResultOnClickListener;

import java.util.List;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class SearchPlaceResultAdatpter extends RecyclerView.Adapter<SearchPlaceResultAdatpter.PlaceViewHolder> {


    List<Place> places;
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
        holder.placeName.setText(places.get(position).getPlaceName());
        holder.placeAddress.setText(places.get(position).getPlaceAddress());
        holder.katechMapX = places.get(position).getKatechMapX();
        holder.katechMapY = places.get(position).getKatechMapY();
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void changeData(List<Place> newData){
        places = newData;
    }

    public void setOnResultClickedListener(SearchResultOnClickListener listener){
        this.mSearchResultItemOnClickListener = listener;
    }


    class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView placeName;
        TextView placeAddress;
        int katechMapX, katechMapY;

        public PlaceViewHolder(final View itemView) {
            super(itemView);

            placeName = (TextView) itemView.findViewById(R.id.viewholder_search_place_name);
            placeAddress = (TextView) itemView.findViewById(R.id.viewholder_search_place_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchResultItemOnClickListener.OnClickListener(itemView);
                }
            });
        }

    }
}

