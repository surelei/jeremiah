package com.jeremiahxu.nio.server;

/**
 * 服务端响应服务接口
 * 
 * @author Jeremiah Xu
 *
 */
public interface ServerService {
	/**
	 * 处理客户端发送的信息，返回响应信息。
	 * 
	 * @param request
	 *            客户端发送的信息
	 * @return 响应的信息
	 */
	public byte[] response(byte[] request);
}
