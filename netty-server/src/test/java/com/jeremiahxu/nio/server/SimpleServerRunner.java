package com.jeremiahxu.nio.server;

public class SimpleServerRunner {

	public static void main(String[] args) {
		ServerService response = new SimpleServerService();
		SimpleServer server = new SimpleServer(81, response);
		server.run();
	}
}
