package c4.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;

import org.apache.commons.lang.StringUtils;

import cr4.listeners.DialogButtonOnClickListener;
import cr4.listeners.DialogButtonOnTouchListener;
import cr4.main.MainActv;
import cr4.main.R;

public class Methods {

	static int counter;		// Used => sortFileList()
	
	
	/****************************************
	 * Enums
	 ****************************************/
	public static enum DialogTags {
		// Generics
		dlg_generic_dismiss, dlg_generic_dismiss_second_dialog, dlg_generic_dismiss_third_dialog,
		
		
		// dlg_create_folder.xml
		dlg_create_folder_ok, dlg_create_folder_cancel,

		// dlg_input_empty.xml
		dlg_input_empty_reenter, dlg_input_empty_cancel,
		
		// dlg_confirm_create_folder.xml
		dlg_confirm_create_folder_ok, dlg_confirm_create_folder_cancel,

		// dlg_confirm_remove_folder.xml
		dlg_confirm_remove_folder_ok, dlg_confirm_remove_folder_cancel,

		// dlg_drop_table.xml
		dlg_drop_table_btn_cancel, dlg_drop_table,
		
		// dlg_confirm_drop.xml
		dlg_confirm_drop_table_btn_ok, dlg_confirm_drop_table_btn_cancel,
		
		// dlg_add_memos.xml
		dlg_add_memos_bt_add, dlg_add_memos_bt_cancel, dlg_add_memos_bt_patterns,
		dlg_add_memos_gv,

		// dlg_move_files.xml
		dlg_move_files_move, dlg_move_files,
		
		// dlg_confirm_move_files.xml	=> ok, cancel, dlg tag
		dlg_confirm_move_files_ok, dlg_confirm_move_files_cancel, dlg_confirm_move_files,

		// dlg_item_menu.xml
		dlg_item_menu_bt_cancel, dlg_item_menu,

		// dlg_create_table.xml
		dlg_create_table_bt_create,

		// dlg_memo_patterns.xml
		dlg_memo_patterns,
		
		// dlg_register_patterns.xml
		dlg_register_patterns_register,

		// dlg_search.xml
		dlg_search, dlg_search_ok,

		// dlg_admin_patterns.xml

		// dlg_confirm_delete_patterns.xml
		dlg_confirm_delete_patterns_ok,
		
	}//public static enum DialogTags
	
	public static enum DialogItemTags {
		// dlg_moveFiles(Activity actv)
		dlg_move_files,
		
		// dlg_add_memos.xml
		dlg_add_memos_gv,

		// dlg_db_admin.xml
		dlg_db_admin_lv,

		// dlg_admin_patterns.xml
		dlg_admin_patterns_lv,

		// dlg_delete_patterns.xml
		dlg_delete_patterns_gv,
		
	}//public static enum DialogItemTags
	
	
	public static enum ButtonTags {
		// MainActivity.java
		main_bt_stop,
		
	}//public static enum ButtonTags
	
	public static enum ItemLongClickTags {
		
		// MainActivity.java
		dir_list,
		
		// ThumbnailActivity.java
		dir_list_thumb_actv,
		
		// Methods.java
		dir_list_move_files,
		
		// TIListAdapter.java
		tilist_checkbox,
		
		
	}//public static enum ItemTags

	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

	public static enum PrefenceLabels {
		
		CURRENT_PATH,
		
		thumb_actv,
		
		chosen_list_item,
		
	}//public static enum PrefenceLabels

	public static enum ListTags {
		// MainActivity.java
		actv_main_lv,
		
	}//public static enum ListTags

	
	/****************************************
	 * Vars
	 ****************************************/
	public static final int vibLength_click = 35;

	static int tempRecordNum = 20;

	/****************************************
	 * Methods
	 ****************************************/
	public static void sortFileList(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/*----------------------------
		 * 1. Prep => Comparator
		 * 2. Sort
			----------------------------*/
		/*----------------------------
		 * 1. Prep => Comparator
			----------------------------*/
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				/*----------------------------
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					----------------------------*/
				
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				int res = pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/*----------------------------
		 * 2. Sort
			----------------------------*/
		Arrays.sort(files, filecomparator);

	}//public static void sortFileList(File[] files)

