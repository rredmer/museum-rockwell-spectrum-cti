ALTER TABLE [dbo].[Activity] DROP CONSTRAINT Activity_FK00
GO

ALTER TABLE [dbo].[ClientList] DROP CONSTRAINT ClientList_FK00
GO

ALTER TABLE [dbo].[Activity] DROP CONSTRAINT Activity_FK01
GO

ALTER TABLE [dbo].[ClientList] DROP CONSTRAINT ClientList_FK01
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[Action]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[Action]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[Activity]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[Activity]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[Applications]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[Applications]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[ClientList]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ClientList]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[DNIS_List]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[DNIS_List]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[ExtensionList]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ExtensionList]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[Spectrum]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[Spectrum]
GO

if exists (select * from sysobjects where id = object_id(N'[dbo].[VRUData]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[VRUData]
GO

CREATE TABLE [dbo].[Action] (
	[ActionType] [int] IDENTITY (1, 1) NOT NULL ,
	[ActionDesc] [nvarchar] (25) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Activity] (
	[ActivityID] [int] IDENTITY (1, 1) NOT NULL ,
	[DateTime] [datetime] NOT NULL ,
	[Action] [int] NOT NULL ,
	[ActionText] [ntext] NULL ,
	[ComputerName] [nvarchar] (10) NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [dbo].[Applications] (
	[Application] [nvarchar] (25) NOT NULL ,
	[AppType] [int] NOT NULL ,
	[ExecuteObject] [nvarchar] (50) NOT NULL ,
	[ServerName] [nvarchar] (50) NOT NULL ,
	[Database] [nvarchar] (50) NULL ,
	[FormName] [nvarchar] (50) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ClientList] (
	[LWN] [nvarchar] (10) NOT NULL ,
	[FCN] [nvarchar] (50) NULL ,
	[TCPIndex] [smallint] NULL ,
	[Application] [nvarchar] (25) NULL ,
	[DNIS] [nvarchar] (10) NULL ,
	[CallID] [nvarchar] (18) NULL ,
	[Status] [nvarchar] (10) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[DNIS_List] (
	[DNIS] [nvarchar] (7) NOT NULL ,
	[Application] [nvarchar] (25) NOT NULL ,
	[Active] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ExtensionList] (
	[LWN] [nvarchar] (4) NOT NULL ,
	[FCN] [nvarchar] (10) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Spectrum] (
	[ID] [int] IDENTITY (1, 1) NOT NULL ,
	[SpectrumEnabled] [nvarchar] (1) NULL ,
	[SpectrumName] [nvarchar] (15) NULL ,
	[SpectrumIP] [nvarchar] (15) NULL ,
	[SpectrumPort] [int] NULL ,
	[SpectrumStatus] [nvarchar] (50) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[VRUData] (
	[CallID] [nvarchar] (10) NOT NULL ,
	[TimeStamp] [datetime] NULL ,
	[SearchResults] [ntext] NULL ,
	[EndOfCall] [ntext] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[Action] WITH NOCHECK ADD 
	CONSTRAINT [aaaaaAction_PK] PRIMARY KEY  NONCLUSTERED 
	(
		[ActionType]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Activity] WITH NOCHECK ADD 
	CONSTRAINT [DF__Temporary__Actio__1BFD2C07] DEFAULT (0) FOR [Action],
	CONSTRAINT [aaaaaActivity_PK] PRIMARY KEY  NONCLUSTERED 
	(
		[ActivityID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[Applications] WITH NOCHECK ADD 
	CONSTRAINT [DF__Temporary__AppTy__0EA330E9] DEFAULT (0) FOR [AppType]
GO

ALTER TABLE [dbo].[ClientList] WITH NOCHECK ADD 
	CONSTRAINT [DF__Temporary__TCPIn__1367E606] DEFAULT (0) FOR [TCPIndex]
GO

ALTER TABLE [dbo].[DNIS_List] WITH NOCHECK ADD 
	CONSTRAINT [DF__Temporary__Activ__173876EA] DEFAULT (0) FOR [Active]
GO

ALTER TABLE [dbo].[Spectrum] WITH NOCHECK ADD 
	CONSTRAINT [aaaaaSpectrum_PK] PRIMARY KEY  NONCLUSTERED 
	(
		[ID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [dbo].[VRUData] WITH NOCHECK ADD 
	CONSTRAINT [aaaaaVRUData_PK] PRIMARY KEY  NONCLUSTERED 
	(
		[CallID]
	)  ON [PRIMARY] 
GO

 CREATE  INDEX [ActionActivity] ON [dbo].[Activity]([Action]) ON [PRIMARY]
GO

 CREATE  INDEX [ActivityDateTime] ON [dbo].[Activity]([DateTime]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [ActivityID] ON [dbo].[Activity]([ActivityID]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [Application] ON [dbo].[Applications]([Application]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [Extension] ON [dbo].[ClientList]([LWN]) ON [PRIMARY]
GO

 CREATE  INDEX [Application] ON [dbo].[DNIS_List]([Application]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [LWN] ON [dbo].[DNIS_List]([DNIS]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [FCN] ON [dbo].[ExtensionList]([FCN]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [Rockwell Number (Hex)] ON [dbo].[ExtensionList]([LWN]) ON [PRIMARY]
GO

 CREATE  INDEX [key] ON [dbo].[VRUData]([CallID]) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Activity] ADD 
	CONSTRAINT [Activity_FK00] FOREIGN KEY 
	(
		[Action]
	) REFERENCES [dbo].[Action] (
		[ActionType]
	),
	CONSTRAINT [Activity_FK01] FOREIGN KEY 
	(
		[ComputerName]
	) REFERENCES [dbo].[ExtensionList] (
		[FCN]
	)
GO

ALTER TABLE [dbo].[ClientList] ADD 
	CONSTRAINT [ClientList_FK00] FOREIGN KEY 
	(
		[Application]
	) REFERENCES [dbo].[Applications] (
		[Application]
	),
	CONSTRAINT [ClientList_FK01] FOREIGN KEY 
	(
		[LWN]
	) REFERENCES [dbo].[ExtensionList] (
		[FCN]
	)
GO

