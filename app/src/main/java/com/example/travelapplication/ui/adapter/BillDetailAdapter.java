package com.example.travelapplication.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.DetailActivity;
import com.example.travelapplication.ui.api.Apiservice;
import com.example.travelapplication.ui.model.Bill;
import com.example.travelapplication.ui.model.BillDetail;
import com.example.travelapplication.ui.model.Tour;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder> {
    private List<BillDetail> mListBillDetail;
    private Activity context;
    private int tong=0;
    public BillDetailAdapter(List<BillDetail> mListBillDetail,Activity context){
        this.mListBillDetail=mListBillDetail;
        this.context=context;
    }
    @NonNull
    @Override
    public BillDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tour_item,parent,false);
        return new BillDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailViewHolder holder, int position) {
        BillDetail billDetail= mListBillDetail.get(position);
        if(billDetail==null){
            return;
        }
        holder.tvname.setText(billDetail.getTitle());
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
        df.setCurrency(currency);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        holder.tvmota.setText(billDetail.getDeparture());
        holder.tvdongia.setText(numberFormat.format(billDetail.getUnit_price()));
        holder.tvsoluong.setText(String.valueOf(billDetail.getQuantity()));
        holder.tvthanhtien.setText(numberFormat.format(billDetail.getSub_total()));
        Picasso.get().load(billDetail.getThumbnail()).into(holder.img);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apiservice.apiServiceDetail.getTourDetail(billDetail.getTour_id(), Utils.token).enqueue(new Callback<Tour>() {
                    @Override
                    public void onResponse(Call<Tour> call, Response<Tour> response) {
                        if (response.isSuccessful()){
                            Tour tour = response.body();
                            Intent intent = new Intent(context, DetailActivity.class);
                            intent.putExtra("tourDetail",tour);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Tour> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListBillDetail!=null)
        {
            return mListBillDetail.size();
        }
        return 0;
    }

    public static class BillDetailViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvname,tvmota,tvdongia,tvsoluong,tvthanhtien;
        private final RelativeLayout layout;
        private final ImageView img;
        public BillDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.name);
            tvmota=itemView.findViewById(R.id.mota);
            tvdongia=itemView.findViewById(R.id.dongia);
            tvsoluong=itemView.findViewById(R.id.quantity);
            tvthanhtien=itemView.findViewById(R.id.thanhtien);
            layout=itemView.findViewById(R.id.id_layout);
            img=itemView.findViewById(R.id.p_img);
        }
    }
}
