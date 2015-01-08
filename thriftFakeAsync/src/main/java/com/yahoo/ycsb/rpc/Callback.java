package com.yahoo.ycsb.rpc;

public class Callback {

	Object response = null;
	boolean done = false;

	public synchronized Object getResult() {
		if (!this.done()) {
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		return this.response;
	}

	public synchronized void onComplete(Object response) {
		this.response = response;
		this.done = true;
		notify();
	}

	public synchronized void onError(Exception e) {

	}

	public synchronized boolean done() {
		return done;
	}
}
