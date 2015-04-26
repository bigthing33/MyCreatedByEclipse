package com.cyq.recorder;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.cyq.recorder.models.Record;
import com.cyq.recorder.models.RecordLab;

public class RecordListFragment extends ListFragment {
	private ArrayList<Record> mRecords;
	private boolean mSubTitleVisible;
	private static final String TAG ="RecordListFragment";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		//设置activity的标题
		getActivity().setTitle(R.string.records_title);
		//获取一个records
		mRecords = RecordLab.get(getActivity()).getRecords();
		//ArrayAdapter<Record> adapter=new ArrayAdapter<Record>(getActivity(),android.R.layout.simple_list_item_1,mRecords);
		RecordAdapter adapter=new RecordAdapter(mRecords);
		setListAdapter(adapter);
		setRetainInstance(true);
		mSubTitleVisible=false;
	}
	
	
    @TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View v=super.onCreateView(inflater, container, savedInstanceState);
		if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB){
			if(mSubTitleVisible){
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		ListView listView = (ListView) v.findViewById(android.R.id.list);
		registerForContextMenu(listView);
		return v;
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((RecordAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
			getActivity().getMenuInflater().inflate(R.menu.record_list_item_context, menu);
	}
	


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info=(AdapterContextMenuInfo) item.getMenuInfo();
		int position=info.position;
		RecordAdapter adapter=(RecordAdapter) getListAdapter();
		Record record = adapter.getItem(position);
		switch (item.getItemId()) {
		case R.id.menu_item_delete_record:
			RecordLab.get(getActivity()).deleteRecord(record);
			adapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_record_list, menu);
		MenuItem showSubtitle =menu.findItem(R.id.menu_item_show_subtitle);
		if(mSubTitleVisible&& showSubtitle!=null){
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}
	
    @TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_record:
			Record record=new Record();
			RecordLab.get(getActivity()).addRecord(record);
			Intent i=new Intent(getActivity(),RecordPagerActivity.class);
			i.putExtra(RecordFragment.EXTRA_RECORD_ID, record.getId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if(getActivity().getActionBar().getSubtitle()==null){
				mSubTitleVisible=true;
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
			}else{
				getActivity().getActionBar().setSubtitle(null);
				mSubTitleVisible=false;
				item.setTitle(R.string.show_subtitle);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}


	


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Record c=  ((RecordAdapter)getListAdapter()).getItem(position);
		//开始一个Recorkactivity
		/*Intent i= new Intent(getActivity(), RecorderActivity.class);
		i.putExtra(RecordFragment.EXTRA_RECORD_ID, c.getId());
		startActivity(i);*/
		Intent i= new Intent(getActivity(), RecordPagerActivity.class);
		i.putExtra(RecordFragment.EXTRA_RECORD_ID, c.getId());
		startActivity(i);
		
	}
	private class RecordAdapter extends ArrayAdapter<Record>{

		public RecordAdapter(ArrayList<Record> records) {
			super(getActivity(), 0,records);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 如果没有试图，，则Inflate 一个
			if(convertView==null){
				convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_record, null);
			}
			Record c=getItem(position);
			TextView titleTextView = (TextView) convertView.findViewById(R.id.record_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			TextView dateTextView = (TextView) convertView.findViewById(R.id.record_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.record_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			return convertView;
		}
		
	}
	
}
