package com.example.travelapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapplication.R;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.RegisterReponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {
    EditText ed1,ed2;
    Button bt1;
    String temp;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepass);

        ed1=findViewById(R.id.otp);
        ed2=findViewById(R.id.password);
        bt1=findViewById(R.id.updateBtn);
        temp=getIntent().getExtras().getString("mail");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestBody role=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(2));
                ApiGetData.apiGetData.updatepass(temp,ed1.getText().toString().trim(),ed2.getText().toString().trim(),role).enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                        String res=response.body().getMessage();
                        Toast.makeText(UpdatePasswordActivity.this,res,Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(UpdatePasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<RegisterReponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
