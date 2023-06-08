package com.example.android7week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class OneFragment : Fragment() {
    // View로 생성되는 Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment 출력 Layout
        return inflater.inflate(R.layout.fragment_bind, container, false)
    }
}

