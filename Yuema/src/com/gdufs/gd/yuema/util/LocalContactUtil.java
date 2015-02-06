package com.gdufs.gd.yuema.util;

import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

public class LocalContactUtil {
	/**
	 * ��ȡ��Phone���ֶ� Phone.DISPLAY_NAME ��ϵ����ʾ���� Phone.NUMBER �绰���� Phone.CONTACT_ID
	 * ��ϵ�˵�ID
	 **/
	private static final String[] PHONES_PROJECTION = new String[]{
			Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID};
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	private static final int PHONES_NUMBER_INDEX = 1;
	// private static final int PHONES_CONTACT_ID_INDEX = 2;

	/** �õ��ֻ�ͨѶ¼��ϵ����Ϣ **/
	public HashMap<String, String> getPhoneContacts(Context mContext) {
		ContentResolver resolver = mContext.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		HashMap<String, String> contactMap = null;
		if (phoneCursor != null) {
			contactMap = new HashMap<String, String>();
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				// Long contactid =
				// phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				contactMap.put(phoneNumber, contactName);
			}
			phoneCursor.close();
		}
		return contactMap;
	}

	/** �õ��ֻ�SIM����ϵ������Ϣ **/
	public HashMap<String, String> getSIMContacts(Context mContext) {
		ContentResolver resolver = mContext.getContentResolver();
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
				null);
		HashMap<String, String> contactMap = null;
		if (phoneCursor != null) {
			contactMap = new HashMap<String, String>();
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				contactMap.put(phoneNumber, contactName);
			}

			phoneCursor.close();
		}
		return contactMap;
	}

}
