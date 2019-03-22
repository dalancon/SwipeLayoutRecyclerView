# SwipeLayoutRecyclerView kotlin实现
类似qq列表滑动删除的效果，提供行侧滑功能，也提供了兼容的RecyclerView，实现了事件处理
图片是使用了别人的，但就是这样的效果

<img src="https://github.com/dalancon/SwipeLayoutRecyclerView/blob/master/sceenshot.gif" alt="Sample"  width="216" height="384"/>


1、可单独使用SwipeLayout

```
<com.dalancon.swipe.widget.SwipeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <LinearLayout
        android:layout_width="150dp"
        android:layout_height="90dp"
        android:layout_gravity="right">

        <TextView
            android:id="@+id/tv_delete"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="90dp"
            android:clickable="true"
            android:gravity="center"
            android:text="Delete"
            android:textColor="#ffffff"
            android:background="#e3e3e3"
            android:textSize="20sp" />

        <TextView
            android:id="@+id/tv_tag"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="90dp"
            android:clickable="true"
            android:gravity="center"
            android:text="Tag"
            android:textColor="#ffffff"
            android:background="@android:color/holo_green_dark"
            android:textSize="20sp" />
    </LinearLayout>

    <TextView
        android:id="@+id/tv_content"
        android:layout_width="match_parent"
        android:layout_height="90dp"
        android:background="@color/colorAccent"
        android:gravity="center"
        android:text="content"
        android:textColor="#ffffff"
        android:textSize="25sp" />


</com.dalancon.swipe.widget.SwipeLayout>
```
 
内部包含两个布局 一个是滑动显示的菜单，一个是内容布局，由于是使用的framelayout实现 所以菜单作为第一个子view 并且 提供android:layout_gravity="right"


2、如果要于recyclerview一起使用，请使用提供的SwipeRecylerView，这里面处理了触摸事件 可以实现qq的列表的
 ```
 <com.dalancon.swipe.widget.SwipeRecylerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
 ```
其他跟使用recyclerview一样。








