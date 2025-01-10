/*******************************************************************************
********************************************************************************
**                                                                            **
**  Class......:  clsApp (Object)                                             **
**                                                                            **
**  Description:  CTI Server Application Main Class.                          **
**                                                                            **
**  Written by Ronald D. Redmer, September 2000.                              **
********************************************************************************
*******************************************************************************/
package jserver;
import javax.swing.UIManager;
import java.awt.*;
import java.lang.*;

public class clsApp {
  public static String sVersion = "CTI JServer Version 1.26 01-01-2001";
  public clsLogFile oLOG = new clsLogFile("C:\\", "APPMAIN", sVersion);
  public clsData oData = new clsData(oLOG);
  public clsCTIServer oCTI;
  public clsSpectrum oSpectrum;
  private final boolean packFrame = false;
  private final String sLogKey = "[clsApp    ]";

  // Construct the application
  public clsApp() {

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Creating Server for sockets clients...");
    oCTI = new clsCTIServer(oData);

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Creating client for Rockwell Spectrum...");
    oSpectrum = new clsSpectrum(oCTI, oData.SpectrumIP, oData.SpectrumPort);
    oSpectrum.start();

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Creating User Interface...");
    clsUI frame = new clsUI(oLOG, oCTI, oSpectrum);
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

  public static void main(String[] args) {  // Called from command line
    new clsApp();                           // Instantiate CTI Server object
  }
}
