package io.basketball.basketball

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by CJJ on 2017/8/1.
 *@author CJJ
 */
open class TeamLogoBehavior : CoordinatorLayout.Behavior<View>, AppBarLayout.OnOffsetChangedListener {
    var offsetY: Int = 0
    var offsetX: Int = 0
    val minScale = 0.5f
    var Y: Float = 0f
    var X: Float = 0f
    var side: Int = 0
    var logoId: Int = 0
    var logo: AppCompatImageView? = null
    lateinit var child: ViewGroup


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TeamLogoBehavior)
        offsetY = ta.getDimensionPixelOffset(R.styleable.TeamLogoBehavior_offsetY, 0)
        offsetX = ta.getDimensionPixelOffset(R.styleable.TeamLogoBehavior_offsetX, 0)
        logoId = ta.getResourceId(R.styleable.TeamLogoBehavior_logoId, View.NO_ID)
        side = ta.getInt(R.styleable.TeamLogoBehavior_offsetSide, 0)
        ta.recycle()

    }

    constructor() : super()


    override fun layoutDependsOn(parent: CoordinatorLayout?, view: View, dependency: View): Boolean {
        if (dependency is AppBarLayout) {
            val appBarLayout = dependency
            appBarLayout.addOnOffsetChangedListener(this)
            child = view as ViewGroup
            logo = child.findViewById(logoId) as AppCompatImageView
            if (Y == 0f)
                Y = child.y
            if (X == 0f)
                X = child.x
            return true
        }
        return false
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        val appBarLayout = dependency as AppBarLayout
        val layoutParams = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior as android.support.design.widget.AppBarLayout.Behavior
        val topAndBottomOffset = behavior.topAndBottomOffset
        if (BuildConfig.DEBUG)
            Log.i(TeamLogoBehavior::class.java.name, "Offset====== $topAndBottomOffset")

        val totalScrollRange = appBarLayout.totalScrollRange
        val curOffset = Math.abs(topAndBottomOffset)
        val ratio = 0.01f * ((curOffset * 100) / totalScrollRange)

        val scale = minScale + (1 - minScale) * (1 - ratio)
        val scrollY = offsetY * ratio
        val settleY = Y - scrollY

        val scrollX = offsetX * ratio
        val settleX = X + scrollX

        ViewCompat.setScaleX(logo, scale)
        ViewCompat.setScaleY(logo, scale)
        ViewCompat.setX(child, settleX)
        ViewCompat.setY(child, settleY)

        if (BuildConfig.DEBUG)
            Log.i(TeamLogoBehavior::class.java.name, "Y:$Y   totalScrollRange:$totalScrollRange verticalOffset:$curOffset  ratio:$ratio " +
                    "scale:$scale scrollY:$scrollY")

        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
//        val totalScrollRange = appBarLayout.totalScrollRange
//        val curOffset = Math.abs(verticalOffset)
//        val ratio = 0.01f * ((curOffset * 100) / totalScrollRange)
//
//        val scale = minScale + (1 - minScale) * (1 - ratio)
//        val scrollY = offsetY * ratio
//        val settleY = Y - scrollY
//
//        val scrollX = offsetX * ratio
//        val settleX = X + scrollX
//
//        ViewCompat.setScaleX(logo, scale)
//        ViewCompat.setScaleY(logo, scale)
//        ViewCompat.setX(child, settleX)
//        ViewCompat.setY(child, settleY)
//
//        if (BuildConfig.DEBUG)
//            Log.i(TeamLogoBehavior::class.java.name, "Y:$Y   totalScrollRange:$totalScrollRange verticalOffset:$verticalOffset  ratio:$ratio " +
//                    "scale:$scale scrollY:$scrollY")

    }


}