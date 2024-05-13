package com.example.travelapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.GioHang2Adapter;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.EventBus.TinhTongEvent;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHang2Activity extends AppCompatActivity {

    private TextView gioHangTrong, tongTien, btnMuaHang;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private GioHang2Adapter gioHang2Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang2);

        initView();
        initControl();
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gioHang2Adapter.tinhTongSoLuongSP()!=0) {
                    Intent intent = new Intent(GioHang2Activity.this, PaymentActivity.class);
                    intent.putExtra("tongtien", gioHang2Adapter.tinhTongTien());
                    intent.putExtra("soluong", gioHang2Adapter.tinhTongSoLuongSP());
                    startActivityForResult(intent, 1);
                }else
                    Toast.makeText(GioHang2Activity.this,"Chưa có sản phẩm nào để thanh toán",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gioHang2Adapter.getListGioHang();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        gioHang2Adapter = new GioHang2Adapter(this, new ArrayList<>()); // Khởi tạo adapter mới
        recyclerView.setAdapter(gioHang2Adapter); // Đặt adapter vào recyclerView

        gioHang2Adapter.getListGioHang(); // Cập nhật danh sách giỏ hàng

        gioHang2Adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                double tongTienCart = gioHang2Adapter.tinhTongTien(); // Gọi phương thức tính tổng tiền từ adapter
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tongTien.setText(decimalFormat.format(tongTienCart)); // Hiển thị tổng tiền lên textView "tongTien"
            }
        });

    }

    private void initView() {
        gioHangTrong = findViewById(R.id.tv_gio_hang_trong);
        toolbar = findViewById(R.id.toolbar);
        tongTien = findViewById(R.id.tv_tong_tien2);
        recyclerView = findViewById(R.id.rcv_gio_hang);
        btnMuaHang = findViewById(R.id.btnMuaHang);
    }


}