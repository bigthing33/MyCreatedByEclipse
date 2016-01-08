package com.example.testdemo.activity;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testdemo.R;
import com.example.testdemo.R.id;
import com.example.testdemo.R.layout;
import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.MyUtils;
import com.example.testdemo.widget.HorizontalScrollViewEx;

public class InerIntercept extends BaseActivity {
	private HorizontalScrollViewEx mListContainer;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inerintercepte);
		initUI() ;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initUI() {
		LayoutInflater inflater=getLayoutInflater();
		mListContainer=(HorizontalScrollViewEx) findViewById(R.id.container);
		final int screenWidth=MyUtils.getScreenMetrics(this).widthPixels;
		final int screenHeight=MyUtils.getScreenMetrics(this).heightPixels;
		for (int i = 0; i < 3; i++) {
			ViewGroup layout=(ViewGroup) inflater.inflate(R.layout.content_layout, mListContainer,false);
			layout.getLayoutParams().width=screenWidth;
			TextView textView=(TextView) layout.findViewById(R.id.title);
			textView.setText("page"+(i+1));
			layout.setBackgroundColor(Color.rgb(255/(i+1), 255/(i+1), 0));
			createList(layout);
			mListContainer.addView(layout);
			
			
		}
		

	}
    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(InerIntercept.this, "click item",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
