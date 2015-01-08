package com.yahoo.ycsb.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class Avro extends DB {

	Hello proxy;
	NettyTransceiver client;
	
	public void init() throws DBException {
		
		try {
			client = new NettyTransceiver(
					new InetSocketAddress("10.30.7.239", 65111));
			proxy = (Hello) SpecificRequestor.getClient(Hello.class,
					client);
		} catch (IOException e) {
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
		try {
			proxy.send("Hello World");
		} catch (AvroRemoteException e) {
			e.printStackTrace();
		}
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
	public void cleanup() throws DBException {
		client.close();
	}

}
