package c4.utils;

import java.util.ArrayList;
import java.util.List;

import cr4.main.MainActv;
import cr4.main.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<String> {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	//
	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public MainListAdapter(Context con, int resourceId, List<String> list) {
		// Super
		super(con, resourceId, list);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<TI> items)

	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	/*----------------------------
		 * Steps
		 * 1. Set up
		 * 2. Get view
		 * 3. Get item
		 * 
		 * 4. Set bg color
			----------------------------*/
    	/*----------------------------
		 * 1. Set up
			----------------------------*/
    	View v = null;

    	if (convertView != null) {
			v = convertView;
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_main, null);
			
		}//if (convertView != null)

    	/*----------------------------
		 * 2. Get view
			----------------------------*/
		TextView tv = (TextView) v.findViewById(R.id.list_row_main_tv);

		/*----------------------------
		 * 3. Get item
			----------------------------*/
		String text = (String) getItem(position);
		
		tv.setText(text);
		
		/*----------------------------
		 * 4. Set bg color
			----------------------------*/
		int stored_position = Methods.get_pref((Activity) con, MainActv.prefName_list_position, -1);
		
		if (stored_position == -1) {
			
			return v;
			
		}//if (stored_position == -1)
		
		if (stored_position == position) {
			
			tv.setBackgroundColor(Color.BLUE);
			tv.setTextColor(Color.WHITE);
			
		} else {//if (Main)
			
			tv.setBackgroundColor(Color.WHITE);
			tv.setTextColor(Color.BLACK);
			
		}//if (Main)
		
		
//    	return null;
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)



}//public class TIListAdapter extends ArrayAdapter<TI>
