package com.example.android7week_drawerlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.android7week_drawerlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        var binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 드로어 메뉴 토글 버튼 선언
        toggle = ActionBarDrawerToggle(this, binding.drawer,
            R.string.drawer_opened, R.string.drawer_closed)
        // 기존에는 기본 버튼 아이콘이 상단 좌측에 출력된다
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // 상단좌측의 버튼을 내비게이션아이콘(≡)으로 변경
        toggle.syncState()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 토글 버튼을 클릭 이벤트 발생시 드로어가 출력
        if (toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }
}

