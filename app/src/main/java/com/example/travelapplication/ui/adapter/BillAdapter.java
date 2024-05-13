package com.example.travelapplication.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.BillDetailActivity;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.model.Bill;
import com.example.travelapplication.ui.model.BillDetail;
import com.example.travelapplication.ui.utils.Utils;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<Bill> mListBill;
    int tong=0;
    private List<BillDetail> mDetailList=new ArrayList<BillDetail>();
    private Activity context;
    public BillAdapter(List<Bill> mListBill, Activity context){
        this.mListBill=mListBill;
        this.context=context;
    }
    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bill_item,parent,false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill= mListBill.get(position);
        if(bill==null){
            return;
        }
        if(bill.getStatus()==1)
        {
            holder.tvtrangthai.setText("Đang đặt hàng");
            holder.tvtrangthai.setTextColor(Color.GREEN);
        }else {
            holder.tvtrangthai.setText("Đã hủy");
        }
        holder.tvid.setText(String.valueOf(bill.getBill_id()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(bill.getCreated_date());
        holder.tvgngay.setText(currentDate);
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
        df.setCurrency(currency);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        holder.tvtongtien.setText(numberFormat.format(bill.getTotal()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillDetailActivity.class);
                intent.putExtra("IdBill",bill.getBill_id());
                intent.putExtra("status",bill.getStatus());
                intent.putExtra("total",numberFormat.format(bill.getTotal()));
                intent.putExtra("date",currentDate);
                intent.putExtra("note",bill.getNote());
                intent.putExtra("name",bill.getName());
                ApiGetData.apiGetData.getDetailBillById(bill.getBill_id(), Utils.token).enqueue(new Callback<List<BillDetail>>() {
                    @Override
                    public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                       mDetailList=response.body();
                      for(BillDetail i: mDetailList)
                      {
                          tong+=i.getQuantity();
                      }
                        intent.putExtra("soluong",tong);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<BillDetail>> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListBill!=null){
            return mListBill.size();
        }
        return 0;
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvtrangthai, tvgngay, tvid, tvtongtien, btndetail;
        private final RelativeLayout layout;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtrangthai= itemView.findViewById(R.id.status);
            tvgngay= itemView.findViewById(R.id.date);
            tvid=itemView.findViewById(R.id.idbill);
            tvtongtien= itemView.findViewById(R.id.total);
            btndetail= itemView.findViewById(R.id.btndetail);
            layout= itemView.findViewById(R.id.id_layout);
        }
    }
}
