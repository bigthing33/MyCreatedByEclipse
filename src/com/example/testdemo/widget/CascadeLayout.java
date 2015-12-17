package com.example.testdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.testdemo.R;

public class CascadeLayout extends ViewGroup {

	private int mHorizontalSpacing;// 离上个view的水平偏移距离
	private int mVerticalSpacing;// 离上个view的竖直偏移距离

	/**
	 * 当使用XML创建该构造函数时会调用这个构造方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public CascadeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取自定义的属性值,没有的时候就使用默认值
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CascadeLayout);
		try {
			// getDimensionPixelSize()获取属性值的方法，第一个参数是在XML中书写的值，是如果没有写的时候会使用第二个默认的值。
			mVerticalSpacing = a.getDimensionPixelSize(
					R.styleable.CascadeLayout_vertical_spacing,
					getResources().getDimensionPixelSize(
							R.dimen.cascade_vertical_spacing));
			mHorizontalSpacing = a.getDimensionPixelSize(
					R.styleable.CascadeLayout_horizontal_spacing,
					getResources().getDimensionPixelSize(
							R.dimen.cascade_horizontal_spacing));
		} finally {
			a.recycle();
		}

	}

	// 测量宽度和高度
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getPaddingLeft();
		int height = getPaddingTop();
		int verticalSpacing;
		// 获得子视图的个数
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			verticalSpacing = mVerticalSpacing;

			View child = getChildAt(i);
			// 循環調用，测量每个子视图的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);

			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			width = getPaddingLeft() + mHorizontalSpacing * i;

			lp.x = width;
			lp.y = height;

			if (lp.verticalSpacing >= 0) {
				verticalSpacing = lp.verticalSpacing;
			}

			width += child.getMeasuredWidth();
			height += verticalSpacing;
		}

		width += getPaddingRight();
		height += getChildAt(getChildCount() - 1).getMeasuredHeight()
				+ getPaddingBottom();
		// 设置视图的宽度和高度
		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof LayoutParams;
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
		return new LayoutParams(p.width, p.height);
	}

	/**
	 * 该方法让视图和子视图安置在指定的位置,参数的意义自己找吧
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			LayoutParams lp = (LayoutParams) child.getLayoutParams();

			child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y
					+ child.getMeasuredHeight());
		}
	}

	/**
	 * 重写了LayoutParams类该类用于保存每个子视图的x和y轴的位置,
	 * 为了让这个内部类产生作用必须重写generateDefaultLayoutParams，generateLayoutParams等上面的方法。
	 * 
	 * @author Administrator
	 * 
	 */
	public static class LayoutParams extends ViewGroup.LayoutParams {
		int x;
		int y;
		public int verticalSpacing;

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);

			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CascadeLayout_LayoutParams);
			try {
				verticalSpacing = a
						.getDimensionPixelSize(
								R.styleable.CascadeLayout_LayoutParams_layout_vertical_spacing,
								-1);
			} finally {
				a.recycle();
			}
		}

		public LayoutParams(int w, int h) {
			super(w, h);
		}

	}

}
