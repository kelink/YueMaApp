package com.gdufs.gd.yuema.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 在获取图片后回调接口传回结果
 * 
 * @author Administrator
 * 
 */
public interface ImgCallBack {
	public void resultImgCall(ImageView imageView, Bitmap bitmap);
}
