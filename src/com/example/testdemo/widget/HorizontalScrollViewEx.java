package com.example.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
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

	private void init() {
		if (mScroller == null) {
			mScroller = new Scroller(getContext());
			mVelocityTracker = VelocityTracker.obtain();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// 是否拦截当前点击事件
		boolean intercepted = false;
		// 记录此事件当前滑动到的坐标
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 必须不拦截ACTION_DOWN，因为一旦拦截，那么后续的ACTION_MOVE和ACTION_UP都会叫给父容器处理了。
			intercepted = false;
			if (!mScroller.isFinished()) {
				// 如果当前滑动还没有停止，则先停止这个滑动
				mScroller.abortAnimation();
				//并且让该自定义控件拦截接下来的一系列事件
				intercepted = true;
			}

			break;
		case MotionEvent.ACTION_MOVE:
			// 此次事件滑动的距离
			int deltaX = x - mLastXIntercept;
			int deltaY = y - mLastYIntercept;
			if (Math.abs(deltaX) > Math.abs(deltaY)) {
				// 如果此次事件当中横向滑动的距离大于纵向滑动的距离
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
				//在Down时如果还在滑动，则先停止滑动
				mScroller.abortAnimation();
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastX;
			int deltaY = y - mLastY;
			// ScrollBy调用的是相对于mScrollX和mScrollY的位置
			scrollBy(-deltaX, 0);
			break;
		}
		case MotionEvent.ACTION_UP: {
			// 当手指抬起时计算滑动的距离
			int scrollX = getScrollX();
			// 滑动速度按照1000毫秒计算
			mVelocityTracker.computeCurrentVelocity(1000);
			// 获取水平滑动的速度
			float xVelocigy = mVelocityTracker.getXVelocity();
			if (Math.abs(xVelocigy) >= 50) {
				// 如果速度大于50，根据滑动的方向来判断是否需要更换page
				mChildIndex = xVelocigy > 0 ? mChildIndex - 1 : mChildIndex + 1;
			} else {
				// 如果速度小于50，根据滑动的距离来判断应该选哪一个page
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measuredWidth = 0;
		int measuredHeight = 0;
		final int childCount = getChildCount();
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);// 获得父容器宽度的剩余空间
		int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
		if (childCount == 0) {
			// 这个方法很重要，最后测量出来的值就再这边设置，即这边设置的就是测量之后的宽和高
			// 如果没有子元素，则直接设置为0
			setMeasuredDimension(0, 0);
		} else if (widthMeasureMode == MeasureSpec.AT_MOST
				&& heightMeasureMode == MeasureSpec.AT_MOST) {
			// 这里有一个假设，假设所有子view的宽高一致
			// 如果高宽用了wrap
			final View childView = getChildAt(0);
			measuredWidth = childView.getMeasuredHeight() * childCount;
			measuredHeight = childView.getMeasuredHeight();
			setMeasuredDimension(measuredWidth, measuredHeight);
		} else if (heightMeasureMode == MeasureSpec.AT_MOST) {
			final View childView = getChildAt(0);
			measuredHeight = childView.getMeasuredHeight();
			setMeasuredDimension(widthMeasureSize, measuredHeight);

		} else if (widthMeasureMode == MeasureSpec.AT_MOST) {
			final View childView = getChildAt(0);
			measuredWidth = childView.getMeasuredWidth() * childCount;
			setMeasuredDimension(measuredWidth, heightMeasureSize);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();
		mChildrenSize = childCount;
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(0);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				mChildWidth = childWidth;
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}

		}
	}

	

	private void smoothScrollBy(int dx, int dy) {
		mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
		invalidate();
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
			
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		mVelocityTracker.recycle();
		super.onDetachedFromWindow();
	}
	
	

}
