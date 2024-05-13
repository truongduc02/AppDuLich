package com.example.travelapplication.ui.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.DetailActivity;
import com.example.travelapplication.ui.activity.GioHang2Activity;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.api.RegisterReponse;
import com.example.travelapplication.ui.model.EventBus.TinhTongEvent;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.my_interface.ImageClickCart;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHang2Adapter extends RecyclerView.Adapter<GioHang2Adapter.MyViewHolder>{

    Context context;
    private List<GioHang> gioHangList;
    GioHang gioHang;
    int temp;

    public GioHang2Adapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
        getListGioHang();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        gioHang = gioHangList.get(position);
        holder.bindData(gioHang);
        Picasso.get().load(gioHang.getThumbnail()).into(holder.img_gioHang);

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_gioHang,imgTru, imgCong;
        private TextView tv_gioHang_tensp;
        private TextView tv_gioHang_gia;
        private TextView tv_gioHang_soLuong;
        private TextView tv_gioHang_gia2,tvdescription;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_gioHang = itemView.findViewById(R.id.img_giohang);
            tv_gioHang_tensp = itemView.findViewById(R.id.tv_name_giohang);
            tv_gioHang_gia = itemView.findViewById(R.id.tv_gia_giohang);
            tv_gioHang_soLuong = itemView.findViewById(R.id.quantity_giohang);
            tv_gioHang_gia2 = itemView.findViewById(R.id.gia_giohang2);
            imgTru = itemView.findViewById(R.id.img_giohang_tru);
            imgCong = itemView.findViewById(R.id.img_giohang_cong);
            tvdescription=itemView.findViewById(R.id.tv_mota_giohang);

            // Bắt sự kiện click vào imgTru để giảm số lượng
            imgTru.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    GioHang gioHang = gioHangList.get(position);
                    Log.d("test2",String.valueOf(position));
                    int currentQuantity = gioHang.getQuantity_selected();
                    if (currentQuantity > 1) {
                        minus(gioHang.getTour_id(),gioHang.getDeparture_selected());
                    } else if (currentQuantity == 1) {
                        showDelete(gioHang.getTour_id(),gioHang.getDeparture_selected()); // xoá sp
                    }
                }
            });

            imgCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    GioHang gioHang1 = gioHangList.get(position);
                    addCart(gioHang1.getTour_id(),gioHang1.getDeparture_selected());
                }
            });

        }
        public void bindData(GioHang gioHang) {
            tv_gioHang_tensp.setText(gioHang.getTitle()); // Hiển thị tiêu đề sản phẩm
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tv_gioHang_gia.setText(decimalFormat.format(gioHang.getPrice())); // Hiển thị giá sản phẩm
            tv_gioHang_gia2.setText(decimalFormat.format(gioHang.getSub_total())); // Hiển thị tổng giá của sản phẩm
            tv_gioHang_soLuong.setText(String.valueOf(gioHang.getQuantity_selected()));
            tvdescription.setText(gioHang.getDeparture_selected());

        }
    }

    private void showDelete(int tourId, String depature) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn xóa sản phẩm khỏi giỏ hàng?");
        builder.setPositiveButton("Đồng ý", (dialog, which) -> deleteCart(tourId,depature));
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void deleteCart(int tourId, String depature) {
        Apiservice.apiService.deleteCart(Utils.user_current.getUser_id(), tourId,depature,Utils.token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getListGioHang();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
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
    public void getListGioHang(){
        try {
            if(String.valueOf(Utils.user_current.getUser_id())!=null) {
                temp=Utils.user_current.getUser_id();
                Apiservice.apiService.getListGioHang(temp, Utils.token).enqueue(new Callback<List<GioHang>>() {
                    @Override
                    public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                        if (response.isSuccessful()){
                            List<GioHang> list = response.body();
                            gioHangList = list; // cập nhật danh sách thông tin giỏ hàng
                            notifyDataSetChanged(); // cập nhật giao diện khi có dữ liệu mới

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


    private void minus(int tourId, String departure){
        Apiservice.apiService.postChangeCart(Utils.user_current.getUser_id(), tourId,"decrease",departure,Utils.token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getListGioHang();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
   }

   private void addCart(int tourId, String departure){
        Apiservice.apiService.postChangeCart(Utils.user_current.getUser_id(), tourId,"increase", departure, Utils.token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getListGioHang();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
   }
}
