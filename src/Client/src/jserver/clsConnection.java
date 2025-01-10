/*******************************************************************************
********************************************************************************
**                                                                            **
**  Class......:  clsConnection (Thread)                                      **
**                                                                            **
**  Description:  Creates thread to process a client connection.              **
**                                                                            **
********************************************************************************
*******************************************************************************/
package jserver;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class clsConnection extends Thread {
  private final String sClass = "[Connection]";    // Class ID
  static int numberOfConnections = 0;       // The number if active connections
  public String sHostAddr = null;           // IP Address of Client Host
  public String sHostName = null;           // Network Name of Client Host
  public String sFCN = null;                // "Friendly" Client Name
  public int iLWN = 0;                      // Rockwell Logical Workstation Number
  private clsCTIServer server = null;       // Pointer to CTI Server
  private Socket client = null;             // Socket to talk to client
  private clsConnectionWatcher watcher;     // Pointer to thread watcher
  private PrintWriter out;                  // Socket output stream
  private BufferedReader in;                // Socket input stream
  private boolean bDone = false;            // Flag indicating thread is done (completed)

  public clsConnection(clsCTIServer s, Socket client_socket, ThreadGroup CurrentConnections, int priority, clsConnectionWatcher watcher) {
    super(CurrentConnections, "Client " + numberOfConnections++); // Call constructor of parent class with thread name
    server = s;
    this.setPriority(priority);         // Set thread priority
    this.client = client_socket;        // Store pointer to socket
    this.watcher = watcher;             // Store pointer to watcher object
    sHostName = client.getInetAddress().getHostName();
    sHostAddr = client.getInetAddress().getHostAddress();
    this.start();                       // Start the thread running
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: run                                                      **
  **                                                                          **
  **  Description..: This method contains the executable code for the Thread. **
  **                 In this case, the thread is used to manage all communic- **
  **                 ations with a Client over Sockets.                       **
  **                                                                          **
  **  Notes........: This implements a very simple protocol to perform very   **
  **                 basic CTI functionality on the Client desktop.           **
  **                                                                          **
  *****************************************************************************/
  public void run() {
    String inline, outputLine;

    //--- Establish connection to the Server database
    server.oLOG.LogWrite(server.oLOG.LOG_INFO, "Starting Connection Thread for Host=" + sHostName);

    //--- Configure Sockets for this client and set up communications loop
    try {
      out = new PrintWriter(client.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));

      iLWN = server.oData.GetLWNfromFCN(sHostName);   // Get the Rockwell LWN for this client

      Send("CONNECTED");                    // Alert client that we are ready!

      //--- Loop until this client goes away!
      while (!bDone) {
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
        try { sleep(3000); } catch (InterruptedException Ex) {;}
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
  }
}


