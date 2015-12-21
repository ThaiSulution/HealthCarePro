package app.healthcare.docbaoonline.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import app.healthcare.R;
import app.healthcare.docbaoonline.models.Variables;

public class NoRssActivity extends Activity {

	private WebView webView;
	private ProgressDialog dialog;
	private String link;

	@SuppressWarnings({ "static-access" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);

		// int key = getIntent().getExtras().getInt(Variables.key);
		int paper = getIntent().getExtras().getInt(Variables.paper);
		int position = getIntent().getExtras().getInt(Variables.position);
		Log.d("Position", position + "");
		// RssItem item = Variables.MAP.get(key).get(position);
		// setTitle(item.getTitle());
		setTitle(Variables.PAPERS[paper]);
		link = Variables.LINKS[paper][position];
		Log.d("LINK", link);

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setScrollbarFadingEnabled(false);
		webView.setScrollBarStyle(webView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setInitialScale(1);
		webView.getSettings().setLightTouchEnabled(true);
		webView.setWebViewClient(new MyWebViewClient());

		dialog = ProgressDialog.show(this, "", "Loading...");
		new MyTask().execute();
		// webView.loadUrl(link);
	}

	class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			webView.loadUrl(link);
			return null;
		}

	}

	class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			if (dialog != null) {
				dialog.dismiss();
			}
			Log.d("URL finish", url);
			super.onPageFinished(view, url);
		}

	}
}
