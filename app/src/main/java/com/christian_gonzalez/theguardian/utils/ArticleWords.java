package com.christian_gonzalez.theguardian.utils;

import java.util.Date;

public class ArticleWords {

    private final String mType;
    private final String sectionName;
    private final String webTitle;
    private final Date articleDate;
    private final String articleUrl;
    private final String articleImage;
    private final String contributor;

    public ArticleWords(String Type, String Section, Date Date, String Title, String Url, String image, String autor) {
        mType = Type;
        sectionName = Section;
        webTitle = Title;
        articleDate = Date;
        articleUrl = Url;
        articleImage = image;
        contributor = autor;
    }

    public String getType() {
        return mType;
    }

    public String getSection() {
        return sectionName;
    }

    public String getTittle() {
        return webTitle;
    }

    public Date getDate() {
        return articleDate;
    }

    public String getUrl() {
        return articleUrl;
    }

    public String getImage() {
        return articleImage;
    }

    public String getContributor() {
        return contributor;
    }

}
