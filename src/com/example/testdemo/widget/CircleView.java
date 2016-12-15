package com.example.testdemo.widget;

import com.example.testdemo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
/**
 * 继承view，重写onDraw方法。
 * @author Administrator
 *
 */
public class CircleView extends View {
	private int mColor=Color.RED;
	private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

	public CircleView(Context context) {
		super(context);
		init();
	}


	public CircleView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		init();
	}
	
	public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.CircleView);
		mColor=a.getColor(R.styleable.CircleView_circle_color, Color.RED);
		a.recycle();
 		init();
	}
	private void init(){
		mPaint.setColor(mColor);
	}
	


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMeasureMode=MeasureSpec.getMode(widthMeasureSpec);
		int widthMeasureSize=MeasureSpec.getSize(widthMeasureSpec);
		int heightMeasureMode=MeasureSpec.getMode(heightMeasureSpec);
		int heightMeasureSize=MeasureSpec.getSize(heightMeasureSpec);
		if (widthMeasureMode==MeasureSpec.AT_MOST&&heightMeasureMode==MeasureSpec.AT_MOST) {
			setMeasuredDimension(200, 200);
		}else if (widthMeasureMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(200, heightMeasureSize);
		} else if (heightMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSize, 200);
        }
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final int paddingLeft = getPaddingLeft();
		final int paddingRight = getPaddingRight();
		final int paddingBottom = getPaddingBottom();
		final int paddingTop = getPaddingTop();
		
		int width=getWidth()-paddingLeft-paddingRight;
		int height=getHeight()-paddingTop-paddingBottom;
		int radius=Math.min(width, height)/2;
		canvas.drawCircle(paddingLeft+width/2, paddingTop+height/2, radius, mPaint);
	}
	

}
