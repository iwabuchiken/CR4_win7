package cr4.listeners;

import c4.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class ButtonOnTouchListener implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public ButtonOnTouchListener(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		Methods.ButtonTags tag = (Methods.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			switch (tag) {
			
			case main_bt_stop:
			case history_bt_top:
			case history_bt_bottom:
			case history_bt_prev:
			case history_bt_next:
				
				v.setBackgroundColor(Color.GRAY);
//				break;
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP:
			switch (tag) {
			
			case main_bt_stop:
			case history_bt_top:
			case history_bt_bottom:
			case history_bt_prev:
			case history_bt_next:
				
				v.setBackgroundColor(Color.WHITE);
				
				break;
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

}
