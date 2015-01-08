package com.yahoo.ycsb.rpc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;


/**
 * The class responsible for reading and deserializing incoming messages. Should
 * be run in its own thread.
 * 
 */
public class MessageReceiver extends ConnectionRequiredRunnable {
	
	ConcurrentMap<Long, Callback> callbacks = new ConcurrentHashMap<Long, Callback>();
	
	private final MessageService.Processor processor;
	private final TProtocol protocol;
	private boolean isRunning;

	
	public MessageReceiver(TProtocol protocol, ConnectionStatusMonitor connectionMonitor) {
		super(connectionMonitor, "Message Receiver");
		this.protocol = protocol;
		isRunning = true;
		MessageService.Iface messageHandler = new MessageService.Iface() {
			@Override
			public void sendMessage(Message msg) throws TException {
				long id = msg.getId();
				Callback callback = callbacks.get(id);
				String str = msg.getMessage();
				callback.onComplete(str);
				callbacks.remove(id);
			}
		};
		this.processor = new MessageService.Processor(messageHandler);
	}

	@Override
	public void run() {
		connectWait();
		while (true) {
			try {
				while (processor.process(protocol, null) == true) {
					
				}
			} catch (TException e) {
				if (!isRunning) break;
				disconnected();
			}
		}
	}
	
	public synchronized void addCallback(Long id, Callback callback) {
		this.callbacks.put(id, callback);
	}
	
	public synchronized void stop() {
		protocol.getTransport().close();
		isRunning = false;
	}
}
