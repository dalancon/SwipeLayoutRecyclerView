package com.dalancon.swipe.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by dalancon on 2019/3/21.
 */

public class SwipeRecylerView extends RecyclerView {

    int mTouchSlop = 0;

    public SwipeRecylerView(Context context) {
        this(context, null);
    }

    public SwipeRecylerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent e) {
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//
//        return super.dispatchTouchEvent(e);
//    }

    float downX = 0;
    float downY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.e("SwipeRecylerView", "onInterceptTouchEvent -> " + e.getAction());
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                downY = e.getY();
                int position = pointToPosition((int) e.getX(), (int) e.getY());
                int count = getChildCount();
                if (count > position) {
                    View positionView = getChildAt(position);
                    if (positionView instanceof SwipeLayout) {
                        SwipeLayout childView = (SwipeLayout) positionView;
                        if (childView.isOpen()) {//不拦截
                            return super.onInterceptTouchEvent(e);
                        }
                    }
                }

                if (hasChildOpen()) {
                    closeMenu();
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = e.getY() - downY;
                float dx = e.getX() - downX;

                if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > mTouchSlop) {//横向滑动
                    return false;
                }


                break;
            case MotionEvent.ACTION_UP:
                break;
        }


        return super.onInterceptTouchEvent(e);
    }

    /**
     * 当前手指位置的position(屏幕上显示的第一个Item为0)
     */
    private Rect touchFrame;

    private int pointToPosition(int x, int y) {
        Rect frame = touchFrame;
        if (frame == null) {
            touchFrame = new Rect();
            frame = touchFrame;
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }


    private boolean hasChildOpen() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof SwipeLayout) {
                SwipeLayout childView = (SwipeLayout) view;
                if (childView.isOpen()) {
                    return true;
                }

            }
        }
        return false;
    }

    private void closeMenu() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof SwipeLayout) {
                SwipeLayout childView = (SwipeLayout) view;
                if (childView.isOpen()) {
                    childView.closeMenu();
                }

            }
        }
    }

}
