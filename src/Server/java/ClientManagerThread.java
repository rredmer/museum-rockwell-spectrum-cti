/**
  <strong>ClientManagerThread:  Manage client connections.</strong>

  <p>
  This thread blocks on a socket accept call and therefore must NOT provide
  ANY shared/synchronized objects.

  <p>
  @version 	0.2, 03/11/2001
  @author 	Ronald D. Redmer (rxredmer@affina.com)
  @since	0.0

*/
package jserver;

import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;


public class ClientManagerThread extends Thread {

  private final String sLogKey = "[CTI Server]";
  private final int DEFAULT_PORT = 6001;         // Default TCP Port
  protected ServerThread server;
  protected LogFile oLOG;
  protected ThreadGroup CurrentConnections;
  protected ClientWatcherThread WatcherThread;
  private ServerSocket server_port;

  boolean bDone = false;

  public ClientManagerThread(ServerThread s, LogFile log) {
    server = s;
    oLOG = log;
    this.start();
  }


  // The body of the server thread.  Loop forever, listening for and
  // accepting connections from clients.  For each connection,
  // create a Connection object to handle communication through the
  // new Socket.  When we create a new connection, add it to the
  // Vector of connections, and display it in the List.  Note that we
  // use synchronized to lock the Vector of connections.  The ConnectionWatcher
  // class does the same, so the watcher won't be removing dead
  // connections while we're adding fresh ones.

  public void run() {

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Manager Thread Running...");

    // Create a ConnectionWatcher thread to wait for other threads to die
    WatcherThread = new ClientWatcherThread(server);

    try {
      oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Starting Server on Port=" + DEFAULT_PORT);
      server_port = new ServerSocket(DEFAULT_PORT);
    } catch (IOException e) { oLOG.LogIOException(sLogKey, e); }

    // Create a threadgroup for our connections
    CurrentConnections = new ThreadGroup("Server Connections");
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Created Thread Group=" + CurrentConnections.toString());



    while(!bDone) {

      try { this.sleep(100); }
      catch (InterruptedException e) { ; }


      try {
        Socket client_socket = server_port.accept();

        ClientThread c = new ClientThread(server, client_socket, this.CurrentConnections, 3, WatcherThread);
        synchronized (server.ClientConnections) {    // prevent simultaneous access.

          c.iLWN = server.GetLWNfromFCN(c.sHostName);

          server.ClientConnections.addElement(c);
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "New Client IP=" + c.sHostAddr + " HOST=" + c.sHostName + " Thread=" + c.getName());
        }
      } catch (IOException e) { bDone = true; }
    }

    //--- Close Server Port
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Closing Server Port on normal shutdown...");
    try {
      server_port.close();
    } catch (IOException e) { oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "Error on CTI Server Close: " + e.toString());}
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Server Port Closed.");

  //--- Stop ConnectionWatcher
   oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Stopping Connection Watcher on normal shutdown...");
   WatcherThread.Dispose();
   try {WatcherThread.join(2000);} catch (InterruptedException Ex) {;}

  }
}