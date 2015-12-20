package com.example.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalScrollViewEx extends ViewGroup {
	private static final String TAG = HorizontalScrollViewEx.class.getName();
    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;
	private Scroller mScroller;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        mChildrenSize = childCount;
	}
    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		//是否拦截当前点击事件
        boolean intercepted = false;
        //记录此事件当前滑动到的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//必须不拦截ACTION_DOWN，因为一旦拦截，那么后续的ACTION_MOVE和ACTION_UP都会叫给父容器处理了。
            intercepted = false;
            if (!mScroller.isFinished()) {
            	//如果当前滑动已经结束停止动画，并拦截此事件
                mScroller.abortAnimation();
                intercepted = true;
            }
			
			break;
		case MotionEvent.ACTION_MOVE:
			//此次事件滑动的距离
            int deltaX = x - mLastXIntercept;
            int deltaY = y - mLastYIntercept;
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
            	//如果此次事件当中横向滑动的距离大于纵向滑动的距离
                intercepted = true;
            } else {
                intercepted = false;
            }
			break;
        case MotionEvent.ACTION_UP: {
            intercepted = false;
            break;
        }
		default:
			break;
		}
        Log.d(TAG, "intercepted=" + intercepted);
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
		return intercepted;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            //ScrollBy调用的是相对于mScrollX和mScrollY的位置
            scrollBy(-deltaX, 0);
            break;
        }
        case MotionEvent.ACTION_UP: {
        	//当手指抬起时计算滑动的距离
            int scrollX = getScrollX();
            //获得当前滑动的位置
            int scrollToChildIndex = scrollX / mChildWidth;
            //滑动速度按照1000毫秒计算
            mVelocityTracker.computeCurrentVelocity(1000);
            //获取水平滑动的速度
            float xVelocity = mVelocityTracker.getXVelocity();
            if (Math.abs(xVelocity) >= 50) {
            	//如果速度大于50，根据滑动的方向来判断是否需要更换page
                mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
            } else {
            	//如果速度小于50，根据滑动的距离来判断应该选哪一个page
                mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
            }
            mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
            int dx = mChildIndex * mChildWidth - scrollX;
            smoothScrollBy(dx, 0);
            mVelocityTracker.clear();
            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }
	
	
}
