package c4.utils;

import java.util.List;

import cr4.items.HI;
import cr4.main.MainActv;
import cr4.main.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HIListAdapter2 extends ArrayAdapter<String> {
	
	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;



	public HIListAdapter2(Context con, int resourceId, List<String> HIList) {
		/*----------------------------
		 * 1. super
			----------------------------*/
		super(con, resourceId, HIList);
		
		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}//public HIListAdapter(Context con, int resourceId, List<HI> HIList)

	
	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	/*----------------------------
		 * Steps
		 * 1. Set up
		 * 2. Get item
		 * 3. Get view
		 * 
		 * 4. Set values
		 * 
		 * 5. Store position value
		 * 
		 * 9. Return v
			----------------------------*/
    	/*----------------------------
		 * 1. Set up
			----------------------------*/
    	View v = null;

    	if (convertView != null) {
			v = convertView;
		} else {//if (convertView != null)

//			v = inflater.inflate(R.layout.list_row_main, null);
			v = inflater.inflate(R.layout.list_row_history_2, null);
			
		}//if (convertView != null)

		/*----------------------------
		 * 2. Get item
			----------------------------*/
		String text = (String) getItem(position);
		
		// Log
		Log.d("HIListAdapter.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "getClass: " + text.getClass().getName());
		
		
    	/*----------------------------
		 * 3. Get view
			----------------------------*/
		TextView tv_text = (TextView) v.findViewById(R.id.list_row_history_2_tv);

		/*----------------------------
		 * 4. Set values
			----------------------------*/
		tv_text.setText(text);
		
		/*********************************
		 * 5. Store position value
		 *********************************/
		boolean res = 
				Methods.set_pref(
						(Activity)con,
						MainActv.pref_main_key_thumlist_position,
						position);
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res: " + res);
		
		/*----------------------------
		 * 9. Return v
			----------------------------*/
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)

}//public class HIListAdapter extends ArrayAdapter<HI>
