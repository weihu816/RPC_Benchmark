package com.yahoo.ycsb.rpc;

/**
 * Interface implemented by classes that need to be notified
 * when the connection is lost or established.
 * 
 */
public interface ConnectionStatusListener {
  /**
   * Called when the connection has been lost.
   */
  public void connectionLost();
  
  /**
   * Called when the connection has been established.
   */
  public void connectionEstablished();
  
}
