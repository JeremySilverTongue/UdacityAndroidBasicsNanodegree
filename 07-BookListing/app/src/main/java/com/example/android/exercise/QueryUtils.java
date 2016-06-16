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


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String HTTP_METHOD = "GET";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    private static final String PARAM_QUERY = "q";
    private static final String PARAM_PRINT_TYPE = "printType";
    private static final String PARAM_MAX_RESULTS = "maxResults";

    private static final String VALUE_PRINT_TYPE = "books";
    private static final String VALUE_MAX_RESULTS = "15";

    private static final String KEY_ITEMS = "items";
    private static final String KEY_VOLUME_INFO = "volumeInfo";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHORS = "authors";

    private QueryUtils() {
    }

    public static Map<String, String> getParamMap(String query) {
        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put(PARAM_QUERY, query);
        paramMap.put(PARAM_PRINT_TYPE, VALUE_PRINT_TYPE);
        paramMap.put(PARAM_MAX_RESULTS, VALUE_MAX_RESULTS);

        return paramMap;
    }

    public static URL buildUrl(Map<String, String> paramMap) {
        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if (paramMap != null) {
            for (String param : paramMap.keySet()) {
                String value = paramMap.get(param);
                uriBuilder.appendQueryParameter(param, value);
            }
        }

        String uriString = uriBuilder.toString();
        URL url = null;
        try {
            url = new URL(uriString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL: ", e);
        }
        return url;
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequest(URL url) {
        String output = "";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(HTTP_METHOD);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            output = readInputStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return output;
    }

    public static ArrayList<Book> parseBooks(String json) {

        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONArray bookArray = baseJsonResponse.getJSONArray(KEY_ITEMS);

            for (int i = 0; i < bookArray.length(); i++) {

                JSONObject book = bookArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject(KEY_VOLUME_INFO);

                String title = volumeInfo.getString(KEY_TITLE);

                ArrayList<String> authors = new ArrayList<>();

                if (volumeInfo.has(KEY_AUTHORS)) {
                    JSONArray jsonAuthors = volumeInfo.getJSONArray(KEY_AUTHORS);

                    for (int j = 0; j < jsonAuthors.length(); j++) {
                        authors.add(jsonAuthors.getString(j));
                    }
                }

                books.add(new Book(title, authors));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return books;
    }


}
