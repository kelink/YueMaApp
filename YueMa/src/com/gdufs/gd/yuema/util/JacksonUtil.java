package com.gdufs.gd.yuema.util;

import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
 * 使用JACKSON 进行Bean 转换
 * 
 * @author Administrator
 * 
 */
public class JacksonUtil {

	public JacksonUtil() {
		super();
	}

	private JsonGenerator jsonGenerator = null;
	private ObjectMapper objectMapper = null;

	/**
	 * 創建objectMapper，實例化objectMapper
	 */
	public void init() {
		objectMapper = new ObjectMapper();
		try {
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					System.out, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 銷毀jsonGenerator
	 */
	public void destory() {
		try {
			if (jsonGenerator != null) {
				jsonGenerator.flush();
			}
			if (!jsonGenerator.isClosed()) {
				jsonGenerator.close();
			}
			jsonGenerator = null;
			objectMapper = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将java对象转换成json字符串
	 */
	public static String writeEntity2JSON(Object obj) {
		String jsonStr = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonStr = objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 将json字符串转换成JavaBean对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readJson2Entity(String jsonStr, Class<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		Object object = null;
		try {
			object = objectMapper.readValue(jsonStr, clazz);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) object;

	}

	/**
	 * json 字符串轉化為對應的類型 获取泛型的Collection Type
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类型
	 */
	public static <T> T readJson(String jsonStr, Class<?> collectionClass,
			Class<?>... elementClasses) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(
				collectionClass, elementClasses);
		return mapper.readValue(jsonStr, javaType);

	}
}