	/****************************************
	 *
	 * 
	 * <Caller> 1. Methods.enterDir()
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean update_prefs_currentPath(Activity actv, String newPath) {
		
//		SharedPreferences prefs = 
//				actv.getSharedPreferences(MainActv.prefs_current_path, MainActv.MODE_PRIVATE);
//
//		/*----------------------------
//		 * 2. Get editor
//			----------------------------*/
//		SharedPreferences.Editor editor = prefs.edit();
//
//		/*----------------------------
//		 * 3. Set value
//			----------------------------*/
//		editor.putString(MainActv.prefs_current_path, newPath);
//		
//		try {
//			editor.commit();
//			
//			return true;
//			
//		} catch (Exception e) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Excption: " + e.toString());
//			
//			return false;
//		}
		return false;
		
	}//public static boolean update_prefs_current_path(Activity actv, Strin newPath)

	/****************************************
	 *
	 * 
	 * <Caller> 1. 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean prefs_clear(Activity actv, String pref_name) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, MainActv.MODE_PRIVATE);

		/*----------------------------
		 * 2. Get editor
			----------------------------*/
		SharedPreferences.Editor editor = prefs.edit();

		/*----------------------------
		 * 3. Clear
			----------------------------*/
		try {
			
			editor.clear();
			editor.commit();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Prefs cleared");
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
		}

//		return false;
		
	}//public static boolean clear_prefs_current_path(Activity actv, Strin newPath)

	
	/****************************************
	 *
	 * 
	 * <Caller> 1. Methods.enterDir()
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static String get_currentPath_from_prefs(Activity actv) {
		
//		SharedPreferences prefs = 
//				actv.getSharedPreferences(MainActv.prefs_current_path, MainActv.MODE_PRIVATE);
//
//		return prefs.getString(MainActv.prefs_current_path, null);

		return null;
		
	}//public static String get_currentPath_from_prefs(Activity actv)

	public static void confirm_quit(Activity actv, int keyCode) {
		
//		// TODO 自動生成されたメソッド・スタブ
//		if (keyCode==KeyEvent.KEYCODE_BACK) {
//			
//			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
//			
//	        dialog.setTitle("アプリの終了");
//	        dialog.setMessage("アプリを終了しますか？");
//	        
//	        dialog.setPositiveButton("終了",new DialogListener(actv, dialog, 0));
//	        dialog.setNegativeButton("キャンセル",new DialogListener(actv, dialog, 1));
//	        
//	        dialog.create();
//	        dialog.show();
//			
//		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

	public static List<String> getTableList(Activity actv) {
//		DBUtils dbu = new DBUtils(actv, MainActv.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();
//
//		//=> source: http://stackoverflow.com/questions/4681744/android-get-list-of-tables : "Just had to do the same. This seems to work:"
//		String q = "SELECT name FROM " + "sqlite_master"+
//						" WHERE type = 'table' ORDER BY name";
//		
//		Cursor c = null;
//		try {
//			c = rdb.rawQuery(q, null);
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "c.getCount(): " + c.getCount());
//
//		} catch (Exception e) {
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception => " + e.toString());
//		}
//		
//		// Table names list
//		List<String> tableList = new ArrayList<String>();
//		
//		// Log
//		if (c != null) {
//			c.moveToFirst();
//			
//			for (int i = 0; i < c.getCount(); i++) {
//				//
//				tableList.add(c.getString(0));
//				
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "c.getString(0): " + c.getString(0));
//				
//				
//				// Next
//				c.moveToNext();
//				
//			}//for (int i = 0; i < c.getCount(); i++)
//
//		} else {//if (c != null)
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "c => null");
//		}//if (c != null)
//
////		// Log
////		Log.d("Methods.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "c.getCount(): " + c.getCount());
////		
//		rdb.close();
//		
//		return tableList;
		
		return null;
	}//public static List<String> getTableList()

	/****************************************
	 *		insertDataIntoDB()
	 * 
	 * <Caller> 
	 * 1. private static boolean refreshMainDB_3_insert_data(Activity actv, Cursor c)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static int insertDataIntoDB(Activity actv, String tableName, Cursor c) {
		/*----------------------------
		 * Steps
		 * 0. Set up db
		 * 1. Move to first
		 * 2. Set variables
		 * 3. Obtain data
		 * 4. Insert data
		 * 5. Close db
		 * 6. Return => counter
			----------------------------*/
