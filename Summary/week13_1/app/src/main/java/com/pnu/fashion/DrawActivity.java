package com.pnu.fashion;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.util.Pair;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.divyanshu.draw.widget.DrawView;
import com.pnu.fashion.classify.Classifier;

import java.io.IOException;
import java.util.Locale;

public class DrawActivity extends AppCompatActivity {
    Classifier cls;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        DrawView drawView = findViewById(R.id.drawView);
        // 선 두께 및 실선 크기 변경
        drawView.setStrokeWidth(100.0f);
        drawView.setBackgroundColor(Color.BLACK);
        drawView.setColor(Color.WHITE);

        TextView resultView = findViewById(R.id.resultView);

        Button classifyBtn = findViewById(R.id.classifyBtn);
        // Drawview로부터 이미지 추출
        classifyBtn.setOnClickListener(v->{
            Bitmap image = drawView.getBitmap();

            Pair<Integer, Float> res = cls.classify(image);
            // TextView를 통한 결과 표시
            String outStr = String.format(
                    Locale.ENGLISH,
                    "%d, %.0f%%",
                    res.first,
                    res.second * 100.0f);
            resultView.setText(outStr);
        });

        // DrawView 초기화
        Button clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(v->{
            drawView.clearCanvas();
        });

        cls = new Classifier(this);
        try {
            cls.init();
        } catch (IOException ioe){
            Log.d("DigitClassifier","failed to init Classifier", ioe);

        }
    }

    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }
}
