package com.example.android.quakereport.data;

import android.net.Uri;

public class NewsStory {

    public final String title;
    public final String thumbnail;

    public final Uri uri;

    NewsStory(String title,
              String thumbnail,

              String url
    ) {

        this.title = title;
        this.thumbnail = thumbnail;

        this.uri = Uri.parse(url);
    }
}
