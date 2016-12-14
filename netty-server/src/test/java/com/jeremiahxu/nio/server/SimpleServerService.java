package com.jeremiahxu.nio.server;

import java.io.UnsupportedEncodingException;

public class SimpleServerService implements ServerService {

	public byte[] response(byte[] request) {
		try {
			System.out.println(new String(request, "UTF-8"));
			return "这是服务器端返回的中文。".getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
