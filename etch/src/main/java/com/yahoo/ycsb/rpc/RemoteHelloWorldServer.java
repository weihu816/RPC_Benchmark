// This file automatically generated by:
//   Apache Etch 1.4.0 (LOCAL-0) / java 1.4.0 (LOCAL-0)
//   Tue Jan 06 14:39:52 CST 2015
// This file is automatically created and should not be edited!

package com.yahoo.ycsb.rpc;


/**
 * Call to message translator for HelloWorldServer.
 */
@SuppressWarnings("unused")
public final class RemoteHelloWorldServer extends RemoteHelloWorld implements HelloWorldServer
{
	/**
	 * Constructs the RemoteHelloWorldServer.
	 *
	 * @param svc
	 * @param vf
	 */
	public RemoteHelloWorldServer( org.apache.etch.bindings.java.support.DeliveryService svc, org.apache.etch.bindings.java.msg.ValueFactory vf )
	{
		super( svc, vf );
	}

	/**
	 * {@link _Async} class instance used to hide asynchronous message
	 * implementation. Use this to invoke the asynchronous message
	 * implementations.
	 */
	public final _Async _async = new _Async();

	/**
	 * {@link _Async} class instance used to hide asynchronous message
	 * implementation. This is here for backwards compatibility only, use
	 * {@link #_async} instead.
	 * @deprecated
	 */
	@Deprecated
	public final _Async _inner = _async;

	public final String say_hello(
		String str
	)
	{
		return
		_async._end_say_hello( _async._begin_say_hello(
			str
		) );
	}

	/**
	 * Asynchronous implementation of service methods.
	 */
	public final class _Async extends RemoteHelloWorld._Async
	{

		/**
		 * Begins a call to say_hello.
		 *
		 * @return mailbox used to retrieve the result using _end_say_hello.
		 * @see RemoteHelloWorldServer#say_hello
		 * @see #_end_say_hello
		 */
		public final org.apache.etch.bindings.java.support.Mailbox _begin_say_hello(
			String str
		)
		{
			org.apache.etch.bindings.java.msg.Message _msg = _newMessage( ValueFactoryHelloWorld._mt_com_yahoo_ycsb_rpc_HelloWorld_say_hello );
			_msg.put( ValueFactoryHelloWorld._mf_str, str );
			return _begincall( _msg );
		}
		
		/**
		 * Ends a call to say_hello.
		 *
		 * @param mb mailbox returned by _begin_say_hello.
		 *
		 * @see RemoteHelloWorldServer#say_hello
		 * @see #_begin_say_hello
		 */
		public final String _end_say_hello( org.apache.etch.bindings.java.support.Mailbox mb )
		{
			try
			{
				return
					(String)
						_endcall( mb,
							ValueFactoryHelloWorld._mt_com_yahoo_ycsb_rpc_HelloWorld__result_say_hello );
			}
			catch ( Exception e )
			{
				if (e instanceof RuntimeException) throw (RuntimeException) e;
				throw new RuntimeException( "unexpected exception from peer: "+e, e );
			}
		}

		// Mixin Methods
	}
}
