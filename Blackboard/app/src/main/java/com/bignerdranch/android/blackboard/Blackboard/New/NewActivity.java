package com.bignerdranch.android.blackboard.Blackboard.New;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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

import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationAvatar;
import com.bignerdranch.android.blackboard.Settings.Change.InformationActivity;
import com.bignerdranch.android.blackboard.Settings.Change.UploadAvatar;
import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Bean.Organization.Organization;
import com.bignerdranch.android.blackboard.Utils.Utils;
import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
import com.bignerdranch.android.blackboard.Utils.MyResponse;
import com.bignerdranch.android.blackboard.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

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

public class NewActivity extends AppCompatActivity {

    /*新建组织*/

    private Context context;
    private CircleImageView portrait;
    private TextView newPortrait;
    private EditText newNameEdittext;
    private EditText newIntroductionEdittext;
    private Button newBackButton;
    private Button newOrganizationButton;
    private int creatable=0;

//    private API createORGN;

    private static final String TAG = "tag";

    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;
    private Retrofit mRetrofit2;
    private File file;
    private Uri imageUri;
    public static final int REQUEST_CODE_TAKE = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        //初始化界面
        initView();



        //创建按钮
        newOrganizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences p = getSharedPreferences("URL", MODE_PRIVATE);
                String url = p.getString("url","");

                if (newNameEdittext.getText().toString().trim().equals("") ||
                    newIntroductionEdittext.getText().toString().trim().equals("") || url.equals(""))
                {
                    Toast.makeText(NewActivity.this, "一个都不能少哦", Toast.LENGTH_SHORT).show();
                }else
                {
                    Organization organization = new Organization(
                            newNameEdittext.getText().toString(),
                            newIntroductionEdittext.getText().toString());
                    sendNetWorkRequest(organization);

                }
            }
        });

    }

    //初始化界面
    private void initView()
    {
        portrait = findViewById(R.id.img_portrait);
        newPortrait = findViewById(R.id.new_portrait);

        newNameEdittext = findViewById(R.id.new_name);
        newIntroductionEdittext = findViewById(R.id.new_introduction);

        newBackButton = findViewById(R.id.back1);
        newOrganizationButton = findViewById(R.id.create);
    }




    //创建组织
    private void sendNetWorkRequest(Organization organization)
    {
        SharedPreferences p = getSharedPreferences(Utils.SP,MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN,null);

        Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("http://119.3.2.168:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        API createORGN = retrofit.create(API.class);
        Call<MyResponse<Organization>> call = createORGN.createOgn(organization,Authorization);

        call.enqueue(new Callback<MyResponse<Organization>>()
        {
            @Override
            public void onResponse(Call<MyResponse<Organization>> call, Response<MyResponse<Organization>> response)
            {
                if (response.isSuccessful()) {
                    Toast.makeText(NewActivity.this, "创建成功", Toast.LENGTH_SHORT).show();

                    String name = response.body().getData().getOrganization_name();
                    int id = response.body().getData().getID();

                    upload(file,response.body().getData().getOrganization_name());

                    Intent intent = OrganizationActivity.newIntent(NewActivity.this,name,id);
                    startActivity(intent);

                    finish();
                }else
                    Toast.makeText(NewActivity.this, "昵称已被占用 error:"+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyResponse<Organization>> call, Throwable t)
            {
                Toast.makeText(NewActivity.this, "sth wrong :( ", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //返回
    public void ClickBack(View view)
    {
        finish();
    }


    public void ChangeAvatar2(View view)
    {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            takePhoto(v);
            bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            choosePhoto(v);
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        bottomSheetDialog.show();
    }


    private void upload(File file,String organizationName) {

        OkHttpClient.Builder client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build().newBuilder();

        //创建OkHttp client
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//此处有四个级别，body为显示所有

        //判断是开发者模式，则调用OkHttp日志记录拦截器，方便debug
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
        String Authorization = p.getString("token",null);

        API changeAvatar = mRetrofit2.create(API.class);
        Call<OrganizationAvatar> call = changeAvatar.changeImage(organizationName, part, Authorization);
        call.enqueue(new Callback<OrganizationAvatar>() {
            @Override
            public void onResponse(Call<OrganizationAvatar> call, Response<OrganizationAvatar> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(NewActivity.this, "成功了", Toast.LENGTH_SHORT).show();
                    String url = response.body().getData().getUrl();
                    SharedPreferences p = getSharedPreferences("URL1", MODE_PRIVATE);
                    SharedPreferences.Editor editor = p.edit();
                    editor.putString("url",url);
                    editor.commit();
                }
                else{
                    Toast.makeText(NewActivity.this, "出错了", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<OrganizationAvatar> call, Throwable t) {
                Toast.makeText(NewActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void takePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 真正的执行去拍照
            doTake();
        } else {
            // 去申请权限
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
                Toast.makeText(this, "你没有获得摄像头权限~", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "你没有获得访问相册的权限~", Toast.LENGTH_SHORT).show();
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
                    portrait.setImageBitmap(null);
                } else {
                    // 获取拍摄的照片
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        portrait.setImageBitmap(bitmap);
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

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            portrait.setImageBitmap(bitmap);
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
            file = new File(imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            portrait.setImageBitmap(bitmap) ;
//            String imageToBase64 = ImageUtil.imageToBase64(bitmap);
//            imageBase64 = imageToBase64;
        }
    }


    public void choosePhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 真正的去打开相册
            openAlbum();
        } else {
            // 去申请权限
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