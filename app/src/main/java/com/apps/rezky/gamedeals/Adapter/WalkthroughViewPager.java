package com.apps.rezky.gamedeals.Adapter;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    27 juli 2021
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.apps.rezky.gamedeals.R;
import com.apps.rezky.gamedeals.view.walkthrough.ScreenItem;

import java.util.List;

public class WalkthroughViewPager extends PagerAdapter {

    //pebuatan variable
    Context mContext;
    List<ScreenItem> mListScreen;


    //konsturktor untuk viewpager dan list
    public WalkthroughViewPager(Context mContext, List<ScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_walkthrough,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.walkthrough_img);
        TextView title = layoutScreen.findViewById(R.id.walkthrough_title);
        TextView description = layoutScreen.findViewById(R.id.walkthrough_desc);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDesc());
        imgSlide.setImageResource(mListScreen.get(position).getScreenimg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}