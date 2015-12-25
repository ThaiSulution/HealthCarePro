package app.healthcare.call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import app.healthcare.Constants;
import app.healthcare.R;
import app.healthcare.dataobject.DoctorDTO;

import com.gc.materialdesign.widgets.Dialog;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class ListDoctor extends Activity {

	static final String KEY_ID = "ID";
	static final String KEY_NAME = "NAME";
	static final String KEY_NUMBER_CALL = "NUMBER_CALL";
	static final String KEY_NUMBER_SKYPE = "NUMBER_SKYPE";
	static final String KEY_NUMBER_VIBER = "NUMBER_VIBER";
	final Handler mHandler = new Handler();
	ListView list;
	ListDoctorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_doctor);
		initList();
	}

	private void initList() {
		ArrayList<HashMap<String, String>> arrayDoctor = new ArrayList<HashMap<String, String>>();
		// looping through all song nodes &lt;song&gt;
		for (int i = 0; i < Constants.getInstance().listDoctorDTO.size(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key =&gt; value
			map.put(KEY_ID,
					String.valueOf(Constants.getInstance().listDoctorDTO.get(i)
							.getDoctorId()));
			map.put(KEY_NUMBER_CALL,
					Constants.getInstance().listDoctorDTO.get(i)
							.getNumberCall());
			map.put(KEY_NUMBER_SKYPE, Constants.getInstance().listDoctorDTO
					.get(i).getSkypeNumber());
			map.put(KEY_NUMBER_VIBER, Constants.getInstance().listDoctorDTO
					.get(i).getViberNumber());
			map.put(KEY_NAME,
					String.valueOf(Constants.getInstance().listDoctorDTO.get(i)
							.getDoctorName()));
			// adding HashList to ArrayList
			arrayDoctor.add(map);
		}
		list = (ListView) findViewById(R.id.list_doctor);
		// Getting adapter by passing xml data ArrayList
		adapter = new ListDoctorAdapter(this, arrayDoctor);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int pos, long id) {
				if (isNetworkAvaiable()) {
					final Dialog dialog = new Dialog(ListDoctor.this,
							"Chọn thao tác", "Bạn có muốn gọi bằng Viber", 123);
					dialog.addCancelButton("No");
					dialog.show();
					dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							initiateViberUri(Constants.getInstance().listDoctorDTO
									.get(pos).getViberNumber());
							dialog.dismiss();
						}
					});
					dialog.setOnCancelButtonClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							initiateSkypeUri(Constants.getInstance().listDoctorDTO
									.get(pos).getSkypeNumber());
							dialog.dismiss();
						}
					});

				} else {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"
							+ Constants.getInstance().listDoctorDTO.get(pos)
									.getNumberCall()));
					startActivity(callIntent);
				}
			}
		});
		final Dialog dialog = new Dialog(this, "Xóa bác sĩ",
				"Bạn có muốn xóa thông tin bác sĩ này?", R.drawable.doctor14);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				final int index = pos;
				dialog.show();
				dialog.getButtonAccept().setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								ParseQuery<DoctorDTO> queryDoctor = ParseQuery
										.getQuery("DoctorDTO");
								queryDoctor.whereEqualTo("doctorId", Constants
										.getInstance().listDoctorDTO.get(index)
										.getDoctorId());
								queryDoctor
										.findInBackground((new FindCallback<DoctorDTO>() {
											@Override
											public void done(
													List<DoctorDTO> datas,
													ParseException e) {
												if (e == null) {
													for (int i = 0; i < datas
															.size(); i++) {
														for (int j = 0; j < Constants
																.getInstance().listDoctorDTO
																.size(); j++) {
															if (Constants
																	.getInstance().listDoctorDTO
																	.get(j)
																	.getDoctorId() == datas
																	.get(i)
																	.getDoctorId()) {
																Constants
																		.getInstance().listDoctorDTO
																		.remove(j);
															}
														}
														datas.get(i)
																.deleteInBackground(
																		new DeleteCallback() {

																			@Override
																			public void done(
																					ParseException arg0) {
																				initList();
																			}
																		});

													}
												}
											}

										}));

								dialog.dismiss();
							}
						});
				return false;
			}
		});
	}

	private boolean isNetworkAvaiable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		Log.d("Network 3", (info != null) + "  " + info);
		return (info != null);
	}

	public void initiateSkypeUri(String mySkypeUri) {
		if (!isSkypeClientInstalled(this.getApplicationContext())) {
			goToMarket(this.getApplicationContext(), "com.skype.raider");
			return;
		}
		Uri skypeUri = Uri.parse("skype:" + mySkypeUri + "?call");
		Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
		myIntent.setData(skypeUri);
		myIntent.setComponent(new ComponentName("com.skype.raider",
				"com.skype.raider.Main"));
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(myIntent);
		return;
	}

	public void initiateViberUri(String myViberUri) {
		if (!isSkypeClientInstalled(this.getApplicationContext())) {
			goToMarket(this.getApplicationContext(), "com.viber.voip");
			return;
		}
		Uri uri = Uri.parse("tel:" + Uri.encode(myViberUri));
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
		intent.setData(uri);
		startActivity(intent);
		return;
	}

	public boolean isSkypeClientInstalled(Context myContext) {
		PackageManager myPackageMgr = myContext.getPackageManager();
		try {
			myPackageMgr.getPackageInfo("com.skype.raider",
					PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			return (false);
		}
		return (true);
	}

	public void goToMarket(Context myContext, String id) {
		Uri marketUri = Uri.parse("market://details?id=" + id);
		Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myContext.startActivity(myIntent);

		return;
	}

	public boolean isViberClientInstalled(Context myContext) {
		PackageManager myPackageMgr = myContext.getPackageManager();
		try {
			myPackageMgr.getPackageInfo("com.viber.voip",
					PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			return (false);
		}
		return (true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_list_doctor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.addNew) {
			Intent addNewIntent = new Intent(this, ActivityAddDoctor.class);
			startActivity(addNewIntent);
			return true;
		} else if (id == R.id.refresh) {
			initList();
		}
		return super.onOptionsItemSelected(item);
	}
}
