package jserver;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;
import java.lang.String.*;


public class SpectrumClient extends Thread {
  private final String sVersion = "Rockwell Spectrum 6.1 Interface Version 1.11";
  private final String sLogKey = "[Spectrum  ]";
  private final String CON_SPC_TRANID = "R001";
  private final int CON_SPC_DATA_PACKET = 0;
  private final int CON_SPC_KEEP_ALIVE_PACKET = 255;
  private final int CON_SPC_MSG_HDR_SIZE = 4;
  private final int CON_SPC_MSG_FEATURE_ACCESS = 0;		// Message table for Spectrum 6.1
  private final int CON_SPC_MSG_RESERVED1	= 1;
  private final int CON_SPC_MSG_CLEAR_CONNECTION = 2;
  private final int CON_SPC_MSG_MAKE_CALL	= 3;
  private final int CON_SPC_MSG_TRANSFER_CALL = 4;
  private final int CON_SPC_MSG_NEW_PARTY_TRANSFER = 5;
  private final int CON_SPC_MSG_ROUTE_SELECT = 6;
  private final int CON_SPC_MSG_GET_CALLER_NUMBER = 7;
  private final int CON_SPC_MSG_MAKE_PREDICTIVE_CALL = 9;
  private final int CON_SPC_MSG_HOLD_CALL	= 10;
  private final int CON_SPC_MSG_RETRIEVE_CALL = 11;
  private final int CON_SPC_MSG_HOST_INITIATED_ROUTE = 12;
  private final int CON_SPC_MSG_GENERAL_ERROR = 127;
  private final int CON_SPC_MSG_NEGATIVE_RESPONSE = 128;
  private final int CON_SPC_MSG_POSITION_STATUS_RESPONSE = 129;
  private final int CON_SPC_MSG_POSITION_STATUS_CHANGE = 130;
  private final int CON_SPC_MSG_CALL_CLEARED = 131;
  private final int CON_SPC_MSG_RESERVED2 = 132;
  private final int CON_SPC_MSG_DEVICE_DROPPED = 133;
  private final int CON_SPC_MSG_CONNECTION_NOT_CLEARED = 134;
  private final int CON_SPC_MSG_CALL_DIALED = 135;
  private final int CON_SPC_MSG_CALL_FAILED = 136;
  private final int CON_SPC_MSG_CALL_TRANSFERRED = 137;
  private final int CON_SPC_MSG_CALL_NOT_TRANSFERRED = 138;
  private final int CON_SPC_MSG_NEW_PARTY_TRANSFER_FAILED = 139;
  private final int CON_SPC_MSG_CALL_ARRIVAL = 140;
  private final int CON_SPC_MSG_CALL_ESTABLISHED = 141;
  private final int CON_SPC_MSG_ROUTE_USED = 142;
  private final int CON_SPC_MSG_CALLER_NUMBER = 143;
  private final int CON_SPC_MSG_NO_CALLER_NUMBER = 144;
  private final int CON_SPC_MSG_SUBSCRIBER_INFORMATION = 145;
  private final int CON_SPC_MSG_CALL_ALLOCATED_AND_ROUTED = 147;
  private final int CON_SPC_MSG_CALL_PROGRESS_FAILURE = 148;
  private final int CON_SPC_MSG_HOST_ROUTE_FAILED = 149;
  private final int CON_SPC_MSG_CALL_HELD_MESSAGE = 150;
  private final int CON_SPC_MSG_CALL_NOT_HELD_MESSAGE = 151;
  private final int CON_SPC_MSG_CALL_RETRIEVED = 152;
  private final int CON_SPC_MSG_CALL_NOT_RETRIEVED = 153;
  private final int CON_SPC_MSG_CALL_STATUS = 154;
  private final int CON_SPC_MSG_HOST_INITIATED_ROUTE_USED = 155;
  private final int CON_SPC_MSG_HOST_INITIATED_ROUTE_FAILED = 156;
  private final int CON_SPC_ELM_ACCOUNT_INFORMATION = 3;		// Message Elements for Spectrum 6.1
  private final int CON_SPC_ELM_ACTIVITY = 4;
  private final int CON_SPC_ELM_AGENT_GROUP = 5;
  private final int CON_SPC_ELM_AGENT_INFORMATION_GROUP = 6;
  private final int CON_SPC_ELM_AGENT_STATE = 7;
  private final int CON_SPC_ELM_ALLOCATION = 8;
  private final int CON_SPC_ELM_APPLICATION_ID = 10;
  private final int CON_SPC_ELM_APPLICATION_REQUEST_NUMBER = 11;
  private final int CON_SPC_ELM_CONFERENCE_CALLS_CALL_ID = 12;
  private final int CON_SPC_ELM_CALL_ID = 13;
  private final int CON_SPC_ELM_CALL_TYPE = 14;
  private final int CON_SPC_ELM_CALLED_PARTY_NUMBER = 15;
  private final int CON_SPC_ELM_CALLER_NUMBER = 16;
  private final int CON_SPC_ELM_CALLER_NUMBER_ID = 17;
  private final int CON_SPC_ELM_CALLING_DEVICE_ID = 18;
  private final int CON_SPC_ELM_CALLING_PARTY_NUMBER = 19;
  private final int CON_SPC_ELM_COMPLETION_REASON = 20;
  private final int CON_SPC_ELM_DESTINATION_DN = 21;
  private final int CON_SPC_ELM_DETECTION_METHOD = 22;
  private final int CON_SPC_ELM_DNIS_NUMBER = 23;
  private final int CON_SPC_ELM_ERROR_CODE = 25;
  private final int CON_SPC_ELM_ERROR_TYPE = 26;
  private final int CON_SPC_ELM_ERROR_VALUE = 27;
  private final int CON_SPC_ELM_HOST_INFORMATION = 28;
  private final int CON_SPC_ELM_INSTRUCTION_NEEDED = 29;
  private final int CON_SPC_ELM_IEC_CODE = 30;
  private final int CON_SPC_ELM_LOGICAL_WORKSTATION_NUMBER = 31;
  private final int CON_SPC_ELM_MESSAGE_FOR_AGENT_CONSOLE = 32;
  private final int CON_SPC_ELM_ORIGIN_ANNOUNCEMENT_ID = 34;
  private final int CON_SPC_ELM_PACING_INFORMATION = 35;
  private final int CON_SPC_ELM_TRUNK_PORT_ID = 36;
  private final int CON_SPC_ELM_POSITION = 37;
  private final int CON_SPC_ELM_PREVIOUS_CALL_INFORMATION = 38;
  private final int CON_SPC_ELM_PREVIOUS_POSITION = 39;
  private final int CON_SPC_ELM_RING_TIMEOUT = 41;
  private final int CON_SPC_ELM_ROUTING_ATTRIBUTES = 42;
  private final int CON_SPC_ELM_SET_QUERY_FLAG = 43;
  private final int CON_SPC_ELM_SIGN_IN_NUMBER = 44;
  private final int CON_SPC_ELM_TAG = 45;
  private final int CON_SPC_ELM_TARGET_PARTY_LOGICAL_WORK_NUMBER = 46;
  private final int CON_SPC_ELM_TARGET_PARTY_NUMBER = 47;
  private final int CON_SPC_ELM_TRANSFER_INDICATOR = 48;
  private final int CON_SPC_ELM_TRUNK_GROUP_NUMBER = 49;
  private final int CON_SPC_ELM_TYPE_OF_ANSWER = 50;
  private final int CON_SPC_ELM_FEATURE = 52;
  private final int CON_SPC_ELM_SIGN_OUT_REASON = 53;
  private final int CON_SPC_ELM_APPLICATION_PACING_INFORMATION = 54;
  private final int CON_SPC_ELM_ORIGINATING_LOGICAL_WORK_NUMBER = 55;
  private final int CON_SPC_ELM_ORIGINATING_POSITION = 56;
  private final int CON_SPC_ELM_PREVIOUS_LOGICAL_WORK_NUMBER = 57;
  private final int CON_SPC_ELM_TERMINATING_LOGICAL_WORK_NUMBER = 58;
  private final int CON_SPC_ELM_TERMINATING_POSITION = 59;
  private final int CON_SPC_ELM_TERMINATING_TRUNK_PORT_ID = 60;
  private final int CON_SPC_ELM_ORIGINATING_TRUNK_PORT_ID = 61;
  private final int CON_SPC_ELM_ORIGINATING_TRUNK_GROUP_NUMBER = 62;
  private final int CON_SPC_ELM_TERMINATING_TRUNK_GROUP_NUMBER = 63;
  private final int CON_SPC_ELM_PREVIOUS_TRUNK_PORT_ID = 64;
  private final int CON_SPC_ELM_PREVIOUS_TRUNK_GROUP_NUMBER = 65;
  private final int CON_SPC_ELM_TARGET_TRUNK_PORT_ID = 70;
  private final int CON_SPC_ELM_ROUTE_TRI_TONE_CALLS = 71;
  private final int CON_SPC_ELM_ANSWER_MACHINE_DETECT_CALL_ID = 72;
  private final int CON_SPC_ELM_ANSWER_MACHINE_DETECT_APP_ID = 73;
  private final int CON_SPC_ELM_QUEUED_AGENT_GROUPS = 74;
  private final int CON_SPC_ELM_TELESCRIPT_INFORMATION = 75;
  private final int CON_SPC_ELM_OVERFLOW_INFORMATION = 76;
  private final int CON_SPC_ELM_ON_HOLD_CALL_ID = 77;
  private final int CON_SPC_ELM_BARGED_IN_CALL_ID = 78;
  private final int CON_SPC_ELM_TELESCRIPT_DIGIT_VARIABLE = 79;

