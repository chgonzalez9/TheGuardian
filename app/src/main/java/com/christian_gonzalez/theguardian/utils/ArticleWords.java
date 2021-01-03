package com.christian_gonzalez.theguardian.utils;

import java.util.Date;

public class ArticleWords {

    private String mType;
    private String sectionName;
    private String webTitle;
    private Date articleDate;
    private String articleUrl;

    public ArticleWords(String Type, String Section, Date Date, String Title, String Url) {
        mType = Type;
        sectionName = Section;
        webTitle = Title;
        articleDate = Date;
        articleUrl = Url;
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

}
