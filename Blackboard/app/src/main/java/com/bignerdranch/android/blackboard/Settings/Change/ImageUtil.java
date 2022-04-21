package com.bignerdranch.android.blackboard.Settings.Change;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtil {
    public static String imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        String baseStr = Base64.encodeToString(buffer, Base64.DEFAULT);
        return baseStr;
    }

    public static Bitmap base64ToImage(String bitmap64) {
        byte[] bytes = Base64.decode(bitmap64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;

    }

}
