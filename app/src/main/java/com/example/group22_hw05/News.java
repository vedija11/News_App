package com.example.group22_hw05;

public class News {
    String author, title, url, urlToImage, publishedAt;

    public News() {
    }

    @Override
    public String toString() {
        return "News{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
