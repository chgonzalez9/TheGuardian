package com.christian_gonzalez.theguardian.adapter;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.christian_gonzalez.theguardian.utils.ArticleWords;
import com.christian_gonzalez.theguardian.utils.Query;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<ArticleWords>> {

    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<ArticleWords> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return Query.fetchArticleData(mUrl);
    }
}
