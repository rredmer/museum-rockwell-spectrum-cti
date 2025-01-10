
/* TABLE:   CTI_CLIENT_LIST
 *
 * There will be one record in this table for every Rockwell Spectrum handset.
 * ( < 2000 ? )
 *
 * This table provides a list of computers and handsets that are available for 
 * CTI.  It is used to correlate the telephone handset identifier (LWN) with 
 * the computer name (FCN) for telephony messaging.  The LWN and FCN codes
 * are unique to each location.
 *
 * The Primary Key for this table is (LOCATION, LWN).
 */
CREATE TABLE CTI_CLIENT_LIST (

	/* The LOCATION code identifies the location for the client (OPM, R2, MONT).
	 */
	LOCATION	VARCHAR(10) NOT NULL,

	/* The LWN identifies the Logical WorkStation Number, which is a unique number
	 * assigned to each telehone handset (unique for each location).
	 */
	LWN		VARCHAR(10) NOT NULL,

	/* The FCN identifies the Friendly Computer Name, which is a unique code assigned
	 * to each desktop PC.  Specifically, it is the Microsoft Windows Computer Name or
	 * the Unix host name.  This name is used to identify the physical desktop where
	 * application messages, such as screen pop are to be routed based on the LWN sent
	 * by the ACD.
	 */
	FCN		VARCHAR(15) NOT NULL,

	/* The TCP Index identifies the index number of the thread providing the TCP 
	 * connection for the client.  This is useful for debugging the server.
	 */
	TCP_INDEX	NUMBER(2,0),

	/* The STATUS identifies the operational status of the client (Active, InActive).
	 * This field is NOT intended to provide a "real-time" status for the client, it is
	 * to be used to mark clients as inactive as a lock-out mechanism.
	 */
	STATUS		VARCHAR(10) NOT NULL
);


/* TABLE:   CTI_APPLICATION
 *
 * There will be one record in this table for every customer application, including seperate
 * records for testing and training instances.
 * ( < 500 ? )
 *
 * This table provides a list of applications that support telephony messaging.  It used
 * by the CTI Server to build command messages that are unique to each application for
 * screen pops, etc.
 *
 * The Primary Key for this table is (APPLICATION).
 */
CREATE TABLE CTI_APPLICATION (

	/* The APPLICATION code identifies a telephony-enabled application.
	 */
	APPLICATION	VARCHAR(25) NOT NULL,
	
	/* The APP_TYPE code identifies the type of application (LN=LotusNotes, GT=GT-X).
	 */
	APP_TYPE	VARCHAR(2) NOT NULL,
	
	/* The EXECUTE_OBJECT code identifies the object on the desktop to be executed to
	 * receive and respond to the telephony message.
	 */
	EXECUTE_OBJECT	VARCHAR(50) NOT NULL,

	/* The SERVER_NAME code identifies the server parameter for the EXECUTE_OBJECT, in the
	 * case of LotusNotes this is the Notes Server, in the case of GT-X it is the Service
	 * Object Server, in the case of the web it may be a http server.
	 */
	SERVER_NAME	VARCHAR(50),

	/* The DATABASE code identifies the database to utilize for telephony messaging.  This
	 * is currently only used for LotusNotes applications.
	 */
	DATABASE	VARCHAR(50),

	/* The FORM_NAME code identified the form to display for telephony messaging.  This is
	 * currently only used for LotusNotes applications.
	 */
	FORM_NAME	VARCHAR(50),

	/* The STATUS code identifies the operational status of the application (Active, InActive).
	 */
	STATUS		VARCHAR(10)

);


/* TABLE:   CTI_DNIS_LIST
 *
 * There will be one record in this table for every DNIS on every Spectrum in the company.
 * ( < 500 ?)
 *
 * This table provides a list of DNIS numbers that are associated with CTI enabled
 * applications.  The CTI Server uses the DNIS passed by the ACD to retrieve
 * application data for telephony messaging.
 *
 * The Primary Key for this table is (DNIS).
 */
