package app.healthcare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

public class DialogResultHeartRate extends Dialog{
	Context c;
	public DialogResultHeartRate(Context context) {
		super(context);
		setContentView(R.layout.custom_dialog);
		c = context;
	}
	
	public void startNewActivity(){
		Intent i = new Intent(c,HeartRateResult.class);
		c.startActivity(i);
	}
}
