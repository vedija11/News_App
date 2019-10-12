package com.example.group22_hw05;

public class News {
    String author, title, urlToImage, publishedAt;

    public News() {
    }

    public News(String author, String title, String publishedAt) {
        this.author = author;
        this.title = title;
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "News{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
