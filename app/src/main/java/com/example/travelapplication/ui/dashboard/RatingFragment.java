package com.example.travelapplication.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.LoginActivity;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.model.Rank;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingFragment extends Fragment {

    private View view;
    String ma,mail,temp,temp1;
    TextView ten,diem,conlai,thanhvien,bac,vang,bachkim,tb1,tb2,tb3;
    RelativeLayout layout1;
    ProgressBar progressBar;
    ImageView iv1,iv2,iv3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_rank_member,container,false);

        ten=view.findViewById(R.id.name_rank);
        diem=view.findViewById(R.id.score_rank);
        layout1=view.findViewById(R.id.backgound_rank);
        progressBar= view.findViewById(R.id.exp_bar);
        conlai=view.findViewById(R.id.con_lai);
        thanhvien= view.findViewById(R.id.rank_member);
        bac= view.findViewById(R.id.rank_sliver);
        vang= view.findViewById(R.id.rank_gold);
        bachkim= view.findViewById(R.id.rank_platinum);
        tb1= view.findViewById(R.id.no1);
        tb2= view.findViewById(R.id.no2);
        tb3= view.findViewById(R.id.no3);
        iv1= view.findViewById(R.id.ic_checked);
        iv2= view.findViewById(R.id.ic_checked_2);
        iv3= view.findViewById(R.id.ic_checked_3);

        callApi();
        click_rankthanhvien();
        click_rankbac();
        click_rankvang();
        click_rankbachkim();
        return view;
    }
    private void callApi(){
        try {
            if(Utils.token!=null&&Utils.email!=null) {
                ma = Utils.token;
                mail=Utils.email;
                temp1=ma;
                temp=mail;
                ApiGetData.apiGetData.getRankByEmail(temp,temp1).enqueue(new Callback<Rank>() {
                    @Override
                    public void onResponse(Call<Rank> call, Response<Rank> response) {
                        if(response.isSuccessful()&&response.body()!=null){
                            Rank rank= response.body();
                            ten.setText(rank.getTitle());
                            if(rank.getTitle().equals("Bạch Kim")){
                                int colorInt = Color.parseColor("#7FFFD4");
                                layout1.setBackgroundColor(colorInt);
                                progressBar.setMax(12500);
                                conlai.setText("Đã đạt cấp tối đa!");
                                bachkim.setPaintFlags(bachkim.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                                int flags=bac.getPaintFlags();
                                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                                bac.setPaintFlags(flags);
                                bac.setBackgroundColor(Color.WHITE);
                                int flags_vang=vang.getPaintFlags();
                                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                                vang.setPaintFlags(flags_vang);
                                vang.setBackgroundColor(Color.WHITE);
                                int flags_bk=thanhvien.getPaintFlags();
                                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                                thanhvien.setPaintFlags(flags_bk);
                                thanhvien.setBackgroundColor(Color.WHITE);
                                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                                tb2.setText("Tặng 1500 điểm sinh nhật");
                                tb3.setText("Tặng 100 điểm dịp Lễ (30/4, 02/09), Tết (Dương lịch, Âm lịch)");
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                int Colorint=Color.parseColor("#4AA9BC");
                                bachkim.setBackgroundColor(Colorint);
                            } else if (rank.getTitle().equals("Vàng")) {
                                int colorInt = Color.parseColor("#7FFFD4");
                                layout1.setBackgroundColor(Color.YELLOW);
                                progressBar.setMax(10000);
                                conlai.setText("Còn "+(10000- rank.getScore())+" điểm nữa để lên thành viên Bạch kim");
                                int flags=bac.getPaintFlags();
                                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                                bac.setPaintFlags(flags);
                                int flags_vang=thanhvien.getPaintFlags();
                                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                                thanhvien.setPaintFlags(flags_vang);
                                int flags_bk=bachkim.getPaintFlags();
                                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                                bachkim.setPaintFlags(flags_bk);
                                vang.setPaintFlags(vang.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                                tb2.setText("Tặng 1000 điểm sinh nhật");
                                tb3.setText("Tặng 100 điểm dịp Lễ (30/4, 02/09), Tết (Dương lịch, Âm lịch)");
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                int Colorint=Color.parseColor("#4AA9BC");
                                vang.setBackgroundColor(Colorint);
                                bac.setBackgroundColor(Color.WHITE);
                                bachkim.setBackgroundColor(Color.WHITE);
                                thanhvien.setBackgroundColor(Color.WHITE);
                            }
                            else if (rank.getTitle().equals("Bạc")) {
                                int colorInt = Color.parseColor("#7FFFD4");
                                layout1.setBackgroundColor(Color.GRAY);
                                progressBar.setMax(7500);
                                conlai.setText("Còn "+(7500- rank.getScore())+" điểm nữa để lên thành viên Vàng");
                                int flags=thanhvien.getPaintFlags();
                                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                                thanhvien.setPaintFlags(flags);
                                int flags_vang=vang.getPaintFlags();
                                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                                vang.setPaintFlags(flags_vang);
                                int flags_bk=bachkim.getPaintFlags();
                                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                                bachkim.setPaintFlags(flags_bk);
                                bac.setPaintFlags(bac.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                                tb2.setText("Tặng 500 điểm sinh nhật");
                                tb3.setText("Tặng 100 điểm dịp Lễ (30/4, 02/09), Tết (Dương lịch, Âm lịch)");
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                int Colorint=Color.parseColor("#4AA9BC");
                                bac.setBackgroundColor(Colorint);
                                vang.setBackgroundColor(Color.WHITE);
                                bachkim.setBackgroundColor(Color.WHITE);
                                thanhvien.setBackgroundColor(Color.WHITE);
                            }
                            else
                            {
                                int colorInt = Color.parseColor("#7FFFD4");
                                layout1.setBackgroundColor(Color.BLACK);
                                progressBar.setMax(5000);
                                conlai.setText("Còn "+(5000- rank.getScore())+" điểm nữa để lên thành viên Bạc");
                                thanhvien.setPaintFlags(thanhvien.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                                int flags=bac.getPaintFlags();
                                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                                bac.setPaintFlags(flags);
                                int flags_vang=vang.getPaintFlags();
                                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                                vang.setPaintFlags(flags_vang);
                                int flags_bk=bachkim.getPaintFlags();
                                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                                bachkim.setPaintFlags(flags_bk);
                                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                                tb2.setText("");
                                tb3.setText("");
                                iv2.setVisibility(View.INVISIBLE);
                                iv3.setVisibility(View.INVISIBLE);
                                int Colorint=Color.parseColor("#4AA9BC");
                                thanhvien.setBackgroundColor(Colorint);
                                bac.setBackgroundColor(Color.WHITE);
                                bachkim.setBackgroundColor(Color.WHITE);
                                vang.setBackgroundColor(Color.WHITE);
                            }
                            diem.setText(rank.getScore()+" điểm");
                            progressBar.setProgress(rank.getScore());

                        }
                    }

                    @Override
                    public void onFailure(Call<Rank> call, Throwable t) {
                        Toast.makeText(getContext(),"Thất bại call",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void click_rankbac(){
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flags=thanhvien.getPaintFlags();
                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                thanhvien.setPaintFlags(flags);
                int flags_vang=vang.getPaintFlags();
                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                vang.setPaintFlags(flags_vang);
                int flags_bk=bachkim.getPaintFlags();
                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                bachkim.setPaintFlags(flags_bk);
                bac.setPaintFlags(bac.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                tb2.setText("Tặng 500 điểm sinh nhật");
                tb3.setText("Tặng 100 điểm dịp Lễ (30/4, 02/09), Tết (Dương lịch, Âm lịch)");
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                int Colorint=Color.parseColor("#4AA9BC");
                bac.setBackgroundColor(Colorint);
                vang.setBackgroundColor(Color.WHITE);
                bachkim.setBackgroundColor(Color.WHITE);
                thanhvien.setBackgroundColor(Color.WHITE);
            }
        });
    }
    private void click_rankthanhvien(){
        thanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhvien.setPaintFlags(thanhvien.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                int flags=bac.getPaintFlags();
                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                bac.setPaintFlags(flags);
                int flags_vang=vang.getPaintFlags();
                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                vang.setPaintFlags(flags_vang);
                int flags_bk=bachkim.getPaintFlags();
                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                bachkim.setPaintFlags(flags_bk);
                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                tb2.setText("");
                tb3.setText("");
                iv2.setVisibility(View.INVISIBLE);
                iv3.setVisibility(View.INVISIBLE);
                int Colorint=Color.parseColor("#4AA9BC");
                thanhvien.setBackgroundColor(Colorint);
                bac.setBackgroundColor(Color.WHITE);
                bachkim.setBackgroundColor(Color.WHITE);
                vang.setBackgroundColor(Color.WHITE);
            }
        });
    }
    private void click_rankvang(){
        vang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flags=bac.getPaintFlags();
                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                bac.setPaintFlags(flags);
                int flags_vang=thanhvien.getPaintFlags();
                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                thanhvien.setPaintFlags(flags_vang);
                int flags_bk=bachkim.getPaintFlags();
                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                bachkim.setPaintFlags(flags_bk);
                vang.setPaintFlags(vang.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                tb2.setText("Tặng 1000 điểm sinh nhật");
                tb3.setText("Tặng 100 điểm dịp Lễ (30/4, 02/09), Tết (Dương lịch, Âm lịch)");
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                int Colorint=Color.parseColor("#4AA9BC");
                vang.setBackgroundColor(Colorint);
                bac.setBackgroundColor(Color.WHITE);
                bachkim.setBackgroundColor(Color.WHITE);
                thanhvien.setBackgroundColor(Color.WHITE);
            }
        });
    }
    private void click_rankbachkim(){
        bachkim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bachkim.setPaintFlags(bachkim.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                int flags = bac.getPaintFlags();
                flags &= ~Paint.UNDERLINE_TEXT_FLAG;
                bac.setPaintFlags(flags);
                bac.setBackgroundColor(Color.WHITE);
                int flags_vang = vang.getPaintFlags();
                flags_vang &= ~Paint.UNDERLINE_TEXT_FLAG;
                vang.setPaintFlags(flags_vang);
                vang.setBackgroundColor(Color.WHITE);
                int flags_bk = thanhvien.getPaintFlags();
                flags_bk &= ~Paint.UNDERLINE_TEXT_FLAG;
                thanhvien.setPaintFlags(flags_bk);
                thanhvien.setBackgroundColor(Color.WHITE);
                tb1.setText("Cộng điểm sau khi sử dụng dịch vụ tại Traveally");
                tb2.setText("Tặng 1500 điểm sinh nhật");
                tb3.setText("Tặng 100 điểm dịp Lễ (30/4, 02/09), Tết (Dương lịch, Âm lịch)");
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                int Colorint = Color.parseColor("#4AA9BC");
                bachkim.setBackgroundColor(Colorint);
            }
        });
    }

}
