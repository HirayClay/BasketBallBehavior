package io.basketball.basketball

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout

/**
 * Created by CJJ on 2017/8/1.
 *@author CJJ
 */
open class TeamLogoBehavior : CoordinatorLayout.Behavior<View>, AppBarLayout.OnOffsetChangedListener {
    var offsetY: Int
    var offsetX: Int
    val minScale = 1f
    var Y: Float = 0f
    var X: Float = 0f
    var side: Int = 0
    lateinit var child: View


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TeamLogoBehavior)
        offsetY = ta.getDimensionPixelOffset(R.styleable.TeamLogoBehavior_offsetY, 0)
        offsetX = ta.getDimensionPixelOffset(R.styleable.TeamLogoBehavior_offsetX, 0)
        side = ta.getInt(R.styleable.TeamLogoBehavior_offsetSide, 0)
        ta.recycle()

    }


    override fun layoutDependsOn(parent: CoordinatorLayout?, view: View, dependency: View): Boolean {
        if (dependency is AppBarLayout) {
            val appBarLayout = dependency
            appBarLayout.addOnOffsetChangedListener(this)
            child = view
            if (Y == 0f)
                Y = child.y
            if (X == 0f)
                X = child.x
            return true
        }
        return false
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val totalScrollRange = appBarLayout.totalScrollRange
        val curOffset = Math.abs(verticalOffset)
        var ratio = 0.01f * ((curOffset * 100) / totalScrollRange)

        val scale = minScale + (1 - minScale) * (1 - ratio)
        ViewCompat.setScaleX(child, scale)
        ViewCompat.setScaleY(child, scale)
        val scrollY = offsetY * ratio
        val settleY = Y - scrollY

        val scrollX = offsetX * ratio
        val settleX = X + scrollX


        ViewCompat.setX(child, settleX)
        ViewCompat.setY(child, settleY)

        Log.i(TeamLogoBehavior::class.java.name, "Y:$Y   totalScrollRange:$totalScrollRange verticalOffset:$verticalOffset  ratio:$ratio " +
                "scale:$scale scrollY:$scrollY")

    }


}