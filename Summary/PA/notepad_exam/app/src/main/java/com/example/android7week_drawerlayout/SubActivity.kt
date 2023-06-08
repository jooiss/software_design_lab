package com.example.android7week_drawerlayout

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android7week_drawerlayout.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity()  {
    lateinit var toggle: ActionBarDrawerToggle
    var database: SQLiteDatabase? = null
    val databaseName = "notepad"
    val tableName = "note"

    override fun onCreate(savedInstanceState: Bundle?) {
        var binding = ActivitySubBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 토글 설정

        binding.btn1.setOnClickListener {
            // Main-Activity 전환 기능

            //Sub to Main 뒤로가기 방지
        }

        binding.btnSave.setOnClickListener {
            // 데이터 베이스에 등록

            Toast.makeText(this, "Note is saved!!", Toast.LENGTH_SHORT).show()

            //데이터 베이스 등록 이후 Main Activity 자동 전환

            //Sub to Main 뒤로가기 방지
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }


}