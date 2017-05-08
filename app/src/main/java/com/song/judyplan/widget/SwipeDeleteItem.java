package com.song.judyplan.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Judy on 2017/5/7.
 */

public class SwipeDeleteItem extends ViewGroup {
    private View mLeftView;
    private View mRightView;
    private int mRightHeight;
    private int mRightWidth;
    private int mLeftHeight;
    private int mLeftWidth;
    private ViewDragHelper mViewDragHelper;

    public SwipeDeleteItem(Context context) {
        this(context, null);
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //防止多个手指滑动
                if (pointerId == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == mLeftView) {
                    if (left > 0) {
                        return 0;
                    } else if (left < -mRightWidth) {
                        return -mRightWidth;
                    }
                } else if (child == mRightView) {
                    if (left < mLeftWidth - mRightWidth) {
                        return mLeftWidth - mRightWidth;
                    } else if (left > mLeftWidth) {
                        return mLeftWidth;
                    }
                }
                return left;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == mLeftView) {
                    //当左侧的控件滑动的时候，需要让右侧的跟着滑动
                    //将左侧的dx累加到右侧的左边距，然后布局右侧控件
                    int newLeft = mRightView.getLeft() + dx;
                    mRightView.layout(newLeft, 0, newLeft + mRightWidth, mRightHeight);
                } else if (changedView == mRightView) {
                    int newLeft = mLeftView.getLeft() + dx;
                    mLeftView.layout(newLeft, 0, newLeft + mLeftWidth, mLeftHeight);
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mLeftView.getLeft() < -mRightWidth / 2) {
                    open();
                } else {
                    close();
                }
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return mLeftView == child ? child.getWidth() : 0;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return mLeftView == child ? child.getWidth() : 0;
            }

        });
    }

    private void close() {
        mViewDragHelper.smoothSlideViewTo(mLeftView, 0, 0);
        postInvalidateOnAnimation();
        SwipeDeleteItemManager.getInstance().setSwipeDeleteItem(null);
    }

    private void open() {
        mViewDragHelper.smoothSlideViewTo(mLeftView, -mRightWidth, 0);
        postInvalidateOnAnimation();
        SwipeDeleteItemManager.getInstance().setSwipeDeleteItem(this);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final SwipeDeleteItem SwipeDeleteItem = SwipeDeleteItemManager.getInstance().getSwipeDeleteItem();
        if (SwipeDeleteItem != null && SwipeDeleteItem != this) {
            SwipeDeleteItem.close();
            return true;
        }

        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLeftView = getChildAt(0);
        mRightView = getChildAt(1);

        measureChild(mLeftView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mRightView, widthMeasureSpec, heightMeasureSpec);

        mLeftWidth = mLeftView.getMeasuredWidth();
        mLeftHeight = mLeftView.getMeasuredHeight();
        mRightWidth = mRightView.getMeasuredWidth();
        mRightHeight = mRightView.getMeasuredHeight();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLeftView.layout(0, 0, mLeftWidth, mLeftHeight);
        mRightView.layout(mLeftWidth, 0, mLeftWidth + mRightWidth, mRightHeight);
    }

    public static class SwipeDeleteItemManager {
        private SwipeDeleteItemManager() {
        }

        private SwipeDeleteItem mSwipeDeleteItem;
        private static SwipeDeleteItemManager sSwipeDeleteItemManager;

        public static SwipeDeleteItemManager getInstance() {
            synchronized (SwipeDeleteItemManager.class) {
                if (sSwipeDeleteItemManager == null) {
                    sSwipeDeleteItemManager = new SwipeDeleteItemManager();
                }
            }
            return sSwipeDeleteItemManager;
        }

        public SwipeDeleteItem getSwipeDeleteItem() {
            return mSwipeDeleteItem;
        }

        public void setSwipeDeleteItem(SwipeDeleteItem SwipeDeleteItem) {
            mSwipeDeleteItem = SwipeDeleteItem;
        }
    }
}
