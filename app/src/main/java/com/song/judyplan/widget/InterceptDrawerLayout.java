package com.song.judyplan.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InterceptDrawerLayout extends DrawerLayout {
    private int startX;
    private int startY;

    public InterceptDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public InterceptDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = (int) (ev.getX() - startX);
                int dY = (int) (ev.getY() - startY);
                return Math.abs(dX) > Math.abs(dY);
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
