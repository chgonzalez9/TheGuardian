package com.christian_gonzalez.theguardian.utils;

import android.text.TextUtils;
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
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Query {

    public static final String LOG_TAG = Query.class.getSimpleName();

    private Query() {
    }

    public static List<ArticleWords> fetchArticleData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Guardian JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<ArticleWords> extractFeatureFromJson(String articleJSON) {
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        List<ArticleWords> articles = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            JSONObject responseArray = baseJsonResponse.getJSONObject("response");

            JSONArray resultsArray = responseArray.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject articleObjects = resultsArray.getJSONObject(i);

                String type = articleObjects.getString("type");

                String section = articleObjects.getString("sectionName");

                Date date = Date.from(Instant.parse(articleObjects.getString("webPublicationDate")));

                String title = articleObjects.getString("webTitle");

                String url = articleObjects.getString("webUrl");

                JSONObject imageObject = articleObjects.getJSONObject("fields");

                String image = imageObject.getString("thumbnail");

                JSONArray autorArray = articleObjects.getJSONArray("tags");

                JSONObject contributorObject = autorArray.getJSONObject(0);

                String contributor = contributorObject.getString("webTitle");

                ArticleWords information = new ArticleWords(type, section, date, title, url, image, contributor);

                articles.add(information);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Guardian JSON results", e);
        }
        return articles;
    }

}
