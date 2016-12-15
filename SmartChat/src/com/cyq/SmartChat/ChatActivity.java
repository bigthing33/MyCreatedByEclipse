package com.cyq.SmartChat;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyq.SmartChat.Utils.HttpCallBackListener;
import com.cyq.SmartChat.Utils.HttpUtil;
import com.cyq.SmartChat.model.Msg;

public class ChatActivity extends Activity {
	private ListView msgListView;
	private EditText question_et;
	private Button send;
	private String question;
	private Handler myHandler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case 0:
				String text=  (String) msg.obj;
				Msg msg_recevied=new Msg(text, Msg.TYPE_RECEVIED);
				msgList.add(msg_recevied);
				adapter.notifyDataSetChanged();//������Ϣʱˢ��
				msgListView.setSelection(msgList.size());//��λlistview�����һ��
				question_et.setText("");//��������
				break;

			default:
				break;
			}
		}
	};
	private MsgAdapter adapter;
	private List<Msg> msgList=new ArrayList<Msg>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		initMsgs();
		msgListView=(ListView) findViewById(R.id.msg_list_view);
		adapter=new MsgAdapter(ChatActivity.this, R.layout.msg_item, msgList);
		msgListView.setAdapter(adapter);
		question_et=(EditText) findViewById(R.id.question);
		send=(Button) findViewById(R.id.send);
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String APIKEY = "1794a5cd37dd2190565ff0e806287269"; 
					String INFO;
					question=question_et.getText().toString();
					if(!TextUtils.isEmpty(question)){
						Msg msg_send=new Msg(question, Msg.TYPE_SEND);
						msgList.add(msg_send);
						adapter.notifyDataSetChanged();//������Ϣʱˢ��
						msgListView.setSelection(msgList.size());//��λlistview�����һ��
						INFO = URLEncoder.encode(question, "utf-8");
						String request = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
						HttpUtil.sendHttpRequest(request, new HttpCallBackListener() {
							
							@Override
							public void onFinish(String response) {
								try {
									Message msg=new Message();
									msg.what=0;
									JSONObject jsonObj=new JSONObject(response);
									msg.obj=jsonObj.getString("text").toString();
									myHandler.sendMessage(msg);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								
							}
							
							@Override
							public void onErro(Exception e) {
								runOnUiThread(new  Runnable() {
									public void run() {
										Toast.makeText(ChatActivity.this, "����ʧ��", 1).show();
									}
								});
								
							}
						});
					}
					else{
						Message msg=new Message();

						msg.what=1;
						msg.obj="����Ҫ��ʲô����";
						myHandler.sendMessage(msg);
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
		});
		
		
	}
	private void initMsgs(){
		Msg msg_1=new Msg("�ף�����ʲô�����˵����,ʲô������Ŷ��ֻҪ���ʣ��Ҿͻ�ش��㣬���������ܡ�", Msg.TYPE_RECEVIED);
		msgList.add(msg_1);
		
	}
		
	}
