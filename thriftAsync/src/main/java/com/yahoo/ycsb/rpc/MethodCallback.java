package com.yahoo.ycsb.rpc;

import org.apache.thrift.async.AsyncMethodCallback;

public class MethodCallback implements AsyncMethodCallback<Object> {
	Object response = null;
	boolean done = false;

	public synchronized Object getResult() {
		// 返回结果值
		if (!done) {
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		return this.response;
	}

	// 处理服务返回的结果值
	@Override
	public synchronized void onComplete(Object response) {
		this.response = response;
		done = true;
		notify();
	}

	// 处理调用服务过程中出现的异常
	@Override
	public synchronized void onError(Exception arg0) {
		// TODO Auto-generated method stub

	}

	public synchronized boolean done() {
		return done;
	}
}