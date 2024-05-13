package com.example.travelapplication.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.activity.BillActivity;
import com.example.travelapplication.ui.activity.LoginActivity;
import com.example.travelapplication.ui.activity.ProfileActivity;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleFragment extends Fragment {

    private View view;
    Button edit,logout,bill;
    TextView name,email;
    String ma,mail,temp1,temp;
    CircleImageView avatar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile,container,false);

        edit= view.findViewById(R.id.editProfile);
        name= view.findViewById(R.id.name);
        email= view.findViewById(R.id.phoneNumber);
        avatar = view.findViewById(R.id.avatar);
        logout= view.findViewById(R.id.exit);
        bill= view.findViewById(R.id.history);
        callApi();
        EditProfile();
        getBill();
        Log_out();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }
    private void callApi(){
        try {
            if(Utils.token!=null&&Utils.email!=null) {
                ma = Utils.token;
                mail=Utils.email;
                temp1=ma;
                temp=mail;
                ApiGetData.apiGetData.getUserByEmail(temp,temp1).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()&&response.body()!=null){
                            User user= response.body();
                            if(user.getName()==null||user.getName().equals("")){
                                name.setText("Khách hàng "+user.getUser_id());
                            }else {
                                name.setText(user.getName());
                            }
                            email.setText(user.getEmail());
                            if(user.getAvatar()==null||user.getAvatar().equals("")){
                                Picasso.get().load("https://res.cloudinary.com/dmxjhtj5p/image/upload/v1713345418/q6icck5pzy4linkxrao2.jpg").into(avatar);
                            }else{
                                Picasso.get().load(user.getAvatar()).into(avatar);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(),"Hết phiên đăng nhập",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getContext(),LoginActivity.class);
                        startActivityForResult(intent,1);

                    }
                });
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void EditProfile(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
               startActivityForResult(intent,1);
            }
        });
    }
    private void getBill(){
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BillActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    private void Log_out(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

}
