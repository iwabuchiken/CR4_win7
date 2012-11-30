package c4.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class Task_RegisterText extends AsyncTask<Integer, Integer, Integer> {

	Activity actv;
	
	public Task_RegisterText(Activity actv) {
		
		this.actv = actv;
		
	}

	@Override
	protected Integer doInBackground(Integer... arg0) {
		// TODO Auto-generated method stub
		return 1;
	}//protected String doInBackground(Integer... arg0)

	@Override
	protected void onPostExecute(Integer result) {
		
		super.onPostExecute(result);
		
		// debug
		Toast.makeText(actv, "result => " + result.intValue(), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	

}//public class Task_RegisterText extends AsyncTask<Integer, Integer, String>
