package com.gdufs.gd.yuema.myvolley;

import java.io.File;
import java.util.Map;

/**
 * 适用于上传类的request 接口
 * 
 * @author Administrator
 * 
 */
public interface MultiPartRequest {

	public void addFileUpload(String param, File file);

	public void addStringUpload(String param, String content);

	public Map<String, File> getFileUploads();

	public Map<String, String> getStringUploads();
}