USE [KESM]
GO

IF OBJECT_ID(N'dbo.contact', N'U') IS NOT NULL
   DROP TABLE [dbo].[contact];
GO
IF OBJECT_ID(N'dbo.address', N'U') IS NOT NULL
   DROP TABLE [dbo].[address];
GO
IF OBJECT_ID(N'dbo.person', N'U') IS NOT NULL
   DROP TABLE [dbo].[person];
GO
IF OBJECT_ID(N'dbo.contact_type', N'U') IS NOT NULL
   DROP TABLE [dbo].[contact_type];
GO

CREATE TABLE person (
  id bigint IDENTITY(1,1) NOT NULL,
  first_name nvarchar(50) NOT NULL,
  last_name nvarchar(50) NOT NULL,
  id_card nchar(8) NOT NULL,
  birth_date date NOT NULL,
  birth_place nvarchar(50) NULL,
  tax_number nchar(10) NULL,
  PRIMARY KEY (id)
)
GO

CREATE TABLE address (
  id bigint IDENTITY(1,1) NOT NULL,
  city nvarchar(250) NULL,
  postal_code smallint NULL,
  street nvarchar(250) NULL,
  temporary bit NULL,
  person_id bigint NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_address_person FOREIGN KEY (person_id) REFERENCES person (id)
)
GO

CREATE TABLE contact_type (
  id bigint IDENTITY(1,1) NOT NULL,
  title nvarchar(250) NOT NULL,
  PRIMARY KEY (id)
)

CREATE TABLE contact (
  id bigint IDENTITY(1,1) NOT NULL,
  contact nvarchar(250) NOT NULL,
  address_id bigint NOT NULL,
  contact_type_id bigint NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_contact_address FOREIGN KEY (address_id) REFERENCES address (id),
  CONSTRAINT fk_contact_contact_type FOREIGN KEY (contact_type_id) REFERENCES contact_type (id)
)
GO
