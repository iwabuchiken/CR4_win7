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

public class HIListAdapter extends ArrayAdapter<HI> {
	
	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;



	public HIListAdapter(Context con, int resourceId, List<HI> HIList) {
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
			v = inflater.inflate(R.layout.list_row_history, null);
			
		}//if (convertView != null)

		/*----------------------------
		 * 2. Get item
			----------------------------*/
		HI hi = (HI) getItem(position);
		
		// Log
		Log.d("HIListAdapter.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "getClass: " + hi.getClass().getName());
		
		
    	/*----------------------------
		 * 3. Get view
			----------------------------*/
		TextView tv_text_id = (TextView) v.findViewById(R.id.list_row_history_tv_text_id);
		TextView tv_text = (TextView) v.findViewById(R.id.list_row_history_tv_text);
		TextView tv_position = (TextView) v.findViewById(R.id.list_row_history_tv_position);
		TextView tv_item = (TextView) v.findViewById(R.id.list_row_history_tv_item);
		TextView tv_created_at = (TextView) v.findViewById(R.id.list_row_history_tv_created_at);

		/*----------------------------
		 * 4. Set values
			----------------------------*/
		tv_text_id.setText(String.valueOf(hi.getText_id()));
		tv_text.setText(hi.getText());
		tv_position.setText(String.valueOf(hi.getPosition()));
		tv_item.setText(hi.getItem());
		tv_created_at.setText(Methods.convert_millSec_to_DateLabel(hi.getCreated_at()));
		
		/*----------------------------
		 * 9. Return v
			----------------------------*/
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)

}//public class HIListAdapter extends ArrayAdapter<HI>
