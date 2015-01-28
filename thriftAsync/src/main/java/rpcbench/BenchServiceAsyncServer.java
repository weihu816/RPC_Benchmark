package rpcbench;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class BenchServiceAsyncServer {

	public static void main(String[] args) {
		try {
			TNonblockingServerSocket socket = new TNonblockingServerSocket(7911);
			BenchService.Processor processor = new BenchService.Processor(
					new BenchServiceImpl());
			TThreadedSelectorServer.Args thhsArgs = new TThreadedSelectorServer.Args(
					socket);
			thhsArgs.processor(processor);
			thhsArgs.transportFactory(new TFramedTransport.Factory());
			thhsArgs.protocolFactory(new TCompactProtocol.Factory());
			TServer server = new TThreadedSelectorServer(thhsArgs);
			System.out.println("Start server on port 7911 ...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}