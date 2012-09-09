package cr4.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cr4.items.HI;
import cr4.listeners.ButtonOnClickListener;
import cr4.listeners.ButtonOnTouchListener;

import c4.utils.DBUtils;
import c4.utils.HIListAdapter;
import c4.utils.HIListAdapter2;
import c4.utils.Methods;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class HisActv extends ListActivity {

	public static List<HI> HIList;
	public static List<String> textList;
	public static ArrayAdapter<String> adp;
	public static HIListAdapter2 adp2;

	public static ListView lv_history;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.history);
		
		setTitle(this.getClass().getName());
		
		set_up();
		
		set_listeners();

	}//public void onCreate(Bundle savedInstanceState)

	private void set_listeners() {
		/*********************************
		 * 1. Button: Top
		 * 2. Button: Bottom
		 * 3. Button: Previous
		 * 4. Button: Next
		 *********************************/
		/*********************************
		 * 1. Button: Top
		 *********************************/
		Button bt_stop = (Button) findViewById(R.id.history_bt_top);
		
		bt_stop.setTag(Methods.ButtonTags.history_bt_top);
		
		bt_stop.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_stop.setOnClickListener(new ButtonOnClickListener(this));

		/*********************************
		 * 2. Button: Bottom
		 *********************************/
		Button bt_bottom = (Button) findViewById(R.id.history_bt_bottom);
		
		bt_bottom.setTag(Methods.ButtonTags.history_bt_bottom);
		
		bt_bottom.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_bottom.setOnClickListener(new ButtonOnClickListener(this));

		/*********************************
		 * 3. Button: Previous
		 *********************************/
		Button bt_prev = (Button) findViewById(R.id.history_bt_prev);
		
		bt_prev.setTag(Methods.ButtonTags.history_bt_prev);
		
		bt_prev.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_prev.setOnClickListener(new ButtonOnClickListener(this));
		
		
	}//private void set_listeners()

	private void set_up() {
		/*----------------------------
		 * 1. Set up db
		 * 2. Query
		 * 3. Build a list
		 * 
		 * 4. Create an adapter
		 * 5. Set adapter
		 * 
		 * 6. Initialize list view
		 * 
		 * 9. Close db
		 * 
			----------------------------*/
		DBUtils dbu = new DBUtils(this, MainActv.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		String sql = "SELECT * FROM " + MainActv.tableName_read_history;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return;
		}
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "c.getCount() => " + c.getCount());

		if (c.getCount() < 1) {
			
			// Log
			Log.d("HisActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			rdb.close();
			
			return;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. Build a list
			----------------------------*/
		c.moveToFirst();
		
//		List<String> textList = new ArrayList<String>();
		HIList = new ArrayList<HI>();

//		"text_id",		 "text", 	"position", 	"item", 		"created_at"
		
		for (int i = 0; i < c.getCount(); i++) {
			
			HIList.add(new HI(
					
					(int)c.getLong(1),
					c.getString(2),
					(int)c.getLong(3),
					c.getString(4),
					c.getLong(5)
					));

			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		// Log
		for (int i = 0; i < HIList.size(); i++) {

			Log.d("HisActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Text_id(): " + HIList.get(i).getText_id() + " / " + 
					"position: " + HIList.get(i).getPosition());
//					+ "]", "Text_id(): " + HIList.get(i).getText_id());
//					+ "]", "getText(): " + HIList.get(i).getText());

		}//for (int i = 0; i < HIList.size(); i++)
		
		textList = new ArrayList<String>();
		
		for (int i = 0; i < HIList.size(); i++) {
			
			textList.add("(" + (HIList.get(i).getText_id() + 1) + ") " 
							+ HIList.get(i).getText()
							+ "(" + (HIList.get(i).getPosition() + 1) + " : "
							+ Methods.convert_millSec_to_DateLabel(HIList.get(i).getCreated_at()) + ")");
			
		}//for (int i = 0; i < HIList.size(); i++)
		
		/*----------------------------
		 * 4. Create an adapter
			----------------------------*/
		adp2 = new HIListAdapter2(
				this,
				R.layout.list_row_history_2,
				textList
				);

//		adp = new ArrayAdapter<String>(
//								this,
//								android.R.layout.simple_list_item_1,
//								textList
//				);
		
////		ArrayAdapter<HI> adp = new ArrayAdapter<HI>(
//		HIListAdapter adp = new HIListAdapter(
//				this,
////				android.R.layout.simple_list_item_1,
////				R.layout.list_row_history,
////				R.layout.list_row_history,
//				R.layout.history,
//				HIList
//				);

		/*----------------------------
		 * 5. Set adapter
			----------------------------*/
//		setListAdapter(adp);
		setListAdapter(adp2);
		
		/*********************************
		 * 6. Initialize list view
		 *********************************/
		lv_history = this.getListView();
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();

		
	}//private void set_up()
	

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
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
	}
	
}//public class HisActv extends ListActivity
