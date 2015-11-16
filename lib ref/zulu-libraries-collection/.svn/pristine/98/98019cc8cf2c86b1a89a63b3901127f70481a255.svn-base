package zulu.app.libraries.ripple;

import java.util.ArrayList;

import zulu.app.libraries.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    private ArrayList<String> sourcesArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test_ripple);

        final RippleView rippleView = (RippleView) findViewById(R.id.rect);
        final TextView textView = (TextView) findViewById(R.id.rect_child);

        rippleView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("Sample", "Click Rect !");
            }
        });
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("Sample", "Click rect child !");
            }
        });

        sourcesArrayList.add("Samsung");
        sourcesArrayList.add("Android");
        sourcesArrayList.add("Google");
        sourcesArrayList.add("Asus");
        sourcesArrayList.add("Apple");
        sourcesArrayList.add("Samsung");
        sourcesArrayList.add("Android");
        sourcesArrayList.add("Google");
        sourcesArrayList.add("Asus");
        sourcesArrayList.add("Apple");
        sourcesArrayList.add("Samsung");
        sourcesArrayList.add("Android");
        sourcesArrayList.add("Google");
        sourcesArrayList.add("Asus");
        sourcesArrayList.add("Apple");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CustomAdapter customAdapter = new CustomAdapter();
        customAdapter.updateList(sourcesArrayList);

        customAdapter.setOnTapListener(new OnTapListener()
        {
            @Override
            public void onTapView(int position)
            {
                Log.e("MainActivity", "Tap item : " + position);
            }
        });
        recyclerView.setAdapter(customAdapter);

    }
}
