package com.example.travelapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.Article;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleActivity extends AppCompatActivity {
    private ImageView imageBtnBack;
    private TextView tvTitle, tvdescription;
    private ImageView imgArticle;
    private Article article;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initView();
        initControl();


    }

    private void initControl() {
        Intent intent = getIntent();
        if (intent != null){
            int article = (int) intent.getSerializableExtra("articleDetail");
            getArticleDetail(article);

        }
    }

    private void initView() {

        tvdescription = findViewById(R.id.motaArticle);
        tvdescription.setMovementMethod(new ScrollingMovementMethod());


        imageBtnBack = findViewById(R.id.img_back);
        imageBtnBack.setOnClickListener(v -> onBackPressed());
    }
        private void getArticleDetail(int articleId){
        Apiservice.apiServiceArticleDetail.getArticleDetail(articleId,Utils.token).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                     article = response.body();
                     tvdescription.setText(Html.fromHtml(article.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {

            }
        });
    }
}
