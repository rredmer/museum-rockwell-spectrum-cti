package jserver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.borland.dx.sql.dataset.*;
import com.borland.dx.dataset.*;
import com.borland.dbswing.*;

public class clsUI extends JFrame {
  JPanel contentPane;
  JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem menuFileRefresh = new JMenuItem();
  JMenu menuHelp = new JMenu();
  JMenuItem menuHelpAbout = new JMenuItem();
  JLabel statusBar = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JTabbedPane jTabMain = new JTabbedPane();
  JPanel jPnlSpectrum = new JPanel();
  JPanel jPnlClients = new JPanel();
  JPanel jPnlLWN = new JPanel();
  JPanel jPnlOLE = new JPanel();
  Database dbServer = new Database();
  QueryDataSet qrySpectrum = new QueryDataSet();
  XYLayout xYLayout2 = new XYLayout();
  TableScrollPane tblScrollSpectrum = new TableScrollPane();
  JdbTable jdbTableSpectrum = new JdbTable();
  JdbNavToolBar jdbNavSpectrum = new JdbNavToolBar();
  Column column1 = new Column();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  JdbTable jdbTableClients = new JdbTable();
  JdbNavToolBar jdbNavClients = new JdbNavToolBar();
  TableScrollPane tblScrollClients = new TableScrollPane();
  QueryDataSet qryClients = new QueryDataSet();
  Column column2 = new Column();
  QueryDataSet qryLWN = new QueryDataSet();
  JdbTable jdbTableLWN = new JdbTable();
  JdbNavToolBar jdbNavLWN = new JdbNavToolBar();
  TableScrollPane tblScrollLWN = new TableScrollPane();
  Column column3 = new Column();
  QueryDataSet qryOLE = new QueryDataSet();
  Column column4 = new Column();
  JdbTable jdbTableOLE = new JdbTable();
  JdbNavToolBar jdbNavOLE = new JdbNavToolBar();
  TableScrollPane tblScrollOLE = new TableScrollPane();
  JPanel jPnlDNIS = new JPanel();
  XYLayout xYLayout7 = new XYLayout();
  QueryDataSet qryDNIS = new QueryDataSet();
  JdbTable jdbTableDNIS = new JdbTable();
  JdbNavToolBar jdbNavDNIS = new JdbNavToolBar();
  TableScrollPane tblScrollDNIS = new TableScrollPane();
  Column column5 = new Column();
  clsLogFile oLOG;
  clsCTIServer oCTI;
  clsSpectrum oSPECTRUM;
  Column column6 = new Column();
  Column column7 = new Column();
  Column column8 = new Column();
  Column column9 = new Column();
  Column column10 = new Column();
  Column column11 = new Column();
  Column column12 = new Column();
  Column column13 = new Column();
  Column column14 = new Column();
  Column column15 = new Column();
  Column column16 = new Column();
  Column column17 = new Column();
  Column column18 = new Column();
  Column column19 = new Column();
  Column column20 = new Column();
  Column column21 = new Column();
  Column column22 = new Column();
  Column column23 = new Column();
  Column column24 = new Column();
  JButton jButton1 = new JButton();
  JMenuItem menuFileExit = new JMenuItem();
  String sLogKey = "[clsUI     ]";

