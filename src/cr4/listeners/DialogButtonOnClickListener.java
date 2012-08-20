package cr4.listeners;

import c4.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;		//=> Used in dlg_input_empty_btn_XXX

	//
	Vibrator vib;
	
	// Used in => Methods.dlg_addMemo(Activity actv, long file_id, String tableName)
	long file_id;
	String tableName;
	
	public DialogButtonOnClickListener(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.dlg = dlg1;
		this.dlg2 = dlg2;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg, long file_id, String tableName) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		this.tableName = tableName;
		
		this.file_id = file_id;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg, long file_id, String tableName)

	@Override
	public void onClick(View v) {
		//
		Methods.DialogTags tag = (Methods.DialogTags) v.getTag();

		//
		switch (tag) {
		
		case dlg_generic_dismiss://------------------------------------------------
			
			vib.vibrate(Methods.vibLength_click);
			
			dlg.dismiss();
			
			break;// case dlg_generic_dismiss
			
		case dlg_register_texts_ok://------------------------------------------------
			
			vib.vibrate(Methods.vibLength_click);
			
			Methods.register_texts(actv, dlg);
			
			break;// case dlg_register_texts_ok
			
		default: // ----------------------------------------------------
			break;
		}//switch (tag)
	}

}//public class DialogButtonOnClickListener implements OnClickListener