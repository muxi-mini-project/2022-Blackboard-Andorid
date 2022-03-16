package com.bignerdranch.android.blackboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ChangeActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private Button btnChange;
    private ChangeHeadImagePop popupWindow = null;
    private RelativeLayout main;
    private Uri iconUri = null;
    private Uri cropImageUri = null;

    public static final String imageDirPath = Environment.getExternalStorageDirectory()+"/moon/images";
    public static final String takePhotoImageName = "take_photo.jpg";
    public static final String crop_ImageName = "crop_image.jpg";
    //照相
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;
    //选择图片
    private static final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    //剪切图片
    private static final int REQUEST_CODE_CROP_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        init();
    }


    private void init(){
        popupWindow = new ChangeHeadImagePop(this);
        main = (RelativeLayout) findViewById(R.id.activity_change);
        circleImageView = (CircleImageView) findViewById(R.id.img_portrait);
        btnChange = (Button) findViewById(R.id.btn_change);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showLocation(main);
            }
        });

        popupWindow.setOnItemClickListener(new ChangeHeadImagePop.OnItemClickListener() {
            @Override
            public void onClick(ChangeHeadImagePop.MENUITEM item, String str) {
                switch (item){
                    //拍照
                    case ITEM1:
                        File dir = new File(imageDirPath);
                        if(!dir.exists()){
                            dir.mkdirs();
                        }

                        File file = new File(dir,takePhotoImageName);//指定拍照后相片保存地址，以覆盖方式保存。
                        Log.e("相机存放图片地址",file.getAbsolutePath());
                        iconUri = Uri.fromFile(file);
                        // 启动相机
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 设置输出路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
                        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
                        break;
                    //手机相册中选择
                    case ITEM2:
                        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent2, REQUEST_CODE_CHOOSE_IMAGE);
                        break;
                    //取消
                    case ITEM3:

                        break;

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(ChangeActivity.this,"取消",Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode){
            case REQUEST_CODE_TAKE_PHOTO:
                if(null != iconUri){
                    File file = new File(iconUri.getPath());
                    Log.e("文件是否存在",file.exists()+"");
                    // 打开相机页面后，如果按返回键也会回调，所以需要判断是否拍摄了照片
                    if (file.exists()) {
                        // 裁剪图片
                        startCropImage(iconUri);
                    }
                }

                break;
            case REQUEST_CODE_CHOOSE_IMAGE:
                Log.e("相册选择",data.getData()+"");
                if (data.getData() != null) {
                    iconUri = data.getData();
                    startCropImage(iconUri);
                }
                break;
            case REQUEST_CODE_CROP_IMAGE:
                Toast.makeText(ChangeActivity.this,"剪切完毕",Toast.LENGTH_SHORT).show();
                //上传图片，然后设置图片，这里不上传图片。
                if(null !=cropImageUri){
                    Log.e("剪切图片地址",cropImageUri.getPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(imageDirPath+File.separator+crop_ImageName);
                    circleImageView.setImageBitmap(bitmap);
                    bitmap.recycle();
                }
                break;
        }
    }


    /**
     * 裁剪图片
     * @param uri
     */
    public void startCropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 图片处于可裁剪状态
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 是否之处缩放
        intent.putExtra("scale", true);
        // 设置图片的输出大小, 对于普通的头像,应该设置一下,可提高头像的上传速度
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 以Uri的方式传递照片
        File dir = new File(imageDirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File crop_image = new File(dir,crop_ImageName);
        cropImageUri = Uri.fromFile(crop_image);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        // 设置图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        // 关闭人脸识别
        intent.putExtra("noFaceDetection", false);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
}