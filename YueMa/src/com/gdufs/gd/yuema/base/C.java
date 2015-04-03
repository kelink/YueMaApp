package com.gdufs.gd.yuema.base;

/**
 * 应用使用的常量
 * 
 * @author Administrator
 * 
 */
public class C {
	// 服务器端
	public class Server {
		public static final String HOST_STRING = "http://192.168.202.65:8080/gd";
	}

	// 接口定义
	public class API {
		// 登陆
		public static final String LOGON = Server.HOST_STRING + "/user/login";
		// 获取验证码
		public static final String GET_REGIST_CODE = Server.HOST_STRING
				+ "/user/getRegisterCode";
		// 注册
		public static final String REGIST = Server.HOST_STRING
				+ "/user/register";
		// 修改用户信息
		public static final String CHANGE_USER_PROFILE = "";
		// 通讯录上传接口
		public static final String UPLOAD_CONTACT = Server.HOST_STRING + "/";
	}

	// 请求参数的同一名称
	public class ParamsName {
		public static final String USERNAME = "userName";
		public static final String PASSWORD = "pwd";
		public static final String PHONE_NUM = "phoneNum";
		public static final String UID = "uID";
		public static final String REGIST_CODE = "registCode";
		public static final String CONTACT = "contact";
		public static final String HOST_NUM = "hostNum";
	}

	// 网络请求的类型
	public class RequestType {
		public static final int STRING = 0;
		public static final int JSONOBJ = 1;
		public static final int JSONARRAY = 2;
		public static final int IMAGE = 3;
		public static final int NORMAL = 4;// 自定义适应SpringMVC的请求

	}

	public class NetWorkMsg {
		public static final String NETWORK_EXCEPTION = "网络异常";
		public static final String NETWORK_DISCONNECTED = "无法连接网络";
		public static final String SERVER_EXCEPTION = "服务器内部错误";
		public static final String PARSER_EXCEPTION = "服务器解析错误";
		public static final String REQUEST_FAIL = "请求失败";
	}

	public class ActivityMsg {
		public static final String LOGGING = "登录中...";
		public static final String LOADING = "加载中...";
		public static final String LOGON_EXCEPTION = "登陆异常";
		public static final String WRONG_PROFILE = "账户或密码错误";
	}

	public class JPushConstant {
		public static final String TAG = "JPush";
		public static final int MSG_SET_ALIAS = 1001;
		public static final int MSG_SET_TAGS = 1002;
		public static final String MESSAGE_RECEIVED_ACTION = "com.gdufs.yuema.MESSAGE_RECEIVED_ACTION";
		public static final String KEY_TITLE = "title";
		public static final String KEY_MESSAGE = "message";
		public static final String KEY_EXTRAS = "extras";
	}

	public class Task {
		public static final int TASK_REGIST = 3000;// 注册
		public static final int TASK_GET_REGIST_CODE = 3001;// 获取验证码
		public static final int TASK_LOGON = 3002;// 登录
		public static final int TASK_SET_PWD = 3003;// 设置密码
		public static final int TASK_UPLOAD_CONTACT = 3004;// 上传通讯录
	}

	public class ServiceAction {

	}

	// 在activity中操作时候的消息
	public class ActivityMessage {
		public static final String invalidPhone = "无效手机号码，请检查";
		public static final String noAggrement = "请先同意服务协议";
		public static final String blankField = "有空项，请完善";
	}

	// 用于返回的参数
	public class ResponseCode {
		public final static String NOEXCUTE = "-1";
		public final static String SUCCESS = "1";
		public final static String ERROR = "2";

	}

	public class ResponseMessage {
		public final static String REGIST_ERROR_PARMANTS = "error parmant";
		public final static String REGIST_CODE_TIME_OUT = "code over time, please get regist code again";
		public final static String REGIST_DB_ERROR = "regist in database error";
		public final static String NOEXCUTE = "no process current request";
		public final static String SUCCESS = "Execute successfully";
		public final static String REQUEST_ERROR = "Request occured error";
		public final static String NETWORK_ERROR = "NetWork occured error";
		public final static String UNKNOWN_ERROR = "Unknown error";
		public final static String ERROR = "execute error";
		public final static String NOT_FUND = "404 NOT Fund";
	}

}
