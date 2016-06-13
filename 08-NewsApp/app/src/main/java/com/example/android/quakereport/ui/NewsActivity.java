package com.example.android.quakereport.ui;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.data.NewsLoader;
import com.example.android.quakereport.data.NewsStory;
import com.example.android.quakereport.data.QueryUtils;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsStory>> {

    private static final int NEWS_LOADER_ID = 1;

    @BindView(R.id.news_stories)
    ListView newsListView;

    @BindView(R.id.empty_view)
    View emptyView;

    @BindView(R.id.error)
    TextView errorTextView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private NewsAdapter newsAdapter;
    private URL url;

    private OnItemClickListener storyClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NewsStory newsStory = newsAdapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, newsStory.uri);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        ButterKnife.bind(this);

        newsAdapter = new NewsAdapter(this);
        newsListView.setAdapter(newsAdapter);
        newsListView.setOnItemClickListener(storyClickListener);
        newsListView.setEmptyView(emptyView);

        url = QueryUtils.buildUrl();
        updateEarthquakeData();
    }

    private void updateEarthquakeData() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (QueryUtils.getParamMap() == null) {
            displayError(getString(R.string.error_no_params));
        } else if (url == null) {
            displayError(getString(R.string.error_bad_url));
        } else if (!manager.getActiveNetworkInfo().isConnected()) {
            displayError(getString(R.string.error_no_connection));
        } else {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }
    }

    private void displayError(String message) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
        Timber.e(message);
    }


    @Override
    public Loader<List<NewsStory>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsStory>> loader, List<NewsStory> data) {
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        } else {
            displayError(getString(R.string.error_no_result));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsStory>> loader) {
        newsAdapter.clear();
    }
}
