package com.example.travelapplication.ui.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.ArticleActivity;
import com.example.travelapplication.ui.activity.DetailActivity;
import com.example.travelapplication.ui.adapter.ArticleAdapter;
import com.example.travelapplication.ui.adapter.TourAdapter;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.Article;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.my_interface.IClickItemArticleListener;
import com.example.travelapplication.ui.my_interface.IClickItemTourListener;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TourNamFragment extends Fragment {

    private RecyclerView rcvTour;
    private View aView;

    private List<Tour> mTourList;
    private TourAdapter tourAdapter;
    private int soLuong = 1;

    public TourNamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        aView = inflater.inflate(R.layout.fragment_tour_nam, container, false);
        rcvTour = aView.findViewById(R.id.rcv_tour_nam);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcvTour.setLayoutManager(gridLayoutManager);

        mTourList = new ArrayList<>();
        tourAdapter = new TourAdapter();

        callApiListTour();

        return aView;
    }

    private void callApiListTour(){
        Apiservice.apiService.getAllTour().enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                mTourList = response.body();
                tourAdapter.setData(mTourList, new IClickItemTourListener() {
                    @Override
                    public void onClickItemTour(Tour tour) {
                        int tourId = tour.getTour_id();
                        getTourDetail(tourId);
                    }

                    @Override
                    public void onClickAddToCart(Tour tour) {
                        openDialog(Gravity.BOTTOM,tour);
                    }
                });
                rcvTour.setAdapter(tourAdapter);
            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {

            }
        });
    }

    private void getTourDetail(int tourId){
        Apiservice.apiServiceDetail.getTourDetail(tourId, Utils.token).enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()){
                    Tour tour = response.body();
                    //chuyen sang trang detail
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("tourDetail",tour);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });
    }
    private void openDialog(int gratity,Tour tour){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gratity;
        window.setAttributes(layoutParams);

        if (Gravity.BOTTOM == gratity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        ImageView imgAnh = dialog.findViewById(R.id.img_dialog);
        TextView tvName = dialog.findViewById(R.id.tv_name_dialog);
        TextView tvPrice = dialog.findViewById(R.id.tv_gia_dialog);
        ImageView imgTru = dialog.findViewById(R.id.img_tru_dialog);
        ImageView imgCong = dialog.findViewById(R.id.img_cong_dialog);
        TextView tvThemCart = dialog.findViewById(R.id.tv_themCart_dialog);
        TextView tvsoLuong = dialog.findViewById(R.id.tv_soluong_dialog);


        Picasso.get().load(tour.getThumbnail()).into(imgAnh);
        tvName.setText(tour.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvPrice.setText(decimalFormat.format(tour.getPrice()));

        imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong > 1) {
                    soLuong--; // Giảm số lượng nếu số lượng lớn hơn 1
                    tvsoLuong.setText(String.valueOf(soLuong)); // Hiển thị giá trị số lượng mới
                }
            }
        });

        imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong++; // Tăng số lượng
                tvsoLuong.setText(String.valueOf(soLuong)); // Hiển thị giá trị số lượng mới
            }
        });


        tvThemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}