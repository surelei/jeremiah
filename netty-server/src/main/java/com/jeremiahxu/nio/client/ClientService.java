package com.jeremiahxu.nio.client;

/**
 * 客户端请求服务接口。
 * 
 * @author Jeremiah Xu
 *
 */
public interface ClientService {
	/**
	 * 取得要请求的内容。
	 * 
	 * @return
	 */
	public byte[] request();

	/**
	 * 获得响应后的处理
	 * 
	 * @param response
	 */
	public void response(byte[] response);
}
