USE [master]
GO
/****** Object:  Database [Gestor_de_Guardias]    Script Date: 21/05/2025 05:47:31 p. m. ******/
CREATE DATABASE [Gestor_de_Guardias]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'Gestor_de_Guardias', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Gestor_de_Guardias.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'Gestor_de_Guardias_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\Gestor_de_Guardias_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Gestor_de_Guardias].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Gestor_de_Guardias] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET ANSI_NULLS OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET ANSI_PADDING OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET ARITHABORT OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [Gestor_de_Guardias] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [Gestor_de_Guardias] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET  DISABLE_BROKER
GO
ALTER DATABASE [Gestor_de_Guardias] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [Gestor_de_Guardias] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET RECOVERY SIMPLE
GO
ALTER DATABASE [Gestor_de_Guardias] SET  MULTI_USER
GO
ALTER DATABASE [Gestor_de_Guardias] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [Gestor_de_Guardias] SET DB_CHAINING OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [Gestor_de_Guardias] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [Gestor_de_Guardias] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [Gestor_de_Guardias] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [Gestor_de_Guardias] SET QUERY_STORE = ON
GO
ALTER DATABASE [Gestor_de_Guardias] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Gestor_de_Guardias]
GO
/****** Object:  User [guardiasadm]    Script Date: 21/05/2025 05:47:32 p. m. ******/
CREATE USER [guardiasadm] FOR LOGIN [guardiasadm] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [guardiasadm]
GO
/****** Object:  Table [dbo].[configuracion]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[configuracion](
	[hora_inicio] [time](0) NOT NULL,
	[hora_fin] [time](0) NOT NULL,
	[dia_semana] [tinyint] NOT NULL,
	[dia_es_receso] [bit] NOT NULL,
 CONSTRAINT [PK_configuracion] PRIMARY KEY CLUSTERED
(
	[hora_inicio] ASC,
	[hora_fin] ASC,
	[dia_semana] ASC,
	[dia_es_receso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[esquema]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[esquema](
	[dia_semana] [tinyint] NOT NULL,
	[dia_es_receso] [bit] NOT NULL,
	[tipo_persona] [nchar](1) NULL,
	[sexo] [nchar](1) NULL,
	[cant_personas] [tinyint] NOT NULL,
 CONSTRAINT [PK_esquema_1] PRIMARY KEY CLUSTERED
(
	[dia_semana] ASC,
	[dia_es_receso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[horario]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[horario](
	[inicio] [time](0) NOT NULL,
	[fin] [time](0) NOT NULL,
 CONSTRAINT [PK_horario] PRIMARY KEY CLUSTERED
(
	[inicio] ASC,
	[fin] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[periodo_no_planificable]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[periodo_no_planificable](
	[inicio] [date] NOT NULL,
	[fin] [date] NOT NULL,
 CONSTRAINT [PK_periodo_no_planificable] PRIMARY KEY CLUSTERED
(
	[inicio] ASC,
	[fin] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[persona]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[persona](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[nombre] [nvarchar](50) NOT NULL,
	[apellido] [nvarchar](50) NOT NULL,
	[sexo] [char](1) NOT NULL,
	[carnet] [nvarchar](11) NOT NULL,
	[ultima_guardia_hecha] [date] NULL,
	[guardias_de_recuperacion] [int] NOT NULL,
	[baja] [date] NULL,
	[reincorporacion] [date] NULL,
	[tipo] [char](1) NOT NULL,
 CONSTRAINT [PK_persona] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rol]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rol](
	[nombre] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_rol] PRIMARY KEY CLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[turno_de_guardia]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[turno_de_guardia](
	[hora_inicio] [time](0) NOT NULL,
	[hora_fin] [time](0) NOT NULL,
	[persona_asignada] [bigint] NOT NULL,
	[hecho] [bit] NULL,
	[fecha] [date] NOT NULL,
 CONSTRAINT [PK_turno_de_guardia] PRIMARY KEY CLUSTERED
(
	[hora_inicio] ASC,
	[hora_fin] ASC,
	[fecha] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuario]    Script Date: 21/05/2025 05:47:32 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuario](
	[nombre] [nvarchar](50) NOT NULL,
	[contrasenna] [nvarchar](50) NOT NULL,
	[rol] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_usuario] PRIMARY KEY CLUSTERED
(
	[nombre] ASC,
	[contrasenna] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[esquema] ADD  CONSTRAINT [DF_esquema_cant_personas]  DEFAULT ((1)) FOR [cant_personas]
GO
ALTER TABLE [dbo].[persona] ADD  CONSTRAINT [DF_persona_guardias_de_recuperacion]  DEFAULT ((0)) FOR [guardias_de_recuperacion]
GO
ALTER TABLE [dbo].[configuracion]  WITH CHECK ADD  CONSTRAINT [FK_configuracion_esquema] FOREIGN KEY([dia_semana], [dia_es_receso])
REFERENCES [dbo].[esquema] ([dia_semana], [dia_es_receso])
GO
ALTER TABLE [dbo].[configuracion] CHECK CONSTRAINT [FK_configuracion_esquema]
GO
ALTER TABLE [dbo].[configuracion]  WITH CHECK ADD  CONSTRAINT [FK_configuracion_horario] FOREIGN KEY([hora_inicio], [hora_fin])
REFERENCES [dbo].[horario] ([inicio], [fin])
GO
ALTER TABLE [dbo].[configuracion] CHECK CONSTRAINT [FK_configuracion_horario]
GO
ALTER TABLE [dbo].[turno_de_guardia]  WITH CHECK ADD  CONSTRAINT [FK_turno_de_guardia_horario] FOREIGN KEY([hora_inicio], [hora_fin])
REFERENCES [dbo].[horario] ([inicio], [fin])
GO
ALTER TABLE [dbo].[turno_de_guardia] CHECK CONSTRAINT [FK_turno_de_guardia_horario]
GO
ALTER TABLE [dbo].[turno_de_guardia]  WITH CHECK ADD  CONSTRAINT [FK_turno_de_guardia_persona] FOREIGN KEY([persona_asignada])
REFERENCES [dbo].[persona] ([id])
GO
ALTER TABLE [dbo].[turno_de_guardia] CHECK CONSTRAINT [FK_turno_de_guardia_persona]
GO
ALTER TABLE [dbo].[usuario]  WITH CHECK ADD  CONSTRAINT [FK_usuario_rol] FOREIGN KEY([rol])
REFERENCES [dbo].[rol] ([nombre])
GO
ALTER TABLE [dbo].[usuario] CHECK CONSTRAINT [FK_usuario_rol]
GO
USE [master]
GO
ALTER DATABASE [Gestor_de_Guardias] SET  READ_WRITE
GO
