package com.yahoo.ycsb.rpc;   
  
import java.rmi.*;   
import java.rmi.server.*;   
  
/**  
 * 扩展了UnicastRemoteObject类，并实现远程接口 HelloInterface  
 */  
public class HelloImpl extends UnicastRemoteObject implements HelloInterface   
{   
   private String message;   
  
   /**  
    * 必须定义构造方法，即使是默认构造方法，也必须把它明确地写出来，因为它必须抛出出RemoteException异常  
    */  
   public HelloImpl(String msg) throws RemoteException   
   {   
      message = msg;   
   }   
  
   /**  
    * 远程接口方法的实现  
    */  
   public String say(String para) throws RemoteException   
   {   
      return message;   
   }   
}   