package com.gdufs.yuema;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.baseview.DateTimePickDialog;
import com.gdufs.gd.yuema.util.BitMapUtil;

/**
 * 活动发布界面
 * 
 * @author Administrator
 * 
 */
public class NewActivityActivity extends BaseUi {
	private Spinner labels;
	private ArrayAdapter<CharSequence> labelsAdapter = null;
	private static String[] labelInfo = { "好玩", "认识朋友", "新奇", "免费" };

	private EditText startDateTime;
	private EditText endDateTime;
	private ImageButton imageButton;
	private ImageView imageView;
	private View parentView;

	// 弹出框
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	// 拍照
	private Bitmap photo;
	private File file;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_activity,
				null);
		setContentView(parentView);

		initView();
		imageButton = (ImageButton) this
				.findViewById(R.id.imageButton_newActivity);
		imageView = (ImageView) this.findViewById(R.id.imageview_newactivity);

		labels = (Spinner) this.findViewById(R.id.spinnerLabel);
		// 动态实现的下拉框，数据在程序中获得，实际项目可能来自数据库等
		labelsAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_dropdown_item, labelInfo);
		// 设置下拉列表的风格
		labelsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		labels.setAdapter(labelsAdapter);
		labels.setOnItemSelectedListener(new SpinnerSelectedListener());
		// 设置默认值
		labels.setVisibility(View.VISIBLE);
		// 时间输入框
		startDateTime = (EditText) findViewById(R.id.editText_beginTime);

		endDateTime = (EditText) findViewById(R.id.editText_endTime);
		startDateTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DateTimePickDialog dateTimePicKDialog = new DateTimePickDialog(
						NewActivityActivity.this, null);
				dateTimePicKDialog.showDateTimePicKDialog(startDateTime);

			}
		});

		endDateTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DateTimePickDialog dateTimePicKDialog = new DateTimePickDialog(
						NewActivityActivity.this, null);
				dateTimePicKDialog.showDateTimePicKDialog(endDateTime);
			}
		});
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});
	}

	private void initView() {
		pop = new PopupWindow(NewActivityActivity.this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
				/***
				 * 这个是调用android内置的intent，来过滤图片文件 ，同时也可以过滤其他的
				 */
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 2);
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
	}

	// 下拉框选择事件
	private class SpinnerSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String city = parent.getItemAtPosition(position).toString();
			Toast.makeText(NewActivityActivity.this, "选择的城市是：" + city,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}

	}

	private static final int TAKE_PICTURE = 0x000001;
	private static final int PICK_PICTURE = 0x000002;

	public void photo() {
		destoryImage();
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			file = new File(saveDir, "temp.jpg");
			file.delete();
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					toast("照片创建失败!");
					return;
				}
			}
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, 1);
		} else {
			toast("sdcard无效或没有插入!");
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (requestCode == 1 && resultCode == RESULT_OK) {
				if (file != null && file.exists()) {
					int screenWidth = getWindowManager().getDefaultDisplay()
							.getWidth() - 20; // 屏幕宽（像素，如：480px）
					int screenHeight = getWindowManager().getDefaultDisplay()
							.getHeight() - 20; // 屏幕高（像素，如：800p
					imageView.setImageBitmap(BitMapUtil.createImageThumbnail(
							file.getPath(), screenWidth, 256));
				}
			}
			break;
		case PICK_PICTURE:
			if (resultCode == Activity.RESULT_OK) {
				/**
				 * 当选择的图片不为空的话，在获取到图片的途径
				 */
				Uri uri = data.getData();
				Log.e("photo uri--》》", "uri = " + uri);
				try {
					String[] pojo = { MediaStore.Images.Media.DATA };
					Cursor cursor = managedQuery(uri, pojo, null, null, null);
					if (cursor != null) {
						ContentResolver cr = this.getContentResolver();
						int colunm_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String path = cursor.getString(colunm_index);
						/***
						 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，
						 * 你选择的文件就不一定是图片了， 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
						 */
						if (path.endsWith("jpg") || path.endsWith("png")) {
							String picPath = path;
							toast(picPath);
							Bitmap bitmap = BitmapFactory.decodeStream(cr
									.openInputStream(uri));
							imageView.setImageBitmap(bitmap);
						} else {
							toast("木有jpg或者png照片");
						}
					} else {
						toast("没有照片");
					}

				} catch (Exception e) {
				}
			}
			break;
		default:
			break;
		}
	}

	// GridView 显示

	@Override
	protected void onDestroy() {
		destoryImage();
		super.onDestroy();
	}

	private void destoryImage() {
		if (photo != null) {
			photo.recycle();
			photo = null;
		}
	}
}
