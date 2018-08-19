package com.xiaogch.rpc.meta;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class RpcServiceMeta {

	static Logger LOGGER = LogManager.getLogger(RpcServiceMeta.class);

	private final Class clazz;
	private final Map<String , List<RpcMethodHandler>> methodHandlerMap;

	public RpcServiceMeta(Class clazz , Map<String, List<RpcMethodHandler>> methodHandlerMap) {
		Assert.notNull(clazz , "clazz can't be null");
		Assert.notNull(methodHandlerMap , "methodHandlerMap can't be null");
		this.clazz = clazz;
		this.methodHandlerMap = methodHandlerMap;
	}

	/**
	 * 获取rpc方法
	 * @param methodName 方法名
	 * @param parameterTypes 参数类型列表
	 * @return
	 */
	public RpcMethodHandler getRpcMethodHandler(String methodName , Class[] parameterTypes){
		Assert.notNull(parameterTypes , "parameterTypes can't be null");
		Assert.hasText(methodName , "methodName can't be empty");

		for (Class clazz : parameterTypes) {
			Assert.notNull(clazz , "parameterTypes can't contain null");
		}

		List<RpcMethodHandler> rpcMethodHandlers = methodHandlerMap.get(methodName);
		if (rpcMethodHandlers == null || rpcMethodHandlers.isEmpty()) {
			LOGGER.error("no RpcMethodHandler for class={} , method={} , parameterTypes={}", clazz.getName() , methodName , parameterTypes);
			throw new IllegalArgumentException("no RpcMethodHandler for class=" + clazz.getName() + " method=" + methodName);
		}
		for (RpcMethodHandler rpcMethodHandler : rpcMethodHandlers) {

			if (rpcMethodHandler == null) {
				continue;
			}

			// 方法名不一致
			if (!methodName.equals(rpcMethodHandler.getMethodName())) {
				continue;
			}

			// 参数个数不一致
			if (parameterTypes.length != rpcMethodHandler.getParameterTypes().length) {
				continue;
			}

			// 类型和参数
			boolean hitFlag = true;
			for (int index = 0 ; index < parameterTypes.length ; index ++) {
				hitFlag = parameterTypes[index].equals(rpcMethodHandler.getParameterTypes()[index]);
				if (!hitFlag) {
					break;
				}
			}

			if (hitFlag) {
				return rpcMethodHandler;
			}
		}
		LOGGER.error("no RpcMethodHandler for class={} , method={} , parameterTypes={}" , clazz.getName() , methodName , parameterTypes);
		throw new IllegalArgumentException("no RpcMethodHandler for class=" + clazz.getName() + " method=" + methodName);

	}


	public Class getClazz() {
		return clazz;
	}

	public Map<String, List<RpcMethodHandler>> getMethodHandlerMap() {
		return methodHandlerMap;
	}
}