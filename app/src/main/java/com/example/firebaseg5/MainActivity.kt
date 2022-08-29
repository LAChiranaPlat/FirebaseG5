package com.example.firebaseg5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseg5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var layout:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout= ActivityMainBinding.inflate(layoutInflater)
        setContentView(layout.root)

    }
}