package com.kanav.redditreader;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends ListActivity {

	private RequestQueue reqQueue;
	private String url = "http://www.reddit.com/r/all/.json";
	private ArrayList<RedditItem> itemArray;
	private RedditAdapter adapter;
	private ListView listView;
	private Context mContext;
	
	public static final String TAG = "RedditReader";
	public static final String URL = "URL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		listView = getListView();
		reqQueue = Volley.newRequestQueue(this);
		itemArray = new ArrayList<RedditItem>();
		adapter = new RedditAdapter(itemArray, this);
		listView.setAdapter(adapter);
		listView.setFadingEdgeLength(0);

		JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, url, null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {
						parseJson(arg0);
						adapter.notifyDataSetChanged();
					}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		reqQueue.add(jsonReq);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, DetailView.class);
				intent.putExtra(URL, itemArray.get(arg2).url);
				startActivity(intent);				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void parseJson(JSONObject jsonObject) {
		try {
			JSONObject value = jsonObject.getJSONObject("data");
			JSONArray children = value.getJSONArray("children");
			for(int i = 0; i< children.length(); i++) {
				JSONObject child = children.getJSONObject(i).getJSONObject("data");
				RedditItem item = new RedditItem();
				item.title = (String) child.opt("title");

				if(item.title != null) {
					item.url = child.optString("url");
					item.points = child.optInt("score");
					item.subreddit = child.optString("subreddit");
					item.thumbNail = child.optString("thumbnail");
	                itemArray.add(item);
				}
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
