package com.example.travelapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapplication.MainActivity2;
import com.example.travelapplication.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MomoActivity extends AppCompatActivity {
    WebView wb1;
    String url;
    private static final long SPLASH_DISPLAY_LENGTH = 70000;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_momo);
        wb1= findViewById(R.id.web_momo);
        wb1.setWebViewClient(new newclient());
        WebSettings web= wb1.getSettings();
        web.setJavaScriptEnabled(true);
        url=getIntent().getExtras().getString("web");
        wb1.loadUrl(url);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển hướng đến màn hình tiếp theo
                Intent mainIntent = new Intent(MomoActivity.this, MainActivity2.class);
                startActivity(mainIntent);
                finish(); // Đảm bảo người dùng không thể quay lại màn hình Splash
                Toast.makeText(MomoActivity.this,"Thanh toán thành công",Toast.LENGTH_LONG).show();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
class newclient extends  WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}

