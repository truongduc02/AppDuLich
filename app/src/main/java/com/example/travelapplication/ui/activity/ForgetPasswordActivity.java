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

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText ed1;
    Button bt1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        ed1=findViewById(R.id.email);
        bt1=findViewById(R.id.emailBtn);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestBody role=RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(2));
                ApiGetData.apiGetData.sendMail(ed1.getText().toString().trim(),role).enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                        String res=response.body().getMessage();
                        Toast.makeText(ForgetPasswordActivity.this,res,Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(ForgetPasswordActivity.this,UpdatePasswordActivity.class);
                        intent.putExtra("mail",ed1.getText().toString().trim());
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
