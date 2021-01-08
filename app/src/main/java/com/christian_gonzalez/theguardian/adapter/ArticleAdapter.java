package com.christian_gonzalez.theguardian.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.christian_gonzalez.theguardian.R;
import com.christian_gonzalez.theguardian.utils.ArticleWords;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        ImageView image = (ImageView) listItemView.findViewById(R.id.article_image);
        Glide.with(getContext()).load(currentWord.getImage()).into(image);

        TextView title = listItemView.findViewById(R.id.article_title);
        title.setText(currentWord.getTittle());

        TextView section = listItemView.findViewById(R.id.article_section);
        section.setText(currentWord.getSection());

        TextView type = listItemView.findViewById(R.id.article_type);
        type.setText(currentWord.getType());

        TextView contributor = listItemView.findViewById(R.id.article_contributor);
        contributor.setText(currentWord.getContributor());

        TextView date = listItemView.findViewById(R.id.article_date);
        String formattedDate = formatDate(currentWord.getDate());
        date.setText(formattedDate);

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy h:mm");
        return dateFormat.format(dateObject);
    }

}
