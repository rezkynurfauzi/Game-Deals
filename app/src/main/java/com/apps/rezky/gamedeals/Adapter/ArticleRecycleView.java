package com.apps.rezky.gamedeals.Adapter;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    29 juli 2021
*/

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apps.rezky.gamedeals.R;

import com.apps.rezky.gamedeals.api.APIDataArticle;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ArticleRecycleView extends RecyclerView.Adapter<ArticleRecycleView.ViewHolder> {

    //inisialisasi variable
    LayoutInflater inflater;
    private ArrayList<APIDataArticle> articles;
    private ArticleRecycleView.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ArticleRecycleView.OnItemClickListener listener) {
        mListener = listener;
    }

    public ArticleRecycleView(Context ctx, ArrayList<APIDataArticle> articles) {
        this.inflater = LayoutInflater.from(ctx);
        this.articles = articles;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_article_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Memasukan data dari API ke recycle view
        holder.articleTitle.setText(articles.get(position).getTitle());
        holder.articleSummary.setText(articles.get(position).getSummary());
        holder.articleAuthors.setText("Authors : "+articles.get(position).getAuthor());
        holder.articlePublishDate.setText(articles.get(position).getPublishDate().substring(0,10));
        Picasso.get().load(articles.get(position).getImageUrl()).fit().centerCrop().into(holder.articleCoverImage);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitle,articleSummary,articleAuthors,articlePublishDate;
        public ImageView articleCoverImage;

        public ViewHolder(View itemView) {
            super(itemView);
            articleCoverImage = itemView.findViewById(R.id.articleImage);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleSummary = itemView.findViewById(R.id.articleSummary);
            articleAuthors = itemView.findViewById(R.id.articleAuthors);
            articlePublishDate = itemView.findViewById(R.id.articlePublish);

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