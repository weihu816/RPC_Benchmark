package com.yahoo.ycsb.rpc;

import org.apache.thrift.TException;

public class HelloServiceImpl implements Hello.Iface {

	@Override
	public String helloString(String para) throws TException {
		return para;
	}


}
