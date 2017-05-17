package com.naver.smarteditor.map_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RViewHolder> {

    List<item> items;
    Context mContext;

    public MyAdapter(Context context, List<item> passedItem) {
        mContext = context;
        items = passedItem;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent, false);

        RViewHolder viewHolder = new RViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {

        holder.place.setText(items.get(position).getTitle());
        holder.placeAddress.setText(items.get(position).getRoadAddress());


    }

    public void changeData(List<item> items){
        this.items = items;
    }
    @Override
    public int getItemCount() {
        if(items != null)
            return items.size();
        else
            return 0;
    }

    class RViewHolder extends RecyclerView.ViewHolder{
        TextView place;
        TextView placeAddress;

        public RViewHolder(View itemView) {
            super(itemView);

            place = (TextView) itemView.findViewById(R.id.place);
            placeAddress = (TextView) itemView.findViewById(R.id.place_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
