package com.yahoo.ycsb.rpc;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class Thrift extends DB {

	Hello.Client client;
	
	public Thrift() {
		
	}
	
	public void init() throws DBException {
		try {
			TTransport transport = new TSocket("10.30.7.239", 7911);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			client = new Hello.Client(protocol);
		} catch (TTransportException e) {
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
			client.helloString("Hello World");
		} catch (TException e) {
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

}
