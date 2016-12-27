package com.jeremiahxu.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisAdvice {
	private static String NXXX = "NX";
	private static String EXPX = "EX";
	private int time = 60;
	private JedisPool jedisPool;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private byte[] objects2bytes(Object[] objs) throws Throwable {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bao);
		for (Object obj : objs) {
			out.writeObject(obj);
		}
		byte[] b = bao.toByteArray();
		return b;
	}

	/**
	 * 在核心业务执行前执行，不能阻止核心业务的调用。
	 * 
	 * @param joinPoint
	 */
	private void doBefore(JoinPoint joinPoint) {
	}

	/**
	 * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
	 * 
	 * 注意：当核心业务抛异常后，立即退出，转向After Advice 执行完毕After Advice，再转到Throwing Advice
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	private Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		Jedis jedis = this.jedisPool.getResource();
		try {
			Object[] params = pjp.getArgs();
			if (params.length == 0) {
				params = new Object[1];
				String className = pjp.getSignature().getDeclaringTypeName();
				String methodName = pjp.getSignature().getName();
				params[0] = className + "." + methodName;
				System.out.println(params[0]);
			}
			byte[] key = this.objects2bytes(params);
			byte[] b = jedis.get(key);
			if (b == null || b.length == 0) {// redis中没有数据
				// 调用原方法
				retVal = pjp.proceed();
				if (retVal != null || !(retVal instanceof Void)) {
					byte[] result = this.objects2bytes(new Object[] { retVal });
					jedis.set(key, result, NXXX.getBytes(), EXPX.getBytes(), time);
				}
			} else {// redis里有数据
				ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(b));
				retVal = input.readObject();
				System.out.println("redis");
			}
		} finally {
			jedis.close();
		}
		return retVal;
	}

	/**
	 * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
	 * 
	 * @param joinPoint
	 */
	private void doAfter(JoinPoint joinPoint) {
	}

	/**
	 * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
	 * 
	 * @param joinPoint
	 */
	private void doReturn(JoinPoint joinPoint) {
	}

	/**
	 * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
	 * 
	 * @param joinPoint
	 * @param ex
	 */
	private void doThrowing(JoinPoint joinPoint, Throwable ex) {
	}
}
