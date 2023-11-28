USE DMS
-- delete tables if exists
DROP TABLE IF EXISTS protocol_document
DROP TABLE IF EXISTS document
DROP TABLE IF EXISTS protocol

-- create document table
CREATE TABLE [dbo].[document](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](200) NOT NULL,
	[type] [varchar](10) NOT NULL,
	[created_by] [varchar](100) NULL,
	[created_at] [datetime] NULL,
 CONSTRAINT [pk_document_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
ALTER TABLE [dbo].[document] ADD  DEFAULT (NULL) FOR [created_by]
ALTER TABLE [dbo].[document] ADD  DEFAULT (NULL) FOR [created_at]


-- create protocol table
CREATE TABLE [dbo].[protocol](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](200) NOT NULL,
	[status] [varchar](80) NOT NULL,
	[created_by] [varchar](100) NULL,
	[created_at] [datetime] NULL,
 CONSTRAINT [pk_protocol_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
ALTER TABLE [dbo].[protocol] ADD  DEFAULT (NULL) FOR [created_by]
ALTER TABLE [dbo].[protocol] ADD  DEFAULT (NULL) FOR [created_at]

-- create protocol_document table - relation between protocol and its document
DROP TABLE IF EXISTS protocol_document;
CREATE TABLE [dbo].[protocol_document](
	[prot_id] [int] NOT NULL,
	[doc_id] [int] NOT NULL,
 CONSTRAINT [PK_protocol_document] PRIMARY KEY CLUSTERED 
(
	[prot_id] ASC,
	[doc_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
ALTER TABLE [dbo].[protocol_document]  WITH CHECK ADD  CONSTRAINT [FK_protocol_document_document_id] FOREIGN KEY([doc_id])
REFERENCES [dbo].[document] ([id])
ALTER TABLE [dbo].[protocol_document] CHECK CONSTRAINT [FK_protocol_document_document_id]
ALTER TABLE [dbo].[protocol_document]  WITH CHECK ADD  CONSTRAINT [FK_protocol_document_protocol_id] FOREIGN KEY([prot_id])
REFERENCES [dbo].[protocol] ([id])
ALTER TABLE [dbo].[protocol_document] CHECK CONSTRAINT [FK_protocol_document_protocol_id]