//		/*----------------------------
//		 * 0. Set up db
//			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, MainActv.dbName);
//		
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//		
//		/*----------------------------
//		 * 1. Move to first
//			----------------------------*/
//		c.moveToFirst();
//
//		/*----------------------------
//		 * 2. Set variables
//			----------------------------*/
//		int counter = 0;
//		int counter_failed = 0;
//		
//		/*----------------------------
//		 * 3. Obtain data
//			----------------------------*/
//		for (int i = 0; i < c.getCount(); i++) {
//
//			String[] values = {
//					String.valueOf(c.getLong(0)),
//					c.getString(1),
//					c.getString(2),
//					String.valueOf(c.getLong(3)),
//					String.valueOf(c.getLong(4))
//			};
//
//			/*----------------------------
//			 * 4. Insert data
//			 * 		1. Insert data to tableName
//			 * 		2. Record result
//			 * 		3. Insert data to backupTableName
//			 * 		4. Record result
//				----------------------------*/
//			boolean blResult = 
//						dbu.insertData(wdb, tableName, DBUtils.cols_for_insert_data, values);
//				
//			if (blResult == false) {
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "i => " + i + "/" + "c.getLong(0) => " + c.getLong(0));
//				
//				counter_failed += 1;
//				
//			} else {//if (blResult == false)
//				counter += 1;
//			}
//
//			//
//			c.moveToNext();
//			
//			if (i % 100 == 0) {
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Done up to: " + i);
//				
//			}//if (i % 100 == 0)
//			
//		}//for (int i = 0; i < c.getCount(); i++)
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "All data inserted: " + counter);
//		
//		/*----------------------------
//		 * 5. Close db
//			----------------------------*/
//		wdb.close();
//		
//		/*----------------------------
//		 * 6. Return => counter
//			----------------------------*/
//		//debug
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "counter_failed(sum): " + counter_failed);
//		
//		return counter;

		return -1;
		
	}//private static int insertDataIntoDB(Activity actv, Cursor c)

	private static boolean insertDataIntoDB(
			Activity actv, String tableName, String[] types, String[] values) {
//		/*----------------------------
//		* Steps
//		* 1. Set up db
//		* 2. Insert data
//		* 3. Show message
//		* 4. Close db
//		----------------------------*/
//		/*----------------------------
//		* 1. Set up db
//		----------------------------*/
//		DBUtils dbu = new DBUtils(actv, MainActv.dbName);
//		
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//		
//		/*----------------------------
//		* 2. Insert data
//		----------------------------*/
//		boolean result = dbu.insertData(wdb, tableName, types, values);
//		
//		/*----------------------------
//		* 3. Show message
//		----------------------------*/
//		if (result == true) {
//		
//			// debug
//			Toast.makeText(actv, "Data stored", 2000).show();
//			
//			/*----------------------------
//			* 4. Close db
//			----------------------------*/
//			wdb.close();
//			
//			return true;
//			
//		} else {//if (result == true)
//		
//			// debug
//			Toast.makeText(actv, "Store data => Failed", 200).show();
//			
//			/*----------------------------
//			* 4. Close db
//			----------------------------*/
//			wdb.close();
//			
//			return false;
//		
//		}//if (result == true)
//		
//		/*----------------------------
//		* 4. Close db
//		----------------------------*/

		return false;
		
	}//private static boolean insertDataIntoDB()


	public static boolean set_pref(Activity actv, String pref_name, String value) {
//		SharedPreferences prefs = 
//				actv.getSharedPreferences(pref_name, MainActv.MODE_PRIVATE);
//
//		/*----------------------------
//		 * 2. Get editor
//			----------------------------*/
//		SharedPreferences.Editor editor = prefs.edit();
//
//		/*----------------------------
//		 * 3. Set value
//			----------------------------*/
//		editor.putString(pref_name, value);
//		
//		try {
//			editor.commit();
//			
//			return true;
//			
//		} catch (Exception e) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Excption: " + e.toString());
//			
//			return false;
//		}

		return false;
	}//public static boolean set_pref(String pref_name, String value)

	public static String get_pref(Activity actv, String pref_name, String defValue) {
//		SharedPreferences prefs = 
//				actv.getSharedPreferences(pref_name, MainActv.MODE_PRIVATE);
//
//		/*----------------------------
//		 * Return
//			----------------------------*/
//		return prefs.getString(pref_name, defValue);
		
		return null;

	}//public static boolean set_pref(String pref_name, String value)

	public static boolean set_pref(Activity actv, String pref_name, int value) {
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, MainActv.MODE_PRIVATE);

		/*----------------------------
		 * 2. Get editor
			----------------------------*/
		SharedPreferences.Editor editor = prefs.edit();

		/*----------------------------
		 * 3. Set value
			----------------------------*/
		editor.putInt(pref_name, value);
		
		try {
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
		}

