/* Copyright [2015] [Nguyễn Hoàng Vũ , Nguyễn Phi Viễn]

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package app.healthcare.symptom.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import app.healthcare.R;
import app.healthcare.symptom.list.ListSymptom;

public class ListMenu extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listfood);
		TabHost tabHost = getTabHost();
		TabSpec dsmonan = tabHost.newTabSpec("Món ăn");
		View indicator11 = getLayoutInflater().inflate(R.layout.tab_bg_food,
				null);
		dsmonan.setIndicator(indicator11);
		Intent lmonanIntent = new Intent(this, ListSymptom.class);
		dsmonan.setContent(lmonanIntent);
		tabHost.addTab(dsmonan);

	}

}
