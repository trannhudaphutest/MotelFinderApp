package com.example.mrm82.motelfinderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mrm82 on 28/12/2017.
 */

public class PostAdapter extends ArrayAdapter<PostConstruct> {
    @NonNull Activity context;
    int resource;
    @NonNull List<PostConstruct> objects;
    public PostAdapter(@NonNull Activity context, int resource, @NonNull List<PostConstruct> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    TextView txtTitle,txtArea,txtPrice,txtType;
    CardView cardView;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View itemView= layoutInflater.inflate(this.resource,null);
        cardView = itemView.findViewById(R.id.card_view);
        txtTitle= itemView.findViewById(R.id.txtPostTitle);
        txtArea = itemView.findViewById(R.id.txtArea);
        txtType= itemView.findViewById(R.id.txtPostType);
        txtPrice = itemView.findViewById(R.id.txtPrice);
        PostConstruct post = this.objects.get(position);
        txtArea.setText(post.getPostArea());
        txtPrice.setText(post.getPostPrice());
        txtTitle.setText(post.getPostTitle());
        txtType.setText(post.getPostType());
        return itemView;
    }
}
