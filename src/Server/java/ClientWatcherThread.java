
package jserver;
import java.util.*;
import java.io.*;

public class ClientWatcherThread extends Thread {
  protected ServerThread server;
  private boolean bDone = false;
  private String sLogKey = "[Watcher   ]";

  public ClientWatcherThread(ServerThread s) {
    super(s.ManagerThread.CurrentConnections, "ConnectionWatcher");
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
      try { this.sleep(100); }
      catch (InterruptedException e) { ; }

//      try { this.wait(1000); }
//      catch (InterruptedException e) {
//        System.out.println("Caught an Interrupted Exception");
//        bDone = true;
//      }

      // prevent simultaneous access

      synchronized(server.ClientConnections) {
        // loop through the connections
        for(int i = 0; i < server.ClientConnections.size(); i++) {
          ClientThread c;
          c = (ClientThread)server.ClientConnections.elementAt(i);
          // If thread is dead, remove it from list
          if (!c.isAlive()) {
            server.ClientConnections.removeElementAt(i);
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
/* END OF ClientManagerThread.java *******************************************/
