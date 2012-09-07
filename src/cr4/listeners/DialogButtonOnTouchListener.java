package cr4.listeners;

import c4.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DialogButtonOnTouchListener implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg;
	
	public DialogButtonOnTouchListener(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
	}
	
	public DialogButtonOnTouchListener(Activity actv) {
		//
		this.actv = actv;
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		Methods.DialogTags tag = (Methods.DialogTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
				switch (tag) {
				
				case dlg_generic_dismiss:
				case dlg_register_texts_ok:
					//
					v.setBackgroundColor(Color.GRAY);
					
					break;
				}//switch (tag)
		
			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP:
			switch (tag) {

			case dlg_generic_dismiss:
			case dlg_register_texts_ok:
					//
					v.setBackgroundColor(Color.WHITE);
					
					break;
				}//switch (tag)
		
			break;//case MotionEvent.ACTION_UP:
		
		}//switch (event.getActionMasked())
		return false;
	}

}//public class DialogButtonOnTouchListener implements OnTouchListener
