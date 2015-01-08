package com.yahoo.ycsb.rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class RMI extends DB {

	HelloInterface hello;

	public void init() throws DBException {
		
			try {
				HelloInterface hello = (HelloInterface) Naming
						.lookup("//10.30.7.239:9099/Hello");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
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
		try {
			hello.say("Hello World");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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
	public void cleanup() {
		
	}

}
