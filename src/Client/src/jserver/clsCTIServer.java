/*******************************************************************************
********************************************************************************
**                                                                            **
**  Class......:  clsCTIServer (Thread)                                       **
**                                                                            **
**  Description:  Creates thread to process client connections on a given     **
**                Socket.                                                     **
**                                                                            **
**  Written by Ronald D. Redmer, September 2000.                              **
********************************************************************************
*******************************************************************************/
package jserver;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;

public class clsCTIServer extends Thread {
    public clsData oData;
    private static final int DEFAULT_PORT = 6001;                         // Default TCP Port
    private final String sVersion = "CTI Connection Server Version 1.00";
    private final String sLogKey = "[CTI Server]";
    private int port;                                     // Actual TCP Port
    private ServerSocket server_port;
    protected ThreadGroup CurrentConnections;
    protected Vector connections;
    protected clsConnectionWatcher watcher;

    clsLogFile oLOG;
    boolean bDone = false;

    // Create a ServerSocket to listen for connections on & start the thread.
    public clsCTIServer(clsData oDATA) {
        super("Server");
        this.port = DEFAULT_PORT;
        oData = oDATA;
        oLOG = new clsLogFile("C:\\", "SERVER", sVersion);

        try {
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Starting JServer on Port=" + this.port);
          server_port = new ServerSocket(this.port);
        }
        catch (IOException e) { oLOG.LogIOException(sLogKey, e); }

        // Create a threadgroup for our connections
        CurrentConnections = new ThreadGroup("Server Connections");
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Creating Thread Group=" + CurrentConnections.toString());

        // Initialize a vector to store our connections in
        connections = new Vector();
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Creating Client Connection Vector=" + connections.getClass().getName());
        // Create a ConnectionWatcher thread to wait for other threads to die
        watcher = new clsConnectionWatcher(this);
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Created Connection Thread Watcher=" + watcher.getName());

        // Start the server listening for connections
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Starting server thread..." + this.getName());
        this.start();
    }


    // Exit with an error message, when an exception occurs.
    public void fail(Exception e, String msg) {
        //clsMain.jlblStatus.setText(msg + ": " +  e.getMessage());
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "CRITICAL FAILURE IN SERVER..." + msg);
        System.exit(1);
    }

  // The body of the server thread.  Loop forever, listening for and
  // accepting connections from clients.  For each connection,
  // create a Connection object to handle communication through the
  // new Socket.  When we create a new connection, add it to the
  // Vector of connections, and display it in the List.  Note that we
  // use synchronized to lock the Vector of connections.  The ConnectionWatcher
  // class does the same, so the watcher won't be removing dead
  // connections while we're adding fresh ones.
  public synchronized void run() {
    while(!bDone) {
      try { this.wait(100); }
      catch (InterruptedException e) {
       oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "CTI Server Caught an Interrupt.");
       bDone = true;
      }
      try {
        Socket client_socket = server_port.accept();
        clsConnection c = new clsConnection(this, client_socket, CurrentConnections, 3, watcher);
        synchronized (connections) {    // prevent simultaneous access.
          connections.addElement(c);
          // clsServerUI.listModel.addElement("IP=" + c.sHostAddr + " HOST=" + c.sHostName);
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "New Client IP=" + c.sHostAddr + " HOST=" + c.sHostName + " Thread=" + c.getName());
        }
      } catch (IOException e) { oLOG.LogIOException(sLogKey, e); bDone = true; }
    }
    //--- Close Server Port
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "CTI Server Thread Finished.");
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: Dispose                                                  **
  **                                                                          **
  **  Description..: This method simply sets the exit flag for termination.   **
  **                                                                          **
  **  Notes........:                                                          **
  **                                                                          **
  *****************************************************************************/
  public void Dispose() {

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Attempting CTI Server Shutdown...");
    bDone = true;

    //--- Stop all connections
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Closing all connections on normal shutdown...");
    for(int i = 0; i < this.connections.size(); i++) {
      clsConnection c;
      c = (clsConnection) this.connections.elementAt(i);
      c.Dispose();
    }
    this.connections.removeAllElements();

    //--- Stop ConnectionWatcher
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Stopping Connection Watcher on normal shutdown...");
    watcher.Dispose();
    try {watcher.join(2000);} catch (InterruptedException Ex) {;}

    this.interrupt();
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Closing Server Port on normal shutdown...");
    try {
      server_port.close();
    } catch (IOException e) { oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "Error on CTI Server Close: " + e.toString());}
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Server Port Closed.");
  }


  public void ScreenPOP(long lCallID, int iLWN, String sDNIS, String sANI) {
    //--- Loop through each connection and see if client for screen pop is signed in
    for(int i = 0; i < this.connections.size(); i++) {
      clsConnection oCon;
      oCon = (clsConnection) this.connections.elementAt(i);
      //--- If the LWN matches, then retrieve the application data and send it
      if (iLWN == oCon.iLWN) {
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Sending Screen POP for " + lCallID + " to LWN=" + iLWN);
        oCon.Send("SCREENPOP" + "-" + sDNIS.trim() + "-" + sANI.trim() + "-" + "00:00:00" + "-" + oData.GetAppByDNIS(sDNIS));
        break;
      }
    }
  }

  public void ScreenCLEAR(long lCallID, int iLWN, String sDNIS, String sANI) {
    //--- Loop through each connection and see if client for screen CLEAR is signed in
    for(int i = 0; i < this.connections.size(); i++) {
      clsConnection oCon;
      oCon = (clsConnection) this.connections.elementAt(i);
      //--- If the LWN matches, then retrieve the application data and send it
      if (iLWN == oCon.iLWN) {
        oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Sending Screen CLEAR for " + lCallID + " to LWN=" + iLWN);
        oCon.Send("CALLCLEAR");
      }
    }
  }

  public boolean IsLwnOn(int iLWN) {
    boolean bFound = false;
    // oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Checking Logon Status for LWN=" + iLWN);
    for(int i = 0; i < this.connections.size(); i++) {
      clsConnection c;
      c = (clsConnection) this.connections.elementAt(i);
      if (iLWN == c.iLWN) {
        bFound = true;
      }
    }
    return bFound;
  }
}


