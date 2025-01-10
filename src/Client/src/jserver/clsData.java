
package jserver;
import java.lang.*;
import java.util.*;
import com.borland.dx.sql.dataset.*;
import com.borland.dx.dataset.*;

public class clsData {
  public String SpectrumIP;
  public int SpectrumPort;
  private final String sLogKey = "[clsData   ]";
  private Database dbServer = new Database();
  private QueryDataSet querySpectrums = new QueryDataSet();
  private QueryDataSet queryAPP = new QueryDataSet();
  private QueryDataSet queryLWN = new QueryDataSet();
  private Column colLWN = new Column();
  private Column colDNIS = new Column();
  private clsLogFile oLOG;
  private Vector vApps;                   // Application Vector - holds application data
  private Vector vUsers;                  // User Vector - holds user/lwn data

  public clsData(clsLogFile oLogFile) {
    oLOG = oLogFile;
    try {
      jbInit();

      querySpectrums.refresh();                            // Refresh the query
      oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Retrieving Spectrum information.  Found " + querySpectrums.rowCount() + " records...");
      this.SpectrumIP = querySpectrums.getString("SpectrumIP");
      this.SpectrumPort = querySpectrums.getInt("SpectrumPort");

      vApps = new Vector();                 // Instantiate the application vector
      oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Application vector initialized.");
      this.ReloadAppVector();

      vUsers = new Vector();                // Instantiate the user vector
      oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "User vector initialized.");
      this.ReloadUserVector();

    }
    catch(Exception e) {
      e.printStackTrace();
    }
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

    oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Deleting Application Vector on normal shutdown.");
    this.vApps.removeAllElements();

    oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Deleting User Vector on normal shutdown.");
    this.vUsers.removeAllElements();

