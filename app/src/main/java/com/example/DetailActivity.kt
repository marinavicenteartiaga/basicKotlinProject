package com.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_character.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("key_id")

        if(savedInstanceState == null){
            val fragment = DetailFragment.newInstance(id)

            supportFragmentManager
                .beginTransaction()
                .add(R.id.detailContainer, fragment)
                .commit()
        }
    }
}
