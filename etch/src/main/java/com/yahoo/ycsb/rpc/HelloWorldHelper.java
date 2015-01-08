// This file automatically generated by:
//   Apache Etch 1.4.0 (LOCAL-0) / java 1.4.0 (LOCAL-0)
//   Tue Jan 06 14:39:52 CST 2015
// This file is automatically created and should not be edited!

package com.yahoo.ycsb.rpc;

import org.apache.etch.bindings.java.support.DeliveryService;
import org.apache.etch.bindings.java.support.Pool;
import org.apache.etch.bindings.java.support.ServerFactory;
import org.apache.etch.bindings.java.msg.ValueFactory;
import org.apache.etch.bindings.java.support.DefaultServerFactory;
import org.apache.etch.util.core.io.Transport;
import org.apache.etch.bindings.java.support.TransportFactory;
import org.apache.etch.bindings.java.support.TransportHelper;
import org.apache.etch.bindings.java.transport.DefaultDeliveryService;
import org.apache.etch.bindings.java.transport.MailboxManager;
import org.apache.etch.bindings.java.transport.PlainMailboxManager;
import org.apache.etch.bindings.java.transport.TransportMessage;
import org.apache.etch.util.Resources;
import org.apache.etch.util.URL;

/**
 * Transport helper for HelloWorld. All methods are static.
 */
abstract public class HelloWorldHelper extends TransportHelper
{

	/**
	 * Constructs a new server session listener per specifications in uri and
	 * resources. This listener will accept requests from clients for new server
	 * sessions.
	 *
	 * @param uri contains specifications for the server session listener and
	 * for the server session transport stack.
	 *
	 * @param resources additional resources to aid in constructing new server
	 * sessions.
	 *
	 * @param implFactory factory used to construct a new instance implementing
	 * HelloWorldServer. The new instance will receive and process messages from
	 * the client session.
	 *
	 * @return a server session listener.
	 *
	 * @throws Exception
	 */
	public static ServerFactory newListener( final String uri,
		final Resources resources, final HelloWorldServerFactory implFactory )
		throws Exception
	{
		final Resources res = initResources( resources );
		
		final Transport<ServerFactory> listener = TransportFactory.getListener( uri, res );
		
		return new DefaultServerFactory( listener, implFactory )
		{
			public void newServer( TransportMessage t, String uri, Resources r )
				throws Exception
			{
				ValueFactory vf = (ValueFactory) r.get( Transport.VALUE_FACTORY );
				MailboxManager x = new PlainMailboxManager( t, uri, r );
				DeliveryService d = new DefaultDeliveryService( x, uri, r );
				RemoteHelloWorldClient client = new RemoteHelloWorldClient( d, vf );
				HelloWorldServer server = implFactory.newHelloWorldServer( client );
				Pool qp = (Pool) r.get( QUEUED_POOL );
				Pool fp = (Pool) r.get( FREE_POOL );
				new StubHelloWorldServer( d, server, qp, fp );
				client._start();
			}

			public ValueFactory newValueFactory( String uri )
			{
				return new ValueFactoryHelloWorld( uri );
			}
			
			@Override
			public String toString()
			{
				return "HelloWorldHelper.ServerFactory/" + listener;
			}
		};
	}

	/**
	 * Factory used by
	 * {@link HelloWorldHelper#newListener(String, Resources, ${i}ServerFactory)}
	 * to construct a new instance implementing {@link HelloWorldServer}. The new
	 * instance will receive and process messages from the client session.
	 */
	public interface HelloWorldServerFactory
	{
		/**
		 * Constructs a new instance implementing HelloWorldServer. The new
		 * instance will receive and process messages from the client session.
		 *
		 * @param client an instance of RemoteHelloWorldClient which may be used to
		 * send messages to the client session.
		 * @return a new instance implementing HelloWorldServer (typically
		 * ImplHelloWorldServer).
		 * @throws Exception
		 */
		public HelloWorldServer newHelloWorldServer( RemoteHelloWorldClient client )
			throws Exception;
	}

	/**
	 * Constructs a new client session per specifications in uri and resources.
	 * 
	 * @param uri contains specifications for the client session transport
	 * stack.
	 * 
	 * @param resources additional resources to aid in constructing new client
	 * sessions.
	 * 
	 * @param implFactory factory used to construct a new instance implementing
	 * HelloWorldClient. The new instance will receive and process messages from
	 * the server session.
	 * 
	 * @return an instance of RemoteHelloWorldServer initialized by uri and
	 * resources which may be used to send messages to the server session.
	 * 
	 * @throws Exception
	 */
	public static RemoteHelloWorldServer newServer( String uri,
		Resources resources, HelloWorldClientFactory implFactory )
		throws Exception
	{
		final Resources res = initResources( resources );
		
		final ValueFactoryHelloWorld vf = new ValueFactoryHelloWorld( uri );
		res.put( Transport.VALUE_FACTORY, vf );
		
		URL u = new URL( uri );
		
		TransportMessage m = TransportFactory.getTransport( uri, res );
		MailboxManager r = new PlainMailboxManager( m, u, resources );
		DeliveryService d = new DefaultDeliveryService( r, u, resources );
		RemoteHelloWorldServer server = new RemoteHelloWorldServer( d, vf );
		HelloWorldClient client = implFactory.newHelloWorldClient( server );
		Pool qp = (Pool) res.get( QUEUED_POOL );
		Pool fp = (Pool) res.get( FREE_POOL );
		new StubHelloWorldClient( d, client, qp, fp );

		return server;
	}

	/**
	 * Factory used by
	 * {@link HelloWorldHelper#newServer(String, Resources, ${i}ClientFactory)}
	 * to construct a new instance implementing {@link HelloWorldClient}. The new
	 * instance will receive and process messages from the server session.
	 */
	public interface HelloWorldClientFactory
	{
		/**
		 * Constructs a new instance implementing HelloWorldClient. The new
		 * instance will receive and process messages from the server session.
		 * 
		 * @param server an instance of RemoteHelloWorldServer which may be used to
		 * send messages to the server session.
		 * @return a new instance implementing HelloWorldClient (typically
		 * ImplHelloWorldClient).
		 * @throws Exception
		 */
		public HelloWorldClient newHelloWorldClient( RemoteHelloWorldServer server )
			throws Exception;
	}
}