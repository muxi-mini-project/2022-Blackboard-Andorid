package com.bignerdranch.android.blackboard.Settings.Change;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;


import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Mine.Information;
import com.bignerdranch.android.blackboard.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;



import de.hdodenhof.circleimageview.BuildConfig;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformationActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private Retrofit mRetrofit;
    private API information;
    private EditText mInformationEditText;
    private Retrofit mRetrofit1,mRetrofit2;
    private API change,changeAvatar;
    private Button informationButton;
    private Button backButton;
    private Context context;

    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;

    private Uri imageUri;
    private String imageBase64;
    private CircleImageView ivAvatar;
    private File file;
//    private String base64;


    //????????????
    private BottomSheetDialog bottomSheetDialog;
    //????????????
    private View bottomView;

    /**
     * ????????????
     *
     * @param view
     */
    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //??????
        tvTakePictures.setOnClickListener(v -> {
            takePhoto(v);
            bottomSheetDialog.cancel();
        });
        //????????????
        tvOpenAlbum.setOnClickListener(v -> {
            choosePhoto(v);
            bottomSheetDialog.cancel();
        });
        //??????
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        mInformationEditText = findViewById(R.id.information_nickname);
        informationButton = findViewById(R.id.information_change);
        backButton = findViewById(R.id.information_back);
        ivAvatar = findViewById(R.id.iv_avatar);

        SharedPreferences p = getSharedPreferences("URL", MODE_PRIVATE);
        String url = p.getString("url",null);
        Glide.with(this.getApplicationContext())
                .load(url)
                .into(ivAvatar);


        sendNetworkRequest();



//        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
//        String image64 = spfRecord.getString("image_64", "");
//        ivAvatar.setImageBitmap(ImageUtil.base64ToImage(image64));

//        base64 = image64;


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = getIntent();
//                Integer back = intent1.getIntExtra("from",0);
//                if(back==0){
//                    Intent intent = new Intent(InformationActivity.this, PageActivity.class);
//                    startActivity(intent);
//                } else{
//                    Intent intent = new Intent(InformationActivity.this, SettingsActivity.class);
//                    startActivity(intent);
//                }

                finish();
            }
        });

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChangeName changeInformation = new ChangeName(
                        mInformationEditText.getText().toString()
                );


                changeInformation(changeInformation);

                Intent intent1 = getIntent();
                Integer back = intent1.getIntExtra("from",0);

//                SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
//                SharedPreferences.Editor edit = spfRecord.edit();
//                edit.putString("image_64", imageBase64);
//                edit.apply();


//                if(back==0){
//                    Intent intent = new Intent(InformationActivity.this, PageActivity.class);
//                    startActivity(intent);
//                } else{
//                    Intent intent = new Intent(InformationActivity.this, SettingsActivity.class);
//                    startActivity(intent);


//                if(back==0){
//                    Intent intent = new Intent(InformationActivity.this, PageActivity.class);
//                    startActivity(intent);
//                } else{
//                    Intent intent = new Intent(InformationActivity.this, SettingsActivity.class);
//                    startActivity(intent);
//                }
                finish();
            }
        });

    }


    public void sendNetworkRequest() {
        //??????OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//????????????????????????body???????????????

        //????????????????????????????????????OkHttp??????????????????????????????debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //??????retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token","null");

        information = mRetrofit.create(API.class);
        Call<Information> call = information.get(Authorization);

        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (response.isSuccessful()) {

                    String nickname = response.body().getData().getNickname();
                    mInformationEditText.setText(nickname);


                } else {
                    Toast.makeText(InformationActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                Toast.makeText(InformationActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void changeInformation(ChangeName changeName) {

        //??????OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//????????????????????????body???????????????

        //????????????????????????????????????OkHttp??????????????????????????????debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //??????retrofit
        mRetrofit1 = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token",null);

        change = mRetrofit1.create(API.class);
        Call<ChangeName> call = change.put(changeName,Authorization);
        //??????????????????
        call.enqueue(new Callback<ChangeName>() {
            @Override
            public void onResponse(Call<ChangeName> call, Response<ChangeName> response) {
                if (response.isSuccessful()) {

//                    Intent intent = new Intent(InformationActivity.this, SettingsActivity.class);
//                    startActivity(intent);

                } else {
                    Log.d("InformationActivity", "error");
                    Toast.makeText(InformationActivity.this, "?????????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeName> call, Throwable t) {
                Toast.makeText(InformationActivity.this, "?????????", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void upload(File file) {

        OkHttpClient.Builder client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build().newBuilder();

        //??????OkHttp client
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//????????????????????????body???????????????

        //????????????????????????????????????OkHttp??????????????????????????????debug
        if(BuildConfig.DEBUG) {
            client.addInterceptor(logging);
        }


        mRetrofit2 = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestFile);

        SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token","null");

        changeAvatar = mRetrofit2.create(API.class);
        Call<UploadAvatar> call = changeAvatar.post(part, Authorization);
        call.enqueue(new Callback<UploadAvatar>() {
            @Override
            public void onResponse(Call<UploadAvatar> call, Response<UploadAvatar> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(InformationActivity.this, "?????????", Toast.LENGTH_SHORT).show();
                    String url = response.body().getData().getUrl();

                    SharedPreferences p = getSharedPreferences("URL", MODE_PRIVATE);
                    SharedPreferences.Editor editor = p.edit();
                    editor.putString("url",url);
                    editor.commit();


                }
                else{
                    Toast.makeText(InformationActivity.this, "?????????", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<UploadAvatar> call, Throwable t) {
                Toast.makeText(InformationActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // ????????????????????????
            doTake();
        } else {
            // ???????????????
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doTake();
            } else {
                Toast.makeText(this, "??????????????????????????????~", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "????????????????????????????????????~", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void doTake() {
        file = new File(getExternalCacheDir(), "imageOut.jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT > 24) {
            // contentProvider
            imageUri = FileProvider.getUriForFile(this, "com.bignerdranch.android.blackboard.fileprovider", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE) {
            if (resultCode == RESULT_OK) {
                if (getFile() == null || !getFile().exists()) {
                    ivAvatar.setImageBitmap(null);
                } else {
                    upload(getFile());
                    // ?????????????????????
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ivAvatar.setImageBitmap(bitmap);
//                        String imageToBase64 = ImageUtil.imageToBase64(bitmap);
//                        imageBase64 = imageToBase64;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_CHOOSE) {
            if(data == null){
                return;
            }else{
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT < 19) {
                        handleImageBeforeApi19(data);
                    } else {
                        handleImageOnApi19(data);
                    }
                }
            }
        }
    }

    private void handleImageBeforeApi19(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnApi19(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);

            if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                if (documentId != null && documentId.startsWith("msf:")) {
                    resolveMSFContent(uri, documentId);
                    return;
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

        displayImage(imagePath);
    }

    private void resolveMSFContent(Uri uri, String documentId) {

        file = new File(getCacheDir(), "temp_file" + getContentResolver().getType(uri).split("/")[1]);

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4 * 1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            upload(file);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivAvatar.setImageBitmap(bitmap);
//            imageBase64 = ImageUtil.imageToBase64(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        Log.d(TAG, "displayImage: ------------" + imagePath);
        if (imagePath != null) {
            File file = new File(imagePath);
            upload(file);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivAvatar.setImageBitmap(bitmap) ;
//            String imageToBase64 = ImageUtil.imageToBase64(bitmap);
//            imageBase64 = imageToBase64;
        }
    }


    public void choosePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // ????????????????????????
            openAlbum();
        } else {
            // ???????????????
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    private File getFile(){
        return file;
    }
}