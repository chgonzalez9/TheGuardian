package com.christian_gonzalez.theguardian.utils;

public class ArticleWords {

    private String sectionName;
    private String webTittle;
    private Long articleDate;
    private String articleUrl;

    public ArticleWords(String Section, Long Date, String Tittle, String Url) {
        sectionName = Section;
        webTittle = Tittle;
        articleDate = Date;
        articleUrl = Url;
    }

    public String getSection() {
        return sectionName;
    }

    public String getTittle() {
        return webTittle;
    }

    public Long getDate() {
        return articleDate;
    }

    public String getUrl() {
        return articleUrl;
    }

}
