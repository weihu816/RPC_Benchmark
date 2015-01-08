package com.yahoo.ycsb.rpc;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class Etch extends DB {

	final String uri = "tcp://10.30.7.239:4001?TcpTransport.reconnectDelay=4000";
    RemoteHelloWorldServer server;
    
	public Etch() {
		
	}
	
	public void init() throws DBException {
		try {
			server = HelloWorldHelper.newServer( uri, null, new MainHelloWorldClient());
			server._startAndWaitUp(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	
	@Override
	public int delete(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(String arg0, String arg1,
			HashMap<String, ByteIterator> arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read(String arg0, String arg1, Set<String> arg2,
			HashMap<String, ByteIterator> arg3) {
		// A Simple RPC Call
		server.say_hello("Hello World");
		return 0;
	}

	@Override
	public int scan(String arg0, String arg1, int arg2, Set<String> arg3,
			Vector<HashMap<String, ByteIterator>> arg4) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String arg0, String arg1,
			HashMap<String, ByteIterator> arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cleanup() {
		try {
			server._stopAndWaitDown(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
