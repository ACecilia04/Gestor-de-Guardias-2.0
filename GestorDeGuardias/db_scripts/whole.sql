USE [master]
GO
/****** Object:  Database [Gestor_de_Guardias]    Script Date: 04/06/2025 07:58:30 p. m. ******/
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
/****** Object:  User [guardiasadm]    Script Date: 04/06/2025 07:58:31 p. m. ******/
CREATE USER [guardiasadm] FOR LOGIN [guardiasadm] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [guardiasadm]
GO
/****** Object:  Table [dbo].[configuracion]    Script Date: 04/06/2025 07:58:31 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[configuracion](
	[dia_semana] [tinyint] NOT NULL,
	[dia_es_receso] [bit] NOT NULL,
	[horario] [bigint] NOT NULL,
	[tipo_persona] [nvarchar](20) NULL,
	[sexo] [nchar](1) NULL,
	[cant_personas] [tinyint] NOT NULL,
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[borrado] [bit] NOT NULL,
 CONSTRAINT [PK_esquema] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[horario]    Script Date: 04/06/2025 07:58:31 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[horario](
	[inicio] [time](0) NOT NULL,
	[fin] [time](0) NOT NULL,
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[borrado] [bit] NOT NULL,
 CONSTRAINT [PK_horario] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[periodo_no_planificable]    Script Date: 04/06/2025 07:58:31 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[periodo_no_planificable](
	[inicio] [date] NOT NULL,
	[fin] [date] NOT NULL,
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[borrado] [bit] NULL,
 CONSTRAINT [PK_periodo_no_planificable] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[persona]    Script Date: 04/06/2025 07:58:31 p. m. ******/
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
	[tipo] [nvarchar](20) NOT NULL,
	[borrado] [bit] NOT NULL,
 CONSTRAINT [PK_persona] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rol]    Script Date: 04/06/2025 07:58:31 p. m. ******/
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
/****** Object:  Table [dbo].[tipo_persona]    Script Date: 04/06/2025 07:58:31 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_persona](
	[nombre] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_tipo_persona] PRIMARY KEY CLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[turno_de_guardia]    Script Date: 04/06/2025 07:58:31 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[turno_de_guardia](
	[persona_asignada] [bigint] NOT NULL,
	[hecho] [bit] NULL,
	[fecha] [date] NOT NULL,
	[horario] [bigint] NOT NULL,
	[borrado] [bit] NOT NULL,
	[id] [bigint] IDENTITY(1,1) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuario]    Script Date: 04/06/2025 07:58:31 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuario](
	[nombre] [nvarchar](50) NOT NULL,
	[contrasenna] [nvarchar](50) NOT NULL,
	[rol] [nvarchar](50) NOT NULL,
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[borrado] [bit] NOT NULL,
 CONSTRAINT [PK_usuario] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_usuario]    Script Date: 04/06/2025 07:58:31 p. m. ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_usuario] ON [dbo].[usuario]
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[configuracion] ADD  DEFAULT ((1)) FOR [cant_personas]
GO
ALTER TABLE [dbo].[configuracion] ADD  DEFAULT ((0)) FOR [borrado]
GO
ALTER TABLE [dbo].[horario] ADD  DEFAULT ((0)) FOR [borrado]
GO
ALTER TABLE [dbo].[periodo_no_planificable] ADD  CONSTRAINT [DF_periodo_no_planificable_borrado]  DEFAULT ((0)) FOR [borrado]
GO
ALTER TABLE [dbo].[persona] ADD  DEFAULT ((0)) FOR [guardias_de_recuperacion]
GO
ALTER TABLE [dbo].[persona] ADD  DEFAULT ((0)) FOR [borrado]
GO
ALTER TABLE [dbo].[turno_de_guardia] ADD  CONSTRAINT [DF__turno_de___borra__47DBAE45]  DEFAULT ((0)) FOR [borrado]
GO
ALTER TABLE [dbo].[usuario] ADD  CONSTRAINT [DF_usuario_borrado]  DEFAULT ((0)) FOR [borrado]
GO
ALTER TABLE [dbo].[configuracion]  WITH CHECK ADD  CONSTRAINT [FK_configuracion_horario] FOREIGN KEY([horario])
REFERENCES [dbo].[horario] ([id])
GO
ALTER TABLE [dbo].[configuracion] CHECK CONSTRAINT [FK_configuracion_horario]
GO
ALTER TABLE [dbo].[configuracion]  WITH CHECK ADD  CONSTRAINT [FK_configuracion_tipo_persona] FOREIGN KEY([tipo_persona])
REFERENCES [dbo].[tipo_persona] ([nombre])
GO
ALTER TABLE [dbo].[configuracion] CHECK CONSTRAINT [FK_configuracion_tipo_persona]
GO
ALTER TABLE [dbo].[persona]  WITH CHECK ADD  CONSTRAINT [FK_persona_tipo_persona] FOREIGN KEY([tipo])
REFERENCES [dbo].[tipo_persona] ([nombre])
GO
ALTER TABLE [dbo].[persona] CHECK CONSTRAINT [FK_persona_tipo_persona]
GO
ALTER TABLE [dbo].[turno_de_guardia]  WITH CHECK ADD  CONSTRAINT [FK_turno_de_guardia_horario1] FOREIGN KEY([horario])
REFERENCES [dbo].[horario] ([id])
GO
ALTER TABLE [dbo].[turno_de_guardia] CHECK CONSTRAINT [FK_turno_de_guardia_horario1]
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
USE [Gestor_de_Guardias]
GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_configuracion_create]
    @diaSemana INT,
    @diaEsReceso BIT,
    @horario BIGINT,
    @tipoPersona VARCHAR(50),
    @sexo CHAR(1),
    @cantPersonas INT
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM configuracion
		WHERE horario = @horario
		AND dia_semana = @diaSemana
		AND dia_es_receso = @diaEsReceso
		AND borrado = 0
	);

	IF (@count > 0)
		THROW 51000, 'Configuracion existente', 1

    INSERT INTO configuracion (dia_semana, dia_es_receso, horario, tipo_persona, sexo, cant_personas)
    VALUES (@diaSemana, @diaEsReceso, @horario, @tipoPersona, @sexo, @cantPersonas);
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_configuracion_delete]
    @id BIGINT
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM configuracion
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Configuracion inexistente', 1

    UPDATE configuracion
	SET borrado = 1
	WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_get_cant_personas_asignables]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_configuracion_get_cant_personas_asignables]
@horario BIGINT,
@fecha DATE
	AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @esReceso BIT =
        CASE
            WHEN EXISTS (SELECT 1 FROM periodo_no_planificable WHERE @fecha BETWEEN inicio AND fin)
            THEN 1 ELSE 0
        END;
    -- Insert statements for procedure here
	SELECT cant_personas from configuracion
    WHERE dia_semana = DATEPART(WEEkDAY, @fecha) and dia_es_receso = @esReceso
END
GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_configuracion_read]
AS
BEGIN
    SELECT * FROM configuracion
	WHERE borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_read_by_id]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_configuracion_read_by_id]
    @id bigint
AS
BEGIN
    SELECT * FROM configuracion
    WHERE id = @id
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_read_by_pk]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_configuracion_read_by_pk]
    @horario bigint,
    @fecha DATE,
    @dia_es_receso BIT
AS
BEGIN
    SELECT * FROM configuracion
    WHERE horario = @horario
	AND dia_semana = DATEPART(WEEKDAY, @fecha)
	AND dia_es_receso = @dia_es_receso
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_configuracion_update]
    @id BIGINT,
    @dia_semana TINYINT,
    @dia_es_receso BIT,
    @horario BIGINT,
    @tipo_persona VARCHAR(50),
    @sexo CHAR(1),
    @cant_personas INT
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM configuracion
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Configuracion inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM configuracion
		WHERE horario = @horario
		AND dia_semana = @dia_semana
		AND dia_es_receso = @dia_es_receso
		AND borrado = 0
		AND id <> @id
	);

	IF (@count > 0)
		THROW 51000, 'Configuracion existente', 1

    UPDATE configuracion
    SET dia_semana = COALESCE(@dia_semana, dia_semana),
        dia_es_receso = COALESCE(@dia_es_receso, dia_es_receso),
        horario =COALESCE(@horario, horario),
        tipo_persona = COALESCE(@tipo_persona, tipo_persona),
        sexo = COALESCE(@sexo, sexo),
        cant_personas = COALESCE(@cant_personas, cant_personas)
    WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuraciones_de_fecha]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_configuraciones_de_fecha]
	@fecha DATE
AS
BEGIN
	SET NOCOUNT ON;

	  DECLARE @esReceso BIT =
        CASE
            WHEN EXISTS (SELECT 1 FROM periodo_no_planificable WHERE @fecha BETWEEN inicio AND fin)
            THEN 1 ELSE 0
        END;

	SELECT * FROM configuracion
	WHERE dia_semana = DATEPART(WEEKDAY, @fecha) AND dia_es_receso = @esReceso
END
GO
/****** Object:  StoredProcedure [dbo].[sp_get_turnos_a_partir_de]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_get_turnos_a_partir_de]
    @fecha DATE
AS
BEGIN
SELECT
        tg.fecha,
        tg.hecho,
        tg.id,
        h.id AS horario_id,
        h.inicio,
        h.fin,
        p.id AS persona_id,
        p.nombre,
        p.apellido,
        p.sexo,
        p.carnet,
        p.ultima_guardia_hecha,
        p.guardias_de_recuperacion,
        p.baja,
        p.reincorporacion,
        p.tipo,
        p.borrado
    FROM turno_de_guardia tg
    JOIN persona p ON tg.persona_asignada = p.id
    JOIN horario h ON tg.horario = h.id
    WHERE
        MONTH(tg.fecha) = MONTH(@fecha) AND YEAR(tg.fecha) = YEAR(@fecha)
    ORDER BY tg.fecha, h.inicio;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_horario_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_horario_create]
    @inicio time(0),
    @fin time(0)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM horario
		WHERE inicio = @inicio
		AND fin = @fin
	);

	IF (@count > 0)
		THROW 51000, 'Horario existente', 1

    INSERT INTO horario (inicio, fin)
		VALUES (@inicio, @fin);
END

GO
/****** Object:  StoredProcedure [dbo].[sp_horario_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_horario_delete]
    @id BIGINT
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM horario
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Horario inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE horario = @id
		AND borrado = 0
	);

	IF (@count > 0)
		THROW 51000, 'Horario no se puede eliminar porque está siendo utilizado', 1

    UPDATE horario
		SET borrado = 1
		WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_horario_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_horario_read]
AS
BEGIN
    SELECT * FROM horario
	WHERE borrado = 0
	ORDER BY inicio asc
END

GO
/****** Object:  StoredProcedure [dbo].[sp_horario_read_by_id]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_horario_read_by_id]
    @id nvarchar(11)
AS
BEGIN
    SELECT * FROM horario
	WHERE id = @id
	AND borrado = 0
		ORDER BY inicio asc;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_horario_read_by_pk]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_horario_read_by_pk]
    @inicio time(0),
    @fin time(0)
AS
BEGIN
    SELECT * FROM horario
	WHERE inicio = @inicio
	AND fin = @fin
	AND borrado = 0
		ORDER BY inicio asc;

END

GO
/****** Object:  StoredProcedure [dbo].[sp_horario_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_horario_update]
    @id BIGINT,
    @inicio time(0),
    @fin time(0)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM horario
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Horario inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM horario
		WHERE inicio = @inicio
		AND fin = @fin
		AND id <> @id
	);

	IF (@count > 0)
		THROW 51000, 'Horario existente', 1

    UPDATE horario
	SET
		inicio = @inicio,
		fin = @fin
    WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_horarios_de_fecha]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_horarios_de_fecha]
	@fecha DATE
AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @esReceso BIT =
        CASE
            WHEN EXISTS (SELECT 1 FROM periodo_no_planificable WHERE @fecha BETWEEN inicio AND fin)
            THEN 1 ELSE 0
        END;
SELECT * FROM horario h
JOIN configuracion c ON
c.horario = h.id
WHERE
c.dia_semana = DATEPART(WEEKDAY, @fecha) AND
c.dia_es_receso = @esReceso
	ORDER BY inicio asc;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_count]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_count]
AS
BEGIN
SELECT COUNT(*) FROM periodo_no_planificable
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_create]
    @inicio date,
    @fin date
AS
BEGIN
    INSERT INTO periodo_no_planificable (inicio, fin) VALUES (@inicio, @fin);
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_delete]
    @inicio date,
    @fin date
AS
BEGIN
    DELETE FROM periodo_no_planificable WHERE inicio = @inicio AND fin = @fin;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_has_date]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_has_date]
	@fecha DATE
AS
BEGIN
	SET NOCOUNT ON;
SELECT IIF(total > 0, 1, 0) as existe
FROM (
	SELECT COUNT(*) as total
	FROM periodo_no_planificable
	WHERE @fecha BETWEEN inicio AND fin
) as total;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_in_date]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_in_date]
@fecha DATE
AS
BEGIN
	SELECT *
	FROM periodo_no_planificable
	WHERE @fecha BETWEEN inicio AND fin
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_read]
AS
BEGIN
    SELECT * FROM periodo_no_planificable;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_read_by_pk]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_read_by_pk]
    @inicio date,
    @fin date
AS
BEGIN
    SELECT * FROM periodo_no_planificable WHERE inicio = @inicio AND fin = @fin;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_update]
    @inicio date,
    @fin date,
    @nuevo_inicio date,
    @nuevo_fin date
AS
BEGIN
    UPDATE periodo_no_planificable
    SET inicio = @nuevo_inicio,
        fin = @nuevo_fin
    WHERE inicio = @inicio AND fin = @fin;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_count_by_tipo]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_persona_count_by_tipo]
	@tipo nvarchar(20)
AS
BEGIN
	SELECT COUNT(*) AS total
	FROM persona
	WHERE tipo = @tipo
	AND borrado = 0
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_persona_create]
    @nombre NVARCHAR(50),
    @apellido NVARCHAR(50),
    @sexo CHAR(1),
    @carnet NVARCHAR(11),
    @tipo NVARCHAR(20)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE LOWER(LTRIM(carnet)) = LOWER(LTRIM(@carnet))
		OR (LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre)) AND LOWER(LTRIM(apellido)) = LOWER(LTRIM(@apellido)))
	);

	IF (@count > 0)
		THROW 51000, 'Persona existente', 1

    INSERT INTO persona (nombre, apellido, sexo, carnet, tipo)
		VALUES (@nombre, @apellido, @sexo, @carnet, @tipo);
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_delete_by_ci]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_delete_by_ci]
    @carnet nvarchar(11)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE LOWER(LTRIM(carnet)) = LOWER(LTRIM(@carnet))
	);

	IF (@count = 0)
		THROW 51000, 'Persona inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia t
		INNER JOIN persona p ON p.id = t.persona_asignada
		WHERE LOWER(LTRIM(p.carnet)) = LOWER(LTRIM(@carnet))
	);

	IF (@count > 0)
		THROW 51000, 'Persona no se puede eliminar porque está siendo utilizado', 1

    DELETE FROM persona
		WHERE LOWER(LTRIM(carnet)) = LOWER(LTRIM(@carnet));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_delete_by_id]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_delete_by_id]
    @id nvarchar(11)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Persona inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE persona_asignada = @id
	);

	IF (@count > 0)
		THROW 51000, 'Persona no se puede eliminar porque está siendo utilizado', 1

    DELETE FROM persona
		WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_get_disponible_para_turno]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_persona_get_disponible_para_turno]
	@fecha DATE,
	@tipo nvarchar(20),
	@sexo char(1) = null
	AS
BEGIN
	SELECT * FROM persona
	WHERE(
	tipo = @tipo AND
	(@sexo IS NULL OR sexo = @sexo) AND
	(@fecha < baja OR baja IS NULL) AND
	(@fecha > reincorporacion OR reincorporacion IS NULL))
	ORDER BY guardias_de_recuperacion, apellido, nombre;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_read]
AS
BEGIN
    SELECT * FROM persona
	WHERE borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_baja]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_read_by_baja]
    @baja date
AS
BEGIN
    SELECT * FROM Persona
    WHERE baja = @baja
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_ci]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_read_by_ci]
    @carnet nvarchar(11)
AS
BEGIN
    SELECT * FROM persona
	WHERE LOWER(LTRIM(carnet)) = LOWER(LTRIM(@carnet))
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_tipo]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_read_by_tipo]
    @tipo NVARCHAR(20)
AS
BEGIN
    SELECT * FROM persona
    WHERE LOWER(LTRIM(tipo)) = LOWER(LTRIM(@tipo))
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_update]
    @id BIGINT,
    @nombre NVARCHAR(50) = NULL,
    @apellido NVARCHAR(50) = NULL,
    @sexo CHAR(1) = NULL,
    @carnet NVARCHAR(11) = NULL,
    @ultima_guardia_hecha DATE = NULL,
    @guardias_de_recuperacion INT = NULL,
    @baja DATE = NULL,
    @reincorporacion DATE = NULL,
    @tipo NVARCHAR(20) = NULL
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Persona inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE LOWER(LTRIM(carnet)) = LOWER(LTRIM(@carnet))
		OR (LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre)) AND LOWER(LTRIM(apellido)) = LOWER(LTRIM(@apellido)))
		AND id <> @id
	);

	IF (@count > 0)
		THROW 51000, 'Persona existente', 1

    UPDATE persona
    SET nombre = COALESCE(@nombre, nombre),
        apellido = COALESCE(@apellido, apellido),
        sexo = COALESCE(@sexo, sexo),
        carnet = COALESCE(@carnet, carnet),
        ultima_guardia_hecha = COALESCE(@ultima_guardia_hecha, ultima_guardia_hecha),
        guardias_de_recuperacion = COALESCE(@guardias_de_recuperacion, guardias_de_recuperacion),
        baja = COALESCE(@baja, baja),
        reincorporacion = COALESCE(@reincorporacion, reincorporacion),
        tipo = COALESCE(@tipo, tipo)
    WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_update_baja]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_update_baja]
    @id BIGINT,
    @baja DATE
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Persona inexistente', 1

    UPDATE persona
		SET baja = @baja
		WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_rol_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_rol_create]
    @nombre nvarchar(50)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM Rol
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count > 0)
		THROW 51000, 'Rol existente', 1

    INSERT INTO rol (nombre)
		VALUES (LOWER(LTRIM(@nombre)));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_rol_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_rol_delete]
    @nombre nvarchar(50)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM Rol
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count = 0)
		THROW 51000, 'Rol inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM Usuario
		WHERE LOWER(LTRIM(rol)) = LOWER(LTRIM(@nombre))
	);

	IF (@count > 0)
		THROW 51000, 'Rol no se puede eliminar porque está siendo utilizado', 1

    DELETE FROM rol
	WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_rol_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_rol_read]
AS
BEGIN
    SELECT * FROM rol;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_rol_read_by_nombre]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_rol_read_by_nombre]
    @nombre nvarchar(50)
AS
BEGIN
    SELECT * FROM rol WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_rol_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_rol_update]
    @nombre nvarchar(50),
    @nuevo_nombre nvarchar(50)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM Rol
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count = 0)
		THROW 51000, 'Rol inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM Rol
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nuevo_nombre))
	);

	IF (@count > 0)
		THROW 51000, 'Rol existente', 1

    UPDATE rol
    SET nombre = LOWER(LTRIM(@nuevo_nombre))
    WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_tipo_persona_create]
    @nombre nvarchar(20)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM tipo_persona
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count > 0)
		THROW 51000, 'Tipo de persona existente', 1

    INSERT INTO tipo_persona (nombre)
		VALUES (LOWER(LTRIM(@nombre)));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_tipo_persona_delete]
    @nombre nvarchar(20)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM tipo_persona
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count = 0)
		THROW 51000, 'Tipo de persona inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM (
			SELECT id
				FROM configuracion
				WHERE LOWER(LTRIM(tipo_persona)) = LOWER(LTRIM(@nombre))
			UNION
			SELECT id
				FROM persona
				WHERE LOWER(LTRIM(tipo)) = LOWER(LTRIM(@nombre))
		) AS x
	);

	IF (@count > 0)
		THROW 51000, 'El tipo de persona no se puede eliminar porque está siendo utilizado', 1

    DELETE tipo_persona
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_tipo_persona_read]
AS
BEGIN
    SELECT * FROM tipo_persona;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_read_by_nombre]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_tipo_persona_read_by_nombre]
    @nombre nvarchar(20)
AS
BEGIN
    SELECT * FROM tipo_persona
	WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_tipo_persona_update]
    @nombre nvarchar(20),
    @nuevo_nombre nvarchar(20)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM tipo_persona
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count = 0)
		THROW 51000, 'Tipo de persona inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM tipo_persona
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nuevo_nombre))
	);

	IF (@count > 0)
		THROW 51000, 'Tipo de persona existente', 1

    UPDATE tipo_persona
		SET nombre = LOWER(LTRIM(@nuevo_nombre))
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_turno_de_guardia_create]
    @persona_asignada bigint,
    @fecha date,
    @horario bigint
AS
BEGIN
    INSERT INTO turno_de_guardia (persona_asignada, fecha, horario)
    VALUES (@persona_asignada, @fecha, @horario);
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_delete]
    @id BIGINT
AS
BEGIN
	DECLARE @count int;
	DECLARE @fecha_turno DATE;
	    DECLARE @today DATE = GETDATE();

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Turno de guardia inexistente', 1;

	SELECT @fecha_turno = fecha
    FROM turno_de_guardia
    WHERE id = @id;

	IF (@fecha_turno < @today)
			THROW 51000, 'No se pueden borrar turnos de fechas pasadas.', 1;

      UPDATE turno_de_guardia
		SET borrado = 1
		WHERE id = @id or fecha > @fecha_turno or fecha = @fecha_turno;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_delete_a_partir_de_fecha]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_delete_a_partir_de_fecha]
    @fecha DATE
AS
BEGIN
	DECLARE @count int;
	DECLARE @today DATE = GETDATE();

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE fecha = @fecha
	);

	IF (@count = 0)
		THROW 51000, 'No hay turnos de guardia en esta fecha.', 1;

	IF (@fecha < @today)
			THROW 51000, 'No se pueden borrar turnos de fechas pasadas.', 1;

      UPDATE turno_de_guardia
		SET borrado = 1
		WHERE fecha > @fecha or fecha = @fecha;

END
GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read]
AS
BEGIN
    SELECT
       tg.fecha,
       tg.hecho,
       tg.id,
       h.id AS horario_id,
       h.inicio,
       h.fin,
       p.id AS persona_id,
       p.nombre,
       p.apellido,
       p.sexo,
       p.carnet,
       p.tipo,
       p.ultima_guardia_hecha,
       p.guardias_de_recuperacion,
       p.baja,
       p.reincorporacion,
       p.tipo,
       p.borrado
    FROM turno_de_guardia tg
    JOIN persona p ON tg.persona_asignada = p.id
    JOIN horario h ON tg.horario = h.id
    WHERE tg.borrado = 0
    ORDER BY tg.fecha, h.inicio;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read_by_pk]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read_by_pk]
    @fecha date,
    @horario bigint
AS
BEGIN
    SELECT
       tg.fecha,
       tg.hecho,
       tg.id,
       h.id AS horario_id,
       h.inicio,
       h.fin,
       p.id AS persona_id,
       p.nombre,
       p.apellido,
       p.sexo,
       p.carnet,
       p.tipo,
       p.ultima_guardia_hecha,
       p.guardias_de_recuperacion,
       p.baja,
       p.reincorporacion,
       p.tipo,
       p.borrado
    FROM turno_de_guardia tg
    JOIN persona p ON tg.persona_asignada = p.id
    JOIN horario h ON tg.horario = h.id
    WHERE tg.fecha = @fecha AND tg.horario = @horario
    ORDER BY tg.fecha, h.inicio;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_update]
    @id BIGINT,
    @persona_asignada bigint,
    @fecha date,
    @horario bigint,
    @hecho bit
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE id = @id
	);
    IF (@count = 0)
		THROW 51000, 'Turno de guardia inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE fecha = @fecha
		AND horario = @horario
		AND id <> @id
	);

	IF (@count > 0)
		THROW 51000, 'Turno de guardia existente', 1

    UPDATE turno_de_guardia
    SET hecho = @hecho
    WHERE persona_asignada = @persona_asignada
      AND fecha = @fecha
      AND horario = @horario;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_create]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_usuario_create]
    @nombre nvarchar(50),
    @contrasenna nvarchar(50),
    @rol nvarchar(50)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM Usuario
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
	);

	IF (@count > 0)
		THROW 51000, 'Usuario existente', 1

	INSERT INTO usuario (nombre, contrasenna, rol)
	    VALUES (@nombre, @contrasenna, LOWER(LTRIM(@rol)));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_delete]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_usuario_delete]
    @id bigint
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM Usuario
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Usuario inexistente', 1

    DELETE FROM usuario
	WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_read]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_usuario_read]
AS
BEGIN
    SELECT * FROM usuario;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_read_by_id]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_usuario_read_by_id]
    @id bigint
AS
BEGIN
    SELECT * FROM usuario WHERE id = @id;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_read_by_nombre]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_usuario_read_by_nombre]
    @nombre nvarchar(50)
AS
BEGIN
    SELECT * FROM usuario
	WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre));
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_update]    Script Date: 04/06/2025 09:45:17 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_usuario_update]
    @id bigint,
    @nombre nvarchar(50),
    @contrasenna nvarchar(50),
    @rol nvarchar(50)
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM Usuario
		WHERE id = @id
	);

	IF (@count = 0)
		THROW 51000, 'Usuario inexistente', 1

	SET @count = (
		SELECT COUNT(*)
		FROM Usuario
		WHERE LOWER(LTRIM(nombre)) = LOWER(LTRIM(@nombre))
		AND id <> @id
	);

	IF (@count > 0)
		THROW 51000, 'Usuario existente', 1

    UPDATE usuario
    SET nombre = @nombre,
        contrasenna = @contrasenna,
        rol = LOWER(LTRIM(@rol))
    WHERE id = @id;
END
GO

USE [master]
GO
ALTER DATABASE [Gestor_de_Guardias] SET  READ_WRITE
GO
 ------------------------------------------------------------------------------------------------------------------------------------------------------------
 SET IDENTITY_INSERT [dbo].[configuracion] ON
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'1', N'0', N'1', N'Estudiante', N'M', N'1', N'1', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'2', N'0', N'1', N'Estudiante', N'M', N'1', N'2', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'3', N'0', N'1', N'Estudiante', N'M', N'1', N'3', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'4', N'0', N'1', N'Estudiante', N'M', N'1', N'4', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'5', N'0', N'1', N'Estudiante', N'M', N'1', N'5', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'6', N'0', N'1', N'Estudiante', N'M', N'1', N'6', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'7', N'0', N'1', N'Estudiante', N'M', N'1', N'7', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'6', N'0', N'2', N'Estudiante', N'F', N'1', N'8', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'7', N'0', N'2', N'Estudiante', N'F', N'1', N'9', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'6', N'0', N'3', N'Trabajador', NULL, N'1', N'10', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'6', N'0', N'4', N'Trabajador', NULL, N'1', N'11', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'7', N'0', N'3', N'Trabajador', NULL, N'1', N'12', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'7', N'0', N'4', N'Trabajador', NULL, N'1', N'13', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'1', N'1', N'3', N'Trabajador', NULL, N'1', N'14', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'1', N'1', N'4', N'Trabajador', NULL, N'1', N'15', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'2', N'1', N'3', N'Trabajador', NULL, N'1', N'16', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'2', N'1', N'4', N'Trabajador', NULL, N'1', N'17', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'3', N'1', N'3', N'Trabajador', NULL, N'1', N'18', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'3', N'1', N'4', N'Trabajador', NULL, N'1', N'19', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'4', N'1', N'3', N'Trabajador', NULL, N'1', N'20', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'4', N'1', N'4', N'Trabajador', NULL, N'1', N'21', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'5', N'1', N'3', N'Trabajador', NULL, N'1', N'22', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'5', N'1', N'4', N'Trabajador', NULL, N'1', N'23', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'6', N'1', N'3', N'Trabajador', NULL, N'1', N'24', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'6', N'1', N'4', N'Trabajador', NULL, N'1', N'25', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'7', N'1', N'3', N'Trabajador', NULL, N'1', N'26', N'0')
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES (N'7', N'1', N'4', N'Trabajador', NULL, N'1', N'27', N'0')
 GO

 SET IDENTITY_INSERT [dbo].[configuracion] OFF
 GO


 SET IDENTITY_INSERT [dbo].[horario] ON
 GO

 INSERT INTO [dbo].[horario] ([inicio], [fin], [id], [borrado]) VALUES (N'20:00:00', N'08:00:00', N'1', N'0')
 GO

 INSERT INTO [dbo].[horario] ([inicio], [fin], [id], [borrado]) VALUES (N'08:00:00', N'20:00:00', N'2', N'0')
 GO

 INSERT INTO [dbo].[horario] ([inicio], [fin], [id], [borrado]) VALUES (N'09:00:00', N'14:00:00', N'3', N'0')
 GO

 INSERT INTO [dbo].[horario] ([inicio], [fin], [id], [borrado]) VALUES (N'14:00:00', N'19:00:00', N'4', N'0')
 GO

 SET IDENTITY_INSERT [dbo].[horario] OFF
 GO


INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'8', N'Carolina', N'Lorenzo', N'f', N'05102278053', NULL, N'0', NULL, NULL, N'Estudiante', N'0')
GO

INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'9', N'Amanda', N'Aguilera Garcia', N'f', N'04022578073', NULL, N'0', NULL, NULL, N'Estudiante', N'0')
GO

INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'10', N'Tony', N'Gracia', N'm', N'93072178062', NULL, N'0', NULL, NULL, N'Trabajador', N'0')
GO

INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'11', N'Sonia', N'Perez Lovelle', N'f', N'68110225093', NULL, N'0', NULL, NULL, N'Trabajador', N'0')
GO

INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'12', N'Daniel', N'Pardo', N'm', N'98040225688', NULL, N'0', NULL, NULL, N'Trabajador', N'0')
GO

INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'13', N'Pepe', N'Grillo', N'm', N'05102278066', NULL, N'0', NULL, NULL, N'Estudiante', N'0')
GO

INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [ultima_guardia_hecha], [guardias_de_recuperacion], [baja], [reincorporacion], [tipo], [borrado]) VALUES (N'14', N'Fito', N'Paez', N'm', N'04120225366', NULL, N'0', NULL, NULL, N'Estudiante', N'0')
GO

SET IDENTITY_INSERT [dbo].[persona] OFF
GO


INSERT INTO [dbo].[rol] ([nombre]) VALUES (N'Administrador')
GO

INSERT INTO [dbo].[rol] ([nombre]) VALUES (N'Usuario')
GO

INSERT INTO [dbo].[tipo_persona] ([nombre]) VALUES (N'Estudiante')
GO

INSERT INTO [dbo].[tipo_persona] ([nombre]) VALUES (N'Trabajador')
GO


------------------------CREAR PERSONAS------------------------------------------------------------------------------------------------
-- TRABAJADORES
EXEC sp_persona_create N'Mercedes Maria', N'López González', 'f', N'70101787233', N'Trabajador';
EXEC sp_persona_create N'Byron David', N'Cevallos Trujillo', 'm', N'73050632847', N'Trabajador';
EXEC sp_persona_create N'Gonzalo Luis', N'Balcazar Campoverde', 'm', N'87051952603', N'Trabajador';
EXEC sp_persona_create N'Fernanda Noemi', N'Campos Mendieta', 'f', N'78092587290', N'Trabajador';
EXEC sp_persona_create N'Jaime Eduardo', N'Cárdenas Molina', 'm', N'93020322844', N'Trabajador';
EXEC sp_persona_create N'Carlos Daniel', N'Villavicencio Pesantez', 'm', N'84092027046', N'Trabajador';
EXEC sp_persona_create N'Marlene Estefania', N'Novillo Jara', 'f', N'78111051339', N'Trabajador';
EXEC sp_persona_create N'Johanna Sofia', N'Perez Cabrera', 'f', N'63040450933', N'Trabajador';
EXEC sp_persona_create N'Martha Patricia', N'Morales Harris', 'f', N'70071327412', N'Trabajador';
EXEC sp_persona_create N'Diana Lucía', N'Iñiguez Iñiguez', 'f', N'85110233916', N'Trabajador';
EXEC sp_persona_create N'Jaime Vicente', N'Chuchuca Serrano', 'm', N'73122830205', N'Trabajador';
EXEC sp_persona_create N'Sahira Maribel', N'Martinez Cepeda', 'f', N'91101144653', N'Trabajador';
EXEC sp_persona_create N'Xavier Eduardo', N'Montalvo Aponte', 'm', N'82021061482', N'Trabajador';
EXEC sp_persona_create N'Alex Ruben', N'Cobos Veloz', 'm', N'65021376166', N'Trabajador';
EXEC sp_persona_create N'Mireya Katerine', N'Pazmiño Arregui', 'f', N'66040878337', N'Trabajador';
EXEC sp_persona_create N'Danilo Fernando', N'García García', 'm', N'74122662229', N'Trabajador';
EXEC sp_persona_create N'Oswaldo Ernesto', N'Lopez Bravo', 'm', N'86112273545', N'Trabajador';
EXEC sp_persona_create N'Juan Jose', N'Ortega Vintimilla', 'm', N'91122339065', N'Trabajador';
EXEC sp_persona_create N'Geovanny Ramiro', N'Cabezas Velasco', 'm', N'63110921162', N'Trabajador';
EXEC sp_persona_create N'Marlene Alexandra', N'Narvaez Calvachi', 'f', N'74102594455', N'Trabajador';
EXEC sp_persona_create N'Lenin Eduardo', N'Saguay Sanaguano', 'm', N'83022156066', N'Trabajador';
EXEC sp_persona_create N'Hernán Sebastián', N'López Montero', 'm', N'94061345883', N'Trabajador';
EXEC sp_persona_create N'Pedro', N'Guaraca Cuñas', 'm', N'64101742842', N'Trabajador';
EXEC sp_persona_create N'Raquel Alejandra', N'Arcos Cabezas', 'f', N'74061424753', N'Trabajador';
EXEC sp_persona_create N'Georgina Piedad', N'Panchez Hernandez', 'f', N'81010751413', N'Trabajador';
EXEC sp_persona_create N'Karina Monserrath', N'Masache Alvarado', 'f', N'92122025074', N'Trabajador';
EXEC sp_persona_create N'Diego Javier', N'Cajas Logroño', 'm', N'62101372362', N'Trabajador';
EXEC sp_persona_create N'Ricardo Israel', N'Valencia Cuviña', 'm', N'75031748220', N'Trabajador';
EXEC sp_persona_create N'Lilli Lucia', N'Romero Pacheco', 'f', N'86092649453', N'Trabajador';
EXEC sp_persona_create N'Susana Del Rocio', N'Andrade Soto', 'f', N'94040884719', N'Trabajador';
EXEC sp_persona_create N'José Eliecer', N'Chicaiza Ronquillo', 'm', N'61051126489', N'Trabajador';
EXEC sp_persona_create N'Edgar Guillerno', N'Guasca Tulmo', 'm', N'75090959743', N'Trabajador';
EXEC sp_persona_create N'Gustavo Patricio', N'Mena Zapata', 'm', N'84111526245', N'Trabajador';
EXEC sp_persona_create N'Lourdes Del Rocío', N'Lara Valencia', 'f', N'94010697432', N'Trabajador';
EXEC sp_persona_create N'Fanny Margoth', N'Cañar Caisalitin', 'f', N'64122519575', N'Trabajador';
EXEC sp_persona_create N'Jorge Alfonso', N'Llerena Vargas', 'm', N'76032257421', N'Trabajador';

-- ESTUDIANTES
EXEC sp_persona_create N'Alexandra Elizabeth', N'Carrion Córdova', 'f', N'05032394338', N'Estudiante';
EXEC sp_persona_create N'Jorge Xavier', N'Paredes Lucas', 'm', N'06100346049', N'Estudiante';
EXEC sp_persona_create N'Andrés Alberto', N'Suárez Pineda', 'm', N'01011788427', N'Estudiante';
EXEC sp_persona_create N'Wilson Fernando', N'Montenegro Espinoza', 'm', N'05121525761', N'Estudiante';
EXEC sp_persona_create N'Karol Narcisa', N'Macias Yanez', 'f', N'02011137116', N'Estudiante';
EXEC sp_persona_create N'Elizzabeth Vanessa', N'De Ring Salvador', 'f', N'06021673071', N'Estudiante';
EXEC sp_persona_create N'Luz Mariuxi', N'Murillo Calvache', 'f', N'02091027151', N'Estudiante';
EXEC sp_persona_create N'Santander Rosero', N'Luis Enrique', 'm', N'01112526860', N'Estudiante';
EXEC sp_persona_create N'Gladys Monserrate', N'Ordóñez Alemán', 'f', N'05051486014', N'Estudiante';
EXEC sp_persona_create N'Maria Jose', N'Franco Pico', 'f', N'01061965833', N'Estudiante';
EXEC sp_persona_create N'Diego Alejandro', N'Redroban Becerra', 'm', N'04092080501', N'Estudiante';
EXEC sp_persona_create N'Angela Lucciola', N'Suarez Velasquez', 'f', N'05102048498', N'Estudiante';
EXEC sp_persona_create N'Martha Fabiola', N'Rizzo Gonzalez', 'f', N'06091298833', N'Estudiante';
EXEC sp_persona_create N'Carlos Dionisio', N'Sornoza Moran', 'm', N'02120755742', N'Estudiante';
EXEC sp_persona_create N'Karen Gabriela', N'Romero Zavala', 'f', N'04101954771', N'Estudiante';
EXEC sp_persona_create N'Marlon Roberto', N'Banegas Andrade', 'm', N'01011186884', N'Estudiante';
EXEC sp_persona_create N'Douglas Alfredo', N'Plaza Galarza', 'm', N'02061281247', N'Estudiante';
EXEC sp_persona_create N'Teodoro Ivan', N'Blakman Briones', 'm', N'01100144547', N'Estudiante';
EXEC sp_persona_create N'Mireya Veronica', N'Choez Caicedo', 'f', N'01112288152', N'Estudiante';
EXEC sp_persona_create N'Kurt Ernesto', N'Lainez Landi', 'm', N'03101439760', N'Estudiante';
EXEC sp_persona_create N'Luis Gabriel', N'De Sousa Diaz', 'm', N'05111090166', N'Estudiante';
EXEC sp_persona_create N'Jessica Rosa', N'Macio Cueva', 'f', N'02041375955', N'Estudiante';
EXEC sp_persona_create N'Kerlin Gustavo', N'Yagual Arreaga', 'm', N'03030339569', N'Estudiante';
EXEC sp_persona_create N'Jose Santiago', N'Lindao Gonzalez', 'm', N'01010576841', N'Estudiante';
EXEC sp_persona_create N'Carlos Humberto', N'Viteri Torres', 'm', N'04061565685', N'Estudiante';
EXEC sp_persona_create N'Carlos Alberto', N'Vaca Nuñez', 'm', N'05050240809', N'Estudiante';
EXEC sp_persona_create N'Nina Dolores', N'Romero Delgado', 'f', N'03081534231', N'Estudiante';
EXEC sp_persona_create N'Hilda Maria', N'Molina Lopez', 'f', N'04080235136', N'Estudiante';
EXEC sp_persona_create N'Luis Fernando', N'Valarezo Lingen', 'm', N'05022226625', N'Estudiante';
EXEC sp_persona_create N'Lilia Edith', N'Garcia Anchundia', 'f', N'01091895597', N'Estudiante';
EXEC sp_persona_create N'Rosendo', N'Pinilla Bustamante', 'm', N'04092958123', N'Estudiante';
EXEC sp_persona_create N'Timoteo', N'Pinilla Calderón', 'm', N'05060543228', N'Estudiante';
EXEC sp_persona_create N'Sandalio', N'Pinto Alcázar', 'm', N'04031443303', N'Estudiante';
EXEC sp_persona_create N'Federico', N'Pizarro Coello', 'm', N'03010520044', N'Estudiante';
EXEC sp_persona_create N'Eugenio', N'Pla Alegre', 'm', N'04021160367', N'Estudiante';
EXEC sp_persona_create N'Urbano', N'Plana Cornejo', 'm', N'07040427065', N'Estudiante';
EXEC sp_persona_create N'Dimas Rafael', N'Polo Bravo', 'm', N'00080561087', N'Estudiante';
EXEC sp_persona_create N'Leoncio', N'Pombo Nadal', 'm', N'01052912505', N'Estudiante';
EXEC sp_persona_create N'Leyre', N'Ponce Quintana', 'm', N'09032391987', N'Estudiante';
EXEC sp_persona_create N'Elpidio', N'Pont Expósito', 'm', N'09122350388', N'Estudiante';
EXEC sp_persona_create N'Nilo', N'Porras Casas', 'm', N'06120598508', N'Estudiante';
EXEC sp_persona_create N'Andrés Felipe', N'Prado Infante', 'm', N'03032273247', N'Estudiante';
EXEC sp_persona_create N'Ciro Hernando', N'Puga Patiño', 'm', N'09061075029', N'Estudiante';
EXEC sp_persona_create N'Héctor', N'Puga Tudela', 'm', N'02022432721', N'Estudiante';
EXEC sp_persona_create N'Isaac', N'Pujol Yáñez', 'm', N'08022592243', N'Estudiante';
EXEC sp_persona_create N'Amaro', N'Quero Paredes', 'm', N'00041298806', N'Estudiante';
EXEC sp_persona_create N'Norberto Chucho', N'Raya Saldaña', 'm', N'09021957508', N'Estudiante';
EXEC sp_persona_create N'Gastón', N'Real Barriga', 'm', N'06021351043', N'Estudiante';
EXEC sp_persona_create N'Urbano', N'Real Tolosa', 'm', N'02090795423', N'Estudiante';
EXEC sp_persona_create N'Josep', N'Reina Prieto', 'm', N'01011368406', N'Estudiante';
EXEC sp_persona_create N'Leocadio', N'Requena Barriga', 'm', N'06040539402', N'Estudiante';
EXEC sp_persona_create N'José', N'Reyes Leal', 'm', N'00032012604', N'Estudiante';
EXEC sp_persona_create N'Matías', N'Rincón Roldan', 'm', N'05101772540', N'Estudiante';
EXEC sp_persona_create N'Ibán', N'Rios Calvet', 'm', N'06090822104', N'Estudiante';
EXEC sp_persona_create N'Goyo', N'Ríos Guerra', 'm', N'02041842287', N'Estudiante';
EXEC sp_persona_create N'Basilio', N'Riquelme Gordillo', 'm', N'06062166608', N'Estudiante';
EXEC sp_persona_create N'Alberto', N'Rius Rius', 'm', N'02031023945', N'Estudiante';
EXEC sp_persona_create N'Josué', N'Rocha Juan', 'm', N'03013188362', N'Estudiante';
EXEC sp_persona_create N'Amado', N'Rodríguez Girón', 'm', N'08010481360', N'Estudiante';
EXEC sp_persona_create N'Sosimo', N'Roig Bayo', 'm', N'08042639440', N'Estudiante';
EXEC sp_persona_create N'Ruy', N'Roldan Lara', 'm', N'02092845049', N'Estudiante';
EXEC sp_persona_create N'Manolo Isidro', N'Ros Salgado', 'm', N'09052753221', N'Estudiante';
EXEC sp_persona_create N'Urbano', N'Rosselló Llabrés', 'm', N'00030350986', N'Estudiante';
EXEC sp_persona_create N'Isidro', N'Rozas Gómez', 'm', N'08050547040', N'Estudiante';
EXEC sp_persona_create N'Elías', N'Sacristán Vázquez', 'm', N'08013166429', N'Estudiante';
EXEC sp_persona_create N'Cecilio Apolinar', N'Salas Alemán', 'm', N'08123077047', N'Estudiante';
EXEC sp_persona_create N'Alberto', N'Salas Báez', 'm', N'01081923845', N'Estudiante';
EXEC sp_persona_create N'Isidoro', N'Salas Marqués', 'm', N'00100449127', N'Estudiante';
EXEC sp_persona_create N'Eladio', N'Salcedo Cabanillas', 'm', N'03123147703', N'Estudiante';
EXEC sp_persona_create N'Jose Luis', N'Salgado Monreal', 'm', N'05081377503', N'Estudiante';
EXEC sp_persona_create N'Sergio', N'Salmerón Carreras', 'm', N'02012231543', N'Estudiante';
EXEC sp_persona_create N'Josué', N'Salom González', 'm', N'08100938684', N'Estudiante';
EXEC sp_persona_create N'Marco', N'Sanchez Yuste', 'm', N'03030889249', N'Estudiante';
EXEC sp_persona_create N'Pedro', N'Sancho Garrido', 'm', N'00050146422', N'Estudiante';
EXEC sp_persona_create N'Gonzalo', N'Sans Miró', 'm', N'00050855265', N'Estudiante';
EXEC sp_persona_create N'Jaime', N'Santiago Vazquez', 'm', N'09041585182', N'Estudiante';
EXEC sp_persona_create N'Benigno', N'Sanz Gomis', 'm', N'08052028224', N'Estudiante';
EXEC sp_persona_create N'Venceslás', N'Sastre Peñalver', 'm', N'08090363643', N'Estudiante';
EXEC sp_persona_create N'Jordi', N'Seco Cantón', 'm', N'06070191487', N'Estudiante';
EXEC sp_persona_create N'Eutropio', N'Sedano Cabanillas', 'm', N'07100357443', N'Estudiante';
EXEC sp_persona_create N'Victor Manuel', N'Sedano Domingo', 'm', N'01033177342', N'Estudiante';
EXEC sp_persona_create N'Pepito', N'Sedano Pou', 'm', N'05042453864', N'Estudiante';
EXEC sp_persona_create N'Curro', N'Segarra Peinado', 'm', N'00041949609', N'Estudiante';
EXEC sp_persona_create N'Leonel', N'Sierra Fajardo', 'm', N'00121292742', N'Estudiante';
EXEC sp_persona_create N'Sigfrido', N'Simó Benavent', 'm', N'07111164182', N'Estudiante';
EXEC sp_persona_create N'Juan Antonio', N'Solana Torrijos', 'm', N'02100290222', N'Estudiante';
EXEC sp_persona_create N'Édgar Manu', N'Suárez Anglada', 'm', N'05052331549', N'Estudiante';
EXEC sp_persona_create N'Hugo', N'Tamarit Hoz', 'm', N'00050113769', N'Estudiante';
EXEC sp_persona_create N'Vasco', N'Tamayo Barrio', 'm', N'04010723166', N'Estudiante';
EXEC sp_persona_create N'Victoriano', N'Tapia Cabanillas', 'm', N'01030679668', N'Estudiante';
EXEC sp_persona_create N'Victor Manuel', N'Tejedor Nevado', 'm', N'06110657501', N'Estudiante';
EXEC sp_persona_create N'Luis Ángel', N'Téllez Conde', 'm', N'01030354747', N'Estudiante';
EXEC sp_persona_create N'Máximo', N'Téllez Ferrera', 'm', N'07100774366', N'Estudiante';
EXEC sp_persona_create N'Baltasar', N'Téllez Vargas', 'm', N'05010834583', N'Estudiante';
EXEC sp_persona_create N'Alcides', N'Tello Fernandez', 'm', N'09031068065', N'Estudiante';
EXEC sp_persona_create N'Rodolfo Duilio', N'Terrón Serrano', 'm', N'03122019904', N'Estudiante';
EXEC sp_persona_create N'Yago', N'Teruel Palomino', 'm', N'06112069287', N'Estudiante';
EXEC sp_persona_create N'Roberto', N'Tomás Bermúdez', 'm', N'00061998800', N'Estudiante';
EXEC sp_persona_create N'Damián', N'Tomas Solís', 'm', N'01050179685', N'Estudiante';
EXEC sp_persona_create N'Anacleto', N'Tomás Torrecilla', 'm', N'09041970662', N'Estudiante';
EXEC sp_persona_create N'Gerardo', N'Tomá Real', 'm', N'07060574389', N'Estudiante';
EXEC sp_persona_create N'Hugo', N'Torrecilla Canals', 'm', N'03091590427', N'Estudiante';
EXEC sp_persona_create N'Jonatan', N'Trillo Larrañaga', 'm', N'01050545567', N'Estudiante';
EXEC sp_persona_create N'Abel', N'Uribe Cerro', 'm', N'00061631407', N'Estudiante';
EXEC sp_persona_create N'Sigfrido', N'Urrutia Vargas', 'm', N'01022472085', N'Estudiante';
EXEC sp_persona_create N'Juan Bautista', N'Valderrama Méndez', 'm', N'01052377727', N'Estudiante';
EXEC sp_persona_create N'Victoriano', N'Valencia Coloma', 'm', N'08060598126', N'Estudiante';
EXEC sp_persona_create N'Jose', N'Valenciano Osuna', 'm', N'04071412349', N'Estudiante';
EXEC sp_persona_create N'Valerio', N'Valentín Céspedes', 'm', N'09050514842', N'Estudiante';
EXEC sp_persona_create N'Carmelo', N'Valentín Mancebo', 'm', N'07010837047', N'Estudiante';
EXEC sp_persona_create N'Isaac', N'Vallejo Coll', 'm', N'06061188741', N'Estudiante';
EXEC sp_persona_create N'Natanael', N'Vallés Prieto', 'm', N'08112858462', N'Estudiante';
EXEC sp_persona_create N'Esteban', N'Vaquero Dominguez', 'm', N'00080317424', N'Estudiante';
EXEC sp_persona_create N'Quirino Rosario', N'Vázquez Carvajal', 'm', N'00011789545', N'Estudiante';
EXEC sp_persona_create N'Manu', N'Vega Girona', 'm', N'00061011241', N'Estudiante';
EXEC sp_persona_create N'José Ángel', N'Vélez Vila', 'm', N'08011425141', N'Estudiante';
EXEC sp_persona_create N'Lucas', N'Vendrell Valverde', 'm', N'07121186389', N'Estudiante';
EXEC sp_persona_create N'Alcides', N'Viana Benavente', 'm', N'08091550202', N'Estudiante';
EXEC sp_persona_create N'Damián', N'Vicens Cabrera', 'm', N'05110316782', N'Estudiante';
EXEC sp_persona_create N'Jerónimo', N'Vidal Hernandez', 'm', N'01100540969', N'Estudiante';
EXEC sp_persona_create N'Eustaquio', N'Vigil Gil', 'm', N'03032892061', N'Estudiante';
EXEC sp_persona_create N'Maximino', N'Vigil Morante', 'm', N'05060587629', N'Estudiante';
EXEC sp_persona_create N'Jose Ignacio', N'Vila Rios', 'm', N'01040682009', N'Estudiante';
EXEC sp_persona_create N'Silvio', N'Vilanova Amador', 'm', N'06101520605', N'Estudiante';
EXEC sp_persona_create N'Román', N'Villa Marqués', 'm', N'03122421525', N'Estudiante';
EXEC sp_persona_create N'Remigio', N'Villalonga Roman', 'm', N'00102956565', N'Estudiante';
EXEC sp_persona_create N'Miguel', N'Villegas Jover', 'm', N'02043082529', N'Estudiante';
EXEC sp_persona_create N'Desiderio', N'Vizcaíno Seguí', 'm', N'02101483786', N'Estudiante';
EXEC sp_persona_create N'Plácido', N'Zabala Feijoo', 'm', N'09011988483', N'Estudiante';
EXEC sp_persona_create N'Rafael Edu', N'Zamorano Ochoa', 'm', N'03100954346', N'Estudiante';
EXEC sp_persona_create N'Santos', N'Zurita Barrio', 'm', N'01100643902', N'Estudiante';