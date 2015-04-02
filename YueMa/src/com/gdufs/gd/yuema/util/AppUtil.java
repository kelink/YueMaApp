package com.gdufs.gd.yuema.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.util.Log;

import com.gdufs.gd.yuema.base.BaseModel;

public class AppUtil {

	/* md5 加密 */
	static public String md5(String str) {
		MessageDigest algorithm = null;
		try {
			algorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (algorithm != null) {
			algorithm.reset();
			algorithm.update(str.getBytes());
			byte[] bytes = algorithm.digest();
			StringBuilder hexString = new StringBuilder();
			for (byte b : bytes) {
				hexString.append(Integer.toHexString(0xFF & b));
			}
			return hexString.toString();
		}
		return "";

	}

	/* 首字母大写 */
	static public String ucfirst(String str) {
		if (str != null && str != "") {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity,
			final String defaultCharset) throws IOException, ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return "";
		}
		// gzip logic start
		if (entity.getContentEncoding().getValue().contains("gzip")) {
			instream = new GZIPInputStream(instream);
		}
		// gzip logic end
		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					"HTTP entity too large to be buffered in memory");
		}
		int i = (int) entity.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		String charset = EntityUtils.getContentCharSet(entity);
		if (charset == null) {
			charset = defaultCharset;
		}
		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(instream, charset);
		CharArrayBuffer buffer = new CharArrayBuffer(i);
		try {
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}

	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity)
			throws IOException, ParseException {
		return gzipToString(entity, null);
	}

	public static SharedPreferences getSharedPreferences(Context ctx) {
		return ctx.getSharedPreferences("com.gw.library.sp.global",
				Context.MODE_PRIVATE);
	}

	public static SharedPreferences getSharedPreferences(Service service) {
		return service.getSharedPreferences("com.gw.library.sp.global",
				Context.MODE_PRIVATE);
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// 业务逻辑

	/**
	 * 获取 Session Id
	 * 
	 * @return
	 */
	static public String getSessionId() {
		return null;
	}

	/* Model 数组转化成 Map 列表 */
	static public List<? extends Map<String, ?>> dataToList(
			List<? extends BaseModel> data, String[] fields) {
		ArrayList<HashMap<String, ?>> list = new ArrayList<HashMap<String, ?>>();
		for (BaseModel item : data) {
			list.add((HashMap<String, ?>) dataToMap(item, fields));
		}
		return list;
	}

	/* Model 转化成 Map */
	static public Map<String, ?> dataToMap(BaseModel data, String[] fields) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			for (String fieldName : fields) {
				Field field = data.getClass().getDeclaredField(fieldName);
				field.setAccessible(true); // have private to be accessable
				map.put(fieldName, field.get(data));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 把从数据库查询的数据转为model的形式
	 * 
	 * @param className
	 * @param mapList
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<? extends BaseModel> hashMapToModel(
			String className, ArrayList<HashMap<String, String>> mapList)
			throws Exception {

		ArrayList<BaseModel> modelList = new ArrayList<BaseModel>();

		for (HashMap<String, String> temp : mapList) {
			BaseModel modelObj = (BaseModel) Class.forName(className)
					.newInstance();
			Class<? extends BaseModel> modelClass = modelObj.getClass();
			Iterator<String> it = temp.keySet().iterator();
			while (it.hasNext()) {
				String varField = it.next();
				String varValue = temp.get(varField);
				// Log.i("appUtil--hashmaptomodel--", varField+"==>"+varValue);
				Field field = modelClass.getDeclaredField(varField);
				field.setAccessible(true); // have private to be accessable
				field.set(modelObj, varValue);
			}
			modelList.add(modelObj);
		}
		return modelList;
	}

	/**
	 * 把jsonObject转为hashmap的格式
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static HashMap<String, String> jsonObject2HashMap(
			JSONObject jsonObject) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Iterator<String> iterator = jsonObject.keys();

		try {
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = jsonObject.getString(key);
				hashMap.put(key, value);

			}
		} catch (Exception e) {
			Log.w("apputil", "jsonobject2hasmap");
		}
		return hashMap;
	}

	/**
	 * 把url返回的json格式转换为arrayList<Hash<key,map>>形式
	 * 
	 * @param jsonArray
	 * @return arrayList
	 */
	@SuppressWarnings("null")
	public static ArrayList<HashMap<String, String>> jsonArray2ArrayList(
			JSONArray jsonArray) {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		JSONObject jsonObject = null;
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				jsonObject = jsonArray.getJSONObject(i);
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = jsonObject.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = jsonObject.getString(key);
					hashMap.put(key, value);
				}
				arrayList.add(hashMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("json", "json转换为arrayList时候错误");
		} catch (Exception e) {
			Log.e("json", "错误");
		}
		return arrayList;
	}

	/* 判断int是否为空 */
	static public boolean isEmptyInt(int v) {
		Integer t = new Integer(v);
		return t == null ? true : false;
	}

	/* 获取毫秒数 */
	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	/* 获取耗费内存 */
	public static long getUsedMemory() {
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return total - free;
	}
}