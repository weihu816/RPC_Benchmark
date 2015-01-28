package rpcbench;

import org.apache.thrift.async.AsyncMethodCallback;

public class MethodCallback implements AsyncMethodCallback<Object> {
	Object response = null;
	boolean done = false;

	public synchronized Object getResult() {
		if (!done) {
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		return this.response;
	}

	@Override
	public synchronized void onComplete(Object response) {
		this.response = response;
		done = true;
		notify();
	}

	@Override
	public synchronized void onError(Exception arg0) {

	}

	public synchronized boolean done() {
		return done;
	}
}