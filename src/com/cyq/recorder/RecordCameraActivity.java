package com.cyq.recorder;

import android.support.v4.app.Fragment;

public class RecordCameraActivity extends SingleFragmentAcitvity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new RecordCameraFragment(); 
	}

}
