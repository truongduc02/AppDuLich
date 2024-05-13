package com.example.travelapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.TourAdapter;
import com.example.travelapplication.ui.adapter.ViewPageAdapter;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivity extends AppCompatActivity {

    private ImageButton imgBtnHome;
    private ImageButton imgSearch;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPageAdapter viewPageAdapter;
    String temp;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        initView();
        temp=getIntent().getExtras().getString("category");
        Log.e("Vuong3",temp);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setIcon(R.drawable.anh1);
                    tab.setText("Tất cả");
                    break;
                case 1:
                    tab.setIcon(R.drawable.anh1);
                    tab.setText("Nội địa");
                    break;
                case 2:
                    tab.setIcon(R.drawable.anh1);
                    tab.setText("Quốc tế");
                    break;
            }
        }).attach();

    }

    private void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        imgBtnHome = findViewById(R.id.ic_button_home);
        imgSearch = findViewById(R.id.img_search);
        
        imgBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
