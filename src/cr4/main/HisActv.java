package cr4.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cr4.items.HI;

import c4.utils.DBUtils;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
