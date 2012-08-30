package cr4.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cr4.items.HI;

import c4.utils.DBUtils;
import c4.utils.HIListAdapter;
import c4.utils.Methods;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class HisActv extends ListActivity {

	
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

	}//public void onCreate(Bundle savedInstanceState)

	private void set_up() {
		/*----------------------------
		 * 1. Set up db
		 * 2. Query
		 * 3. Build a list
		 * 
		 * 4. Create an adapter
		 * 5. Set adapter
		 * 
		 * 9. Close db
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
		List<HI> HIList = new ArrayList<HI>();

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
		
		List<String> textList = new ArrayList<String>();
		
		for (int i = 0; i < HIList.size(); i++) {
			
			textList.add("(" + (HIList.get(i).getText_id() + 1) + ") " 
							+ HIList.get(i).getText()
							+ "(" + (HIList.get(i).getPosition() + 1) + " : "
							+ Methods.convert_millSec_to_DateLabel(HIList.get(i).getCreated_at()) + ")");
			
		}//for (int i = 0; i < HIList.size(); i++)
		
		/*----------------------------
		 * 4. Create an adapter
			----------------------------*/
		ArrayAdapter<String> adp = new ArrayAdapter<String>(
								this,
								android.R.layout.simple_list_item_1,
								textList
				);
		
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
		setListAdapter(adp);
		
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
