package com.example.minhquan.bflagclient.home.user

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.AppBarLayout
import android.util.AttributeSet
import android.view.View
import com.example.minhquan.bflagclient.R


class CollapsingImageBehavior : CoordinatorLayout.Behavior<View> {

    private var mTargetId: Int = 0

    private var mView: IntArray? = null

    private var mTarget: IntArray? = null

    constructor()

    constructor(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingImageBehavior)
            mTargetId = a.getResourceId(R.styleable.CollapsingImageBehavior_collapsedTarget, 0)
            a.recycle()
        }

        if (mTargetId == 0) {
            throw IllegalStateException("collapsedTarget attribute not specified on view for behavior")
        }
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        setup(parent, child)

        val appBarLayout = dependency as AppBarLayout

        val range = appBarLayout.totalScrollRange
        val factor = -appBarLayout.y / range

        val left = mView!![X] + (factor * (mTarget!![X] - mView!![X])).toInt() + 32
        val top = mView!![Y] + (factor * (mTarget!![Y] - mView!![Y])).toInt() - 56
        val width = mView!![WIDTH] + (factor * (mTarget!![WIDTH] - mView!![WIDTH])).toInt()
        val height = mView!![HEIGHT] + (factor * (mTarget!![HEIGHT] - mView!![HEIGHT])).toInt()

        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.width = width
        lp.height = height
        child.layoutParams = lp
        child.x = left.toFloat()
        child.y = top.toFloat()

        return true
    }

    private fun setup(parent: CoordinatorLayout, child: View) {

        if (mView != null) return

        mView = IntArray(4)
        mTarget = IntArray(4)

        mView!![X] = child.x.toInt()
        mView!![Y] = child.y.toInt()
        mView!![WIDTH] = child.width
        mView!![HEIGHT] = child.height

        val target = parent.findViewById<View>(mTargetId)
                ?: throw IllegalStateException("target view not found")

        mTarget!![WIDTH] += target.width
        mTarget!![HEIGHT] += target.height

        var view: View = target
        while (view !== parent) {
            mTarget!![X] += view.x.toInt()
            mTarget!![Y] += view.y.toInt()
            view = view.parent as View
        }

    }

    companion object {

        private const val X = 0
        private const val Y = 1
        private const val WIDTH = 2
        private const val HEIGHT = 3
    }
}