  //Construct the frame
  public clsUI(clsLogFile oLogFile, clsCTIServer oCTIServer, clsSpectrum oSpectrumObj) {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    oLOG = oLogFile;
    oCTI = oCTIServer;
    oSPECTRUM = oSpectrumObj;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    oLOG.LogWrite(oLOG.LOG_INFO, sLogKey + "Initializing User Interface in clsUI...");
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(xYLayout1);
    this.setSize(new Dimension(608, 315));
    this.setTitle("JServer 3.50");
    statusBar.setText(" ");
    menuFile.setText("File");
    menuFileRefresh.setText("Refresh");
    menuFileRefresh.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        FileRefresh_actionPerformed(e);
      }
    });
    menuHelp.setText("Help");
    menuHelpAbout.setText("About");
    menuHelpAbout.addActionListener(new ActionListener()  {

      public void actionPerformed(ActionEvent e) {
        helpAbout_actionPerformed(e);
      }
    });
    dbServer.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor("jdbc:odbc:JServer", "sa", "", false, "sun.jdbc.odbc.JdbcOdbcDriver"));
    qrySpectrum.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    qrySpectrum.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT Spectrum.ID,Spectrum.SpectrumEnabled,Spectrum.SpectrumName,Spectrum.SpectrumIP,Spectrum.Spectr" +
      "umPort,Spectrum.SpectrumStatus FROM CTI.dbo.Spectrum", null, true, Load.ALL));
    jPnlSpectrum.setLayout(xYLayout2);
    jdbTableSpectrum.setDataSet(qrySpectrum);
    jdbNavSpectrum.setDataSet(qrySpectrum);
    column1.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column1.setColumnName("ID");
    column1.setDataType(com.borland.dx.dataset.Variant.INT);
    column1.setRowId(true);
    column1.setSchemaName("dbo");
    column1.setTableName("Spectrum");
    column1.setServerColumnName("ID");
    column1.setSqlType(4);
    jPnlClients.setLayout(xYLayout3);
    jPnlLWN.setLayout(xYLayout4);
    jPnlOLE.setLayout(xYLayout5);
    jdbTableClients.setDataSet(qryClients);
    jdbNavClients.setDataSet(qryClients);
    qryClients.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    qryClients.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT ClientList.LWN,ClientList.FCN,ClientList.TCPIndex,ClientList.Application,ClientList.DNIS,Clien" +
      "tList.CallID,ClientList.Status FROM CTI.dbo.ClientList", null, true, Load.ALL));
    column2.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column2.setColumnName("FCN");
    column2.setDataType(com.borland.dx.dataset.Variant.STRING);
    column2.setPrecision(50);
    column2.setRowId(true);
    column2.setScale(0);
    column2.setSchemaName("dbo");
    column2.setTableName("ClientList");
    column2.setServerColumnName("FCN");
    column2.setSqlType(-9);
    qryLWN.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    qryLWN.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT ExtensionList.LWN,ExtensionList.FCN FROM CTI.dbo.ExtensionList", null, true, Load.ALL));
    jdbTableLWN.setDataSet(qryLWN);
    jdbNavLWN.setDataSet(qrySpectrum);
    jdbNavLWN.setFocusedDataSet(qryLWN);
    column3.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column3.setColumnName("LWN");
    column3.setDataType(com.borland.dx.dataset.Variant.STRING);
    column3.setPrecision(4);
    column3.setRowId(true);
    column3.setScale(0);
    column3.setSchemaName("dbo");
    column3.setTableName("ExtensionList");
    column3.setServerColumnName("LWN");
    column3.setSqlType(-9);
    qryOLE.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    qryOLE.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT Applications.Application,Applications.AppType,Applications.ExecuteObject,Applications.ServerNa" +
      "me,Applications.\"Database\",Applications.FormName FROM CTI.dbo.Applications", null, true, Load.ALL));
    column4.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column4.setColumnName("Application");
    column4.setDataType(com.borland.dx.dataset.Variant.STRING);
    column4.setPrecision(25);
    column4.setRowId(true);
    column4.setScale(0);
    column4.setSchemaName("dbo");
    column4.setTableName("Applications");
    column4.setServerColumnName("Application");
    column4.setSqlType(-9);
    jdbTableOLE.setDataSet(qryOLE);
    jdbNavOLE.setDataSet(qrySpectrum);
    jdbNavOLE.setFocusedDataSet(qryOLE);
    jPnlDNIS.setLayout(xYLayout7);
    qryDNIS.setMetaDataUpdate(MetaDataUpdate.TABLENAME+MetaDataUpdate.PRECISION+MetaDataUpdate.SCALE+MetaDataUpdate.SEARCHABLE);
    qryDNIS.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(dbServer, "SELECT DNIS_List.DNIS,DNIS_List.Application,DNIS_List.Active FROM " +
      "CTI.dbo.DNIS_List", null, true, Load.ALL));
    jdbTableDNIS.setDataSet(qryDNIS);
    jdbNavDNIS.setDataSet(qrySpectrum);
    jdbNavDNIS.setFocusedDataSet(qryDNIS);
    column5.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column5.setColumnName("DNIS");
    column5.setDataType(com.borland.dx.dataset.Variant.STRING);
    column5.setPrecision(7);
    column5.setRowId(true);
    column5.setScale(0);
    column5.setSchemaName("dbo");
    column5.setTableName("DNIS_List");
    column5.setServerColumnName("DNIS");
    column5.setSqlType(-9);
    column6.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column6.setColumnName("SpectrumName");
    column6.setDataType(com.borland.dx.dataset.Variant.STRING);
    column6.setPrecision(15);
    column6.setScale(0);
    column6.setSchemaName("dbo");
    column6.setTableName("Spectrum");
    column6.setServerColumnName("SpectrumName");
    column6.setSqlType(-9);
    column7.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column7.setColumnName("SpectrumEnabled");
    column7.setDataType(com.borland.dx.dataset.Variant.STRING);
    column7.setPrecision(1);
    column7.setScale(0);
    column7.setSchemaName("dbo");
    column7.setTableName("Spectrum");
    column7.setServerColumnName("SpectrumEnabled");
    column7.setSqlType(-9);
    column8.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column8.setColumnName("SpectrumIP");
    column8.setDataType(com.borland.dx.dataset.Variant.STRING);
    column8.setPrecision(15);
    column8.setScale(0);
    column8.setSchemaName("dbo");
    column8.setTableName("Spectrum");
    column8.setServerColumnName("SpectrumIP");
    column8.setSqlType(-9);
    column9.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column9.setColumnName("SpectrumPort");
    column9.setDataType(com.borland.dx.dataset.Variant.INT);
    column9.setSchemaName("dbo");
    column9.setTableName("Spectrum");
    column9.setServerColumnName("SpectrumPort");
    column9.setSqlType(4);
    column10.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column10.setColumnName("SpectrumStatus");
    column10.setDataType(com.borland.dx.dataset.Variant.STRING);
    column10.setPrecision(50);
    column10.setScale(0);
    column10.setSchemaName("dbo");
    column10.setTableName("Spectrum");
    column10.setServerColumnName("SpectrumStatus");
    column10.setSqlType(-9);
    column11.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column11.setColumnName("LWN");
    column11.setDataType(com.borland.dx.dataset.Variant.STRING);
    column11.setPrecision(10);
    column11.setScale(0);
    column11.setSchemaName("dbo");
    column11.setTableName("ClientList");
    column11.setServerColumnName("LWN");
    column11.setSqlType(-9);
    column12.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column12.setColumnName("TCPIndex");
    column12.setDataType(com.borland.dx.dataset.Variant.SHORT);
    column12.setSchemaName("dbo");
    column12.setTableName("ClientList");
    column12.setServerColumnName("TCPIndex");
    column12.setSqlType(5);
    column13.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column13.setColumnName("Application");
    column13.setDataType(com.borland.dx.dataset.Variant.STRING);
    column13.setPrecision(25);
    column13.setScale(0);
    column13.setSchemaName("dbo");
    column13.setTableName("ClientList");
    column13.setServerColumnName("Application");
    column13.setSqlType(-9);
    column14.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column14.setColumnName("DNIS");
    column14.setDataType(com.borland.dx.dataset.Variant.STRING);
    column14.setPrecision(10);
    column14.setScale(0);
    column14.setSchemaName("dbo");
    column14.setTableName("ClientList");
    column14.setServerColumnName("DNIS");
    column14.setSqlType(-9);
    column15.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column15.setColumnName("CallID");
    column15.setDataType(com.borland.dx.dataset.Variant.STRING);
    column15.setPrecision(18);
    column15.setScale(0);
    column15.setSchemaName("dbo");
    column15.setTableName("ClientList");
    column15.setServerColumnName("CallID");
    column15.setSqlType(-9);
    column16.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column16.setColumnName("Status");
    column16.setDataType(com.borland.dx.dataset.Variant.STRING);
    column16.setPrecision(10);
    column16.setScale(0);
    column16.setSchemaName("dbo");
    column16.setTableName("ClientList");
    column16.setServerColumnName("Status");
    column16.setSqlType(-9);
    column17.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column17.setColumnName("FCN");
    column17.setDataType(com.borland.dx.dataset.Variant.STRING);
    column17.setPrecision(10);
    column17.setScale(0);
    column17.setSchemaName("dbo");
    column17.setTableName("ExtensionList");
    column17.setServerColumnName("FCN");
    column17.setSqlType(-9);
    column18.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column18.setColumnName("AppType");
    column18.setDataType(com.borland.dx.dataset.Variant.INT);
    column18.setSchemaName("dbo");
    column18.setTableName("Applications");
    column18.setServerColumnName("AppType");
    column18.setSqlType(4);
    column19.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column19.setColumnName("ExecuteObject");
    column19.setDataType(com.borland.dx.dataset.Variant.STRING);
    column19.setPrecision(50);
    column19.setScale(0);
    column19.setSchemaName("dbo");
    column19.setTableName("Applications");
    column19.setServerColumnName("ExecuteObject");
    column19.setSqlType(-9);
    column20.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column20.setColumnName("ServerName");
    column20.setDataType(com.borland.dx.dataset.Variant.STRING);
    column20.setPrecision(50);
    column20.setScale(0);
    column20.setSchemaName("dbo");
    column20.setTableName("Applications");
    column20.setServerColumnName("ServerName");
    column20.setSqlType(-9);
    column21.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column21.setColumnName("Database");
    column21.setDataType(com.borland.dx.dataset.Variant.STRING);
    column21.setPrecision(50);
    column21.setScale(0);
    column21.setSchemaName("dbo");
    column21.setTableName("Applications");
    column21.setServerColumnName("Database");
    column21.setSqlType(-9);
    column22.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column22.setColumnName("FormName");
    column22.setDataType(com.borland.dx.dataset.Variant.STRING);
    column22.setPrecision(50);
    column22.setScale(0);
    column22.setSchemaName("dbo");
    column22.setTableName("Applications");
    column22.setServerColumnName("FormName");
    column22.setSqlType(-9);
    column23.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column23.setColumnName("Application");
    column23.setDataType(com.borland.dx.dataset.Variant.STRING);
    column23.setPrecision(25);
    column23.setScale(0);
    column23.setSchemaName("dbo");
    column23.setTableName("DNIS_List");
    column23.setServerColumnName("Application");
    column23.setSqlType(-9);
    column24.setAlignment(com.borland.dx.text.Alignment.LEFT | com.borland.dx.text.Alignment.MIDDLE);
    column24.setColumnName("Active");
    column24.setDataType(com.borland.dx.dataset.Variant.BOOLEAN);
    column24.setSchemaName("dbo");
    column24.setTableName("DNIS_List");
    column24.setServerColumnName("Active");
    column24.setSqlType(-7);
    jButton1.setText("Capture");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    menuFileExit.addActionListener(new ActionListener()  {

      public void actionPerformed(ActionEvent e) {
        fileExit_actionPerformed(e);
      }
    });
    menuFileExit.setText("Exit");
    menuFile.add(menuFileRefresh);
    menuFile.add(menuFileExit);
    menuHelp.add(menuHelpAbout);
    menuBar1.add(menuFile);
    menuBar1.add(menuHelp);
    this.setJMenuBar(menuBar1);
    qryDNIS.setColumns(new Column[] {column5, column23, column24});
    qryOLE.setColumns(new Column[] {column4, column18, column19, column20, column21, column22});
    qryLWN.setColumns(new Column[] {column3, column17});
    qryClients.setColumns(new Column[] {column11, column2, column12, column13, column14, column15, column16});
    qrySpectrum.setColumns(new Column[] {column1, column7, column6, column8, column9, column10});
    contentPane.add(statusBar, new XYConstraints(0, 535, 572, -1));
    contentPane.add(jTabMain, new XYConstraints(4, 0, 599, 257));
    jTabMain.add(jPnlSpectrum, "Spectrums");
    jPnlSpectrum.add(tblScrollSpectrum, new XYConstraints(5, 46, 581, 227));
    jPnlSpectrum.add(jdbNavSpectrum, new XYConstraints(1, 1, 368, 41));
    jTabMain.add(jPnlClients, "Clients");
    jPnlClients.add(jdbNavClients, new XYConstraints(0, 0, 368, 41));
    jPnlClients.add(tblScrollClients, new XYConstraints(4, 45, 581, 178));
    jTabMain.add(jPnlLWN, "LWNs");
    jPnlLWN.add(jdbNavLWN, new XYConstraints(0, 0, 368, 41));
    jPnlLWN.add(tblScrollLWN, new XYConstraints(4, 45, 581, 176));
    jTabMain.add(jPnlOLE, "Applications");
    jPnlOLE.add(jdbNavOLE, new XYConstraints(0, 0, 368, 41));
    jPnlOLE.add(tblScrollOLE, new XYConstraints(4, 45, 581, 177));
    jTabMain.add(jPnlDNIS, "DNIS");
    jPnlDNIS.add(jdbNavDNIS, new XYConstraints(0, 0, 368, 41));
    jPnlDNIS.add(tblScrollDNIS, new XYConstraints(4, 45, 581, 175));
    contentPane.add(jButton1, new XYConstraints(4, 261, 107, 40));
    tblScrollDNIS.getViewport().add(jdbTableDNIS, null);
    tblScrollOLE.getViewport().add(jdbTableOLE, null);
    tblScrollLWN.getViewport().add(jdbTableLWN, null);
    tblScrollClients.getViewport().add(jdbTableClients, null);
    tblScrollSpectrum.getViewport().add(jdbTableSpectrum, null);
  }

  //File | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
    oLOG.LogWrite(oLOG.LOG_INFO, "*********************************************************");
    oLOG.LogWrite(oLOG.LOG_INFO, "**                STARTING NORMAL SHUTDOWN             **");
    oLOG.LogWrite(oLOG.LOG_INFO, "*********************************************************");

    oLOG.LogWrite(oLOG.LOG_INFO, "Attempting to shutdown Spectrum Client...");
    oSPECTRUM.Dispose();      // Dispose of Spectrum Object
    try {
      oSPECTRUM.interrupt();
      oSPECTRUM.join(2000);
    } catch (InterruptedException Ex) {;}


   oCTI.Dispose();           // Dispose of CTI Server Object
   try {
      oCTI.interrupt();
      oCTI.join(1000);
   } catch (InterruptedException Ex) {;}

    oLOG.LogWrite(oLOG.LOG_INFO, "Closing database on normal shutdown.");
    try {
      dbServer.commit();
      dbServer.closeConnection();
    } catch (Exception Ex) {;}

    oCTI.oData.Dispose();

    oLOG.Dispose();           // Dispose of Log File Object
    System.exit(0);
  }

  //Help | About action performed
  public void helpAbout_actionPerformed(ActionEvent e) {
    clsUI_AboutBox dlg = new clsUI_AboutBox(this, oLOG);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    }
  }

  //Capture Button - This button Captures all Rockwell Spectrum Traffic to a file.
  void jButton1_actionPerformed(ActionEvent e) {

  }


  void FileRefresh_actionPerformed(ActionEvent e) {
    //oCTI.oAppData.Reload();
  }

}