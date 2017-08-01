package io.basketball.basketball

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAlphaListener()
    }

    private fun setUpAlphaListener() {
        ab_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            //处理alpha
            val totalScrollRange = appBarLayout.totalScrollRange
            val ratio = 0.01f * Math.abs((verticalOffset * 100) / totalScrollRange)
            val alpha = 1 - ratio * ratio * ratio*ratio
            content.alpha = alpha

            header_title.alpha = alpha
            vs_title.alpha = 1 - alpha
        }

    }


}
