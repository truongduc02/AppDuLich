package com.example.travelapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.BillAdapter;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.model.Bill;
import com.example.travelapplication.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {
    RecyclerView rcv;
    ImageView img;
    TextView btn;
    private List<Bill> mListBill;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        rcv=findViewById(R.id.cart_order_recyclerview);
        img=findViewById(R.id.delete_cart_imageView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);


        mListBill= new ArrayList<>();
        img.setOnClickListener(v -> onBackPressed());
        callApi();

    }
    private void callApi(){
        ApiGetData.apiGetData.getBillByEmail(Utils.email,Utils.token).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                mListBill=response.body();
                BillAdapter billAdapter= new BillAdapter(mListBill,BillActivity.this);
                rcv.setAdapter(billAdapter);
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(BillActivity.this,"Chưa có đơn hàng nào",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
    }
}
