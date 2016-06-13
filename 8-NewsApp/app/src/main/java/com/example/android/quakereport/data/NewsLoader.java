package com.example.android.quakereport.data;

import android.content.Context;

import java.net.URL;
import java.util.List;

public class NewsLoader extends android.content.AsyncTaskLoader<List<NewsStory>> {

    public static final String LOG_TAG = NewsLoader.class.getName();

    private URL mUrl;

    public NewsLoader(Context context, URL url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsStory> loadInBackground() {
        String json = QueryUtils.makeHttpRequest(mUrl);
        return QueryUtils.extractEarthquakes(json);
    }
}
