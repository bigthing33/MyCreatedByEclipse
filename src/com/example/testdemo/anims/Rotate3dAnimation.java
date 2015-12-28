package com.example.testdemo.anims;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {
	private final float mFromDegrees;
	private final float mToDegrees;
	private final float mCenterY;
	private final float mCenterX;
	private final float mDepthZ;
	private final boolean mReverse;
	private Camera mCamera;

	public Rotate3dAnimation(float mFromDegrees, float mToDegrees,
			float mCenterY, float mCenterX, float mDepthZ, boolean mReverse) {
		super();
		this.mFromDegrees = mFromDegrees;
		this.mToDegrees = mToDegrees;
		this.mCenterY = mCenterY;
		this.mCenterX = mCenterX;
		this.mDepthZ = mDepthZ;
		this.mReverse = mReverse;
	}


	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera=new Camera();
	}
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		final float fromDegrees=mFromDegrees;
		float degrees=fromDegrees+(mToDegrees-fromDegrees)*interpolatedTime;
		final float centerX=mCenterX;
		final float centerY=mCenterY;
		final Camera camera=mCamera;
		final Matrix matrix=t.getMatrix();
		camera.save();
		if (mReverse) {
			camera.translate(0.0f, 0.0f, mDepthZ*interpolatedTime);
		}else {
			camera.translate(0.0f, 0.0f, mDepthZ*(1.0f-interpolatedTime));
		}
		
		camera.rotateY(degrees);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
		
		
		
		
		
	}
	

}
