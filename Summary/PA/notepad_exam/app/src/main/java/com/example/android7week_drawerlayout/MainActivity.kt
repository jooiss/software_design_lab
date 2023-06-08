package com.example.android7week_drawerlayout

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android7week_drawerlayout.databinding.ActivityMainBinding

class note(t: String, c:String){
    var title:String = t
    var content:String = c
}

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    var database: SQLiteDatabase? = null
    val databaseName = "notepad"
    val tableName = "note"
    override fun onCreate(savedInstanceState: Bundle?) {
        var binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        /* 1) 저장소 생성 */

        if(database != null){
	/* 2) 테이블 생성 */
            val sql = null
            database?.execSQL(sql)
        }

        var output = mutableListOf<note>()

        /* 3) 데이터 조회 */

        setContentView(binding.root)

        /* 4) 토글 설정 */

        /* 5) recycler view 설정 */

        binding.btn2.setOnClickListener {
            /* 6) Sub-Activity 전환 */
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }

}

