package com.yahoo.ycsb.rpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TTransportException;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class ThriftAsync extends DB {

	Hello.AsyncClient asyncClient;
	
	public void init() throws DBException {
		try {
			TAsyncClientManager clientManager = new TAsyncClientManager();
			TNonblockingTransport transport = new TNonblockingSocket("10.30.7.239", 10005);
			TProtocolFactory protocol = new TCompactProtocol.Factory();
			asyncClient = new Hello.AsyncClient(protocol, clientManager, 
				transport);
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
		MethodCallback callback = new MethodCallback();
		try {
			asyncClient.helloString("Hello World", callback);
			callback.getResult();
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
