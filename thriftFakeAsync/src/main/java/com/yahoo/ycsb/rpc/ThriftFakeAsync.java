package com.yahoo.ycsb.rpc;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class ThriftFakeAsync extends DB {
	
	MyClient client;
	
	public void init() throws DBException {
		client = new MyClient("10.30.7.239", 10111);
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
		final Callback callback = new Callback();
    	client.helloStringAsync("Hello World", callback);
		callback.getResult();
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
		client.stop();
	}

}