  private boolean bDone = false;

  private LogFile oLOG;                                         // Spectrum Log File Object
  private ServerThread oCTI;                                    // CTI Server
  private Socket spectrum_socket;                               // TCP-IP Socket for this spectrum
  private PrintWriter out;                                      // Output stream
  private InputStream in;                                       // Input stream
  private byte[] cMsgHdr = new byte[ CON_SPC_MSG_HDR_SIZE + 1 ];// Signed byte buffer from Spectrum
  private byte[] cData = new byte[ 1024 ];                      // Signed byte buffer from Spectrum
  private int[]  iMsgHdr = new int[ CON_SPC_MSG_HDR_SIZE + 1 ]; // UnSigned byte buffer from Spectrum
  private int[]  iData = new int[ 1024 ];                       // UnSigned byte buffer from Spectrum
  private int	 iDataLenRecvd = 0;                             // Length of data buffer
  private int    iSpectrumPort = 0;                             // Port # to connect to on the Spectrum
  private String sSpectrumIP = "";                              // IP Address of spectrum to connect to

  public SpectrumClient(ServerThread oCTIServer, String sIP, int iPort) {
    super("Spectrum Client");                                   // Set thread name

    //---- Get the IP & Port of Spectrum for Connection
    this.sSpectrumIP = sIP;
    this.iSpectrumPort = iPort;
    oLOG = new LogFile("C:\\", "SPECTRUM", sVersion);
    this.oCTI = oCTIServer;
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: run                                                      **
  **                                                                          **
  **  Description..: This method contains the executable code for the Thread. **
  **                 In this case, the thread is used to manage all communic- **
  **                 ations with a Spectrum over Sockets.                     **
  **                                                                          **
  **  Notes........: This implements the message handler for Rockwell Trans-  **
  **                 action Link Version 6.1.  Each individual message can    **
  **                 be parsed dynamically in method calls.                   **
  **                                                                          **
  *****************************************************************************/
  public void run() {
    String buf;
    boolean bConnected = false;

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Spectrum thread running...");

    while (!bDone) {

      try { this.sleep(1); }
      catch (InterruptedException e) { ; }

      if (!bConnected) {
        try {
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Connecting to Spectrum at IP=" + sSpectrumIP + " Port=" + iSpectrumPort);
          spectrum_socket = new Socket(sSpectrumIP, iSpectrumPort);  // Spectrum port
          out = new PrintWriter(spectrum_socket.getOutputStream(), true);
          in = spectrum_socket.getInputStream();
          bConnected = true;
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Connection running...");
        }
        catch (UnknownHostException e) {
          oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "Can't reach host, will try again in 10000 milliseconds.");
          try {this.sleep(1000);} catch (InterruptedException ie) {bDone=true;}
        }
        catch (IOException e) {
          oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "Couldn't get I/O for the connection, will try again in 10000 milliseconds");
          try {this.sleep(1000);} catch (InterruptedException ie) {bDone=true;}
        }
      }
      else {
        iRead();                      // Get buffer from spectrum
        switch (iData[0]) {           // First byte is the message type
  	  case CON_SPC_MSG_FEATURE_ACCESS:
          case CON_SPC_MSG_RESERVED1:
          case CON_SPC_MSG_CLEAR_CONNECTION:
          case CON_SPC_MSG_MAKE_CALL:
          case CON_SPC_MSG_TRANSFER_CALL:
          case CON_SPC_MSG_NEW_PARTY_TRANSFER:
          case CON_SPC_MSG_ROUTE_SELECT:
          case CON_SPC_MSG_GET_CALLER_NUMBER:
          case CON_SPC_MSG_MAKE_PREDICTIVE_CALL:
          case CON_SPC_MSG_HOLD_CALL:
          case CON_SPC_MSG_RETRIEVE_CALL:
          case CON_SPC_MSG_HOST_INITIATED_ROUTE:
          case CON_SPC_MSG_GENERAL_ERROR:
          case CON_SPC_MSG_NEGATIVE_RESPONSE:
          case CON_SPC_MSG_POSITION_STATUS_RESPONSE:
          case CON_SPC_MSG_POSITION_STATUS_CHANGE:
          case CON_SPC_MSG_CALL_CLEARED: {
            CallCleared();
            break;
          }
          case CON_SPC_MSG_RESERVED2:
          case CON_SPC_MSG_DEVICE_DROPPED:
          case CON_SPC_MSG_CONNECTION_NOT_CLEARED:
          case CON_SPC_MSG_CALL_DIALED:
          case CON_SPC_MSG_CALL_FAILED:
          case CON_SPC_MSG_CALL_TRANSFERRED:
          case CON_SPC_MSG_CALL_NOT_TRANSFERRED:
          case CON_SPC_MSG_NEW_PARTY_TRANSFER_FAILED:
          case CON_SPC_MSG_CALL_ESTABLISHED: {
            CallEstablished();
            break;
          }
          case CON_SPC_MSG_ROUTE_USED:
          case CON_SPC_MSG_CALLER_NUMBER:
          case CON_SPC_MSG_NO_CALLER_NUMBER:


          case CON_SPC_MSG_SUBSCRIBER_INFORMATION: {
//            SubscriberInformation();
            break;
          }

          // Host information contains new DNIS  - LESLIE TATE

          case CON_SPC_MSG_CALL_ALLOCATED_AND_ROUTED:
          case CON_SPC_MSG_CALL_PROGRESS_FAILURE:
          case CON_SPC_MSG_HOST_ROUTE_FAILED:
          case CON_SPC_MSG_CALL_HELD_MESSAGE:
          case CON_SPC_MSG_CALL_NOT_HELD_MESSAGE:
          case CON_SPC_MSG_CALL_RETRIEVED:
          case CON_SPC_MSG_CALL_NOT_RETRIEVED:
          case CON_SPC_MSG_CALL_STATUS:
          case CON_SPC_MSG_HOST_INITIATED_ROUTE_USED:
          case CON_SPC_MSG_HOST_INITIATED_ROUTE_FAILED: {
            break;
          }
        }
      }
    }

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Closing Spectrum Port on normal shutdown...");

