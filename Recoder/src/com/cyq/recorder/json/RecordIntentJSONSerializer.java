package com.cyq.recorder.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

import com.cyq.recorder.models.Photo;
import com.cyq.recorder.models.Record;

public class RecordIntentJSONSerializer {
	private Context mContext;
	private String mFilename;
	public RecordIntentJSONSerializer(Context context, String filename) {
		super();
		mContext = context;
		mFilename = filename;
	}
	public ArrayList<Record> loadRecords() throws IOException, JSONException{
		ArrayList<Record> records=new ArrayList<Record>();
		BufferedReader reader=null;
		try{
			/*File file = new File(Environment.getExternalStorageDirectory(), mFilename);
			Log.e("stream",Environment.getExternalStorageDirectory().getAbsolutePath() );
				FileInputStream fis = new FileInputStream(file);
				//把字节流转换成字符流
				reader = new BufferedReader(new InputStreamReader(fis));*/
				InputStream in=mContext.openFileInput(mFilename);
				reader=new BufferedReader(new InputStreamReader(in));
				StringBuilder jsonString=new StringBuilder();
				String line=null;
				while((line=reader.readLine())!=null){
					jsonString.append(line);
				}
				JSONArray array=(JSONArray) new JSONTokener(jsonString.toString()).nextValue();
				
				for(int i=0;i<array.length();i++){
					
					
					/*System.out.println(array.toString());
					Log.e("RecordLab",array.toString());
					Log.e("RecordLab",array.getJSONObject(i).getString("title"));
					Log.e("RecordLab",array.getJSONObject(i).getBoolean("solved"));
					Log.e("RecordLab",array.getJSONObject(i).getLong("date"));
					Log.e("RecordLab",array.getJSONObject(i).getJSONObject("photo").toString());
					Log.e("RecordLab",array.getJSONObject(i).toString());*/
				
					/*Record record=new Record();
					if(array.getJSONObject(i).has("title")){
						
						record.setTitle(array.getJSONObject(i).getString("title"));
					}
					record.setSolved(array.getJSONObject(i).getBoolean("solved"));
					if(array.getJSONObject(i).has("photo")){
						
						String filename=array.getJSONObject(i).getJSONObject("photo").getString("filename");
						Photo photo=new Photo();
						photo.setFilename(filename);
						record.setPhoto(photo);
					}
					record.setDate(array.getJSONObject(i).getString("date"));
					Log.e("RecordLab",array.getJSONObject(i).getString("id")+"::::");*/
					records.add(new Record(array.getJSONObject(i)));
				}
			
		}catch(FileNotFoundException e){
			
		}finally{
			if(reader!=null)
				reader.close();
			
			
		}
		return records;
		
		
	}
	public void saveRecords(ArrayList<Record> records) throws IOException, JSONException{
		//build an array in JSON
		JSONArray array=new JSONArray();
		for(Record c:records){
			array.put(c.toJSON());
			//write the file to disk
			Writer writer=null;
			try {
				OutputStream out=mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
				writer=new OutputStreamWriter(out);
				
				writer.write(array.toString());
				Log.e("RecordLab", "msg");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(writer!=null){
					writer.close();
				}
			}
			
		}
		
	}

}
