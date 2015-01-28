package rpcbench;

import java.util.HashMap;
import java.util.Random;
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

	BenchService.Client client;
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
			benchString = MakeAlphaString();
			TTransport transport = new TSocket("10.30.7.239", 7911);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			client = new BenchService.Client(protocol);
		} catch (TTransportException e) {
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
		try {
			client.Null();
		} catch (TException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insert(String arg0, String arg1,
			HashMap<String, ByteIterator> arg2) {
		try {
			client.MaxResult();
		} catch (TException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int update(String arg0, String arg1,
			HashMap<String, ByteIterator> arg2) {
		try {
			client.MaxArg(benchString);
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
