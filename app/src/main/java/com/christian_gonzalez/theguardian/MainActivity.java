package com.christian_gonzalez.theguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.christian_gonzalez.theguardian.adapter.ArticleAdapter;
import com.christian_gonzalez.theguardian.adapter.ArticleLoader;
import com.christian_gonzalez.theguardian.utils.ArticleWords;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArticleWords>> {

    private static final String TheGuardianUrl = "https://content.guardianapis.com/search?order-by=newest&section=business&page=1&q=economy&api-key=8deebd4e-d7d6-4782-9654-668debf9ce8d";

    private ListView listView;
    private ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        mAdapter = new ArticleAdapter(this, new ArrayList<ArticleWords>());
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ArticleWords currentArticle = mAdapter.getItem(position);
                Uri articleUri = Uri.parse(currentArticle.getUrl());
                Intent website = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(website);
            }
        });
        

        LoaderManager.getInstance(this).initLoader(0, null, this);


    }


    @Override
    public Loader<List<ArticleWords>> onCreateLoader(int id, Bundle args) {

        return new ArticleLoader(this, TheGuardianUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<ArticleWords>> loader, List<ArticleWords> data) {

        View loadingIndicator = findViewById(R.id.loading);
        assert loadingIndicator != null;
        loadingIndicator.setVisibility(View.GONE);

        mAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<ArticleWords>> loader) {
        mAdapter.clear();
    }
}