package com.pnu.fashion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnu.fashion.classify.Classifier;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "FASHION_CLASSIFIER";
    public static final int GALLERY_IMAGE_REQUEST_CODE = 1;

    Classifier cls;
    Bitmap bitmap = null;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        // 레이아웃 관련 요소 설정
        Button selectBtn = findViewById(R.id.selectBtn);
        // Select 버튼 동작 설정
        selectBtn.setOnClickListener(v -> getImageFromGallery());

        // 분류 버튼 클릭 시 동작 설정
        Button classifyBtn = findViewById(R.id.classifyBtn);
        classifyBtn.setOnClickListener(v -> {
            if (bitmap != null) {
                Pair<String, Float> res = cls.classify(bitmap);
                String outStr = String.format(
                        Locale.ENGLISH,
                        "%s, %.0f%%",
                        res.first,
                        res.second * 100.0f
                );
                textView.setText(outStr);
            }
        });

        // Classifier 생성
        cls = new Classifier(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // 저장소로부터 이미지를 불러오기 위한 intent 설정 추가
    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 파일 불러오기 결과 유효성 체크
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_IMAGE_REQUEST_CODE) {
            // 파일 데이터 유효성 체크
            if (data == null) return;

            Uri selectedImage = data.getData();

            // Android 버전에 따른 이미지 처리
            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source src = ImageDecoder.createSource(getContentResolver(), selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(src).copy(Bitmap.Config.ARGB_8888, true);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to read Image", ioe);
            }

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    // 종료를 위한 처리
    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }
}