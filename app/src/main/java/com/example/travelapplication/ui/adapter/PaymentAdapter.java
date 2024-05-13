package com.example.travelapplication.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<GioHang> mListGioHang;
    private Activity context;
    public PaymentAdapter(List<GioHang> mListGioHang,Activity context){
        this.mListGioHang=mListGioHang;
        this.context=context;
    }
    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product_item,parent,false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        GioHang giohang= mListGioHang.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#"); // Chỉ hiển thị phần số nguyên
        decimalFormat.setMaximumIntegerDigits(decimalFormat.getMaximumIntegerDigits() - 1); // Loại bỏ dấu phẩy
        holder.tvquantity.setText(decimalFormat.format(giohang.getQuantity_selected())+"x");
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
        df.setCurrency(currency);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        holder.tvproductname.setText(giohang.getTitle());
        holder.tvprice.setText(numberFormat.format(giohang.getPrice()));
        Picasso.get().load(giohang.getThumbnail()).into(holder.img);
        holder.tvdescription.setText(giohang.getDeparture_selected());
    }

    @Override
    public int getItemCount() {
        if(mListGioHang!=null)
        {
            return mListGioHang.size();
        }
        return 0;
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvquantity, tvproductname,tvprice, tvdescription;
        private final ImageView img;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvquantity=itemView.findViewById(R.id.quantity);
            img=itemView.findViewById(R.id.p_img);
            tvproductname=itemView.findViewById(R.id.product_name);
            tvprice=itemView.findViewById(R.id.cart_price_view);
            tvdescription=itemView.findViewById(R.id.discription);
        }
    }
}
