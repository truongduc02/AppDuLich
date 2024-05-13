package com.example.travelapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.DetailActivity;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.my_interface.IClickAddToCart;
import com.example.travelapplication.ui.my_interface.IClickItemTourListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {

    private List<Tour> listTour;

    private IClickItemTourListener iClickItemTourListener;
    private IClickAddToCart iClickAddToCart;


    public void setData(List<Tour> list,IClickItemTourListener listener){
        this.iClickItemTourListener = listener;
        this.listTour = list;
    }

    public void setFilterList(List<Tour> filterList){
        this.listTour = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour,parent,false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = listTour.get(position);
        if(tour == null){
            return;
        }

        Picasso.get().load(tour.getThumbnail()).into(holder.imageViewTour);
        holder.tvNameTour.setText(tour.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPriceTour.setText(decimalFormat.format(tour.getPrice()));

        holder.tvNameTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemTourListener.onClickItemTour(tour);
            }
        });

        holder.imageAddTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemTourListener.onClickAddToCart(tour);
            }
        });

    }


    @Override
    public int getItemCount() {
        if(listTour != null){
            return listTour.size();
        }
        return 0;
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewTour;
        private TextView tvNameTour;
        private TextView tvPriceTour;

        private ImageButton imageAddTour;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

        imageViewTour = itemView.findViewById(R.id.img_view_tour);
        tvNameTour = itemView.findViewById(R.id.tv_name_tour);
        tvPriceTour = itemView.findViewById(R.id.tv_price_tour);
        imageAddTour = itemView.findViewById(R.id.img_button_add);

        }
    }
}
