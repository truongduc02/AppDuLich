package com.example.travelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapplication.ui.activity.FragmentActivity;

public class test extends AppCompatActivity {

    private TextView tv_all_tour;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_category);

        tv_all_tour = findViewById(R.id.tv_all_tour);
        tv_all_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(test.this, FragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
