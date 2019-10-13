package com.example.group22_hw05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_author = convertView.findViewById(R.id.tv_author);
            viewHolder.tv_publishDate = convertView.findViewById(R.id.tv_publishDate);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tv_title.setText(news.title);
        viewHolder.tv_author.setText(news.author);
        viewHolder.tv_publishDate.setText(news.publishedAt);
        //viewHolder.imageView.setImageBitmap(news.urlToImage);

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_title;
        TextView tv_author;
        TextView tv_publishDate;
        ImageView imageView;
    }
}

