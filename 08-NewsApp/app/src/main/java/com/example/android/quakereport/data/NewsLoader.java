package com.example.android.quakereport.data;

import android.content.Context;

import java.net.URL;
import java.util.List;

public class NewsLoader extends android.content.AsyncTaskLoader<List<NewsStory>> {

    private URL url;

    public NewsLoader(Context context, URL url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsStory> loadInBackground() {
        String json = QueryUtils.makeHttpRequest(url);
        return QueryUtils.extractEarthquakes(json);
    }
}
