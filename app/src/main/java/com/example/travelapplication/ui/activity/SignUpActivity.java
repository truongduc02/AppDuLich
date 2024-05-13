package com.example.travelapplication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.RegisterReponse;
import com.example.travelapplication.ui.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText email1,password1,cpassword1;
    Button btndangky;
    TextView btndangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email1=findViewById(R.id.email);
        password1=findViewById(R.id.password);
        cpassword1=findViewById(R.id.confirmPassword);
        btndangky=findViewById(R.id.loginBtn);
        btndangnhap= findViewById(R.id.dangnhap);
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        callApi();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                SignUpActivity.this.startActivity(intent);
                ((Activity) SignUpActivity.this).finish();
            }
        });
    }
    private void callApi() throws JSONException {
        String username=email1.getText().toString().trim();
        String pass=password1.getText().toString().trim();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email",username);
        jsonObject.put("password",pass);
        JsonObject xyz= new JsonParser().parse(jsonObject.toString()).getAsJsonObject();
        ApiGetData.apiGetData.register(xyz).enqueue(new Callback<RegisterReponse>() {
            @Override
            public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                String res = response.body().getMessage();
                if(res.equals("Đăng ký tài khoản thành công!")){
                    Toast.makeText(SignUpActivity.this, res, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    SignUpActivity.this.startActivity(intent);
                    ((Activity) SignUpActivity.this).finish();
                }
                else {
                    Toast.makeText(SignUpActivity.this, res, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterReponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
