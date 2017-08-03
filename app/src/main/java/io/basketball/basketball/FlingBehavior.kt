package io.basketball.basketball

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Copy and transform by CJJ on 2017/8/3.

 * @author CJJ
 */

class FlingBehavior : AppBarLayout.Behavior {

    companion object {

        private val TAG = FlingBehavior::class.java.name
        private val TOP_CHILD_FLING_THRESHOLD = 1
        private val OPTIMAL_FLING_VELOCITY = 3500f
        private val MIN_FLING_VELOCITY = 20f
    }

    internal var shouldFling = false
    internal var flingVelocityY = 0f

    constructor() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: AppBarLayout?, target: View?,
                                   velocityX: Int, velocityY: Int, consumed: IntArray?) {

        super.onNestedPreScroll(coordinatorLayout, child, target, velocityX, velocityY, consumed)

        if (velocityY > MIN_FLING_VELOCITY) {
            shouldFling = true
            flingVelocityY = velocityY.toFloat()
        } else {
            shouldFling = false
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout?, abl: AppBarLayout?, target: View?) {
        super.onStopNestedScroll(coordinatorLayout, abl, target)
        if (shouldFling) {
            Log.d(TAG, "onNestedPreScroll: running nested fling, velocityY is " + flingVelocityY)
            onNestedFling(coordinatorLayout, abl!!, target, 0f, flingVelocityY, true)
        }
    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout?, child: AppBarLayout, target: View?,
                               velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        var velocityY = velocityY
        var consumed = consumed

        if (target is RecyclerView && velocityY < 0) {
            Log.d(TAG, "onNestedFling: target is recyclerView")
            val recyclerView = target as RecyclerView?
            val firstChild = recyclerView!!.getChildAt(0)
            val childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild)
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }

        // prevent fling flickering when going up
        if (target is NestedScrollView && velocityY > 0) {
            consumed = true
        }

        if (Math.abs(velocityY) < OPTIMAL_FLING_VELOCITY) {
            velocityY = OPTIMAL_FLING_VELOCITY * if (velocityY < 0) -1 else 1
        }
        Log.d(TAG, "onNestedFling: velocityY - $velocityY, consumed - $consumed")

        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }


}