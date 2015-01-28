package rpcbench;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;

public class ThriftAsync extends DB {

	BenchService.AsyncClient asyncClient;
	String benchString;

	public static String MakeAlphaString() {
		StringBuffer str = new StringBuffer();
		Random random = new Random();
		long result = 0;
		int number = 4096;
		for (int i = 0; i < number; i++) {
			switch (random.nextInt(3)) {
			case 0: // CAP letter
				result = Math.round(Math.random() * 25 + 65);
				str.append(String.valueOf((char) result));
				break;
			case 1: // Low letter
				result = Math.round(Math.random() * 25 + 97);
				str.append(String.valueOf((char) result));
				break;
			case 2: // Number
				str.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return str.toString();
	}

	public void init() throws DBException {
		try {
			TAsyncClientManager clientManager = new TAsyncClientManager();
			TNonblockingTransport transport = new TNonblockingSocket(
					"10.30.7.239", 7911);
			TProtocolFactory protocol = new TCompactProtocol.Factory();
			asyncClient = new BenchService.AsyncClient(protocol, clientManager,
					transport);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int delete(String arg0, String arg1) {
		return 0;
	}

	@Override
	public int read(String arg0, String arg1, Set<String> arg2,
			HashMap<String, ByteIterator> arg3) {
		MethodCallback callback = new MethodCallback();
		try {
			asyncClient.Null(callback);
			callback.getResult();
		} catch (TException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insert(String arg0, String arg1,
			HashMap<String, ByteIterator> arg2) {
		MethodCallback callback = new MethodCallback();
		try {
			asyncClient.MaxResult(callback);
			callback.getResult();
		} catch (TException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int update(String arg0, String arg1,
			HashMap<String, ByteIterator> arg2) {
		MethodCallback callback = new MethodCallback();
		try {
			asyncClient.MaxArg(benchString, callback);
			callback.getResult();
		} catch (TException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int scan(String arg0, String arg1, int arg2, Set<String> arg3,
			Vector<HashMap<String, ByteIterator>> arg4) {
		return 0;
	}

}
