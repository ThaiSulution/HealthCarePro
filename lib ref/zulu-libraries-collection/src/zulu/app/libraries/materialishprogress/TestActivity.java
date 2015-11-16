package zulu.app.libraries.materialishprogress;

import zulu.app.libraries.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wheel);

        final ProgressWheel progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);

        Spinner spinnerOptions = (Spinner) findViewById(R.id.spinner_options);
        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        progressWheel.spin();
                        break;
                    case 1:
                        progressWheel.setProgress(0.1f);
                        break;
                    case 2:
                        progressWheel.setProgress(0.25f);
                        break;
                    case 3:
                        progressWheel.setProgress(0.5f);
                        break;
                    case 4:
                        progressWheel.setProgress(0.75f);
                        break;
                    case 5:
                        progressWheel.setProgress(1.0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final int defaultBarColor = progressWheel.getBarColor();
        final int defaultWheelColor = progressWheel.getRimColor();

        Spinner colorOptions = (Spinner) findViewById(R.id.spinner_options_color);
        colorOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        progressWheel.setBarColor(defaultBarColor);
                        break;
                    case 1:
                        progressWheel.setBarColor(Color.RED);
                        break;
                    case 2:
                        progressWheel.setBarColor(Color.MAGENTA);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner wheelColorOptions = (Spinner) findViewById(R.id.spinner_options_rim_color);
        wheelColorOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        progressWheel.setRimColor(defaultWheelColor);
                        break;
                    case 1:
                        progressWheel.setRimColor(Color.LTGRAY);
                        break;
                    case 2:
                        progressWheel.setRimColor(Color.GRAY);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
