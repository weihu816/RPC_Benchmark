package rpcbench;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class BenchServiceServer {

	public static void main(String[] args) {
		try {
			TServerSocket serverTransport = new TServerSocket(7911);
			TProcessor processor = new BenchService.Processor(new BenchServiceImpl());
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
					serverTransport).processor(processor));
			System.out.println("Start server on port 7911...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}