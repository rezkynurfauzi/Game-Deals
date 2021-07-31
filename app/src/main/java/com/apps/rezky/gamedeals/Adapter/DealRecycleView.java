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

import androidx.recyclerview.widget.RecyclerView;

import com.apps.rezky.gamedeals.api.APIData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DealRecycleView extends RecyclerView.Adapter<DealRecycleView.ViewHolder> {
    LayoutInflater inflater;
    List<APIData> deals;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public DealRecycleView(Context ctx, List<APIData> deals){
        this.inflater = LayoutInflater.from(ctx);
        this.deals = deals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_deal_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // bind the data
        holder.dealTitle.setText(deals.get(position).getTitle());
        holder.dealNormarPrice.setText("$ "+deals.get(position).getNormalPrice());
        holder.dealPrice.setText("$ "+deals.get(position).getDealPrice());
        holder.dealDiscount.setText("-"+deals.get(position).getDiscount().substring(0,2)+"%");
        holder.dealMetacritic.setText(deals.get(position).getMetacritic()+" | "+deals.get(position).getSteamreview());
        Picasso.get().load("https://steamcdn-a.akamaihd.net/steam/apps/"+deals.get(position).getCoverImage()+"/capsule_184x69.jpg").into(holder.dealCoverImage);

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView dealTitle,dealNormarPrice,dealPrice,dealDiscount,dealMetacritic;
        public ImageView dealCoverImage;

        public ViewHolder(View itemView) {
            super(itemView);
            dealTitle = itemView.findViewById(R.id.dealTitle);
            dealNormarPrice = itemView.findViewById(R.id.dealNormalPrice);
            dealPrice = itemView.findViewById(R.id.dealPrice);
            dealDiscount = itemView.findViewById(R.id.dealDiscount);
            dealMetacritic = itemView.findViewById(R.id.dealRating);
            dealCoverImage = itemView.findViewById(R.id.coverImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}