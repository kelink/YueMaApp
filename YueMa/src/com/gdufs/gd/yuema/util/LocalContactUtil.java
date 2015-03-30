package com.gdufs.gd.yuema.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

/**
 * 本地联系人工具类
 * 
 * @author Administrator
 * 
 */
public class LocalContactUtil {
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID };
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	private static final int PHONES_NUMBER_INDEX = 1;

	private static Set<ContentValues> contactList = null;

	public static Set<ContentValues> getLocalcontactList(Context context) {
		if (contactList == null) {
			synchronized (LocalContactUtil.class) {
				if (contactList == null) {
					// 处理获取contactList
					contactList = new HashSet<ContentValues>();
					getPhoneContacts(context);
					getSIMContacts(context);
				}
			}
		}
		return contactList;
	}

	/** Get SD card Contacts **/
	private static void getPhoneContacts(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				if (isMobileNum(phoneNumber)) {
					ContentValues item = new ContentValues();
					item.put(phoneNumber, contactName);
					contactList.add(item);
				}

			}
			phoneCursor.close();
		}
	}

	/** get SIM Contacts **/
	private static void getSIMContacts(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				if (isMobileNum(phoneNumber)) {
					ContentValues item = new ContentValues();
					item.put(phoneNumber, contactName);
					contactList.add(item);
				}
			}
			phoneCursor.close();
		}

	}

	/**
	 * 
	 * 2015 最新的手机号码识别匹配正则 /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$/
	 * 
	 * @param mobiles
	 * @return
	 */

	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
