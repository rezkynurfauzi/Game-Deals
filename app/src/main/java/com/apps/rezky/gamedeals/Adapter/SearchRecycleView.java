package com.apps.rezky.gamedeals.Adapter;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    30 juli 2021
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

public class SearchRecycleView extends RecyclerView.Adapter<SearchRecycleView.ViewHolder>{
    LayoutInflater inflater;
    List<APIData> searches;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SearchRecycleView(Context ctx, List<APIData> searches){
        this.inflater = LayoutInflater.from(ctx);
        this.searches = searches;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // menyambugkan data dari array list ke custom layout cardview untuk search game
        holder.searchTitle.setText(searches.get(position).getTitle());
        holder.searchNormarPrice.setText("Normal Price : $ "+searches.get(position).getNormalPrice());
        holder.searchDealPrice.setText("Price Now : $ "+searches.get(position).getDealPrice());
        holder.searchDiscount.setText(""+searches.get(position).getDiscount().substring(0,2).replace(".","")+"%");
        holder.searchMetacritic.setText(searches.get(position).getMetacritic()+" | "+searches.get(position).getSteamreview());
        Picasso.get().load("https://steamcdn-a.akamaihd.net/steam/apps/"+searches.get(position).getCoverImage()+"/header.jpg").fit().into(holder.searchCoverImage);
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView searchTitle,searchNormarPrice,searchDealPrice,searchDiscount,searchMetacritic;
        public ImageView searchCoverImage;

        public ViewHolder(View itemView) {
            super(itemView);
            searchTitle = itemView.findViewById(R.id.searchTitle);
            searchNormarPrice = itemView.findViewById(R.id.searchnormalPrice);
            searchDealPrice = itemView.findViewById(R.id.searchdealPrice);
            searchDiscount = itemView.findViewById(R.id.searchDiscount);
            searchMetacritic = itemView.findViewById(R.id.searchRating);
            searchCoverImage = itemView.findViewById(R.id.searchcoverImage);

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