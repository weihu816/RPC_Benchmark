package com.yahoo.ycsb.rpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class MyClient {

	private TTransport transport;
	private TProtocol protocol;
	private Hello.Client client;
	
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
	
	public MyClient(String server, int port) {
		try {
			transport = new TSocket(server, port);
			transport.open();
			this.protocol = new TBinaryProtocol(transport);
			client = new Hello.Client(protocol);
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
	
	public String helloString(String para) {
		try {
			return client.helloString(para);
		} catch (TException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void helloStringAsync(final String msg, final Callback callback) {
		cachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					callback.onComplete(client.helloString(msg));
				} catch (TException e) {
					e.printStackTrace();
					callback.onError(e);
				}
			}
		});  
	}
	
	public void stop() {
		transport.close();
		cachedThreadPool.shutdown();
	}
	
}
