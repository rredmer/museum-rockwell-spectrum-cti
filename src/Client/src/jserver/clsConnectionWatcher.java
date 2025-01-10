/*******************************************************************************
 *******************************************************************************
 **                                                                           **
 **  Class......:  ConnectionWatcher (Thread)                                 **
 **                                                                           **
 **  Description:  Creates thread to process a client connection.             **
 **                                                                           **
 **          This class waits to be notified that a thread is dying (exiting) **
 **          and then cleans up the list of clients and the graphical list.   **
 **                                                                           **
 ** Written by Ronald D. Redmer, 08-20-2000.                                  **
 *******************************************************************************
 ******************************************************************************/
package jserver;
import java.util.*;
import java.io.*;

public class clsConnectionWatcher extends Thread {
  protected clsCTIServer server;
  private boolean bDone = false;
  private String sLogKey = "[Watcher   ]";

  public clsConnectionWatcher(clsCTIServer s) {
    super(s.CurrentConnections, "ConnectionWatcher");
    server = s;
    this.start();
  }

  // This is the method that waits for notification of exiting threads
  // and cleans up the lists.  It is a synchronized method, so it
  // acquires a lock on the `this' object before running.  This is
  // necessary so that it can call wait() on this.  Even if the
  // the Connection objects never call notify(), this method wakes up
  // every five seconds and checks all the connections, just in case.
  // Note also that all access to the Vector of connections and to
  // the GUI List component are within a synchronized block as well.
  // This prevents the Server class from adding a new conenction while
  // we're removing an old one.
  public synchronized void run() {
    server.oLOG.LogWrite(server.oLOG.LOG_INFO, sLogKey + "Connection Watcher Thread Started.");
    while(!bDone) {
      try { this.wait(1000); }
      catch (InterruptedException e) {
        // System.out.println("Caught an Interrupted Exception");
        bDone = true;
      }
      // prevent simultaneous access
      synchronized(server.connections) {
        // loop through the connections
        for(int i = 0; i < server.connections.size(); i++) {
          clsConnection c;
          c = (clsConnection)server.connections.elementAt(i);
          // If thread is dead, remove it from list
          if (!c.isAlive()) {
            server.connections.removeElementAt(i);
            // clsServerUI.listModel.removeElementAt(i);
            i--;
          }
        }
      }
    }
    server.oLOG.LogWrite(server.oLOG.LOG_INFO, sLogKey + "Connection Watcher Thread Finished.");
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
    this.interrupt();
  }
}
/* END OF clsConnectionWatcher.java *******************************************/
