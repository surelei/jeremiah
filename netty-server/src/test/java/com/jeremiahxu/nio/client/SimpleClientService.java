package com.jeremiahxu.nio.client;

import java.io.UnsupportedEncodingException;

public class SimpleClientService implements ClientService {

	public byte[] request() {
		try {
			return "这是客户端发送的中文。".getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void response(byte[] response) {
		try {
			System.out.println(new String(response, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
