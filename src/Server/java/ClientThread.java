/**
  <strong>TelephonyClient: Service telephony clients connecting via sockets.</strong>

  @version 	0.2, 03/11/2001
  @author 	Ronald D. Redmer (rxredmer@Tpg.com)
  @since	0.0
*/
package jserver;

import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class ClientThread extends Thread {
  private final String sLogKey = "[Connection]";    // Class ID
  public static final int CALL_NEW = 0;
  public static final int CALL_OPEN = 1;
  public static final int CALL_CLOSED = 2;

  static int numberOfConnections = 0;       // The number if active connections
  private ServerThread server = null;       // Pointer to CTI Server
  private Socket client = null;             // Socket to talk to client
  private ClientWatcherThread watcher;     // Pointer to thread watcher
  private PrintWriter out;                  // Socket output stream
  private BufferedReader in;                // Socket input stream
  private boolean bDone = false;            // Flag indicating thread is done (completed)
  public String sHostAddr = null;           // IP Address of Client Host
  public String sHostName = null;           // Network Name of Client Host
  public String sFCN = null;                // "Friendly" Client Name
  public int iLWN = 0;                      // Rockwell Logical Workstation Number

  /**
   * Call Handling Properties
   */
  public String DNIS = "";
  public String ANI = "";
  public long CallID = 0L;
  public int Type = 0;
  public int CallStatus = 0;

  public ClientThread(ServerThread s, Socket client_socket, ThreadGroup CurrentConnections, int priority, ClientWatcherThread watcher) {
    super(CurrentConnections, "Client " + numberOfConnections++); // Call constructor of parent class with thread name
    server = s;
    this.setPriority(priority);         // Set thread priority
    this.client = client_socket;        // Store pointer to socket
    this.watcher = watcher;             // Store pointer to watcher object
    sHostName = client.getInetAddress().getHostName();
    sHostAddr = client.getInetAddress().getHostAddress();
    sFCN = sHostName;
    this.start();                       // Start the thread running
  }

  /**
   * This method provides the main loop for the thread.  It listens for messages
   * on the server pipe and sends/receives messages via sockets to the client.
   */
  public void run() {
    String inline, outputLine;

    //--- Establish connection to the Server database
    server.oLOG.LogWrite(server.oLOG.LOG_INFO, "Started Connection Thread for Host=" + sHostName);

    //--- Configure Sockets for this client and set up communications loop
    try {

      out = new PrintWriter(client.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));

      //  iLWN = server.GetLWNfromFCN(sHostName);   // Get the Rockwell LWN for this client

      Send("CONNECTED");                    // Alert client that we are ready!

      //--- Loop until this client goes away!
      while (!bDone) {

      /***
        inline = in.readLine();

        if ((inline == null) || (inline.equals("Bye")))
          break;
        if (inline.length() > 0) {
          switch (inline.charAt(0)) {
            case 'O':               // Open
              sFCN = inline.substring(1, inline.length());
              out.println("LOGON=" + sFCN);
              break;
            case 'C':               // Close
              out.println("LOGOFF=" + sFCN);
              bDone = true;
              break;
            case 'E':               // Echo Message
              out.println(inline);
              break;
            default:                // Unknown message type
              // out.println("Message not defined...");
          }
        }
        ****/

      try { this.sleep(2000); }
      catch (InterruptedException e) { ; }

//        try { this.wait(800); }
//        catch (InterruptedException e) {
//         server.oLOG.LogWrite(server.oLOG.LOG_INFO, "Connection thread caught interrupt.  Thread=" + this.sHostName);
//         bDone = true;
//        }

      }
    } catch (IOException e) {;}
    server.oLOG.LogWrite(server.oLOG.LOG_INFO, "Connection Thread Finished for Client=" + sHostName);
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
    bDone = true;
    try {
      out.close();
      in.close();
      client.close();
      synchronized (watcher) {watcher.notify();}
    } catch (IOException Ex) {;}
  }


  public void ScreenPOP() {
    String PopMsg;
    if ((CallStatus != CALL_CLOSED) && (DNIS != "")) {
      PopMsg = "SCREENPOP" + "-" + DNIS.trim() + "-" + ANI.trim() + "-" + "00:00:00" + "-" + server.GetAppByDNIS(DNIS);
      server.oLOG.LogWrite(server.oLOG.LOG_INFO, sLogKey + "SCREENPOP=" + CallID + " LWN=" + iLWN + " " + PopMsg);
      this.Send(PopMsg);
      CallStatus = CALL_CLOSED;
    }
  }

  public void CallClear() {
    server.oLOG.LogWrite(server.oLOG.LOG_INFO, sLogKey + "SCREENCLR=" + CallID + " LWN=" + iLWN);
    CallID = 0l;
    DNIS = "";
    ANI = "";
    CallStatus = CALL_NEW;
    Send("CALLCLEAR");
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: Send                                                     **
  **                                                                          **
  **  Description..: This method writes data to the client socket.            **
  **                                                                          **
  **  Notes........: The output buffer is prefixed with a mod-127 checksum.   **
  **                 This was used because the Java character data type is    **
  **                 a signed Byte value (+/- 127).                           **
  **                 This protocol is thus limited to 127-byte messages which **
  **                 is more than enough to pass DNIS & ANI (20 bytes).       **
  **                                                                          **
  *****************************************************************************/
  public void Send(String sOut) {
    int i=0, iCheckSum = 0;                 // Counter and Message checksum
    for (i=0;i<sOut.length();i++) {         // Add up the bytes
      iCheckSum += (int) sOut.charAt(i);
    }
    out.print((char)(iCheckSum % 127));     // Send the checksum (Mod 127)
    out.print((char) sOut.length());        // Send the Message Length
    out.println(sOut);                      // Send the Message
    out.flush();
  }

}


