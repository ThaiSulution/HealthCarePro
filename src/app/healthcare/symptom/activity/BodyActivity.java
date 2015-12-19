package app.healthcare.symptom.activity;



//import phivien.dev.adapters.symptoms.Symptoms4Part;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import app.healthcare.R;
import app.healthcare.symptom.list.ListBenh;

public class BodyActivity extends Activity{

	ImageButton gender, rotate, symptoms;
	ImageView background, overrideBG;
	
	// Khai báo biến xác định t�?a độ bộ phận cơ thể
	int coordinates;
	int coordinates_head = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avatar);
		
		gender = (ImageButton) findViewById(R.id.btn_avt_gender);
		rotate = (ImageButton) findViewById(R.id.btn_avt_flip);
		symptoms = (ImageButton) findViewById(R.id.btn_avt_all_symptoms);
		background = (ImageView) findViewById(R.id.layer2);
		overrideBG = (ImageView) findViewById(R.id.layer1);
		
		
		overrideBG.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					//Kiem tra
					Toast.makeText(BodyActivity.this, "Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()),
                        Toast.LENGTH_LONG).show();
					int x =0;
					int y=0;
					//int red;
					if(y< 150 && y>50) overrideBG.setImageResource(R.drawable.avt_m_front_sel_head);
					else overrideBG.setImageResource(R.drawable.avt_m_front_sel_abdomen);
					
					Intent iSym4Part = new Intent(BodyActivity.this, Symptom4Part.class);
					startActivity(iSym4Part);
				}
				
				return false;
			}
		});
		
		symptoms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iSymptoms = new Intent(BodyActivity.this, ListBenh.class);
				startActivity(iSymptoms);
			}
		});
		
		/*overrideBG.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//idea: get t�?a độ điểm click, t�?a độ điểm nào thì set image chổ đó
				//sau đó xử lý sự kiện click
			switch (overrideBG.getId()){
			case coordinates_head:
				overrideBG.setImageResource(R.drawable.avt_m_front_sel_head);
				break;
			case :
				break;
			default:
				//
				Log.d(TAG, "Null PARTS OF BODY");
				break;
			}
				
			} 
		});
		
		
	}
	
	//Determine the color point of your touch
	
	private void currentColor(Color color)
	{
		//do something
		
	}
	
	
	// Calculate the mid point of the first two fingers.
	private void midPoint(PointF point, MotionEvent event)
	{
		float x =event.getX(0) + event.getX(1);
		float y =event.getY(0) + event.getY(1);
		point.set(x/2, y/2);
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onSearchRequested(View target)
	{
		// do stuff
		//Toast.makeText(MainActivity.this, "Bạn Vừa Bấm Nút",Toast.LENGTH_LONG) .show();
		gender.setBackgroundDrawable(getResources().getDrawable(R.drawable.avt_male));
		//if (background.getDrawable().equals(R.drawable.avt_male))
			//background.setBackgroundResource(0);
			//background.setImageResource(R.drawable.avt_female);
		//else background.setImageResource(R.drawable.avt_male);
	}
	
	public void onRotateRequest(View v)
	{
		/*if(background.getDrawable().equals(R.drawable.avt_male))
			background.setImageResource(R.drawable.avt_male_back);
		else background.setImageResource(R.drawable.avt_female_back);*/
	}
	

	
}