//		return false;
	}//public static boolean set_pref(String pref_name, String value)

	public static int get_pref(Activity actv, String pref_name, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, MainActv.MODE_PRIVATE);

		/*----------------------------
		 * Return
			----------------------------*/
		return prefs.getInt(pref_name, defValue);

//		return -1;
	}//public static boolean set_pref(String pref_name, String value)

	public static Dialog dlg_template_cancel(Activity actv, int layoutId, int titleStringId,
			int cancelButtonId, DialogTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()

	public static Dialog dlg_template_okCancel(Activity actv, int layoutId, int titleStringId,
			int okButtonId, int cancelButtonId, DialogTags okTag, DialogTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()

	public static long getMillSeconds(int year, int month, int date) {
		// Calendar
		Calendar cal = Calendar.getInstance();
		
		// Set time
		cal.set(year, month, date);
		
		// Date
		Date d = cal.getTime();
		
		return d.getTime();
		
	}//private long getMillSeconds(int year, int month, int date)

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static String get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)


	public static void set_text_list(Activity actv) {
		/*----------------------------
		 * 1. Prepare texts
		 * 2. Prep => List
		 * 3. Prep => Adapter
		 * 
		 * 4. Set adapter to list
			----------------------------*/
		/*----------------------------
		 * 1. Prepare texts
			----------------------------*/
		String text = "植物环绕、氛围舒适，店铺充斥着淡淡的咖啡香。９０后女孩李婷万万也没有想到，" +
				"只是因为她在网络上发布的一个网帖，几个月后，这家名为“很多人咖啡馆\"就真的\"梦想成真\"了。" +
				"去年６月大学毕业后，李婷就有一个开咖啡馆的念头，\"这是每个女孩的梦想\"。今年２月，" +
				"敢想敢做的李婷开始在自己活跃的豆瓣网上发起活动，征集广大网友和她一起开家特别的咖啡馆。" +
				"\"有咖啡，有朋友，有一份闲暇的时光，很多人合作开办一个咖啡馆。\"\"一起投钱，一起参与，只要３０００元，" +
				"就可以成为咖啡店的老板。\"李婷在网帖中写道。这个想法马上就得到了不少网友的支持。很快，就有网友报名参加，" +
				"将自己\"投资款\"汇到了账户。\"当我们发现钱越来越多的时候，既觉得高兴又开始不安。毕竟当时还只是私人户头，" +
				"我们迫切感觉应该要注册公司账户，才能让网友的投资更加放心。\"李婷说。经过线下会议，" +
				"这些热心网友们分别成立了选址设计小组，宣传外联小组，市场调查小组，产品研发小组等。从选址到装修风格，" +
				"甚至包括许多细节，都经过一轮轮的投票。\"我们组建了一个股东群，也产生了董事会、监事会，" +
				"每次都是采取投票的形式决定。\"新上任的董事长\"东方\"告诉记者。记者了解到，长沙\"很多人咖啡馆\"拥有１２０名老板，" +
				"年龄从１４至５２岁不等，以８０后居多，来自社会的各行各业，每人手中有３０００元至３万元不等的投资，" +
				"但都只拥有一人一票的表决权。身为董事会、监事会成员的\"核心骨干\"身负重责，不少人放弃了自己原本拥有的工作，" +
				"全职在咖啡店\"打工\"。";
		
		String[] texts = text.split("(，|。)");
		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "texts.length: " + texts.length);
		
		/*----------------------------
		 * 2. Prep => List
			----------------------------*/
		MainActv.textList = new ArrayList<String>();
		
		for (int i = 0; i < texts.length; i++) {
			
			MainActv.textList.add((i + 1) + ". " + texts[i]);
			
		}//for (int i = 0; i < texts.length; i++)
		
//		for (String item : texts) {
//			
//			textList.add(item);
//			
//		}
		
		/*----------------------------
		 * 3. Prep => Adapter
			----------------------------*/
//		MainActv.adp = new ArrayAdapter<String>(
		MainActv.adp = new MainListAdapter(
				actv,
				R.layout.list_row_main,
				MainActv.textList
			);

		/*----------------------------
		 * 4. Set adapter to list
			----------------------------*/
		ListView lv = ((ListActivity) actv).getListView();
		
		lv.setAdapter(MainActv.adp);
		
	}//public static void set_text_list(Activity actv)

	public static String find_text_trunk(String text) {
		/*----------------------------
		 * memo
			----------------------------*/
		String reg1 = "^\\d+\\.(.+)$";
		Pattern p = Pattern.compile(reg1);
		Matcher m = p.matcher(text);

		return m.find() ? m.group(1) : null;
		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "m.find(): " + m.find());
