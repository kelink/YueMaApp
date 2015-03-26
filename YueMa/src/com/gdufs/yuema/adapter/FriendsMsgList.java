package com.gdufs.yuema.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdufs.gd.yuema.base.BaseList;
import com.gdufs.yuema.R;

/**
 * 圈子动态的列表List适配器(待完善)
 * 
 * @author Administrator
 * 
 */
public class FriendsMsgList extends BaseList {
	private ArrayList<Object> friendsMsgList;
	private LayoutInflater inflater;

	@Override
	public int getCount() {
		return friendsMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.tpl_list_item1, null);
		ImageView face = (ImageView) convertView.findViewById(R.id.friendFace);
		TextView userName = (TextView) convertView
				.findViewById(R.id.friendName);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		ImageView activity_picture = (ImageView) convertView
				.findViewById(R.id.activity_picture);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView content = (TextView) convertView.findViewById(R.id.content);
		TextView joinInfo = (TextView) convertView
				.findViewById(R.id.joinerInfo);
		return convertView;
	}

	public void setData(ArrayList<Object> friendsMsgList) {
		this.friendsMsgList = friendsMsgList;

	}

	public void addData(Object friendsMsg) {
		this.friendsMsgList.add(friendsMsg);
	}
}
