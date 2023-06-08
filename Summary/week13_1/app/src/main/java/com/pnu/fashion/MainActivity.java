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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
        
        // findViewById() 함수를 통한 DrawView 버튼 연결
        Button drawBtn = findViewById(R.id.drawBtn);
        // 버튼 리스너 설정
        drawBtn.setOnClickListener(view -> {
            // DrawActivity를 실행하는 인텐트 생성
            Intent i = new Intent(MainActivity.this, DrawActivity.class);
            // StartActivity() 함수에 인텐트를 전달
            startActivity(i);
        });
    }
}