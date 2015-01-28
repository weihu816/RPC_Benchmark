package rpcbench;

import org.apache.thrift.TException;


public class BenchServiceImpl implements BenchService.Iface {

	@Override
	public void Null() throws TException {
		// do nothing
	}

	@Override
	public String MaxResult() throws TException {
		return null;
	}

	@Override
	public void MaxArg(String para) throws TException {
		// do nothing
	}

	
}