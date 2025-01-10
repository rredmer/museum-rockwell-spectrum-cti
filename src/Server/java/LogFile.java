package jserver;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class LogFile {
  private File fLog;                      // Log File handle
  private FileWriter out;                 // Output file writer
  public static final String LOG_INFO =  "[INFO ]";
  public static final String LOG_DEBUG = "[DEBUG]";
  public static final String LOG_WARN =  "[WARN ]";
  public static final String LOG_ERROR = "[ERROR]";

  /*****************************************************************************
  **                                                                          **
  **  Method.......: (Constructor)                                            **
  **                                                                          **
  **  Description..: This method creates a new log file for the current app-  **
  **                 lication session and writes all of the Java system prop- **
  **                 erties to it for debugging.                              **
  **                                                                          **
  *****************************************************************************/
  public LogFile(String sFolder, String sTag, String sVersion) {
    java.util.Properties myProp;
    Enumeration myNames;
    String myValue;
    String sLogFile;

    sLogFile = "LOG_" + sTag + "_" + DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(new Date()) + ".txt";
    sLogFile = sLogFile.replace('/','_');
    sLogFile = sLogFile.replace(':','_');
    sLogFile = sLogFile.replace(' ','_');
    try {
      fLog = new File(sFolder + sLogFile);
      out = new FileWriter(fLog);
    }
    catch (IOException e) { ; }
    this.LogWrite(this.LOG_INFO, "*********************************************************");
    this.LogWrite(this.LOG_INFO, "**    APPLICATION LOG FILE                             **");
    this.LogWrite(this.LOG_INFO, "**                                                     **");
    this.LogWrite(this.LOG_INFO, "**    Written by:  Ronald D. Redmer                    **");
    this.LogWrite(this.LOG_INFO, "*********************************************************");
    this.LogWrite(this.LOG_INFO, "**             START JAVA PROPERTY SETTINGS            **");
    this.LogWrite(this.LOG_INFO, "*********************************************************");
    this.LogWrite(this.LOG_INFO, "APPLICATION VERSION=" + sVersion);
    //--- Write out the system properties
    try {
      myProp = System.getProperties();
      myNames = myProp.propertyNames();
      while (myNames.hasMoreElements()) {
        myValue = myNames.nextElement().toString();
        this.LogWrite(this.LOG_INFO, myValue + "=" + myProp.getProperty( myValue ));
      }
    }
    catch (SecurityException e) {
      this.LogWrite(this.LOG_ERROR, "Security Exception reading Java Settings=" + e.getMessage());
    }
    this.LogWrite(this.LOG_INFO, "*********************************************************");
    this.LogWrite(this.LOG_INFO, "**             END JAVA PROPERTY SETTINGS              **");
    this.LogWrite(this.LOG_INFO, "*********************************************************");
    this.LogWrite(this.LOG_INFO, "Logging file name= " + sLogFile);
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: LogWrite                                                 **
  **                                                                          **
  **  Description..: This method writes a formatted string to output file.    **
  **                                                                          **
  *****************************************************************************/
  public synchronized void LogWrite(String sType, String sOutput) {
    try {
      out.write(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(new Date()) + "|" + sType + sOutput + "\r\n");
      out.flush();
    } catch (IOException e) {;}
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: LogException                                             **
  **                                                                          **
  **  Description..: This method writes a formatted string to output file.    **
  **                                                                          **
  *****************************************************************************/
  public synchronized void LogException(String sRoutine, Exception eEx) {
    try {
      out.write(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(new Date()) + "|" + this.LOG_ERROR + sRoutine + " " + eEx.getMessage() + "\r\n");
      out.flush();
    } catch (Exception e) {;}
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: LogIOException                                           **
  **                                                                          **
  **  Description..: This method writes a formatted string to output file.    **
  **                                                                          **
  *****************************************************************************/
  public synchronized void LogIOException(String sRoutine, IOException eEx) {
    try {
      out.write(DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(new Date()) + "|" + this.LOG_ERROR + sRoutine + " " + eEx.getMessage() + "\r\n");
      out.flush();
    } catch (Exception e) {;}
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: Dispose                                                  **
  **                                                                          **
  **  Description..: This method is called prior to shutdown to exit normally.**
  **                                                                          **
  *****************************************************************************/
  public void Dispose() {
    try {
      this.LogWrite(this.LOG_INFO, "Closing Log File on normal shutdown.");
      out.close();
    } catch (IOException e) {;}
  }

}