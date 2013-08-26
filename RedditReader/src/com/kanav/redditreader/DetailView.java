package com.kanav.redditreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DetailView extends Activity{
	
	private String url;
	private WebView webView;
	private Activity mContext;
	private ProgressDialog mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		Bundle data = getIntent().getExtras();
		setContentView(R.layout.webview);
		if(data != null) {
			url = data.getString(MainActivity.URL);
			webView = (WebView) findViewById(R.id.webview);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setBuiltInZoomControls(true);
			mProgress = new ProgressDialog(mContext);
			mProgress.setMessage("Patience is bitter, but its fruit is sweet.");
			mProgress.show();
			
			webView.setWebChromeClient(new WebChromeClient() {
	            public void onProgressChanged(WebView view, int progress)
	            {
	                if(progress >= 90) {
	                	if(mProgress.isShowing())
							mProgress.dismiss();
	                }
	            }
	        });

			webView.setWebViewClient(new WebViewClient(){
				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					Toast.makeText(mContext, "Oops! " + description, Toast.LENGTH_SHORT).show();
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}
				
			});
			webView.loadUrl(url);
		}
		else
			Toast.makeText(mContext, "Oops! This page doesn't exist", Toast.LENGTH_SHORT).show();
	}

}
