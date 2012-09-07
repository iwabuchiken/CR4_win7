package cr4.listeners;

import java.util.List;

import cr4.main.MainActv;

import c4.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogOnItemClickListener implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;
	
	List<String> textList;	// Used in => Methods.dlg_choose_text(Activity actv)
	
	//
	Vibrator vib;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DialogOnItemClickListener(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, List<String> textList) {
		this.actv = actv;
		this.dlg = dlg;
		
		this.textList = textList;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Methods.DialogItemTags tag = (Methods.DialogItemTags) parent.getTag();
//		
		vib.vibrate(Methods.vibLength_click);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
		case dlg_choose_text_from_db_lv://---------------------------------------
			
//			String item = (String) parent.getItemAtPosition(position);
//			String item = textList.get(position);
			
			String item = MainActv.textList_full.get(position);
			
			Methods.set_pref(
					actv, 
					MainActv.prefName_main, 
					MainActv.pref_main_key_chosen_text,
//					(position + 1) + ": " +item.substring(0, 20));
					(position + 1) + ":" +item.substring(0, 20));
			
			Methods.choose_text(actv, dlg, item);
			
			break;// case dlg_choose_text_from_db_lv
			
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)
}
