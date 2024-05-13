package com.example.travelapplication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.BillDetailAdapter;
import com.example.travelapplication.ui.adapter.PaymentAdapter;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.api.RegisterReponse;
import com.example.travelapplication.ui.model.Bill;
import com.example.travelapplication.ui.model.BillDetail;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.utils.Utils;
import com.example.travelapplication.ui.model.CreateOrder;

import org.json.JSONObject;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
    List<GioHang> mListGioHang;
    RecyclerView rcv;
    ImageButton img;
    EditText txtdiachi,txtten,txtsdt,txtnote;
    float tongtien;
    TextView tvsoluong,tvtamtinh,tvtongtien;
    CardView btn,btn1;
    int soluong,temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        rcv=findViewById(R.id.rcv2);
        img=findViewById(R.id.img_back);
        txtdiachi=findViewById(R.id.order_address_view);
        txtten=findViewById(R.id.order_name_view);
        txtsdt=findViewById(R.id.order_phone_view);
        txtnote=findViewById(R.id.txtluuy);
        tvsoluong=findViewById(R.id.quantity);
        tvtamtinh=findViewById(R.id.tamtinh);
        tvtongtien=findViewById(R.id.cart_total_price_view);
        btn=findViewById(R.id.check_out_btn);
        btn1=findViewById(R.id.check_out_btn1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        mListGioHang= new ArrayList<>();
        tongtien=getIntent().getExtras().getFloat("tongtien");
        soluong=getIntent().getExtras().getInt("soluong");
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
        df.setCurrency(currency);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        tvtamtinh.setText(numberFormat.format(tongtien));
        tongtien+=10000;
        tvtongtien.setText(numberFormat.format(tongtien));
        tvsoluong.setText("Sản phẩm đã chọn ("+String.valueOf(soluong)+")");
        img.setOnClickListener(v -> onBackPressed());
        img.setOnClickListener(v -> onBackPressed());

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        callApi();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestZalo();


            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diem=soluong*250;
                int tam= Utils.score+diem;
                Utils.score=tam;
                final RequestBody score= RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(tam));
                ApiGetData.apiGetData.updatescore(Utils.email,Utils.token,score).enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {

                    }

                    @Override
                    public void onFailure(Call<RegisterReponse> call, Throwable t) {

                    }
                });
                if(tam>=5000&&Utils.rank_id==5)
                {
                    final RequestBody rankid=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(3));
                    final RequestBody resetscore=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                    ApiGetData.apiGetData.updateRank(Utils.email,Utils.token,rankid,resetscore).enqueue(new Callback<RegisterReponse>() {
                        @Override
                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                Utils.rank_id=3;
                        }

                        @Override
                        public void onFailure(Call<RegisterReponse> call, Throwable t) {

                        }
                    });
                }
                if(tam>=7500&&Utils.rank_id==3)
                {
                    final RequestBody rankid=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(2));
                    final RequestBody resetscore=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                    ApiGetData.apiGetData.updateRank(Utils.email,Utils.token,rankid,resetscore).enqueue(new Callback<RegisterReponse>() {
                        @Override
                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                            Utils.rank_id=2;
                        }

                        @Override
                        public void onFailure(Call<RegisterReponse> call, Throwable t) {

                        }
                    });
                }
                if(tam>=10000&&Utils.rank_id==2)
                {
                    final RequestBody rankid=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
                    final RequestBody resetscore=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                    ApiGetData.apiGetData.updateRank(Utils.email,Utils.token,rankid,resetscore).enqueue(new Callback<RegisterReponse>() {
                        @Override
                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                            Utils.rank_id=1;
                        }

                        @Override
                        public void onFailure(Call<RegisterReponse> call, Throwable t) {

                        }
                    });
                }
                final RequestBody diachi = RequestBody.create(MediaType.parse("multipart/form-data"), txtdiachi.getText().toString().trim());
                final RequestBody sdt = RequestBody.create(MediaType.parse("multipart/form-data"), txtsdt.getText().toString().trim());
                ApiGetData.apiGetData.updateDiachiVaSDTByEmail(Utils.email,Utils.token,diachi,sdt).enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {

                    }

                    @Override
                    public void onFailure(Call<RegisterReponse> call, Throwable t) {

                    }
                });
                Bill bill= new Bill(txtten.getText().toString().trim(),0,tongtien,txtnote.getText().toString().trim(),mListGioHang,Utils.email);
                ApiGetData.apiGetData.createBill(bill,Utils.token).enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                        String res=response.body().getMessage();
                        Intent intent= new Intent(PaymentActivity.this,MomoActivity.class);
                        intent.putExtra("web",res);
                        PaymentActivity.this.startActivity(intent);
                        ((Activity) PaymentActivity.this).finish();
                       // Toast.makeText(PaymentActivity.this,"Thanh toán thành công",Toast.LENGTH_SHORT).show();
