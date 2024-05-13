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

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.LoginReponse;
import com.example.travelapplication.ui.api.RegisterReponse;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.model.UserModel;
import com.example.travelapplication.ui.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText email1,password1;
    Button btndangnhap;
    TextView btndangky,btnquenpass;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email1=findViewById(R.id.email);
        password1=findViewById(R.id.password);
        btndangnhap=findViewById(R.id.loginBtn);
        btndangky=findViewById(R.id.dangky);
        btnquenpass=findViewById(R.id.forget_password);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callApi();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(intent);
                ((Activity) LoginActivity.this).finish();
            }
        });
        btnquenpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                LoginActivity.this.startActivity(intent);
                ((Activity) LoginActivity.this).finish();
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

        ApiGetData.apiGetData.login(xyz).enqueue(new Callback<LoginReponse>() {
            @Override
            public void onResponse(Call<LoginReponse> call, Response<LoginReponse> response) {
                String res = response.body().getMessage();
                Utils.token=response.body().getToken();
                Utils.email=response.body().getEmail();
                Utils.score=response.body().getScore();
                Utils.rank_id=response.body().getRank_id();
                callApi2(username);
                if(res.equals("Đăng nhập thành công!")){
                    Toast.makeText(LoginActivity.this, res, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                    LoginActivity.this.startActivity(intent);
                    ((Activity) LoginActivity.this).finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, res, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginReponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApi2(String email){
        ApiGetData.apiGetData.getUserByEmail(email,Utils.token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Utils.user_current = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
