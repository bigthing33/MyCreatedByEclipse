package com.cyq.recorder;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.cyq.recorder.models.Photo;
import com.cyq.recorder.models.Record;
import com.cyq.recorder.models.RecordLab;
import com.cyq.recorder.utils.DataChange;
import com.cyq.recorder.utils.PictureUtils;

import android.R.string;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RecordFragment extends Fragment {
	private static final String TAG="RecordFragment";
	private Record mRecord;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	private ImageView mPhotoView;
	private static final String DIALOG_DATE="date";
	
	public static final String EXTRA_RECORD_ID ="com.cyq.recorder.recordintent.record_id";
	private static final int REQUEST_DATE=0;
	private static final int REQUEST_PHOTO=1;
	private static final int REQUEST_CONTACT=2;
	private ImageButton mPhotoButton;
	private Button mLinkmanButton;
	private static final String DIALOG_IMAGE="image";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//从跳转过来的activity或fragment中获取数据
		//UUID recordId=(UUID) getActivity().getIntent().getSerializableExtra(EXTRA_RECORD_ID);
		UUID recordId= (UUID) getArguments().getSerializable(EXTRA_RECORD_ID);
		mRecord=RecordLab.get(getActivity()).getRecord(recordId);
		setHasOptionsMenu(true);
		
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity())!=null){
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.fragment_record, container, false);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityName(getActivity())!=null){
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	    mTitleField = (EditText) v.findViewById(R.id.record_title);
	    mTitleField.setText(mRecord.getTitle());
	    mTitleField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mRecord.setTitle(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	    //设置button显示文字
	    mDateButton=(Button) v.findViewById(R.id.record_date);
	   // mDateButton.setText(mRecord.getDate().toString());
	    updataDate();
	    //mDateButton.setEnabled(false);
	    mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm=getActivity().getSupportFragmentManager();
				//DatePickFragment dialog=new DatePickFragment();
				DatePickFragment dialog=DatePickFragment.newInstance( mRecord.getDate());
				dialog.setTargetFragment(RecordFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
				
			}
		});
	    //设置CheckBox状态的变化
	    mSolvedCheckBox = (CheckBox) v.findViewById(R.id.record_solved);
	    mSolvedCheckBox.setChecked(mRecord.isSolved());
	    mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// 设置Record是否被记录了
				mRecord.setSolved(isChecked);
				
			}
		});
	    mPhotoButton = (ImageButton) v.findViewById(R.id.record_imageButton);
	    mPhotoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(), RecordCameraActivity.class);
				//startActivity(i);
				startActivityForResult(i, REQUEST_PHOTO);
				
			}
		});
	    mPhotoView=(ImageView) v.findViewById(R.id.record_imageView);
	    mPhotoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Photo p=mRecord.getPhoto();
				if(p==null){
					return;
				}
				FragmentManager fm=getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
				ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
				
			}
		});
	    Button reportButton=(Button) v.findViewById(R.id.record_report_button);
	    reportButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_TEXT,getRecordReport());
				i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.records_report_subject));
				i=Intent.createChooser(i, getString(R.string.send_report));
				startActivity(i);
			}
		});
	    mLinkmanButton=(Button) v.findViewById(R.id.record_linkman_button);
	    mLinkmanButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i,REQUEST_CONTACT);
				
			}
		});
	    if(mRecord.getLinkman()!=null){
	    	mLinkmanButton.setText(mRecord.getLinkman());
	    }
		return v;
	}
	public static RecordFragment newInstance(UUID recordId){
		Bundle args=new Bundle();
		args.putSerializable(EXTRA_RECORD_ID, recordId);
		RecordFragment fragment = new RecordFragment();
		fragment.setArguments(args);
		return fragment;
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=Activity.RESULT_OK) return;
        if(requestCode==REQUEST_DATE){
        	Date date=(Date) data.getSerializableExtra(DatePickFragment.EXTRA_DATE);
        	String strDate = DataChange.dateToStrLong(date);
        	mRecord.setDate(strDate);
        	//mDateButton.setText(mRecord.getDate().toString());
        	updataDate();
        }else if(requestCode==REQUEST_PHOTO){
        	//create a new photo object and attach it to the record
        	String filename=data.getStringExtra(RecordCameraFragment.EXTRA_PHOTO_FILENAME);
        	if(filename!=null){
        		Photo p=new Photo(filename);
        		mRecord.setPhoto(p);
        		showPhoto();
        		//Log.i(TAG, "Record:"+mRecord.getTitle()+"has a photo:"+filename);
        	}
        }else if(requestCode==REQUEST_CONTACT){
        	Uri contactUri=data.getData();
        	String[] queryFields=new String[]{
        			ContactsContract.Contacts.DISPLAY_NAME
        			
        	};
        	Cursor c=getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
        	if(c.getCount()==0){
        		c.close();
        		return;
        	}
        	c.moveToFirst();
        	String linkMan=c.getString(0);
        	mRecord.setLinkman(linkMan);
        	mLinkmanButton.setText(linkMan);
        	c.close();
        }
	}
	public void updataDate(){
		mDateButton.setText(mRecord.getDate().toString());
	}
	public void showPhoto(){
		//set the image button's image based on our photo
		Photo p=mRecord.getPhoto();
		BitmapDrawable b=null;
		if(p!=null){
			String path=getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
			b=PictureUtils.getScaleDrawable(getActivity(), path);
		}
		mPhotoView.setImageDrawable(b);
	}
	

	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}

	@Override
	public void onPause() {
		super.onPause();
		RecordLab.get(getActivity()).saveRecords();
	}

	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);
	}
	private String getRecordReport(){
		String solvedString=null;
		if(mRecord.isSolved()){
			solvedString=getString(R.string.records_report_solved);
		}else{
			solvedString=getString(R.string.records_report_unsolved);
		}
		String dateString=mRecord.getDate();
		String Linkman=mRecord.getLinkman();
		if(Linkman!=null){
			Linkman=getString(R.string.records_report_suspect,Linkman);
			
		}else{
			Linkman=getString(R.string.records_report_no_suspect);
			
		}
		String report=getString(R.string.records_report,mRecord.getTitle(),dateString,solvedString,Linkman);
		
		return report;
			
	}
	
	
	

}
