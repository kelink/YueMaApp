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
		public static final String LOGON = Server.HOST_STRING + "/";
		// 获取验证码
		public static final String GET_REGIST_CODE = "";
		// 注册
		public static final String REGIST = "";
		// 修改用户信息
		public static final String CHANGE_USER_PROFILE = "";
	}

	// 网络请求的类型
	public class RequestType {
		public static final int STRING = 0;
		public static final int JSONOBJ = 1;
		public static final int JSONARRAY = 2;
		public static final int IMAGE = 3;

	}

	public class NetWorkMsg {
		public static final String NETWORK_EXCEPTION = "网络异常";
		public static final String NETWORK_DISCONNECTED = "无法连接网络";
		public static final String SERVER_EXCEPTION = "服务器内部错误";
		public static final String INVALID_REQUEST = "无效请求";
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
		public static final int TASK_LOGON = 3001;
		public static final int TASK_GETREGIST_CODE = 3002;
		public static final int TASK_SET_PWD = 3003;
	}

	public class ServiceAction {

	}

}
