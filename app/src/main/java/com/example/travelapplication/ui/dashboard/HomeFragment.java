package com.example.travelapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.GioHang2Activity;
import com.example.travelapplication.ui.adapter.CategoryAdapter;
import com.example.travelapplication.ui.adapter.TourAdapter;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.Article;
import com.example.travelapplication.ui.model.Category;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.my_interface.IClickItemTourListener;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private View view;
    private List<Category> listCategory;
    private TextView tvUser;
    private CircleImageView imgUser;

    private ImageView imageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvCategory = view.findViewById(R.id.rcv_category);
        tvUser = view.findViewById(R.id.tv_user);
        imgUser = view.findViewById(R.id.img_user);
        imageButton = view.findViewById(R.id.img_cartHome);


        //tvUser.setText(Utils.user_current.getName());
        ApiGetData.apiGetData.getUserByEmail(Utils.email,Utils.token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user=response.body();
                tvUser.setText(user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



        categoryAdapter = new CategoryAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCategory.setLayoutManager(linearLayoutManager);

        listCategory = new ArrayList<>();

        callCategoryData();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHang2Activity.class);
                startActivity(intent);
            }
        });

        return view;

    }

    private void callCategoryData(){
        Apiservice.apiService.getListTour(1, Utils.token).enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                if (response.isSuccessful()) {
                    List<Tour> tourList = response.body();
                    if (tourList != null && !tourList.isEmpty()) {
                        Category category = new Category(1, "Nội địa " , tourList, null);
                        listCategory.add(category);
                        categoryAdapter.setData(listCategory);
                        rcvCategory.setAdapter(categoryAdapter);

                        // xog hàm callCategoryData thi moi goi ham nay
                        callCategoryData1();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                Toast.makeText(getContext(), "--loi--", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callCategoryData1(){
        Apiservice.apiService.getListTour(2,Utils.token).enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                if (response.isSuccessful()) {
                    List<Tour> tourList = response.body();
                    if (tourList != null && !tourList.isEmpty()) {
                        Category category = new Category(2, "Quốc tế " , tourList, null);
                        listCategory.add(category);
                        categoryAdapter.setData(listCategory);
                        rcvCategory.setAdapter(categoryAdapter);

                        // xog hàm callCategoryData1 thi moi goi ham nay
                        callCategoryData2();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                Toast.makeText(getContext(), "--loi--", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callCategoryData2(){
       Apiservice.apiService.getListArticle().enqueue(new Callback<List<Article>>() {
           @Override
           public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
               if (response.isSuccessful()){
                   List<Article> articleList = response.body();
                   if (articleList != null && !articleList.isEmpty()){
                       Category category = new Category(3,"Tin tức",null,articleList);
                       listCategory.add(category);
                       categoryAdapter.setData(listCategory);
                       rcvCategory.setAdapter(categoryAdapter);
                   }
               }
           }

           @Override
           public void onFailure(Call<List<Article>> call, Throwable t) {
               Toast.makeText(getContext(), "--loi--", Toast.LENGTH_SHORT).show();
           }
       });
    }




}
