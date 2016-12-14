package com.jeremiahxu.map.baidu;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class BaiduMapUtil {

	public final static String COORDS_URL = "http://api.map.baidu.com/geocoder/v2/?ak=GITQM8VDPR8a49ANmZK4Q1YKUKa36axX&output=json&";

	/**
	 * 根据地址取得坐标的json字符串。
	 * 
	 * @param address
	 *            地址
	 * @return 坐标的json字符串
	 */
	public static String getCoordsJSON(String address) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(COORDS_URL + "address=" + address)
					.openConnection();
			InputStream input = conn.getInputStream();
			byte[] b = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while (input.read(b) != -1) {
				sb.append(new String(b));
			}
			return sb.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据地址取得经纬度
	 * 
	 * @param address
	 *            地址
	 * @return Coords 坐标对象
	 */
	public static Coords getCoordsByAddress(String address) {
		String json = getCoordsJSON(address);
		JSONObject jsonObject = JSONObject.fromObject(json);
		jsonObject = JSONObject.fromObject(jsonObject.get("result"));
		jsonObject = JSONObject.fromObject(jsonObject.get("location"));
		Coords coords = (Coords) JSONObject.toBean(jsonObject, Coords.class);
		return coords;
	}
}
