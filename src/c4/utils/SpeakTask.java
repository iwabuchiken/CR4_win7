package c4.utils;

import java.util.ArrayList;
import java.util.List;

import cr4.main.MainActv;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//public class SpeakTask extends AsyncTask<String[], Integer, String>{
public class SpeakTask extends AsyncTask<Integer, Integer, String>{

	//
	Activity actv;
	
	int position;
	
	//
	public SpeakTask(Activity actv) {
		
		this.actv = actv;
		
	}//public SearchTask(Activity actv)


	@Override
	protected String doInBackground(Integer... positions) {
		
		this.position = positions[0];
		
		for (int i = position; i < MainActv.textList.size(); i++) {
			
			String text = MainActv.textList.get(position);
			
			Methods.start_speech_speak(text);
			
			while(MainActv.tts.isSpeaking());
			
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				// Log
				Log.d("SpeakTask.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Exception" + e.toString());
				
			}
			
			if (MainActv.textList.size() - 1 > position) {
				
				position += 1;
				
				publishProgress(position);
				
//				Methods.set_pref(actv, MainActv.prefName_list_position, position);
//				
//				MainActv.adp.notifyDataSetChanged();
				
			}//if (MainActv.textList.size() < )
			
			
		}//for (int i = position; i < MainActv.textList.size(); i++)
		
		
//		String text = MainActv.textList.get(position);
////		String text = MainActv.textList.get(positions[0]);
//		
//		Methods.start_speech_speak(text);
//		
//		while(MainActv.tts.isSpeaking());
		
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		/*----------------------------
		 * memo
			----------------------------*/
//		if (MainActv.textList.size() - 1 > position) {
//			
//			position += 1;
//			
//			Methods.set_pref(actv, MainActv.prefName_list_position, position);
//			
//			MainActv.adp.notifyDataSetChanged();
//			
//		}//if (MainActv.textList.size() < )
		
		
		super.onPostExecute(result);

	}//protected void onPostExecute(String result)


	@Override
	protected void onProgressUpdate(Integer... values) {
		
		// Log
		Log.d("SpeakTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Now position is: " + values[0]);

		Methods.set_pref(actv, MainActv.prefName_list_position, values[0]);
		
		MainActv.adp.notifyDataSetChanged();
		
		int decrement = MainActv.main_lv.getChildCount() / 2;
		
		MainActv.main_lv.setSelection(position - decrement);

		
//		super.onProgressUpdate(values);
	}//protected void onProgressUpdate(Integer... values)
	
	
}//public class SearchTask
