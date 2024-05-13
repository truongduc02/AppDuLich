package com.example.travelapplication.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.PhotoViewPageAdapter;
import com.example.travelapplication.ui.model.Tour;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class CTSPActivity extends AppCompatActivity {


    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Tour> mtourList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        viewPager2 = findViewById(R.id.view_page_photo);
        circleIndicator3 = findViewById(R.id.circle_indicator_3);

        mtourList = getListPhoto();
        PhotoViewPageAdapter adapter = new PhotoViewPageAdapter(mtourList);
        viewPager2.setAdapter(adapter);

        circleIndicator3.setViewPager(viewPager2);


    }

    private List<Tour> getListPhoto(){
        List<Tour> list = new ArrayList<>();
//        list.add(new Tour(1,R.drawable.anh1,"Tour 1","15000"));
//        list.add(new Tour(2,R.drawable.anh2,"Tour 2","15000"));
//        list.add(new Tour(1,R.drawable.anh3,"Tour 3","15000"));

        return list;
    }

}
