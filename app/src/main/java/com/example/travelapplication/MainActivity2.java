package com.example.travelapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travelapplication.ui.adapter.ViewHomePageAdapter;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private TextView tv_all_tour;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nextBottomNavigationView();

        if (Utils.mangGioHang != null){

        }else {
            Utils.mangGioHang = new ArrayList<>();
        }

        if (Utils.token != null){

        }



    }


    private void nextBottomNavigationView(){
        viewPager2 = findViewById(R.id.view_pager_2);
        bottomNavigationView = findViewById(R.id.buttom_nav);

        ViewHomePageAdapter viewHomePageAdapter = new ViewHomePageAdapter(this);
        viewPager2.setAdapter(viewHomePageAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_home){
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.bottom_rating) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.bottom_people) {
                    viewPager2.setCurrentItem(2);
                }
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_rating).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_people).setChecked(true);
                        break;
                }
            }
        });
    }

}
