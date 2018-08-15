package com.xiaogch.rpc.meta;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class RpcMethodHandler {
    private final String methodName;
		private final Class[] parameterTypes;
		private final Method method;
		private final Object target;

		public RpcMethodHandler(Object target, String methodName, Method method, Class[] parameterTypes){
			Assert.hasText(methodName , "clazz can't be empty");
			Assert.notNull(target , "target can't be null");
			Assert.notNull(parameterTypes , "parameterTypes can't be null");
			Assert.notNull(method , "method can't be null");
			for (Class clazz : parameterTypes) {
				Assert.notNull(clazz , "parameterTypes can't contain null");
			}
			this.method = method;
			this.methodName = methodName;
			this.parameterTypes = parameterTypes;
			this.target = target;
		}

		public Object invoke(Object[] parameters){
			Assert.notNull(parameters , "parameters can't be null");
			try {
				return method.invoke(target , parameters);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public String getMethodName() {
			return methodName;
		}

		public Class[] getParameterTypes() {
			return parameterTypes;
		}
	}