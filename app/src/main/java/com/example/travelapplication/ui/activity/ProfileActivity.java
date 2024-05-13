package com.example.travelapplication.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.travelapplication.R;
import com.example.travelapplication.ui.api.ApiGetData;
import com.example.travelapplication.ui.api.LoginReponse;
import com.example.travelapplication.ui.api.RealPathUtil;
import com.example.travelapplication.ui.api.RegisterReponse;
import com.example.travelapplication.ui.dashboard.PeopleFragment;
import com.example.travelapplication.ui.model.Account;
import com.example.travelapplication.ui.model.User;
import com.example.travelapplication.ui.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {
    CircleImageView avatar;
    EditText txtten,txtemail,txtdiachi,txtdob,txtnumber;

    RadioButton maleRadioButton,femaleRadioButton;
    Button btnluu;
    Integer id;
    String male="",female="";
    ImageButton back;
    boolean isImageSelected=false;
    static final int MY_REQUEST_CODE=10;

    ProgressDialog progressDialog;
    Map config = new HashMap();
    String imageUrl="";
    String temp="";
    Uri mUri;
    boolean isMediaManagerInitialized = false;
    private Bitmap fixImageOrientation(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ExifInterface exif = new ExifInterface(inputStream);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


        private ActivityResultLauncher<Intent> mAcitivtyResultLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data= result.getData();
                        Uri uri;
                        if(data==null){
                            return;
                        }
                        uri= data.getData();
                        mUri=uri;
                        try{
                            Bitmap rotatedBitmap = fixImageOrientation(uri);
                            avatar.setImageBitmap(rotatedBitmap);
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        avatar= findViewById(R.id.avatar);
        txtten=findViewById(R.id.fullName);
        txtnumber=findViewById(R.id.phoneNumber);
        txtemail= findViewById(R.id.email);
        txtdob= findViewById(R.id.datePicker);
        txtdiachi = findViewById(R.id.address);
        btnluu = findViewById(R.id.loginBtn);
        maleRadioButton=findViewById(R.id.male);
        femaleRadioButton=findViewById(R.id.female);
        back=findViewById(R.id.img_back);
        back.setOnClickListener(v -> onBackPressed());


        callApi();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ....");


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        UpdateProfile();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void callApi()
    {
        String ma = Utils.token.toString();
        String mail = Utils.email.toString();
        ApiGetData.apiGetData.getUserByEmail(mail,ma).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    User user= response.body();
                    id= user.getUser_id();
                    Transformation transformation = new CropCircleTransformation();
                    if(user.getAvatar()==null||user.getAvatar().equals("")){
                        Picasso.get().load("https://res.cloudinary.com/dmxjhtj5p/image/upload/v1713345418/q6icck5pzy4linkxrao2.jpg").transform(transformation).into(avatar);
                        temp="https://res.cloudinary.com/dmxjhtj5p/image/upload/v1713345418/q6icck5pzy4linkxrao2.jpg";
                    }else {
                        Picasso.get().load(user.getAvatar()).transform(transformation).into(avatar);
                        temp=user.getAvatar();
                    }
                    if(user.getName()==null||user.getName().equals("")){
                        txtten.setHint("Chưa cập nhật");
                        txtdiachi.setText("");
                    }else
                    {
                        txtten.setText(user.getName());
                    }
                    txtemail.setText(user.getEmail());
                    txtemail.setEnabled(false);
                    if(user.getPhone_number()==null||user.getPhone_number().equals("")){
                        txtnumber.setHint("Chưa cập nhật");
                        txtdiachi.setText("");
                    }else {
                        txtnumber.setText(user.getPhone_number());
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String currentDate = sdf.format(user.getDate_of_birth());
                    txtdob.setText(currentDate);
                    if(user.getAddress()==null||user.getAddress().equals("")){
                        txtdiachi.setHint("Chưa cập nhật");
                        txtdiachi.setText("");
                    }else {
                        txtdiachi.setText(user.getAddress());
                    }
                    if(user.getGender()==null||user.getGender().equals("Nam")){
                        maleRadioButton.setChecked(true);
                        male=user.getGender();
                    }else
                    {
                        femaleRadioButton.setChecked(true);
                        female = user.getGender();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"Thất bại call",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UpdateProfile() throws ParseException {
        progressDialog.show();
        String ma = Utils.token.toString();
        String mail=Utils.email.toString();
        if(isImageSelected) {
            if (!isMediaManagerInitialized) {
                config.put("cloud_name","dmxjhtj5p");
                config.put("api_key","199694319986311");
                config.put("api_secret","6m7HblSXci5fSHOpP2AWmMrIoR8");
                MediaManager.init(this,config);
                isMediaManagerInitialized = true;
            }
            String strRealPath= RealPathUtil.getRealPath(this,mUri);
            isImageSelected=false;
            MediaManager.get().upload(strRealPath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.e("Vuong", "started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.e("Vuong", "uploading");
                }

                @SuppressLint("SuspiciousIndentation")
                @Override
                public void onSuccess(String requestId, Map resultData) {
                    imageUrl = resultData.get("url").toString();
                    temp=imageUrl;
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                final RequestBody avatar = RequestBody.create(MediaType.parse("multipart/form-data"), imageUrl);
                                final RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), txtten.getText().toString().trim());
                                final RequestBody phone_number = RequestBody.create(MediaType.parse("multipart/form-data"), txtnumber.getText().toString().trim());
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                Date date = null;
                                try {
                                    date = sdf.parse(txtdob.getText().toString().trim());
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                String temp = f.format(date);
                                final RequestBody date_of_birth = RequestBody.create(MediaType.parse("multipart/form-data"), temp);
                                final RequestBody gender;
                                if (maleRadioButton.isChecked()) {
                                    gender = RequestBody.create(MediaType.parse("multipart/form-data"), maleRadioButton.getText().toString());
                                } else {
                                    gender = RequestBody.create(MediaType.parse("multipart/form-data"), femaleRadioButton.getText().toString());
                                }
                                final RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), txtdiachi.getText().toString().trim());
                                ApiGetData.apiGetData.updateUserByEmail(mail,ma ,name, phone_number, date_of_birth,avatar, gender, address).enqueue(new Callback<RegisterReponse>() {
                                    @Override
                                    public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                        progressDialog.dismiss();
                                        String res = response.body().getMessage();
                                        Toast.makeText(ProfileActivity.this, res, Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }

                                    @Override
                                    public void onFailure(Call<RegisterReponse> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ProfileActivity.this, "Thất bại update", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return null;
                            }
                        }.execute();
                    }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.e("Vuong", "Lỗi:" + error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.e("Vuong", "Lôĩ:" + error);
                }
            }).dispatch();
        }else {
        final RequestBody avatar = RequestBody.create(MediaType.parse("multipart/form-data"), temp);
        final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), txtten.getText().toString().trim());
        final RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), txtnumber.getText().toString().trim());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(txtdob.getText().toString().trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String temp = f.format(date);
        final RequestBody date_of_birth = RequestBody.create(MediaType.parse("text/plain"), temp);
        final RequestBody gender;
        if (maleRadioButton.isChecked()) {
            gender = RequestBody.create(MediaType.parse("text/plain"), maleRadioButton.getText().toString());
        } else {
            gender = RequestBody.create(MediaType.parse("text/plain"), femaleRadioButton.getText().toString());
        }
        final RequestBody address = RequestBody.create(MediaType.parse("text/plain"), txtdiachi.getText().toString().trim());
        ApiGetData.apiGetData.updateUserByEmail(mail,ma ,name, phone_number, date_of_birth,avatar, gender, address).enqueue(new Callback<RegisterReponse>() {
            @Override
            public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                progressDialog.dismiss();
                String res = response.body().getMessage();
                Toast.makeText(ProfileActivity.this, res, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<RegisterReponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Thất bại update", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }
    private void onClickRequestPermission()
    {
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            openFile();
            isImageSelected=true;
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openFile();
            isImageSelected=true;
        }else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }

     @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openFile();
                isImageSelected=true;
            }
        }

    }

    private void openFile(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mAcitivtyResultLauncher.launch(Intent.createChooser(intent,"Chọn ảnh"));
    }

}