CREATE TABLE CTI_DNIS_LIST (

	/* The DNIS code identifies the application that is required to support the customer
	 * call.  It is provided by the ACD with call routing information.
	 */
	DNIS		VARCHAR(5) NOT NULL,
	
	/* The APPLICATION code identifies a telephony-enabled application.  It is a foreign-key to 
	 * the CTI_APPLICATION Table.
	 */
	APPLICATION	VARCHAR(25) NOT NULL,

	/* The STATUS code identifies the operational status of the DNIS (Active, InActive).
	 */
	STATUS		VARCHAR(10)
);


/* TABLE:   CTI_SPECTRUM
 *
 * There will be one record in this table for each Rockwell Spectrum phone switch.
 * ( < 10 )
 *
 * This table provides a list of Rockwell Spectrum ACDs and the location codes serviced by
 * each.  It is used to retrieve communication parameters for each CTI Server.
 *
 * The Primary Key for this table is (LOCATION, SPECTRUM_NAME).
 */
CREATE TABLE CTI_SPECTRUM (

	/* The LOCATION code identifies the location for the Spectrum (OPM, R2, MONT).
	 */
	LOCATION	VARCHAR(10) NOT NULL,

	/* The SPECTRUM_NAME code identifies the name of the Spectrum ACD.
	 */
	SPECTRUM_NAME	VARCHAR(15) NOT NULL,

	/* The SPECTRUM_IP code identifies the physical IP-Addess of the Spectrum ACD.
	 */
	SPECTRUM_IP	VARCHAR(15) NOT NULL,

	/* The SPECTRUM_PORT code identifies the TCP/IP port to connect to on the Spectrum for
	 * telephony messaging.
	 */
	SPECTRUM_PORT	NUMBER(5,0),

	/* The STATUS code identifies the operational status of the SPECTRUM ACD (Active, InActive).
	 */
	STATUS		VARCHAR(10)
);

	

/* TABLE:   CTI_CALLHIST
 *
 * There will be one record for every call that is routed to a telephony-enabled application.
 * ( Measure based on current call volume - this will be a huge rapidly growing table that 
 *   should be rolled to archive monthly. )
 *
 * This table provides a call history for every call routed to a telephony-enabled application.
 *
 */
CREATE TABLE CTI_CALLHIST (

	/* The LOCATION code identifies the location for the Spectrum (OPM, R2, MONT).
	 */
	LOCATION	VARCHAR(10) NOT NULL,

	/* The SPECTRUM_NAME code identifies the name of the Spectrum ACD.
	 */
	SPECTRUM_NAME	VARCHAR(15) NOT NULL,

	/* The DNIS code identifies the application that is required to support the customer
	 * call.  It is provided by the ACD with call routing information.
	 */
	DNIS		VARCHAR(5) NOT NULL,
	
	/* The APPLICATION code identifies a telephony-enabled application.  It is a foreign-key to 
	 * the CTI_APPLICATION Table.
	 */
	APPLICATION	VARCHAR(25) NOT NULL,

	/* The LWN identifies the Logical WorkStation Number, which is a unique number
	 * assigned to each telehone handset (unique for each location).
	 */
	LWN		VARCHAR(10) NOT NULL,

	/* The FCN identifies the Friendly Computer Name, which is a unique code assigned
	 * to each desktop PC.  Specifically, it is the Microsoft Windows Computer Name or
	 * the Unix host name.  This name is used to identify the physical desktop where
	 * application messages, such as screen pop are to be routed based on the LWN sent
	 * by the ACD.
	 */
	FCN		VARCHAR(15) NOT NULL,

	/* The Call_ID code identifies the physical call identifier passed by the ACD.
	 */
	CALL_ID		NUMBER(15,0) NOT NULL,

	/* The ANI code identifies the calling party number (Caller ID) passed by the ACD.
	 */
	ANI		VARCHAR(20),

	/* The START_TIME code identifies the starting time of the call.
	 */
	START_TIME	DATE,

	/* The END_TIME code identifies the ending time of the call.
	 */
	END_TIME	DATE,

	/* The DURATION code identifies the duration of the call in milliseconds.
	 */
	DURATION	NUMBER(10, 0),

);		