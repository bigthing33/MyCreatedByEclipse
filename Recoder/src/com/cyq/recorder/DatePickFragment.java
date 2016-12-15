package com.cyq.recorder;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickFragment extends DialogFragment {
	private static final String DIALOG_DATE="date";
	public static final String EXTRA_DATE="com.cyq.recorder.date";
	private Date mDate;
	public static DatePickFragment newInstance(String string){
		Bundle args=new Bundle();
		args.putSerializable(EXTRA_DATE, string);
		DatePickFragment fragment= new DatePickFragment();
		fragment.setArguments(args);
		return fragment;
	}
	

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//mDate=(Date) getArguments().getSerializable(EXTRA_DATE);
		mDate=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_daePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				mDate=(Date) new GregorianCalendar(year,monthOfYear,dayOfMonth).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
				
			}
		});
		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle(R.string.date_picker_title)
		//.setPositiveButton(android.R.string.ok, null)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendResult(Activity.RESULT_OK);
				// TODO Auto-generated method stub
				
			}
		})
		.create();
	}
	public void sendResult(int resultCode){
		if(getTargetFragment()==null)
			return;
		Intent i=new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	

}
