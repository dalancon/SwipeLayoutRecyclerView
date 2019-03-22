package com.dalancon.swipe.widget

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout

/**
 * Created by dalancon on 2019/3/21.
 */

class SwipeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val TAG = "SwipeLayout"
    private var mViewDragHelper: ViewDragHelper
    private lateinit var mContentView: View
    private lateinit var mMenuView: View
    private var mMenuWidth: Int = 0
    private var mTouchSlop = 0
    //菜单是否打开
    private var menuIsOpen = false

    fun isOpen(): Boolean {
        return menuIsOpen
    }

    //屏幕宽度
    private var mScreenWidth: Int

    // 强制关闭menu
    private var forceClose = false

    private var mGestureDetector: GestureDetector

    init {
        mViewDragHelper = ViewDragHelper.create(this, MyDragHelperCallback())

        mGestureDetector = GestureDetector(context, GestureDetectorCallback())
        mScreenWidth = context.resources.displayMetrics.widthPixels
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        mContentView = getChildAt(1)
        mMenuView = getChildAt(0)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mMenuWidth = mMenuView.measuredWidth
    }

    private var leftOffset = 0

    inner class MyDragHelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View?, pointerId: Int): Boolean {
            return child == mContentView
        }

        override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int {
            Log.e(TAG, "clampViewPositionHorizontal left -> $left")
            leftOffset = left
            if (leftOffset < -mMenuWidth)
                leftOffset = -mMenuWidth
            else if (leftOffset >= 0)
                leftOffset = 0

            return leftOffset
        }

        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            Log.e(TAG, "onViewReleased")
            if (forceClose) {
                menuIsOpen = false
                forceClose = false
                leftOffset = 0
                mViewDragHelper.settleCapturedViewAt(0, 0)
                invalidate()
                return
            }

            menuIsOpen = if (leftOffset >= -mMenuWidth / 2) {
                mViewDragHelper.settleCapturedViewAt(0, 0)
                false
            } else {
                mViewDragHelper.settleCapturedViewAt(-mMenuWidth, 0)
                true
            }
            invalidate()
        }
    }

    var downX = 0f
    var downY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.e(TAG, "onInterceptTouchEvent")

        //菜单关闭的话 就拦截事件
        //if (!menuIsOpen) return true

//        getHitRect()


        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mViewDragHelper.processTouchEvent(ev)
                downX = ev.x
                downY = ev.y
                Log.e(TAG, "downX -> $downX  mScreenWidth - mMenuWidth $mScreenWidth  $mMenuWidth")

                Log.e(TAG, "downY -> $downY  top -> $top  measuredHeight -> $measuredHeight")

                if (menuIsOpen) {// 如果菜单是打开的 判断点击区域是否在内容区域， 如果是的 就关闭菜单 并拦截事件
                    if (downX < mScreenWidth - mMenuWidth) {
                        //触摸到了 内容区域 ，关闭菜单
                        forceClose = true

                        //这样写 滑动到0的位置无效 为何？
//                    if (mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0)) {
//                        invalidate()
//                        menuIsOpen = false
//                    }
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = ev.y - downY
                val dx = ev.x - downX

                if (!menuIsOpen) {//菜单关闭时 如果是点击就不拦截  如果是滑动就拦截
                    return !(Math.abs(dx) < mTouchSlop && Math.abs(dy) < mTouchSlop)
                }

            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        Log.e(TAG, "onTouchEvent -> ${event.action}")
        if (mGestureDetector.onTouchEvent(event)) return true
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    fun closeMenu() {
        if (mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0)) {
            menuIsOpen = false
            forceClose = false
            leftOffset = 0
            invalidate()

        }
    }

    inner class GestureDetectorCallback : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
//            Log.e(TAG, "velocityX -> $velocityX")
//
//            if (!menuIsOpen && velocityX < 0) {//打开menu
//                mContentView.translationX = (-mMenuWidth).toFloat()
//                return true
//            }

            return false
        }
    }

}
