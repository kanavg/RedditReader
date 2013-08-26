package com.kanav.redditreader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class RedditAdapter extends BaseAdapter{
	
	private ArrayList<RedditItem> list;
	private Context mContext;
	
	public static class ViewHolder {
		TextView title;
		TextView details;
		TextView points;
		ImageView thumb;
	}

	public RedditAdapter(ArrayList<RedditItem> itemArray, Context context) {
		list = itemArray;
		mContext = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
        	convertView = LayoutInflater.from(mContext).inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.textView1);
            holder.details = (TextView)convertView.findViewById(R.id.textView2);
            holder.points = (TextView)convertView.findViewById(R.id.textView3);
            holder.thumb = (ImageView)convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(list.get(index).title);
        holder.details.setText(list.get(index).subreddit);
        int points = list.get(index).points;
        String text = Integer.toString(points) + " pts.";
        holder.points.setText(text);
        if(!list.get(index).thumbNail.isEmpty())
        	UrlImageViewHelper.setUrlDrawable(holder.thumb, list.get(index).thumbNail);
        else
        	holder.thumb.setImageResource(R.drawable.emo_im_cool);
        
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.move);
        convertView.startAnimation(animation);
        animation = null;
        return convertView;
	}

}
