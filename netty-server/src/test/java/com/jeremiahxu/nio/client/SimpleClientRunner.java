package com.jeremiahxu.nio.client;

public class SimpleClientRunner {

	public static void main(String[] args) {
		ClientService request = new SimpleClientService();
		SimpleClient client = new SimpleClient("127.0.0.1", 81, request);
		client.send();
	}

}
