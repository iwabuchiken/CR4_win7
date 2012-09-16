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
			
			int num = 0;
			
			HisActv.lv_history.setSelection(num);
			
			Methods.set_pref(actv, MainActv.pref_main_key_thumlist_position, num);
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "set_pref: " + num);

			break;// case history_bt_top
			
		case history_bt_bottom://---------------------------------------------------------
			
			case_history_bt_bottom();
			
////			num = HisActv.textList.size() - 1;
//			num = HisActv.textList.size() - HisActv.lv_history.getChildCount() + 1;
//			
//			HisActv.lv_history.setSelection(
////					HisActv.textList.size() - 1);
//					num);
//			
//			Methods.set_pref(actv, MainActv.pref_main_key_thumlist_position, num);
//			
//			// Log
//			Log.d("ButtonOnClickListener.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]", "set_pref: " + num);
//			
//			Log.d("ButtonOnClickListener.java"
//					+ "["
//					+ Thread.currentThread().getStackTrace()[2]
//							.getLineNumber() + "]", 
//					"HisActv.lv_history.getChildCount(): " + 
//							HisActv.lv_history.getChildCount());
			
			
			break;// case history_bt_bottom

		case history_bt_prev://---------------------------------------------------------
			
			case_history_bt_prev();
			
			break;// case history_bt_prev
			
		case history_bt_next://---------------------------------------------------------
			
			case_history_bt_next();
			
			break;// case history_bt_next
			
		}//switch (tag)
		
	}//public void onClick(View v)

	private void case_history_bt_bottom() {
		// TODO Auto-generated method stub
//		num = HisActv.textList.size() - 1;
		int num = HisActv.textList.size() - HisActv.lv_history.getChildCount() + 1;
		
		HisActv.lv_history.setSelection(
//				HisActv.textList.size() - 1);
				num);
		
		Methods.set_pref(actv, MainActv.pref_main_key_thumlist_position, num);
		
		// Log
		Log.d("ButtonOnClickListener.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "set_pref: " + num);
		
		Log.d("ButtonOnClickListener.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", 
				"HisActv.lv_history.getChildCount(): " + 
						HisActv.lv_history.getChildCount());

	}//private void case_history_bt_bottom()

	private void case_history_bt_next() {
		// TODO Auto-generated method stub
		int position = Methods.get_pref(actv, MainActv.pref_main_key_thumlist_position, -1);
		
		int num;
		
		if (position == -1) {

			// debug
			Toast.makeText(actv, "position => -1", 2000).show();
			
			return;
			
		} else {//if (position == -1)
			
			num = position + HisActv.lv_history.getChildCount();
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", 
					"getChildCount(): " + HisActv.lv_history.getChildCount());
			
			if (num > HisActv.textList.size() - 1) {
				
				num = HisActv.textList.size() - HisActv.lv_history.getChildCount() + 1;
				
//				HisActv.lv_history.setSelection(HisActv.textList.size() - 1);
				HisActv.lv_history.setSelection(num);
				
			} else {//if (condition)
				
				HisActv.lv_history.setSelection(num);
				
			}//if (condition)
			
			Methods.set_pref(actv, MainActv.pref_main_key_thumlist_position, num);
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "next: set_pref: " + num);
			
//			HisActv.lv_history.setSelection(position - HisActv.lv_history.getChildCount());
			
		}//if (position == -1)

	}//private void case_history_bt_next()

	private void case_history_bt_prev() {
		// TODO Auto-generated method stub
		int position = Methods.get_pref(actv, MainActv.pref_main_key_thumlist_position, -1);
		
		int num;
		
		if (position == -1) {

			// debug
			Toast.makeText(actv, "position => -1", 2000).show();
			
			return;
			
		} else {//if (position == -1)
			
//			num = position - (HisActv.lv_history.getChildCount() * 2);
			num = position - HisActv.lv_history.getChildCount();
			
			if (num < 0) {
				
				HisActv.lv_history.setSelection(0);
				
			} else {//if (condition)
				
				HisActv.lv_history.setSelection(num);
				
			}//if (condition)
		
			Methods.set_pref(actv, MainActv.pref_main_key_thumlist_position, num);
			
			// Log
			Log.d("ButtonOnClickListener.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "prev: set_pref: " + num);
			
//			HisActv.lv_history.setSelection(position - HisActv.lv_history.getChildCount());
			
		}//if (position == -1)
		

	}//private void case_history_bt_prev()

}//public class ButtonOnClickListener implements OnClickListener
