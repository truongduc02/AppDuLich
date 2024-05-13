package com.example.travelapplication.ui.adapter;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.DetailActivity;
import com.example.travelapplication.ui.model.Tour;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoViewPageAdapter extends RecyclerView.Adapter<PhotoViewPageAdapter.PhotoViewHolder> {

    private List<Tour> mTourList;

    public PhotoViewPageAdapter(List<Tour> mTourList) {
        this.mTourList = mTourList;
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Tour tour = mTourList.get(position);
        if (tour == null){
            return;
        }


        Picasso.get().load(tour.getThumbnail()).into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        if(mTourList != null){
            return mTourList.size();
        }


        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPhoto;
        private TextView tv_name_tour;
        private TextView tv_price_tour;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tv_name_tour = itemView.findViewById(R.id.tv_name_tour);
            tv_price_tour = itemView.findViewById(R.id.tv_price_tour);


        }
    }

}
