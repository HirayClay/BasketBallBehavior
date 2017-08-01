package io.basketball.basketball

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * Created by CJJ on 2017/8/1.
 *@author CJJ
 */
open class AlphaBehavior : CoordinatorLayout.Behavior<View>, AppBarLayout.OnOffsetChangedListener {
    lateinit var child: View

    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, view: View, dependency: View): Boolean {
        if (dependency is AppBarLayout) {
            val appbarLayout = dependency
            appbarLayout.addOnOffsetChangedListener(this)
            child = view
            return true
        }
        return false
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val totalScrollRange = appBarLayout.totalScrollRange
        val ratio = 0.01f * Math.abs((verticalOffset * 100) / totalScrollRange)
        val alpha = interpolate(ratio)
        child.alpha = alpha
    }

    fun interpolate(ratio: Float): Float {
        return 1f - ratio * ratio * ratio
    }
}