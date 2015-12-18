package app.healthcare.call;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import app.dto.DoctorDTO;
import app.healthcare.Constants;
import app.healthcare.R;

import com.gc.materialdesign.widgets.Dialog;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ActivityAddDoctor extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_doctor);
		init();
	}

	private void init() {
		final EditText name = (EditText) findViewById(R.id.tb_name_doctor);
		final EditText numberCall = (EditText) findViewById(R.id.tbnumBerCall);
		final EditText numberSkype = (EditText) findViewById(R.id.tbSkype);
		Button btnAdd = (Button) findViewById(R.id.btnAddDoctor);
		Button btnReinput = (Button) findViewById(R.id.btnReinput);
		btnReinput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				name.setText("");
				numberCall.setText("");
				numberSkype.setText("");
			}
		});
		Button btnShowList = (Button) findViewById(R.id.btn_list_doctor);
		final Intent listDoctor = new Intent(this, ListDoctor.class);
		btnShowList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(listDoctor);
			}
		});
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DoctorDTO dto = new DoctorDTO();
				dto.setDoctorName(name.getText().toString());
				dto.setNumberCall(numberCall.getText().toString());
				dto.setSkypeNumber(numberSkype.getText().toString());
				int id = 1;
				if (Constants.getInstance().listDoctorDTO.size() > 0) {
					id = Constants.getInstance().listDoctorDTO.size() + 1;
				}
				dto.setDoctorId(id);
				Constants.getInstance().listDoctorDTO.add(dto);
				dto.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException ex) {
						if (ex == null) {
							finish();
						} else {
							Constants.getInstance().listDoctorDTO
									.remove(Constants.getInstance().listDoctorDTO
											.size() - 1);
							Dialog dialog = new Dialog(getApplicationContext(),
									"Lưu danh sách bác sĩ", "Có lỗi xảy ra!",
									app.healthcare.R.drawable.doctor);
							dialog.show();
						}
					}
				});
			}
		});

	}
}
