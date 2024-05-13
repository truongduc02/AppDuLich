package com.example.travelapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.TourAdapter;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.my_interface.IClickItemTourListener;
import com.example.travelapplication.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcvTour;
    private TourAdapter tourAdapter;
    private List<Tour> list;
    private SearchView searchView;
    private ImageButton imgBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rcvTour = findViewById(R.id.rcv_search_tour);
        searchView = findViewById(R.id.searchView);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcvTour.setLayoutManager(gridLayoutManager);

        list = new ArrayList<>();
        tourAdapter = new TourAdapter();

        callApiListTour();

        imgBack = findViewById(R.id.img_back_search);
        imgBack.setOnClickListener(v -> onBackPressed());

    }

    private void fileList(String newText) {
        List<Tour> filterList = new ArrayList<>();
        String inputText = newText.toLowerCase().trim();

        for (Tour tour : list) {
            if (tour.getTitle().toLowerCase().contains(inputText)) {
                filterList.add(tour);
            }
        }

        if (filterList.isEmpty()) {
            Toast.makeText(SearchActivity.this, "", Toast.LENGTH_SHORT).show();
        } else {
            tourAdapter.setFilterList(filterList);
        }
    }

    private void callApiListTour(){
        Apiservice.apiService.getAllTour().enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                list = response.body();
                tourAdapter.setData(list, new IClickItemTourListener() {
                    @Override
                    public void onClickItemTour(Tour tour) {
                        int tourId = tour.getTour_id();
                        getTourDetail(tourId);
                    }

                    @Override
                    public void onClickAddToCart(Tour tour) {

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
                    Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                    intent.putExtra("tourDetail",tour);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });
    }


}
