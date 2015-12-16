package com.example.testdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testdemo.base.BaseActivity;
import com.example.testdemo.utils.HttpCallBackListener;
import com.example.testdemo.utils.HttpUtil;

public class SendHttpRequestActivity extends BaseActivity implements OnClickListener {
	private EditText address_et;
	private TextView result_tv;
	private Button send_btn;
	private HttpCallBackListener listener;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				result_tv.setText(msg.obj.toString());
				break;
			case 1:
				result_tv.setText(msg.obj.toString());
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendhttprequest);
		initUI();
		listener = new HttpCallBackListener() {

			@Override
			public void onFinish(String response) {
				Message msg=new Message();
				msg.what=0;
				msg.obj=response;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onErro(Exception e) {
				Message msg=new Message();
				msg.what=1;
				msg.obj="获取失败";
				mHandler.sendMessage(msg);

			}
		};

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initUI() {
		address_et = (EditText) findViewById(R.id.address_et);
		result_tv =  (TextView) findViewById(R.id.result_tv);
		address_et.setText("https://www.baidu.com/");
		send_btn = (Button) findViewById(R.id.send_btn);
		send_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.send_btn:
			SendHttpRequest(address_et.getText().toString(), listener);

			break;

		default:
			break;
		}

	}

	private void SendHttpRequest(String address, HttpCallBackListener listener) {
		HttpUtil.sendHttpRequest(address, listener);

	}

	public static void actionStart(Context context) {
		Intent intent = new Intent(context, SendHttpRequestActivity.class);
		context.startActivity(intent);

	}

}
