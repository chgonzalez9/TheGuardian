package com.christian_gonzalez.theguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.LoaderManager;
import android.content.Loader;

import com.christian_gonzalez.theguardian.adapter.ArticleLoader;
import com.christian_gonzalez.theguardian.utils.ArticleWords;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<ArticleWords>> {

    private static final String TheGuardianUrl = "https://content.guardianapis.com/search?order-by=newest&section=business&page=1&q=economy&api-key=8deebd4e-d7d6-4782-9654-668debf9ce8d";
    private static final int ArticleLoaderID = 1;

    private ListView listView = findViewById(R.id.list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(ArticleLoaderID, null, this);

    }


    @Override
    public Loader<List<ArticleWords>> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(this, TheGuardianUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<ArticleWords>> loader, List<ArticleWords> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<ArticleWords>> loader) {

    }
}