    if (bConnected) {
      try {
        in.close();
        out.close();
        spectrum_socket.close();
      } catch (IOException e) { oLOG.LogIOException(sLogKey, e);}
    }
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Spectrum Server Finished.");

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
  public synchronized void Dispose() {
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Interrupting Spectrum Thread.");
    bDone = true;
    this.interrupt();
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: iRead                                                    **
  **                                                                          **
  **  Description..: This method reads binary data from the Rockwell Socket,  **
  **                 performs checksum validation on the packet, strips off   **
  **                 the packet header and loads the resulting data in the    **
  **                 iData array (class property).                            **
  **                                                                          **
  **  Notes........: This implements the packet parser for Rockwell Trans-    **
  **                 action Link Version 6.1.                                 **
  **                                                                          **
  *****************************************************************************/
  public void iRead() {
    int iCheckSum;
    int iMaxLength = 1024;
    int iStatus = -1;
    int i=0;
    int iHdrLenRecvd = 0;
    int iUserDataLen = 0;
    int iLenRecvd = 0;
    boolean bGetMsgHdr = true;
    boolean bGetData = true;

    //--- Read message header from socket
    while (bGetMsgHdr) {
      try {
        iLenRecvd = in.read(cMsgHdr, iHdrLenRecvd, CON_SPC_MSG_HDR_SIZE - iHdrLenRecvd);
      } catch (IOException e) { oLOG.LogIOException(sLogKey, e); }
      if (iLenRecvd <= 0) oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "Error reading message header...");
      iHdrLenRecvd += iLenRecvd;
      if (iHdrLenRecvd == CON_SPC_MSG_HDR_SIZE) bGetMsgHdr = false;
    }
    if (cMsgHdr[1] != (byte) CON_SPC_DATA_PACKET) {
      if (cMsgHdr[1] != (byte) CON_SPC_KEEP_ALIVE_PACKET) oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "CTI_Read:  Invalid packet type.");
      else oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "CTI_Read:  Keep Alive Packet received.");
    }
    for (i=0;i<=iHdrLenRecvd;i++) {
      iMsgHdr[i] = ((cMsgHdr[i] >= 0) ? cMsgHdr[i] : 256 + cMsgHdr[i]);
    }
    //--- Validate the Message Checksum
    iCheckSum = ~(iMsgHdr[1] + iMsgHdr[2] + iMsgHdr[3])+1;
    iCheckSum = ((iCheckSum >= 0) ? iCheckSum : 256 + iCheckSum);
    if (iCheckSum != iMsgHdr[0]) {
      oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "Checksum Err: i=" + iCheckSum + " O:" + iMsgHdr[0] + " 1:"+ iMsgHdr[1] + " 2:" + iMsgHdr[2] + " 3:" + iMsgHdr[3]);
    }

    //--- Get the data portion of the packet
    iUserDataLen = (iMsgHdr[2] * 0xff) + iMsgHdr[3];
    iDataLenRecvd = 0;
    while (bGetData == true) {
      try {
        iLenRecvd = in.read(cData, iDataLenRecvd, iUserDataLen - iDataLenRecvd);
      } catch (IOException e) { oLOG.LogIOException(sLogKey, e); }
      if (iLenRecvd <= 0) oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "CTI_Read:  Error reading user data.");
      iDataLenRecvd += iLenRecvd;
      if (iDataLenRecvd == iUserDataLen) bGetData = false;
    }
    for (i=0;i<=iDataLenRecvd;i++) {
      iData[i] = ((cData[i] >=0) ? cData[i] : 256 + cData[i]);
    }
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: CallEstablished                                          **
  **                                                                          **
  **  Description..: This method parses the Call Established Message from the **
  **                 Rockwell Spectrum. This message provides DNIS, ANI, etc. **
  **                                                                          **
  **  Notes........: This implements the packet parser for Rockwell Trans-    **
  **                 action Link Version 6.1.                                 **
  **                                                                          **
  *****************************************************************************/
  public void CallEstablished() {
    int iError = 0;
    int iElement = 0;
    int iByte = 1;                            // Start with byte 1 (byte 0 is message type)
    int i = 0;
    int iLWN = 0;
    int iType = 0;
    long CallID = 0L;
    String sLog = "";
    String sANI = "";
    String sDNIS = "";

    //--- Loop for each byte in the input buffer
    while (iByte <= iDataLenRecvd) {
      switch(iData[iByte]) {
        case CON_SPC_ELM_CALL_ID: {								// Uniquely identifies this call
          CallID = iData[iByte+2]*16777216 + iData[iByte+3]*65536 + iData[iByte+4]*256 + iData[iByte+5];
          break;
        }
        case CON_SPC_ELM_TERMINATING_LOGICAL_WORK_NUMBER: {
          iLWN = (iData[iByte+2]*256) + iData[iByte+3];
          break;
        }
        case CON_SPC_ELM_TERMINATING_POSITION:
          break;
        case CON_SPC_ELM_TERMINATING_TRUNK_PORT_ID:
          break;
        case CON_SPC_ELM_TERMINATING_TRUNK_GROUP_NUMBER:
          break;
        case CON_SPC_ELM_LOGICAL_WORKSTATION_NUMBER:
          break;
        case CON_SPC_ELM_PREVIOUS_POSITION:
          break;
        case CON_SPC_ELM_PREVIOUS_TRUNK_PORT_ID:
          break;
        case CON_SPC_ELM_PREVIOUS_TRUNK_GROUP_NUMBER:
          break;
        case CON_SPC_ELM_ORIGINATING_LOGICAL_WORK_NUMBER:
          break;
        case CON_SPC_ELM_ORIGINATING_POSITION:
          break;
        case CON_SPC_ELM_ORIGINATING_TRUNK_PORT_ID:
          break;
        case CON_SPC_ELM_ORIGINATING_TRUNK_GROUP_NUMBER:
          break;
        case CON_SPC_ELM_CALLING_PARTY_NUMBER: {				// The number of the person calling
          sANI = "";
          for (i = 0; i < iData[iByte + 1]; i++) {
            sANI += (char) iData[iByte+5+i];
          }
          iType = iData[iByte+3];
          break;
        }
        case CON_SPC_ELM_DNIS_NUMBER: {
          sDNIS = "";
          for (i = 0; i < iData[iByte + 1]; i++) {
            sDNIS += (char) iData[iByte + 2 + i];
          }
          break;
        }
        case CON_SPC_ELM_APPLICATION_REQUEST_NUMBER:
          break;
        case CON_SPC_ELM_PACING_INFORMATION:
          break;
        case CON_SPC_ELM_APPLICATION_PACING_INFORMATION:
          break;
      }
      iByte += iData[iByte + 1] + 2;
    }
    this.Update(CallID, sANI, sDNIS, iLWN, iType);
  }

  /**
    This method parses the Call Cleared Message from the Rockwell Spectrum.
    This message indicates end of call.
  */
  public void CallCleared() {
    long CallID = 0;
    String sLog = "";
    int iByte = 1;                          // Start with byte 1 (byte 0 is message type)
    CallID = (long)0;

    //--- Loop for each byte in the input buffer
    while (iByte <= iDataLenRecvd) {
      switch(iData[iByte]) {
        case CON_SPC_ELM_CALL_ID: {         // Uniquely identifies this call
          CallID = iData[iByte+2]*16777216 + iData[iByte+3]*65536 + iData[iByte+4]*256 + iData[iByte+5];

          sLog += " CALLID=" + CallID;
          break;
        }
        case CON_SPC_ELM_COMPLETION_REASON:
          break;
      }
      iByte += iData[iByte + 1] + 2;
    }

    this.Delete(CallID);
  }

  /**
    This method parses the Subscriber Information message that may/may not
    contain a more specific DNIS.
  */
  public void SubscriberInformation() {
    long CallID = 0;
    String sLog = "";
    int iByte = 1;                          // Start with byte 1 (byte 0 is message type)
    int i = 0;
    CallID = (long)0;
    String sDNIS = "";

    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "[SUBSCRIBER ]" + sLog);

    //--- Loop for each byte in the input buffer
    while (iByte <= iDataLenRecvd) {
      switch(iData[iByte]) {
        case CON_SPC_ELM_CALL_ID: {         // Uniquely identifies this call
          CallID = iData[iByte+2]*16777216 + iData[iByte+3]*65536 + iData[iByte+4]*256 + iData[iByte+5];

          sLog += " CALLID=" + CallID;
          break;
        }
        //case CON_SPC_ELM_ACCOUNT_INFORMATION: {
        //  sDNIS = "";
        //  for (i = 0; i < iData[iByte + 1]; i++) {
        //    sDNIS += (char) iData[iByte + 2 + i];
        //  }
        //  sLog += " DNIS=" + sDNIS;
        //  break;
        // }
      }
      iByte += iData[iByte + 1] + 2;
    }
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "[SUBSCRIBER ]" + sLog);
    this.Update(CallID, "", sDNIS, 0, 0);    //Update call record with new DNIS
  }

  /**
    This method passes call information to the client thread.  It is called by
    routines that provide call information such as CallEstablished and
    SubscriberInformation.  The client thread provides the logic that determines
    when/how call information is passed to the desktop.
  */
  public void Update(long CallID, String ANI, String DNIS, int LWN, int Type) {
    int NumberOfClients = oCTI.ClientConnections.size();
    for(int iCon = 0; iCon < NumberOfClients; iCon++) {
      ClientThread oCon = (ClientThread) oCTI.ClientConnections.elementAt(iCon);
      if (oCon.iLWN == LWN) {
        oCon.CallID = CallID;
        oCon.DNIS = DNIS;
        oCon.ANI = ANI;
        oCon.Type = Type;
        oCon.ScreenPOP();
        break;
      }
    }
  }

  /**
    This method clears call information in the client thread.  It is called by
    routines that terminate the physical call such as CallCleared.  The client
    thread determines when/how the cleared message is passed to the desktop.
    NOTE:  The only information passed with these types of messages is the
    CallID, so the client threads are compared with it instead of LWN.
  */
  public void Delete(long CallID) {
    int NumberOfClients = oCTI.ClientConnections.size();
    for(int iCon=0; iCon < NumberOfClients; iCon++) {
      ClientThread oCon = (ClientThread) oCTI.ClientConnections.elementAt(iCon);
      if ((CallID != 0) && (oCon.CallID == CallID)) {
        oCon.CallClear();
        break;
      }
    }
  }

}

