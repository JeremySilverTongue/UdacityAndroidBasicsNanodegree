/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.exercise;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView mQueryTextView;
    private TextView mParamsTextView;
    private TextView mUrlTextView;
    private TextView mResultsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueryTextView = (TextView) findViewById(R.id.query);
        mParamsTextView = (TextView) findViewById(R.id.params);
        mUrlTextView = (TextView) findViewById(R.id.url);
        mResultsTextView = (TextView) findViewById(R.id.results);

        mQueryTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search(null);
                return true;
            }
        });


    }


    public void search(View view) {

        String query = mQueryTextView.getText().toString();
        if (query.length() != 0) {

            mQueryTextView.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            Map<String, String> params = QueryUtils.getParamMap(query);
            URL url = QueryUtils.buildUrl(params);

            if (params == null) {
                displayError(getString(R.string.error_no_params));
            } else if (url == null) {
                displayError(getString(R.string.error_bad_url));
            } else if (!manager.getActiveNetworkInfo().isConnected()) {
                displayError(getString(R.string.error_no_connection));
            } else {
                setParamsViewContents(params);
                mUrlTextView.setText(url.toString());
                new FetchBooksTask().execute(url);
            }
        }
    }


    private void setParamsViewContents(Map<String, String> params) {
        if (params != null) {
            StringBuilder paramsViewContents = new StringBuilder();
            for (String param : params.keySet()) {

                paramsViewContents
                        .append(param)
                        .append(": ")
                        .append(params.get(param))
                        .append("\n");
            }
            mParamsTextView.setText(paramsViewContents.toString());
        } else {
            mParamsTextView.setText(getString(R.string.error_no_params));
        }
    }

    private void setResultsViewContents(List<Book> bookList) {


        if (bookList.size() > 0) {
            StringBuilder builder = new StringBuilder();

            for (Book book : bookList) {
                builder.append(getString(R.string.label_title))
                        .append(book.title)
                        .append("\n");

                switch (book.authors.size()) {
                    case 0:
                        builder.append(getString(R.string.label_author))
                                .append(getString(R.string.unknown));
                        break;
                    case 1:
                        builder.append(getString(R.string.label_author))
                                .append(book.authors.get(0));
                        break;
                    case 2:
                        builder.append(getString(R.string.label_authors))
                                .append(book.authors.get(0))
                                .append(getString(R.string.and))
                                .append(book.authors.get(1));
                        break;
                    default:
                        StringBuilder authorBuilder = new StringBuilder();

                        for (int i = 0; i < book.authors.size(); i++) {

                            if (i != 0) {
                                authorBuilder.append(", ");
                            }
                            if (i == book.authors.size() - 1) {
                                authorBuilder.append(getString(R.string.and));
                            }

                            authorBuilder.append(book.authors.get(i));
                        }

                        builder.append(getString(R.string.label_authors))
                                .append(authorBuilder.toString());
                        break;
                }

                builder.append("\n\n");

            }
            mResultsTextView.setText(builder.toString());
        } else {
            mResultsTextView.setText(getString(R.string.error_no_result));
        }

    }

    private void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, message);
    }


    private class FetchBooksTask extends AsyncTask<URL, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(URL... params) {
            Log.d(LOG_TAG, "Beginning query");

            String json = QueryUtils.makeHttpRequest(params[0]);

            Log.d(LOG_TAG, "Got json:" + json);


            return QueryUtils.parseBooks(json);
        }

        @Override
        protected void onPostExecute(List<Book> bookList) {
            Log.d(LOG_TAG, "Query complete");
            setResultsViewContents(bookList);
        }
    }


}
