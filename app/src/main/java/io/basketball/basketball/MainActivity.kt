package io.basketball.basketball

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        setUpAlphaListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpAlphaListener() {
        ab_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            //处理alpha
            val totalScrollRange = appBarLayout.totalScrollRange
            val ratio = 0.01f * Math.abs((verticalOffset * 100) / totalScrollRange)

            Log.i("MainActivity", "ratio:$ratio")

            if (ratio > 0.98f && vs_title.alpha == 0f)
                startAlphaAnimation(vs_title, 0f, 1f)
            if (ratio < 0.98f && vs_title.alpha == 1f)
                startAlphaAnimation(vs_title, 1f, 0f)

            if (ratio < 0.9f && header_title.alpha == 0f) {
                startAlphaAnimation(header_title, 0f, 1f)
                startAlphaAnimation(content, 0f, 1f)

            } else if (ratio > 0.9f && header_title.alpha == 1f) {
                startAlphaAnimation(header_title, 1f, 0f)
                startAlphaAnimation(content, 1f, 0f)
            } else {
                header_title.alpha = 1 - ratio
                content.alpha = 1 - ratio
            }


        }


    }

    fun startAlphaAnimation(target: View, startAlpha: Float, endAlpha: Float) {
        ObjectAnimator.ofFloat(target, View.ALPHA, startAlpha, endAlpha)
                .setDuration(300)
                .start()

    }


}
