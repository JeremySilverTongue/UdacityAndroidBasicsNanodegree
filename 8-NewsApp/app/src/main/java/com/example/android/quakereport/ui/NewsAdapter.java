package com.example.android.quakereport.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.quakereport.R;
import com.example.android.quakereport.data.NewsStory;

import butterknife.BindView;
import butterknife.ButterKnife;


class NewsAdapter extends ArrayAdapter<NewsStory> {

    private final LayoutInflater inflater;

    NewsAdapter(Context context) {
        super(context, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_news, parent, false);
        }

        NewsStoryViewHolder viewHolder = (NewsStoryViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new NewsStoryViewHolder(view);
            view.setTag(viewHolder);
        }

        NewsStory newsStory = getItem(position);
        viewHolder.title.setText(newsStory.title);

        Glide.with(getContext()).load(newsStory.thumbnail).into(viewHolder.thumbnail);

        return view;
    }


    class NewsStoryViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        NewsStoryViewHolder(View view) {

            ButterKnife.bind(this, view);

        }
    }
}
