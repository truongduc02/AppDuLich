package com.example.travelapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.adapter.GioHang2Adapter;
import com.example.travelapplication.ui.adapter.PhotoViewPageAdapter;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.dashboard.CartFragment;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.model.UserModel;
import com.example.travelapplication.ui.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageBtnBack;
    private ImageView imageView;
    private TextView tvNameTour;
    private TextView tvPriceTour;
    private TextView tvItineraryTour;
    private TextView tvScheduleTour;
    private TextView tvStartTour;
    private TextView tvVehiclesTour,tvdescription;
    private TextView btrThemSP, btrMuaHang;
    private NotificationBadge badge;
    private Tour tour;
    private GioHang2Adapter gioHang2Adapter;
    private int soLuong = 1,temp;
    private TextView date1, date2, date3;
    private List<Tour> tourList;
    private List<GioHang> gioHangList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        initData();
        initControl();


    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            tour = (Tour) intent.getSerializableExtra("tourDetail");
            // Hiển thị thông tin chi tiết của tour
            Picasso.get().load(tour.getThumbnail()).into(imageView);
            tvNameTour.setText(tour.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvPriceTour.setText(decimalFormat.format(tour.getPrice()));
            tvItineraryTour.setText(tour.getItinerary());
            tvScheduleTour.setText(tour.getSchedule());
            tvVehiclesTour.setText(tour.getVehicles());
            tvdescription.setText(Html.fromHtml(tour.getDescription(),Html.FROM_HTML_MODE_LEGACY));

        }
    }

    private void initView() {
        // ánh xạ
        imageView = findViewById(R.id.img_photo_2);
        tvNameTour = findViewById(R.id.tv_name_tour_2);
        tvPriceTour = findViewById(R.id.tv_price_tour_2);
        tvItineraryTour = findViewById(R.id.tv_itinerary);
        tvScheduleTour = findViewById(R.id.tv_schedule);
        tvStartTour = findViewById(R.id.tv_start);
        tvVehiclesTour = findViewById(R.id.tv_vehicles);
        btrThemSP = findViewById(R.id.tv_them_gio_hang);
        badge = findViewById(R.id.noti_soluong);
        tvdescription=findViewById(R.id.motaTour);
        //btrMuaHang = findViewById(R.id.btnMuaHangDetail);



        FrameLayout frameLayout = findViewById(R.id.fragmentGioHang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),GioHang2Activity.class);
            startActivity(intent);
           }
       });

        // button back
        imageBtnBack = findViewById(R.id.img_back);
        imageBtnBack.setOnClickListener(v -> onBackPressed());
    }

    private void initControl() {
        btrThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(Gravity.BOTTOM);
            }
        });


    }

    private void openDialog(int gratity){
        final Dialog dialog = new Dialog(this);
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
        date1 = dialog.findViewById(R.id.Date1);
        date2 = dialog.findViewById(R.id.tvDate2);
        date3 = dialog.findViewById(R.id.tvDate3);
        TextView btnmuahang= dialog.findViewById(R.id.btnmuahang);

        Picasso.get().load(tour.getThumbnail()).into(imgAnh);
        tvName.setText(tour.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvPrice.setText(decimalFormat.format(tour.getPrice()));
        String[] departure = tour.getDeparture().toArray(new String[0]);
        date1.setText(departure[0]);
        date2.setText(departure[1]);
        date3.setText(departure[2]);
        
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

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllTextViews();
                date1.setTextColor(Color.RED); // Đổi màu text khi được chọn

            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllTextViews();
                date2.setTextColor(Color.RED); // Đổi màu text khi được chọn
            }
        });

        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllTextViews();
                date3.setTextColor(Color.RED); // Đổi màu text khi được chọn
            }
        });

        tvThemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang(soLuong);
                dialog.dismiss();
            }
        });
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang(soLuong);
                dialog.dismiss();
                try {
                    if(String.valueOf(Utils.user_current.getUser_id())!=null) {
                        temp=Utils.user_current.getUser_id();
                        Apiservice.apiService.getListGioHang(temp, Utils.token).enqueue(new Callback<List<GioHang>>() {
                            @Override
                            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                                if (response.isSuccessful()){
                                    List<GioHang> list = response.body();
                                    gioHangList = list; // cập nhật danh sách thông tin giỏ hàng
                                    Intent intent= new Intent(DetailActivity.this,PaymentActivity.class);
                                    intent.putExtra("tongtien",tinhTongTien());
                                    intent.putExtra("soluong",tinhTongSoLuongSP());
                                    startActivityForResult(intent,1);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                                // Toast.makeText(PaymentActivity.this, "Thất bại call", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }

            }
        });



        dialog.show();
    }
    public float tinhTongTien() {
        float tongTien = 0;
        for (GioHang gioHang : gioHangList) {
            tongTien += gioHang.getSub_total(); // Tính tổng tiền bằng cách cộng dồn tổng giá của từng sản phẩm
        }
        return tongTien;
    }

    public int tinhTongSoLuongSP(){
        int soluong = 0;
        for (GioHang gioHang1 : gioHangList){
            soluong += gioHang1.getQuantity_selected();
        }
        return soluong;
    }

    private void clearAllTextViews() {
        date1.setTextColor(Color.BLACK); // Màu mặc định
        date2.setTextColor(Color.BLACK); // Màu mặc định
        date3.setTextColor(Color.BLACK); // Màu mặc định
    }

    private void themGioHang(int quantity) {

        int userId = Utils.user_current.getUser_id();
        int tourId = tour.getTour_id();
        String token = Utils.token;
        String finaldate = null;

        // Tiến hành xác định giá trị nào đang được chọn
        if (date1.getCurrentTextColor() == Color.RED) {
            finaldate = date1.getText().toString();
        } else if (date2.getCurrentTextColor() == Color.RED) {
            finaldate = date2.getText().toString();
        } else if (date3.getCurrentTextColor() == Color.RED) {
            finaldate = date3.getText().toString();
        }


        UserModel userModel = new UserModel(userId,tourId,quantity,finaldate,token);

        Apiservice.apiService.postTourToDB(userModel).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(DetailActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "loi", Toast.LENGTH_SHORT).show();
            }
        });
    }

}