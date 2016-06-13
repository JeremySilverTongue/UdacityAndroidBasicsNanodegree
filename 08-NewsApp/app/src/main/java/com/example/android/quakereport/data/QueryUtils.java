package com.example.android.quakereport.data;

import android.net.Uri;
import android.net.Uri.Builder;

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
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public final class QueryUtils {

    private static final String HTTP_METHOD = "GET";
    private static final String BASE_URL = "http://content.guardianapis.com/search";

    private static final String PARAM_API_KEY = "api-key";
    private static final String PARAM_SHOW_FILEDS = "show-fields";
    private static final String PARAM_PAGE_SIZE = "page-size";

    private static final String VALUE_API_KEY = "test";
    private static final String VALUE_SHOW_FIELDS = "thumbnail";
    private static final String VALUE_PAGE_SIZE = "20";

    private static final String KEY_RESPONSE = "response";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_WEB_TITLE = "webTitle";
    private static final String KEY_WEB_URL = "webUrl";
    private static final String KEY_FIELDS = "fields";
    private static final String KEY_THUMBNAIL = "thumbnail";

    private QueryUtils() {
    }

    public static Map<String, String> getParamMap() {

        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put(PARAM_API_KEY, VALUE_API_KEY);
        paramMap.put(PARAM_SHOW_FILEDS, VALUE_SHOW_FIELDS);
        paramMap.put(PARAM_PAGE_SIZE, VALUE_PAGE_SIZE);

        return paramMap;
    }

    public static URL buildUrl() {
        Uri baseUri = Uri.parse(BASE_URL);
        Builder uriBuilder = baseUri.buildUpon();

        Map<String, String> paramMap = getParamMap();
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
            Timber.e(e, "Problem building the URL");
        }
        return url;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
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

    static String makeHttpRequest(URL url) {
        String output = "";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(HTTP_METHOD);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            output = readInputStream(inputStream);
            Timber.d(output);
        } catch (IOException e) {
            Timber.e(e, "Problem retrieving the earthquake JSON results.");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return output;
    }

    static List<NewsStory> extractEarthquakes(String earthquakeJSON) {

        ArrayList<NewsStory> newsStories = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONObject response = baseJsonResponse.getJSONObject(KEY_RESPONSE);
            JSONArray jsonStories = response.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonStories.length(); i++) {
                JSONObject jsonStory = jsonStories.getJSONObject(i);
                String title = jsonStory.getString(KEY_WEB_TITLE);
                String url = jsonStory.getString(KEY_WEB_URL);

                JSONObject fields = jsonStory.getJSONObject(KEY_FIELDS);
                String thumbnail = fields.getString(KEY_THUMBNAIL);


                NewsStory newsStory = new NewsStory(title, thumbnail, url);
                newsStories.add(newsStory);
            }

        } catch (JSONException e) {
            Timber.e(e, "Problem parsing the earthquake JSON results");
        }
        return newsStories;
    }
}
