package cr4.main;

import java.util.ArrayList;
import java.util.List;

import cr4.listeners.ButtonOnClickListener;
import cr4.listeners.ButtonOnTouchListener;
import cr4.main.R;

import c4.utils.DBUtils;
import c4.utils.MainListAdapter;
import c4.utils.Methods;
import c4.utils.SpeakTask;
import android.app.Activity;
import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActv extends ListActivity implements TextToSpeech.OnInitListener {

	public static List<String> textList_full;	// Used in => Methods.dlg_choose_text(Activity actv), 
																//		DialogOnItemClickListener # case dlg_choose_text_from_db_lv
	public static List<String> textList;	// Used in => Methods.set_text_list(Activity actv)
	
//	public static ArrayAdapter<String> adp;
	public static MainListAdapter adp;

	public static TextToSpeech tts = null;
	
	public static ListView main_lv;

	public static SpeakTask st;
	
	public static int sen_length = 30;
	
	/*----------------------------
	 * Preferences
		----------------------------*/
	public static String prefName_list_position = "main_list_position";

	public static String prefName_main = "cm2_main";
	public static String pref_main_key_chosen_text = "chosen_text";
	
	/*----------------------------
	 * DB
		----------------------------*/
	public static String dbName = "cr4.db";
	
	public static String tableName_chinese_texts = "chinese_texts";
	
	public static String[] cols_texts =			 {"text",		 "url", 	"created_at", "modified_at", "genre", 	"memo"};
	
	public static String[] col_types_texts = {"TEXT", "TEXT", "INTEGER", "INTEGER",	 "INTEGER", "TEXT"};

	// read_history
	public static String tableName_read_history = "read_history";
	
	public static String[] cols_history =			 {"text_id",		 "text", 	"position", 	"item", 		"created_at"};
	
	public static String[] col_types_history = {"INTEGER", "TEXT", "INTEGER", "TEXT",	"INTEGER"};
	
	
	/** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content, title
		 * 
		 * 3. Set text list
		 * 4. Initialize => tts, lv, st, textList_full
		 * 
		 * 5. Set listeners
		 * 
		 * 6. Setup db
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.main);
		
		setTitle(this.getClass().getName());

		/*----------------------------
		 * 3. Set text list
			----------------------------*/
		Methods.set_text_list(this);
		

		/*----------------------------
		 * 4. Initialize => tts, lv
			----------------------------*/
		tts = new TextToSpeech(this, this);
		
		main_lv = this.getListView();
		
		st = new SpeakTask(this);
		
		textList_full = new ArrayList<String>();
		
		/*----------------------------
		 * 5. Set listeners
			----------------------------*/
		set_listeners();
		
		/*----------------------------
		 * 6. Setup db
			----------------------------*/
		// read_history
		setup_db();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void setup_db() {
		/*----------------------------
		 * 1. Set up db
		 * 
		 * 9. Close db
			----------------------------*/
		/*----------------------------
		 * 1. Set up db
			----------------------------*/
		DBUtils dbu = new DBUtils(this, MainActv.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		boolean res = dbu.tableExists(wdb, MainActv.tableName_read_history);
		
		if (res != false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + MainActv.tableName_read_history);
			
		} else {//if (res != false)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + MainActv.tableName_read_history);
			
			// Create one
			res = dbu.createTable(
											wdb, 
											MainActv.tableName_read_history, 
											MainActv.cols_history,
											MainActv.col_types_history);
			
			if (res == true) {
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: " + MainActv.tableName_read_history);
				
			} else {//if (res == true)
				// Log
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create table failed: " + MainActv.tableName_read_history);
				
			}//if (res == true)
			
		}//if (res != false)

		/*----------------------------
		 * 9. Close db
			----------------------------*/
		wdb.close();
		
	}//private void setup_db()

	private void set_listeners() {
		/*----------------------------
		 * memo
			----------------------------*/
		Button bt_stop = (Button) findViewById(R.id.main_bt_stop);
		
		bt_stop.setTag(Methods.ButtonTags.main_bt_stop);
		
		bt_stop.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_stop.setOnClickListener(new ButtonOnClickListener(this));
		
	}//private void set_listeners()

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater mi = getMenuInflater();
		
//		mi.inflate(R.menu.optionmenu, menu);
		mi.inflate(R.menu.optionmenu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		switch (item.getItemId()) {
		
		case R.id.main_menu_register_texts://----------------------------
			
			Methods.dlg_register_texts(this);
			
			break;// case R.id.main_menu_register_texts
			
		case R.id.main_menu_choose_text://----------------------------
			
			Methods.dlg_choose_text(this);
			
			break;// case R.id.main_menu_register_texts

		case R.id.main_menu_history://----------------------------
			
			Methods.start_HisActv(this);
			
			break;// case R.id.main_menu_register_texts

		}//switch (item.getItemId())

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
		 * 2. Clear tts
			----------------------------*/
		boolean res = Methods.prefs_clear(this, MainActv.prefName_list_position);
		
		/*----------------------------
		 * 2. Clear tts
			----------------------------*/
		if(tts != null && tts.isSpeaking()) {
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "tts != null && tts.isSpeaking()");
			
			tts.stop();
			
			tts.shutdown();
//			tts = null;
			
		} else if(tts != null) {

			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "tts != null");
			
			tts.shutdown();
//			tts = null;
		}
		
		super.onDestroy();
	}//protected void onDestroy()

	/****************************************
	 * onListItemClick(ListView lv, View v, int position, long id)
	 * 
	 * <Caller> 1. 
	 * 
	 * <Desc>
	 *  1. Click => Set pref value; The adapter will refer to the value,
	 *  					then highlight the item clicked (20120828_195017)
	 *  
	 *  <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*----------------------------
		 * 1. Set current position to pref
		 * 
		 * 2. Notify to adapter
		 * 
		 * 2-2. Write log
		 * 
		 * 3. Start speech
		 * 
			----------------------------*/
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
		 * 2-2. Write log
			----------------------------*/
		Methods.write_log(this, lv, position);
		
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
