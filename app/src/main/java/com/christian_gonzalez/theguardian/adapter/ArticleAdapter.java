package com.christian_gonzalez.theguardian.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.christian_gonzalez.theguardian.R;
import com.christian_gonzalez.theguardian.utils.ArticleWords;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<ArticleWords> {

    public ArticleAdapter(Activity context, ArrayList<ArticleWords> Words) {
        super(context, 0, Words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        ArticleWords currentWord = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.article_title);
        title.setText(currentWord.getTittle());

        TextView section = (TextView) listItemView.findViewById(R.id.article_section);
        section.setText(currentWord.getSection());

        TextView type = (TextView) listItemView.findViewById(R.id.article_type);
        type.setText(currentWord.getType());

        return listItemView;
    }

}
