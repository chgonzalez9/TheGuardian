package com.christian_gonzalez.theguardian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.christian_gonzalez.theguardian.adapter.ArticleAdapter;
import com.christian_gonzalez.theguardian.adapter.ArticleLoaderAdapter;
import com.christian_gonzalez.theguardian.utils.ArticleWords;
import com.christian_gonzalez.theguardian.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArticleWords>> {

    private static final String TheGuardianUrl = "https://content.guardianapis.com/search?";

    private ArticleAdapter mAdapter;
    private TextView emptyStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);

        mAdapter = new ArticleAdapter(this, new ArrayList<>());
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            ArticleWords currentArticle = mAdapter.getItem(position);
            Uri articleUri = Uri.parse(currentArticle.getUrl());
            Intent website = new Intent(Intent.ACTION_VIEW, articleUri);
            startActivity(website);
        });

        emptyStateText = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyStateText);

        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager.getInstance(this).initLoader(0, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading);
            loadingIndicator.setVisibility(View.GONE);

            emptyStateText.setText(R.string.no_internet);
        }

    }


    @NonNull
    @Override
    public Loader<List<ArticleWords>> onCreateLoader(int id, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String section = sharedPrefs.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(TheGuardianUrl);
        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("show-fields", "thumbnail");
        builder.appendQueryParameter("show-tags", "contributor");
        builder.appendQueryParameter("section", section);
        builder.appendQueryParameter("order-by", orderBy);
        builder.appendQueryParameter("q", "economy");
        builder.appendQueryParameter("api-key", "8deebd4e-d7d6-4782-9654-668debf9ce8d");

        return new ArticleLoaderAdapter(this, builder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ArticleWords>> loader, List<ArticleWords> data) {

        View loadingIndicator = findViewById(R.id.loading);
        assert loadingIndicator != null;
        loadingIndicator.setVisibility(View.GONE);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo == null ) {
//state that there is no internet connection
            emptyStateText.setText(R.string.no_internet);
        } else if (networkInfo.isConnected()){
            emptyStateText.setText(R.string.no_article);
        }

        mAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ArticleWords>> loader) {
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}