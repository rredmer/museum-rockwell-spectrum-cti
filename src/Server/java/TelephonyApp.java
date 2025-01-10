/**
  <strong>TelephonyApp: Provide interface to corporate telephony infrastructure.</strong>

  <p>
  TelephonyApp provides an application interface to the corporate telephony
  services including ACD and IVR.  This class provides application startup and
  a simple monitoring form.  The core functionality is provided by the
  ServerThread class which accepts client connections and establishes connect-
  ivity to the ACD and IVR.

  <p>
  Initially, the server was designed to support the Rockwell Spectrum ACD by
  implementing the Rockwell Spectrum Transaction Link Protocol version 6.1.
  Future versions of this application should utilize the evolving JTAPI
  specification to maintain some degree of consistancy and support for "off-
  the-shelf" ACD interfaces.

  @version 	0.2, 03/11/2001
  @author 	Ronald D. Redmer (rxredmer@affina.com)
  @since	0.0

*/
package jserver;

import javax.swing.UIManager;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/**
  The UserInterface class provides a simple monitoring form.
*/
class UserInterface extends JFrame {

  /**
   * The ServerThread Object provides the core functionality of the application
   * including database access, client management, and Spectrum support.
   */
  public ServerThread oCTI;

  /**
   * The following objects provide the User Interface controls.
   */
  JPanel contentPane;
  JLabel statusBar = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JButton jButton1 = new JButton();

  /**
   * This method creates a new server thread and calls the UI initialization.
   */
  public UserInterface() {
    oCTI = new ServerThread();
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method initializes the UI controls.
   */
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(xYLayout1);
    this.setSize(new Dimension(608, 315));
    this.setTitle("Telephony Server");
    jButton1.setText("Shutdown");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    contentPane.add(jButton1, new XYConstraints(10, 10, 100, 40));
  }

  /**
   * This method provides for normal application shutdown on window close.
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jButton1_actionPerformed(null);
    }
  }

  /**
   * This method provides for normal shutdown on button press.
   */
  void jButton1_actionPerformed(ActionEvent e) {
   oCTI.Dispose();
   try {
      oCTI.interrupt();
      oCTI.join(1000);
   } catch (InterruptedException Ex) {;}
    System.exit(0);
  }
}

/**
  The TelephonyApp class provides the application startup code.
 */
public class TelephonyApp {
  private final boolean packFrame = false;

  /**
   * This method instantiates the user interface object.
   */
  public TelephonyApp() {
    UserInterface frame = new UserInterface();
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);

  }

  /**
   * This method is called on startup, it simply instantiates the application.
   */
  public static void main(String[] args) {  // Called from command line
    new TelephonyApp();                     // Instantiate Application object
  }
}

