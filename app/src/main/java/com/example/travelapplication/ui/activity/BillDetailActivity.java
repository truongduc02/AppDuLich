package com.example.travelapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.BillAdapter;
import com.example.travelapplication.ui.adapter.BillDetailAdapter;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.RegisterReponse;
import com.example.travelapplication.ui.model.BillDetail;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailActivity extends AppCompatActivity {
    RecyclerView rcv1;
    int ID,status,so;
    String total="",date="",note="",name="";
    ImageView img;
    private List<BillDetail> mListBillDetail;
    RelativeLayout layout;

    TextView tvid,tvtinhtrang,tvngaydat,tvtongtien,tvnote,tvdiachi,tvsodienthoai,tvten,tvconlai;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        rcv1=findViewById(R.id.rcv1);
        img=findViewById(R.id.order_back);
                layout=findViewById(R.id.order_delete);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv1.setLayoutManager(linearLayoutManager);
        mListBillDetail= new ArrayList<>();
        initAll();
        back();
        callApiBillDetail();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestBody status = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                ApiGetData.apiGetData.updatestatus(ID,Utils.token,status).enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                        String res=response.body().getMessage();
                        Toast.makeText(BillDetailActivity.this,res,Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<RegisterReponse> call, Throwable t) {
                        Toast.makeText(BillDetailActivity.this,"Thất bại call",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void initAll()
    {
        tvid=findViewById(R.id.orderID);
        tvtinhtrang=findViewById(R.id.order_detail_status);
        tvngaydat=findViewById(R.id.order_detail_date);
        tvtongtien=findViewById(R.id.order_detail_total_price);
        tvnote=findViewById(R.id.order_comment_view);
        tvdiachi=findViewById(R.id.order_address_view);
        tvsodienthoai=findViewById(R.id.order_phone_view);
        tvten=findViewById(R.id.order_ten_view);
        tvconlai=findViewById(R.id.order_detail_quantity);
        ID=getIntent().getExtras().getInt("IdBill");
        status=getIntent().getExtras().getInt("status");
        so=getIntent().getExtras().getInt("soluong");
        total=getIntent().getExtras().getString("total");
        date=getIntent().getExtras().getString("date");
        note=getIntent().getExtras().getString("note");
        name=getIntent().getExtras().getString("name");
        tvid.setText(String.valueOf(ID));
        tvconlai.setText(String.valueOf(so));

        if(status==1){
            tvtinhtrang.setText("Đã đặt hàng");
            tvtinhtrang.setTextColor(Color.GREEN);
        }else
        {
            tvtinhtrang.setText("Đã hủy");
        }
        tvngaydat.setText(date);
        tvtongtien.setText(total);
        tvnote.setText(note);
        tvten.setText(name);
        ApiGetData.apiGetData.getUserByEmail(Utils.email,Utils.token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user=response.body();
                tvdiachi.setText(user.getAddress());
                tvsodienthoai.setText(user.getPhone_number());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    private void callApiBillDetail(){
        ApiGetData.apiGetData.getDetailBillById(ID, Utils.token).enqueue(new Callback<List<BillDetail>>() {
            @Override
            public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                mListBillDetail=response.body();
                BillDetailAdapter billDetailAdapter= new BillDetailAdapter(mListBillDetail,BillDetailActivity.this);
                rcv1.setAdapter(billDetailAdapter);
            }

            @Override
            public void onFailure(Call<List<BillDetail>> call, Throwable t) {
                Toast.makeText(BillDetailActivity.this,"Thất bại call",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void back(){
        img.setOnClickListener(v -> onBackPressed());
    }
}
