package com.gdufs.yuema;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.toolbox.NetworkImageView;
import com.gdufs.gd.yuema.util.volley.ImageCacheManager;

public class VolleyActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		Button btn = (Button) this.findViewById(R.id.sendbutton);
		final NetworkImageView imageView = (NetworkImageView) this
				.findViewById(R.id.network_image_view);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// showImage(imageView);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showImage(NetworkImageView imageView) {
		new ImageCacheManager();
		imageView.setDefaultImageResId(R.drawable.applogo);
		imageView.setErrorImageResId(R.drawable.applogo);
		imageView
				.setImageUrl(
						"http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
						ImageCacheManager.getInstance().getImageLoader());
	}
	// private static final String URL = "http://www.baidu.com/";
	// private RequestQueue mQueue; //
	//
	// /**
	// * 使用volley请求数
	// */
	// // private void volleyRequest() {
	// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
	// R.drawable.peopleface);
	// StringRequest request = new StringRequest(Method.GET, URL,
	// new Listener<String>() {
	//
	// @Override
	// public void onResponse(String response) {
	// Log.i("response---->", response.toString());
	// // response = response.substring(response.indexOf('img
	// // src='));
	// // response =
	// // response.substring(8,response.indexOf('/>')) ;
	// // Log.v('zgy','===========onResponse========='+response)
	// // ;
	// // mShowResponse.setText('图片地址:'+response);
	// mDialog.dismiss();
	// Toast.makeText(VolleyActivity.this, "上传成功",
	// Toast.LENGTH_SHORT).show();
	//
	// }
	//
	// }, new ErrorListener() {
	//
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// Log.i("response---->", error.toString());
	// // mShowResponse.setText('ErrorResponse'+error.getMessage());
	// Toast.makeText(VolleyActivity.this, "上传失败",
	// Toast.LENGTH_SHORT).show();
	// mDialog.dismiss();
	//
	// }
	// });
	// mQueue.add(request);
	// }
}