//                        String paymentUrl = "http://192.168.1.6/json-api/travel-api/mvc/modules/payment/momo/atm.php";
//
                    }

                    @Override
                    public void onFailure(Call<RegisterReponse> call, Throwable t) {
                        Toast.makeText(PaymentActivity.this,"Thanh toán không thành công",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private  void requestZalo(){
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder("100000");
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int diem=soluong*250;
                                int tam= Utils.score+diem;
                                Utils.score=tam;
                                final RequestBody score= RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(tam));
                                ApiGetData.apiGetData.updatescore(Utils.email,Utils.token,score).enqueue(new Callback<RegisterReponse>() {
                                    @Override
                                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<RegisterReponse> call, Throwable t) {

                                    }
                                });
                                if(tam>=5000&&Utils.rank_id==5)
                                {
                                    final RequestBody rankid=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(3));
                                    final RequestBody resetscore=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                                    ApiGetData.apiGetData.updateRank(Utils.email,Utils.token,rankid,resetscore).enqueue(new Callback<RegisterReponse>() {
                                        @Override
                                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                            Utils.rank_id=3;
                                        }

                                        @Override
                                        public void onFailure(Call<RegisterReponse> call, Throwable t) {

                                        }
                                    });
                                }
                                if(tam>=7500&&Utils.rank_id==3)
                                {
                                    final RequestBody rankid=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(2));
                                    final RequestBody resetscore=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                                    ApiGetData.apiGetData.updateRank(Utils.email,Utils.token,rankid,resetscore).enqueue(new Callback<RegisterReponse>() {
                                        @Override
                                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                            Utils.rank_id=2;
                                        }

                                        @Override
                                        public void onFailure(Call<RegisterReponse> call, Throwable t) {

                                        }
                                    });
                                }
                                if(tam>=10000&&Utils.rank_id==2)
                                {
                                    final RequestBody rankid=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
                                    final RequestBody resetscore=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                                    ApiGetData.apiGetData.updateRank(Utils.email,Utils.token,rankid,resetscore).enqueue(new Callback<RegisterReponse>() {
                                        @Override
                                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                            Utils.rank_id=1;
                                        }

                                        @Override
                                        public void onFailure(Call<RegisterReponse> call, Throwable t) {

                                        }
                                    });
                                }
                                final RequestBody diachi = RequestBody.create(MediaType.parse("multipart/form-data"), txtdiachi.getText().toString().trim());
                                final RequestBody sdt = RequestBody.create(MediaType.parse("multipart/form-data"), txtsdt.getText().toString().trim());
                                ApiGetData.apiGetData.updateDiachiVaSDTByEmail(Utils.email,Utils.token,diachi,sdt).enqueue(new Callback<RegisterReponse>() {
                                    @Override
                                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<RegisterReponse> call, Throwable t) {

                                    }
                                });
                                Bill bill= new Bill(txtten.getText().toString().trim(),0,tongtien,txtnote.getText().toString().trim(),mListGioHang,Utils.email);
                                ApiGetData.apiGetData.createBill(bill,Utils.token).enqueue(new Callback<RegisterReponse>() {
                                    @Override
                                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                        //String res=response.body().getMessage();
                                        Toast.makeText(PaymentActivity.this,"Thanh toán thành công",Toast.LENGTH_SHORT).show();
//                        String paymentUrl = "http://192.168.1.20/json-api/travel-api/mvc/modules/payment/momo/atm.php";
                                        Intent intent = new Intent(PaymentActivity.this, MainActivity2.class);
                                        PaymentActivity.this.startActivity(intent);
                                        ((Activity) PaymentActivity.this).finish();
                                    }

                                    @Override
                                    public void onFailure(Call<RegisterReponse> call, Throwable t) {
                                        Toast.makeText(PaymentActivity.this,"Thanh toán không thành công",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void callApi(){
        try {
                if(String.valueOf(Utils.user_current.getUser_id())!=null) {
                    temp=Utils.user_current.getUser_id();
                    Apiservice.apiService.getListGioHang(temp, Utils.token).enqueue(new Callback<List<GioHang>>() {
                        @Override
                        public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                            List <GioHang> list= response.body();
                            mListGioHang = list;
                            PaymentAdapter paymentAdapter = new PaymentAdapter(mListGioHang, PaymentActivity.this);
                            rcv.setAdapter(paymentAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<GioHang>> call, Throwable t) {
                            Toast.makeText(PaymentActivity.this, "Thất bại call", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        ApiGetData.apiGetData.getUserByEmail(Utils.email,Utils.token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user=response.body();
                txtdiachi.setText(user.getAddress());
                txtsdt.setText(user.getPhone_number());
                txtten.setText(user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
