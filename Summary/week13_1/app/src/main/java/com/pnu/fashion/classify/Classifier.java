package com.pnu.fashion.classify;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import androidx.core.util.Pair;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class Classifier {
    private static final String MODEL_NAME = "keras_model_cnn.tflite";

    Context context;
    // 인터프리터 선언
    Interpreter interpreter = null;
    int modelInputWidth, modelInputHeight, modelInputChannel;
    int modelOutputClasses;
    HashMap<Integer, String> modelLables = new HashMap<>();

    public Classifier(Context context) {
        this.context = context;
    }

    // 4. 텐서플로 라이트 모델 로드
    // ByteBuffer 선언 및 Interpreter 선언을 통한 텐서플로 라이트 모델 로드
    public void init() throws IOException {
        ByteBuffer model = loadModelFile(MODEL_NAME);
        model.order(ByteOrder.nativeOrder());
        interpreter = new Interpreter(model);

        initModelLables();
        initModelShape();
    }

    // 6. 추론
    public Pair<Integer, Float> classify(Bitmap bitmap) {
        // 함수를 통한 데이터 전처리
        ByteBuffer buffer = convertBitmapToGrayByteBuffer(resizeBitmap(bitmap));

        // 결과 저장을 위한 배열 선언
        float[][] result = new float[1][modelOutputClasses];

        // Interpreter의 run() 함수 활용한 텐서플로 라이트 모델 분류 실행
        interpreter.run(buffer, result);

        // 최대 값 추출
        return argmax(result[0]);
    }

    // 6. 추론
    // 출력 데이터는 10개의 출력 클래스(0~9) 중 가장 높은 확률 값을 찾는 argmax 함수 구현
    private Pair<Integer, Float> argmax(float[] array) {
        int argmax = 0;
        float max = array[0];
        for (int i = 0; i < array.length; i++) {
            float f = array[i];
            if (f > max) {
                argmax = i;
                max = f;
            }
        }

        return new Pair<>(argmax, max);
    }

    // 4. 텐서플로 라이트 모델 로드
    private ByteBuffer loadModelFile(String modelName) throws IOException {
        // Assets 리소스 접근이 가능한 객체 생성
        AssetManager am = context.getAssets();
        // Assets 리소스의 경로 획득
        AssetFileDescriptor afd = am.openFd(modelName);
        // MappedByteBuffer 자료형으로 파일 읽기
        FileInputStream fis = new FileInputStream(afd.getFileDescriptor());
        FileChannel fc = fis.getChannel();
        long startOffset = afd.getStartOffset();
        long declaredLength = afd.getDeclaredLength();

        return fc.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // 5. 입력 이미지 전처리 코드 구현
    private void initModelShape() {
        // Interpreter로부터 로드한 모델의 input shape를 변수로 읽어옴 (Channel, Width, Height)
        Tensor inputTensor = interpreter.getInputTensor(0);
        int[] inputShape = inputTensor.shape();
        modelInputChannel = inputShape[0];
        modelInputWidth = inputShape[1];
        modelInputHeight = inputShape[2];

        // Interpreter로부터 로드한 모델의 output shape를 변수로 읽어옴 (Class 수)
        Tensor outputTensor = interpreter.getOutputTensor(0);
        int[] outputShape = outputTensor.shape();
        modelOutputClasses = outputShape[1];
    }

    // https://keras.io/api/datasets/fashion_mnist/
    private void initModelLables() {
        String[] lables = {
            "T-shirt/top", "Trouser", "Pullover", "Dress", "Coat",
            "Sandal", "Shirt", "Sneaker", "Bag", "Ankle boot"
        };

        for (int i=0; i<lables.length; i++) {
            modelLables.put(i, lables[i]);
        }
    }

    // 5. 입력 이미지 전처리 코드 구현
    // 입력 이미지 크기 변환
    private Bitmap resizeBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, modelInputWidth, modelInputHeight, false);
    }

    // 5. 입력 이미지 전처리 코드 구현
    // 입력 이미지 채널 및 포맷 변환
    private ByteBuffer convertBitmapToGrayByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.getByteCount());
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int pixel : pixels) {
            int r = pixel >> 16 & 0xFF;
            int g = pixel >> 8 & 0xFF;
            int b = pixel & 0xFF;

            float avgPixelValue = (r + g + b) / 3.0f;
            float normalizedPixelValue = avgPixelValue / 255.0f;

            byteBuffer.putFloat(normalizedPixelValue);
        }

        return byteBuffer;
    }

    // 6. 추론
    // 인터프리터 닫기
    public void finish() {
        if (interpreter != null)
            interpreter.close();
    }
}