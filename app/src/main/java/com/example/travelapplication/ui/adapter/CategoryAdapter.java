package com.example.travelapplication.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.ArticleActivity;
import com.example.travelapplication.ui.activity.DetailActivity;
import com.example.travelapplication.ui.activity.FragmentActivity;
import com.example.travelapplication.ui.activity.GioHang2Activity;
import com.example.travelapplication.ui.activity.PaymentActivity;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.Article;
import com.example.travelapplication.ui.model.Category;
import com.example.travelapplication.ui.model.GioHang;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.model.UserModel;
import com.example.travelapplication.ui.my_interface.IClickItemArticleListener;
import com.example.travelapplication.ui.my_interface.IClickItemTourListener;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private List<Category> categoryList;
    private List<GioHang> gioHangList;
    private int soLuong ,temp;
    private TextView date1, date2, date3;
    private int  tourId;

    public CategoryAdapter(Context aContext) {
        this.context = aContext;
    }

    public void setData(List<Category> list){

        this.categoryList = list;

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if(category == null){
            return;
        }

        holder.tvNameCategory.setText(category.getNameCategory());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.rcvTour.setLayoutManager(linearLayoutManager);

        TourAdapter tourAdapter = new TourAdapter();
        tourAdapter.setData(category.getTours(), new IClickItemTourListener() {
            @Override
            public void onClickItemTour(Tour tour) {
                tourId = tour.getTour_id();
                getTourDetail(tourId);
            }

            @Override
            public void onClickAddToCart(Tour tour) {
                soLuong=1;
                tourId = tour.getTour_id();
                openDialog(Gravity.BOTTOM,tour);
            }

        });

        holder.rcvTour.setAdapter(tourAdapter);

        // hien thi list Tin tuc
        LinearLayoutManager articleLinearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvArticle.setLayoutManager(articleLinearLayoutManager);
        ArticleAdapter articleAdapter = new ArticleAdapter();
        articleAdapter.setData(category.getArticles(), new IClickItemArticleListener() {
            @Override
            public void onClickItemArticle(Article article) {
                int articleId = article.getArticle_id();
                //getArticleDetail(articleId);
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("articleDetail",articleId);
                context.startActivity(intent);
            }
        });
        holder.rcvArticle.setAdapter(articleAdapter);

    }

    @Override
    public int getItemCount() {
        if(categoryList != null){
            return categoryList.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameCategory;
        private TextView tvAllTour;
        private RecyclerView rcvTour;
        private RecyclerView rcvArticle;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            tvAllTour = itemView.findViewById(R.id.tv_all_tour);
            rcvTour = itemView.findViewById(R.id.rcv_tour_home);
            rcvArticle = itemView.findViewById(R.id.rcv_article_home);

            tvAllTour.setOnClickListener(view -> {
                Intent intent = new Intent(context, FragmentActivity.class);
                intent.putExtra("category",tvNameCategory.getText().toString());
                Log.e("Vuong2",tvNameCategory.getText().toString());
                context.startActivity(intent);
            });
        }
    }

    private void getTourDetail(int tourId){
        Apiservice.apiServiceDetail.getTourDetail(tourId,Utils.token).enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()){
                    Tour tour = response.body();
                    //chuyen sang trang detail
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("tourDetail",tour);
                    Log.d("test", String.valueOf(tour));
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {
                t.printStackTrace(); // In ra lỗi nếu có
            }
        });
    }

//    private void getArticleDetail(int articleId){
//        Apiservice.apiServiceArticleDetail.getArticleDetail(articleId, Utils.token).enqueue(new Callback<Article>() {
//            @Override
//            public void onResponse(Call<Article> call, Response<Article> response) {
//                if (response.isSuccessful()){
//                    Article article = response.body();
//
//                    Intent intent = new Intent(context, ArticleActivity.class);
//                    intent.putExtra("articleDetail",article);
//                    context.startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Article> call, Throwable t) {
//
//            }
//        });
//    }

    private void openDialog(int gratity, Tour tour){
        final Dialog dialog = new Dialog(context);
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
        TextView tvmuahang= dialog.findViewById(R.id.btnmuahang);
        date1 = dialog.findViewById(R.id.Date1);
        date2 = dialog.findViewById(R.id.tvDate2);
        date3 = dialog.findViewById(R.id.tvDate3);


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
        tvmuahang.setOnClickListener(new View.OnClickListener() {
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
                                    Intent intent= new Intent(context,PaymentActivity.class);
                                    intent.putExtra("tongtien",tinhTongTien());
                                    intent.putExtra("soluong",tinhTongSoLuongSP());
                                    context.startActivity(intent);
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
                Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "loi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

