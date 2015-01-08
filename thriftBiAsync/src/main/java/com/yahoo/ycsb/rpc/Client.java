package com.yahoo.ycsb.rpc;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {
	private final ConnectionStatusMonitor connectionMonitor;
	private final MessageReceiver receiver;
	
	private final TTransport transport;
	private final TProtocol protocol;
	private final Thread in;
	private final MessageService.Client client;
	
	public Client(String server, int port) {
		this.transport = new TSocket(server, port);
		this.protocol = new TBinaryProtocol(transport);
		this.connectionMonitor = new ConnectionStatusMonitor(transport);
		this.client = new MessageService.Client(protocol);
		this.receiver = new MessageReceiver(protocol, connectionMonitor);
		in = new Thread(receiver);
		in.start();
		this.connectionMonitor.tryOpen();
	}

	public void sendMessageToServer(Message msg, Callback callback) {
		try {
			receiver.addCallback(msg.getId(), callback);
			client.sendMessage(msg);
		} catch (TException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		transport.close();
		connectionMonitor.stop();
		receiver.stop();
	}
	
}

class CTask implements Callable<Long> {

    int num_seconds;
    Queue<Callback> queue = new LinkedBlockingQueue<Callback>();
    
    public CTask(int x) {
        num_seconds = x;
    }
	
    @Override
    public Long call() throws Exception {

        long total = 1000000000;
        total *= num_seconds;
        long count = 0;
        Message msg;
        
        Callback callback = null;
		Client client = new Client("localhost", 10101);
		long start = System.nanoTime();

		while(System.nanoTime() < start + total) {
			msg = new Message();
	        msg.setMessage("Hello World");
			msg.setId(count);
			callback = new Callback();
			client.sendMessageToServer(msg, callback);
			callback.getResult();
			count++;
		}
		client.shutdown();
		System.out.println(count);
		
        return count;
    }
}