    try {
      queryLWN.close();
      querySpectrums.close();
      queryAPP.close();
      dbServer.commit();
      dbServer.closeConnection();
    } catch (Exception Ex) {;}

  }

  private void jbInit() throws Exception {
    dbServer.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor("jdbc:odbc:JServer", "sa", "", false, "sun.jdbc.odbc.JdbcOdbcDriver"));

    querySpectrums.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    querySpectrums.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT Spectrum.ID,Spectrum.SpectrumEnabled,Spectrum.SpectrumName,Spectrum.SpectrumIP,Spectrum.SpectrumPort,Spectrum.SpectrumStatus FROM Spectrum", null, true, Load.ALL));

    queryAPP.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    queryAPP.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT DNIS_LIST.DNIS,DNIS_LIST.APPLICATION,DNIS_LIST.ACTIVE,APPLICATIONS.APPLICATION,APPLICATIONS.AP" +
      "PTYPE,APPLICATIONS.EXECUTEOBJECT,APPLICATIONS.SERVERNAME,APPLICATIONS.\"DATABASE\",APPLICATIONS.FORMNAM" +
      "E FROM DNIS_LIST,APPLICATIONS WHERE APPLICATIONS.APPLICATION=DNIS_LIST.APPLICATION " +
      "ORDER BY DNIS_List.DNIS", null, true, Load.ALL));
    colDNIS.setColumnName("DNIS");
    colDNIS.setDataType(com.borland.dx.dataset.Variant.STRING);
    colDNIS.setPrecision(7);
    colDNIS.setRowId(true);
    colDNIS.setScale(0);
    colDNIS.setTableName("DNIS_LIST");
    colDNIS.setServerColumnName("DNIS");
    colDNIS.setSqlType(-9);
    queryAPP.setColumns(new Column[] {colDNIS});

    colLWN.setColumnName("LWN");
    colLWN.setDataType(com.borland.dx.dataset.Variant.STRING);
    colLWN.setPrecision(4);
    colLWN.setRowId(true);
    colLWN.setScale(0);
    colLWN.setSchemaName("dbo");
    colLWN.setTableName("ExtensionList");
    colLWN.setServerColumnName("LWN");
    colLWN.setSqlType(-9);
    queryLWN.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    queryLWN.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT ExtensionList.LWN,ExtensionList.FCN FROM CTI.dbo.ExtensionList", null, true, Load.ALL));
    queryLWN.setColumns(new Column[] {colLWN});
 }


  public void ReloadUserVector() {
    String sTmp;
    int iRec = 0;
    vUsers.removeAllElements();
    try {
      queryLWN.refresh();
      oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Retrieving User Information.  Found " + queryLWN.rowCount() + " records...");
      if (queryLWN.rowCount() > 0) {
        for (iRec = 0; iRec < queryLWN.rowCount(); iRec++) {
          clsLwnData oUserObj = new clsLwnData();
          sTmp = queryLWN.getString("LWN");
          oUserObj.LWN = Integer.parseInt(sTmp);
          oUserObj.FCN = queryLWN.getString("FCN");
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Retrieved User Record:  FCN=" + oUserObj.FCN.trim() + " LWN=" + oUserObj.LWN);
          vUsers.addElement (oUserObj);
          queryLWN.next();
        }
      } else {
        oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "No User Records Found.");
      }
      oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Added " + iRec + " user records.");
    }
    catch(Exception e) { oLOG.LogException(sLogKey, e); }
  }

  /*****************************************************************************
  **                                                                          **
  **  Method.......: GetLWNfromFCN                                            **
  **                                                                          **
  **  Description..: This method simply retrieves the Rockwell Logical Work-  **
  **                 station Number (LWN) from the Extensions table for the   **
  **                 specified Friendly Computer Name (FCN).  The FCN is set  **
  **                 to the TCP/IP Host Name of the Client Computer.  It may  **
  **                 be overriden by the client by passing the FCN message    **
  **                 to retain compatibility with older VB-based clients.     **
  **                                                                          **
  **  Notes........: All of the database routines in this application make    **
  **                 use of the Borland JBuilder DataExpress Architecture.    **
  **                                                                          **
  *****************************************************************************/
  public synchronized int GetLWNfromFCN(String sFCN) {
    int iLWN = 0;
    for (int iUser=0; iUser < this.vUsers.size(); iUser++) {
      clsLwnData oUser;
      oUser = (clsLwnData) this.vUsers.elementAt(iUser);
      if (oUser.FCN.trim().compareTo(sFCN.trim()) == 0) {
        iLWN = oUser.LWN;
        break;
      }
    }
    return iLWN;
  }


  public void ReloadAppVector() {
    int iRec = 0;

    //--- Create an object for each Active DNIS in the DNIS table
    vApps.removeAllElements();
    try {
      queryAPP.refresh();
      oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Retrieving Application Information.  Found " + queryAPP.rowCount() + " records...");
      if (queryAPP.rowCount() > 0) {
        for (iRec = 0; iRec < queryAPP.rowCount(); iRec++) {
          clsAppData oAppObj = new clsAppData();
          oAppObj.DNIS = queryAPP.getString("DNIS");
          oAppObj.AppCode = queryAPP.getString("Application");
          oAppObj.Active = queryAPP.getBoolean("Active");
          oAppObj.Database = queryAPP.getString("Database");
          oAppObj.ExecObject = queryAPP.getString("ExecuteObject");
          oAppObj.Server = queryAPP.getString("ServerName");
          oAppObj.FormName = queryAPP.getString("FormName");
          oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Retrieved App Record:  DNIS=" + oAppObj.DNIS.trim() + " App=" + oAppObj.AppCode.trim());
          vApps.addElement (oAppObj);
          queryAPP.next();
        }
      } else {
        oLOG.LogWrite(oLOG.LOG_ERROR, sLogKey + "No Application Records Found.");
      }
      oLOG.LogWrite(oLOG.LOG_DEBUG, sLogKey + "Added " + iRec + " records.");
    }
    catch(Exception e) { oLOG.LogException(sLogKey, e); }
  }

  public synchronized String GetAppByDNIS(String sDNIS) {
    String sAppCode = "";
    for (int iApp=0; iApp < this.vApps.size(); iApp++) {
      clsAppData oA;
      oA = (clsAppData) this.vApps.elementAt(iApp);
      if (oA.DNIS.trim().compareTo(sDNIS.trim()) == 0) {
        sAppCode = oA.ExecObject.trim() + "-" + oA.Server.trim() + "-" + oA.Database.trim() + "-" + oA.FormName.trim();
        break;
      }
    }
    return sAppCode;
  }

}

class clsAppData {
  public long AppID = 0;
  public boolean Active = false;
  public String DNIS = "";
  public String AppCode = "";
  public String ExecObject = "";
  public String Server = "";
  public String Database = "";
  public String FormName = "";

  public clsAppData() {
  }

}

class clsLwnData {
  public int LWN = 0;
  public String FCN = "";

  public clsLwnData() {
  }

}