package com.example.travelapplication.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.model.Article;
import com.example.travelapplication.ui.my_interface.IClickItemArticleListener;
import com.example.travelapplication.ui.my_interface.IClickItemTourListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHoler>{

    private List<Article> listArticle;
    private IClickItemArticleListener iClickItemArticleListener;

    public void setData(List<Article> list, IClickItemArticleListener listener){
        this.listArticle = list;
        this.iClickItemArticleListener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
        return new ArticleViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHoler holder, int position) {
        Article article = listArticle.get(position);
        if (article == null){
            return;
        }
        Picasso.get().load(article.getThumbnail()).into(holder.imgArticle);
        holder.tvArticle.setText(article.getTitle());
        holder.tvDateArticle.setText(article.getCreated_date());

        holder.tvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemArticleListener.onClickItemArticle(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listArticle != null){
            return listArticle.size();
        }
        return 0;
    }

    public class ArticleViewHoler extends RecyclerView.ViewHolder {
        private TextView  tvArticle, tvDateArticle;
        private ImageView imgArticle;

        public ArticleViewHoler(@NonNull View itemView) {
            super(itemView);

            imgArticle = itemView.findViewById(R.id.img_view_article);
            tvArticle = itemView.findViewById(R.id.tv_name_article);
            tvDateArticle = itemView.findViewById(R.id.tv_date_article);
        }
    }

}
