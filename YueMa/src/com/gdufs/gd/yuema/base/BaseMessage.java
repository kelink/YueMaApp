//package com.gdufs.gd.yuema.base;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class BaseMessage {
//
//	private String data;
//	private String info;
//	private String status;
//	private Map<String, BaseModel> resultMap;
//	private Map<String, ArrayList<? extends BaseModel>> resultList;
//
//	public BaseMessage() {
//		this.resultMap = new HashMap<String, BaseModel>();
//		this.resultList = new HashMap<String, ArrayList<? extends BaseModel>>();
//	}
//
//	@Override
//	public String toString() {
//		return data + " | " + status + " | " + info;
//	}
//
//	public String getStatus() {
//		return this.status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getInfo() {
//		return this.info;
//	}
//
//	public void setInfo(String info) {
//		this.info = info;
//	}
//
//	public String getData() {
//		return this.data;
//	}
//
//	/**
//	 * 当服务器返回的data属性是一个json对象时，调用此方法
//	 * 
//	 * @param modelName
//	 * @return
//	 * @throws Exception
//	 */
//	public Object getData(String modelName) throws Exception {
//		Object model = this.resultMap.get(modelName);
//		// catch null exception
//		if (model == null) {
//			throw new Exception("Message data is empty");
//		}
//		return model;
//	}
//
//	/**
//	 * 当服务器返回的data属性是一个json数组时，调用此方法
//	 * 
//	 * @param modelName
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList<? extends BaseModel> getDataList(String modelName)
//			throws Exception {
//		ArrayList<? extends BaseModel> modelList = this.resultList
//				.get(modelName);
//		// catch null exception
//		if (modelList == null || modelList.size() == 0) {
//			throw new Exception("Message data list is empty");
//		}
//		return modelList;
//	}
//
//	// 解析json数据格式，返回结果
//	@SuppressWarnings("unchecked")
//	public void setData(String result) throws Exception {
//		this.data = result;
//		if (result.length() > 0) {
//			JSONObject jsonObject = null;
//			jsonObject = new JSONObject(result);
//			Iterator<String> it = jsonObject.keys();
//			while (it.hasNext()) {
//				// initialize
//				String jsonKey = it.next();
//				String modelName = getModelName(jsonKey);
//				String modelClassName = "com.gw.library.model." + modelName;
//				JSONArray modelJsonArray = jsonObject.optJSONArray(jsonKey);
//				// JSONObject
//				if (modelJsonArray == null) {
//					JSONObject modelJsonObject = jsonObject
//							.optJSONObject(jsonKey);
//					if (modelJsonObject == null) {
//						throw new Exception("Message result is invalid");
//					}
//					this.resultMap.put(modelName,
//							json2model(modelClassName, modelJsonObject));
//					// JSONArray
//				} else {
//					ArrayList<BaseModel> modelList = new ArrayList<BaseModel>();
//					for (int i = 0; i < modelJsonArray.length(); i++) {
//						JSONObject modelJsonObject = modelJsonArray
//								.optJSONObject(i);
//						modelList.add(json2model(modelClassName,
//								modelJsonObject));
//					}
//					this.resultList.put(modelName, modelList);
//				}
//			}
//		}
//	}
//
//	// json数据转化为对应定义的BaseModel对象
//	@SuppressWarnings("unchecked")
//	private BaseModel json2model(String modelClassName,
//			JSONObject modelJsonObject) throws Exception {
//		// auto-load model class
//		BaseModel modelObj = (BaseModel) Class.forName(modelClassName)
//				.newInstance();
//		Class<? extends BaseModel> modelClass = modelObj.getClass();
//		// auto-setting model fields
//		Iterator<String> it = modelJsonObject.keys();
//		while (it.hasNext()) {
//			String varField = it.next();
//			String varValue = modelJsonObject.getString(varField);
//			Field field = modelClass.getDeclaredField(varField);
//			field.setAccessible(true); // have private to be accessable
//			field.set(modelObj, varValue);
//		}
//		return modelObj;
//	}
//
//	private String getModelName(String str) {
//		String[] strArr = str.split("\\W");
//		if (strArr.length > 0) {
//			str = strArr[0];
//		}
//		return AppUtil.ucfirst(str);
//	}
//
// }