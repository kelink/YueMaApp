package com.gdufs.gd.yuema.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.gdufs.gd.yuema.model.FileTraversal;
import com.gdufs.gd.yuema.model.ImgCallBack;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

public class ImageUtil {

	Context context;

	public ImageUtil(Context context) {
		this.context = context;
	}

	/**
	 * 获取全部图片的地址
	 * 
	 * @return
	 */
	public ArrayList<String> listAlldir() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		Uri uri = intent.getData();
		ArrayList<String> list = new ArrayList<String>();
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, proj, null,
				null, null);// managedQuery(uri, proj, null, null, null);
		while (cursor.moveToNext()) {
			String path = cursor.getString(0);
			list.add(new File(path).getAbsolutePath());
		}
		return list;
	}

	/**
	 * 序列化所有的图片对象
	 * 
	 * @return
	 */
	public List<FileTraversal> LocalImgFileList() {
		List<FileTraversal> data = new ArrayList<FileTraversal>();
		String filename = "";
		List<String> allimglist = listAlldir();
		List<String> retulist = new ArrayList<String>();
		if (allimglist != null) {
			Set set = new TreeSet();
			String[] str;
			for (int i = 0; i < allimglist.size(); i++) {
				retulist.add(getfileinfo(allimglist.get(i)));
			}
			for (int i = 0; i < retulist.size(); i++) {
				set.add(retulist.get(i));
			}
			str = (String[]) set.toArray(new String[0]);
			for (int i = 0; i < str.length; i++) {
				filename = str[i];
				FileTraversal ftl = new FileTraversal();
				ftl.filename = filename;
				data.add(ftl);
			}

			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < allimglist.size(); j++) {
					if (data.get(i).filename.equals(getfileinfo(allimglist
							.get(j)))) {
						data.get(i).filecontent.add(allimglist.get(j));
					}
				}
			}
		}
		return data;
	}

	/**
	 * 显示原生图片尺寸大小
	 * 
	 * @param imageFilePath
	 * @param dw
	 * @param dh
	 * @return
	 * @throws FileNotFoundException
	 */
	public Bitmap getPathBitmap(Uri imageFilePath, int dw, int dh)
			throws FileNotFoundException {
		// 获取屏幕的宽和高
		/**
		 * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
		 * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
		 * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
		 * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
		 */
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		// 由于使用了MediaStore存储，这里根据URI获取输入流的形式
		Bitmap pic = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(imageFilePath), null, op);

		int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // �����ȱ���
		int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // ����߶ȱ���

		/**
		 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。 如果高和宽不是全都超出了屏幕，那么无需缩放。
		 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》 这需要判断wRatio和hRatio的大小
		 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。 缩放使用的还是inSampleSize变量
		 */
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		pic = BitmapFactory.decodeStream(context.getContentResolver()
				.openInputStream(imageFilePath), null, op);

		return pic;
	}

	public String getfileinfo(String data) {
		String filename[] = data.split("/");
		if (filename != null) {
			return filename[filename.length - 2];
		}
		return null;
	}

	public void imgExcute(ImageView imageView, ImgCallBack icb,
			String... params) {
		LoadBitAsynk loadBitAsynk = new LoadBitAsynk(imageView, icb);
		loadBitAsynk.execute(params);
	}

	/**
	 * 回调加载图片
	 * 
	 * @author Administrator
	 * 
	 */
	public class LoadBitAsynk extends AsyncTask<String, Integer, Bitmap> {

		ImageView imageView;
		ImgCallBack icb;

		LoadBitAsynk(ImageView imageView, ImgCallBack icb) {
			this.imageView = imageView;
			this.icb = icb;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						bitmap = getPathBitmap(
								Uri.fromFile(new File(params[i])), 200, 200);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				// imageView.setImageBitmap(result);
				icb.resultImgCall(imageView, result);
			}
		}

	}

}
