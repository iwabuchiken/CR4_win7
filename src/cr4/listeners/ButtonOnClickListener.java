package cr4.listeners;

import java.io.File;

import cr4.main.HisActv;
import cr4.main.MainActv;

import c4.utils.Methods;

import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;
	
	public ButtonOnClickListener(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public ButtonOnClickListener(Activity actv, ListView lv) {
		// 
		this.actv = actv;
		this.lv = lv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public void onClick(View v) {
//		//
		Methods.ButtonTags tag = (Methods.ButtonTags) v.getTag();
//
		vib.vibrate(Methods.vibLength_click);
		
		//
		switch (tag) {
		
		case main_bt_stop://---------------------------------------------------------

			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "onClick: main_bt_stop");

			if (MainActv.tts.isSpeaking()) {
				
				// Log
				Log.d("ButtonOnClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "tts is speaking");
				
				
				MainActv.tts.stop();
				
				// Log
				Log.d("ButtonOnClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "tts stopped");
				
			} else {//if (MainActv.tts)
				// Log
				Log.d("ButtonOnClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "tts is not speaking");
				
			}//if (MainActv.tts)
		
			break;// case main_bt_stop

		case history_bt_top://---------------------------------------------------------
			
			HisActv.lv_history.setSelection(0);
			
			break;// case history_bt_top
			
		case history_bt_bottom://---------------------------------------------------------
			
			HisActv.lv_history.setSelection(
					HisActv.textList.size() - 1);
			
			break;// case history_bt_bottom

		case history_bt_prev://---------------------------------------------------------
			
			int position = Methods.get_pref(actv, MainActv.pref_main_key_thumlist_position, -1);
			
			if (position == -1) {

				// debug
				Toast.makeText(actv, "position => -1", 2000).show();
				
				return;
				
			} else {//if (position == -1)
				
				int num = position - HisActv.lv_history.getChildCount();
				
				if (num < 0) {
					
					HisActv.lv_history.setSelection(0);
					
				} else {//if (condition)
					
					HisActv.lv_history.setSelection(num);
					
				}//if (condition)
				
				
//				HisActv.lv_history.setSelection(position - HisActv.lv_history.getChildCount());
				
			}//if (position == -1)
			
			
			break;// case history_bt_prev
			
		}//switch (tag)
		
	}//public void onClick(View v)

}
