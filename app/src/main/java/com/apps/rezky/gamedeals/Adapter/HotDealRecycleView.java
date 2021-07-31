package com.apps.rezky.gamedeals.Adapter;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    28 juli 2021
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apps.rezky.gamedeals.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.rezky.gamedeals.api.APIData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotDealRecycleView extends RecyclerView.Adapter<HotDealRecycleView.ViewHolder> {
    LayoutInflater inflater;
    List<APIData> deals;
    private HotOnItemClickListener mListener;


    public interface HotOnItemClickListener {
        void hotonItemClick(int position);
    }

    public void setHotOnItemClickListener(HotOnItemClickListener listener) {
        mListener = listener;
    }

    public HotDealRecycleView(Context ctx, List<APIData> deals){
        this.inflater = LayoutInflater.from(ctx);
        this.deals = deals;
    }




    @NonNull
    @Override
    public HotDealRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_hot_deal_item,parent,false);
        return new HotDealRecycleView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotDealRecycleView.ViewHolder holder, int position) {
        // bind the data
        holder.dealNormarPrice.setText("$ "+deals.get(position).getNormalPrice());
        holder.dealPrice.setText("$ "+deals.get(position).getDealPrice());
        holder.dealDiscount.setText("-"+deals.get(position).getDiscount().substring(0,2)+"%");
        Picasso.get().load("https://steamcdn-a.akamaihd.net/steam/apps/"+deals.get(position).getCoverImage()+"/header.jpg").into(holder.dealCoverImage);

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView dealNormarPrice,dealPrice,dealDiscount;
        ImageView dealCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dealNormarPrice = itemView.findViewById(R.id.hotdealNormalPrice);
            dealPrice = itemView.findViewById(R.id.hotdealPrice);
            dealDiscount = itemView.findViewById(R.id.hotdealDiscount);
            dealCoverImage = itemView.findViewById(R.id.hotcoverImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.hotonItemClick(position);
                        }
                    }
                }
            });
        }
    }
}