//		
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "m.group(0): " + m.group(0));
//
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "m.group(1): " + m.group(1));

		
	}//public static void find_text_trunk(String text)

	public static void start_speech(Activity actv, ListView lv, View v, int position) {
		/*----------------------------
		 * 1. Get text
		 * 1-2. Modify text
		 * 2. Speak
			----------------------------*/
		MainActv.st = new SpeakTask(actv);
		
		MainActv.st.execute(new Integer[]{position});
		
//		SpeakTask st = new SpeakTask(actv);
//		
//		st.execute(new Integer[]{position});

//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "message" + lv.getchi);
		
		
		/*----------------------------
		 * 1. Get text
			----------------------------*/
//		String text = (String) lv.getItemAtPosition(position);

//		if (position > MainActv.textList.size()) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "position > MainActv.textList.size()");
//			
//			return;
//			
//		}//if (position > MainActv.textList.size())
//
//		for (int i = position; i < MainActv.textList.size(); i++) {
//			SpeakTask st = new SpeakTask(actv);
//			
//			st.execute(new Integer[]{position});
//		}
		
//        if (MainActv.tts.isSpeaking()) {
//        	MainActv.tts.stop();
//        }

//		for (int i = position; i < MainActv.textList.size(); i++) {
//			
//			Methods.set_pref(actv, MainActv.prefName_list_position, i);
//			
//			MainActv.adp.notifyDataSetChanged();
//			
////			MainActv.prefName_list_position			
//			String text = MainActv.textList.get(i);
//
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "text: " + text + "/" + "position: " + i);
//			
//			Methods.start_speech_speak(text);
//			
//			while(MainActv.tts.isSpeaking());
//			
//		}//for (int i = position; i < MainActv.textList.size(); i++)
//		String text = MainActv.textList.get(position);
//
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "text: " + text + "/" + "position: " + position);
//		
//		Methods.start_speech_speak(text);
//		/*----------------------------
//		 * 1-2. Modify text
//			----------------------------*/
//		String text_new = Methods.find_text_trunk(text);
//		
//		if (text_new == null) {
//			
//			text_new = text;
//			
//		}//if (text_new == null)
//		
//		/*----------------------------
//		 * 2. Speak
//			----------------------------*/
//        if (MainActv.tts.isSpeaking()) {
//        	MainActv.tts.stop();
//        }
//
//		MainActv.tts.speak(text_new, TextToSpeech.QUEUE_FLUSH, null);
//		
	}//public static void start_speech(ListView lv, View v, int position)

	public static void start_speech_speak(String text) {
		/*----------------------------
		 * 1. Get text
		 * 1-2. Modify text
		 * 2. Speak
			----------------------------*/
		/*----------------------------
		 * 1. Get text
			----------------------------*/
//		text = (String) lv.getItemAtPosition(position);
		
		/*----------------------------
		 * 1-2. Modify text
			----------------------------*/
		String text_new = Methods.find_text_trunk(text);
		
		if (text_new == null) {
			
			text_new = text;
			
		}//if (text_new == null)
		
		/*----------------------------
		 * 2. Speak
			----------------------------*/
        if (MainActv.tts.isSpeaking()) {
        	MainActv.tts.stop();
        }

		MainActv.tts.speak(text_new, TextToSpeech.QUEUE_FLUSH, null);
		
	}//private static void start_speech_speak(String text)

}//public class Methods
