package cr4.main;

import java.util.List;

import c4.utils.MainListAdapter;
import c4.utils.Methods;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActv extends ListActivity implements TextToSpeech.OnInitListener {

	public static List<String> textList;
//	public static ArrayAdapter<String> adp;
	public static MainListAdapter adp;

	public static TextToSpeech tts = null;
	
	public static ListView main_lv;
	
	/*----------------------------
	 * Preferences
		----------------------------*/
	public static String prefName_list_position = "main_list_position";
	
	/** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content, title
		 * 
		 * 3. Set text list
		 * 4. Initialize => tts, lv
		 * 
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.main);
		
		setTitle(this.getClass().getName());

		/*----------------------------
		 * 3. Set text list
			----------------------------*/
		Methods.set_text_list(this);
		

		tts = new TextToSpeech(this, this);
		
		main_lv = this.getListView();
		
	}//public void onCreate(Bundle savedInstanceState)

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Clear => Preferences
			----------------------------*/
		boolean res = Methods.prefs_clear(this, MainActv.prefName_list_position);
		
		
		super.onDestroy();
	}//protected void onDestroy()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*----------------------------
		 * 0. If tts is speaking, no operation of 2 to 3
		 * 1. Set current position to pref
		 * 
		 * 2. Notify to adapter
		 * 
		 * 3. Start speech
		 * 
			----------------------------*/
		/*----------------------------
		 * 0. If tts is speaking, no operation of 2 to 3
			----------------------------*/
		if (tts.isSpeaking()) {
			
			return;
			
		}//if (this.tts.isSpeaking())
		
		/*----------------------------
		 * 1. Set current position to pref
			----------------------------*/
		boolean res = Methods.set_pref(this, MainActv.prefName_list_position, position);

		/*----------------------------
		 * 2. Notify to adapter
			----------------------------*/
		if (MainActv.adp != null) {
			
			MainActv.adp.notifyDataSetChanged();
			
		}//if (MainActv.adp != null)
			
		/*----------------------------
		 * 3. Start speech
			----------------------------*/
		Methods.start_speech(this, lv, v, position);
		
////		TextView tv = (TextView) findViewById(R.id.list_row_main_tv);
////		
////		tv.setBackgroundColor(Color.BLUE);
////		tv.setTextColor(Color.WHITE);
//		
////		String text = (String) lv.getItemAtPosition(position);
//		
////		TextView tv = (TextView) lv.getItemAtPosition(position);	//=> 08-19 13:19:49.980: E/AndroidRuntime(26391): java.lang.ClassCastException: java.lang.String
//		TextView tv = (TextView) v.findViewById(R.id.list_row_main_tv);
////		tv.setBackgroundColor(Color.BLUE);
////		tv.setTextColor(Color.WHITE);
//
//		String text = tv.getText().toString();
//		
//		
////		v.setBackgroundColor(Color.WHITE);
//		
////		String text_trunk = text.sub
//		String text_trunk = Methods.find_text_trunk(text);
//		
//		// debug
////		Toast.makeText(MainActv.this, text, 2000).show();
//		Toast.makeText(MainActv.this, text_trunk, 2000).show();
		
		super.onListItemClick(lv, v, position, id);
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	@Override
	public void onInit(int arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
    
}//public class MainActv extends ListActivity
