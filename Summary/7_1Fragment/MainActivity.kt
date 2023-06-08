package com.example.android7week

import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragButton = findViewById<Button>(R.id.fragBut1)
        // Fragment 제어 객체 선언
        val fragmentManager: FragmentManager = supportFragmentManager
        var onClicked = false
        fragButton.setOnClickListener{
            if (onClicked) {
                onClicked = false
	    // Fragment Transaction 객체 선언
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
	    // Fragment 제거
                val frameLayout = supportFragmentManager.findFragmentById(R.id.fragment_content)
                transaction.remove(frameLayout!!).commit()
            }
            else {
                onClicked=true
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
	    // Fragment 추가
                transaction.add(R.id.fragment_content, OneFragment()).commit()
            }
        }
    }
}

