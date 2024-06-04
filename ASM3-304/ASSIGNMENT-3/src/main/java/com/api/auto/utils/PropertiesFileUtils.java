package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
	// Đường dẫn đến properties files trong folder configuration
	private static String CONFIG_PATH = "./configuration/configs.properties";

	// 2. Lấy ra giá trị property bất kỳ theo key.
	public static String getProperty(String key) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		// bat exception
		try {
			fileInputStream = new FileInputStream(CONFIG_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty(key);
			return value;
		} catch (Exception ex) {
			System.out.println("Xảy ra lỗi khi đọc giá trị của " + key);
			ex.printStackTrace();
		} finally {

			// luôn nhảy vào đây dù có xảy ra exception hay không.
			// thực hiện đóng luồng đọc
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;

	}

	// 2 ghi property vào file
	public static void setProperty(String key, String value) {
		Properties properties = new Properties();
		FileOutputStream fileOutputStream = null;
		try {
			// Khởi tạo giá trị cho đối tượng FileOutputStream
			fileOutputStream = new FileOutputStream(CONFIG_PATH);
			// Load properties file hiện tại và thực hiện mapping value
			// với key tương ứng
			properties.setProperty(key, value);
			// Lưu key và value vào properties file
			properties.store(fileOutputStream, "Set new value in propertie");
			System.out.println("Set new value in file properties success.");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// luôn nhảy vào đây dù có xảy ra exception hay không.
			// thực hiện đóng luồng ghi
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// 2.save token vào file token.properties với key là “token”
		public static void saveToken(String token) {
	             // Khái báo properties, biến cần thiết
			// Đường dẫn đến properties files trong folder configuration
			String TOKEN_PATH = "./configuration/token.properties";
			Properties properties = new Properties();
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(TOKEN_PATH);
				properties.setProperty("token",token);
				properties.store(fileOutputStream, "Set new value in propertie");
	                 
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				// Luôn nhảy vào đây dù có xảy ra exception hay không.
				// thực hiện đóng luồng
	                    
			}
		}
		// 3 lấy ra token
		public static String getToken() {
	             // Khái báo properties, biến, value
			String TOKEN_PATH = "./configuration/token.properties";
				Properties properties = new Properties();
				String value = null;
				FileInputStream fileInputStream = null;
				// bat exception
				try {
					fileInputStream = new FileInputStream(TOKEN_PATH);
					properties.load(fileInputStream);
					value = properties.getProperty("token");
					return value;
				} catch (Exception ex) {
					System.out.println("Xảy ra lỗi khi đọc giá trị của token");
					ex.printStackTrace();
				}
				return value;
		}
}

