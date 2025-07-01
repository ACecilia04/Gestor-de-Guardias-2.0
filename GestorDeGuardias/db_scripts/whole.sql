USE [master]
GO
/****** Object:  Database [Gestor_de_Guardias]    Script Date: 30/06/2025 10:21:53 p. m. ******/
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
/****** Object:  User [guardiasadm]    Script Date: 30/06/2025 10:21:53 p. m. ******/
CREATE USER [guardiasadm] FOR LOGIN [guardiasadm] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [guardiasadm]
GO
/****** Object:  Table [dbo].[configuracion]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[horario]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[periodo_no_planificable]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[persona]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[rol]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[tipo_persona]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[turno_de_guardia]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Table [dbo].[usuario]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  Index [IX_usuario]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_configuracion_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_configuracion_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_configuracion_get_cant_personas_asignables]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
	SET DATEFIRST 1;
	DECLARE @esReceso BIT =
        CASE
            WHEN EXISTS (SELECT 1 FROM periodo_no_planificable WHERE @fecha BETWEEN inicio AND fin)
            THEN 1 ELSE 0
        END;


    -- Insert statements for procedure here
	SELECT cant_personas from configuracion
    WHERE dia_semana = DATEPART(WEEkDAY, @fecha) and dia_es_receso = @esReceso and horario = @horario
END
GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_configuracion_read]
AS
BEGIN
    SELECT c.*
    FROM configuracion c
    INNER JOIN horario h ON c.horario = h.id
    WHERE c.borrado = 0 AND h.borrado = 0
    ORDER BY c.dia_semana, h.inicio, h.fin;
END;

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_read_by_id]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_configuracion_read_by_pk]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
    SET DATEFIRST 1;
    SELECT * FROM configuracion
    WHERE horario = @horario
	AND dia_semana = DATEPART(WEEKDAY, @fecha)
	AND dia_es_receso = @dia_es_receso
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_configuracion_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_configuraciones_de_fecha]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
    SET DATEFIRST 1;
	  DECLARE @esReceso BIT =
        CASE
            WHEN EXISTS (SELECT 1 FROM periodo_no_planificable WHERE @fecha BETWEEN inicio AND fin)
            THEN 1 ELSE 0
        END;

	SELECT * FROM configuracion
	WHERE dia_semana = DATEPART(WEEKDAY, @fecha) AND dia_es_receso = @esReceso
END
GO
/****** Object:  StoredProcedure [dbo].[sp_get_turnos_a_partir_de]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horario_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horario_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horario_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horario_read_by_id]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horario_read_by_pk]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horario_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_horarios_de_fecha]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
	SET DATEFIRST 1;
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
	ORDER BY h.inicio asc;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_count]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_periodo_no_planificable_count]
AS
BEGIN
SELECT COUNT(*) AS total FROM periodo_no_planificable
END

GO
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_has_date]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_in_date]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_read_by_pk]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_periodo_no_planificable_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_count_by_tipo]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_delete_by_ci]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_delete_by_id]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_get_disponible_para_turno]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
 SELECT p.*
    FROM persona p
    LEFT JOIN (
        SELECT persona_asignada, MAX(fecha) AS ultima_guardia
        FROM turno_de_guardia
        WHERE borrado = 0
        GROUP BY persona_asignada
    ) tg ON p.id = tg.persona_asignada
    WHERE
        p.tipo = @tipo
        AND p.sexo = COALESCE(@sexo, p.sexo)
        AND (@fecha < p.baja OR p.baja IS NULL)
        AND (@fecha > p.reincorporacion OR p.reincorporacion IS NULL)
AND (
         tg.ultima_guardia IS NULL OR
         (tg.ultima_guardia IS NOT NULL AND tg.ultima_guardia >= DATEADD(DAY, -31, @fecha))
        )
    ORDER BY p.guardias_de_recuperacion, p.apellido, p.nombre;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_read]
AS
BEGIN
    SELECT * FROM persona
	WHERE borrado = 0
	ORDER BY apellido, nombre;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_baja]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_ci]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_id]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_read_by_id]
    @id bigint
AS
BEGIN
    SELECT * FROM persona
	WHERE id = @id
	AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_read_by_tipo]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
	AND borrado = 0
    Order By apellido, nombre;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_persona_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_persona_update_baja]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_persona_update_baja]
    @carnet BIGINT,
    @baja DATE
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM persona
		WHERE carnet = @carnet AND borrado = 0
	);

	IF (@count = 0)
		THROW 51000, 'Persona inexistente', 1

    UPDATE persona
		SET baja = @baja
		WHERE carnet = @carnet AND borrado = 0;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_rol_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_rol_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_rol_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_rol_read_by_nombre]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_rol_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_read_by_nombre]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_tipo_persona_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE fecha = @fecha
		AND horario = @horario
		AND persona_asignada = @persona_asignada
		AND borrado = 0

	);

	IF (@count > 0)
		THROW 51000, 'Turno de guardia existente', 1

    INSERT INTO turno_de_guardia (persona_asignada, fecha, horario)
	    VALUES (@persona_asignada, @fecha, @horario);
		UPDATE persona
    SET ultima_guardia_hecha = @fecha
    WHERE id = @persona_asignada
      AND (ultima_guardia_hecha IS NULL OR @fecha > ultima_guardia_hecha);
END


GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_delete_a_partir_de_fecha]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_delete_a_partir_de_fecha]
    @fecha DATE
AS
BEGIN
	DECLARE @count int;

	SET @count = (
		SELECT COUNT(*)
		FROM turno_de_guardia
		WHERE fecha > @fecha or fecha = @fecha
	);

	IF (@count = 0)
		THROW 51000, 'No hay turnos de guardia despues de esta fecha.', 1;

      UPDATE turno_de_guardia
		SET borrado = 1
		WHERE fecha > @fecha or fecha = @fecha;

END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read]
AS
BEGIN
    SELECT
       tg.*
    FROM turno_de_guardia tg
    WHERE tg.borrado = 0
    ORDER BY tg.fecha;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read_a_partir_de]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read_a_partir_de]
    @fecha DATE
AS
BEGIN
    SELECT
       tg.*
    FROM turno_de_guardia tg
    WHERE tg.borrado = 0
    AND MONTH(tg.fecha) = MONTH(@fecha) AND YEAR(tg.fecha) = YEAR(@fecha)
    ORDER BY tg.fecha;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read_by_id]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read_by_id]
    @id bigint
AS
BEGIN
    SELECT
       tg.*
    FROM turno_de_guardia tg
    WHERE tg.id = @id
    ORDER BY tg.fecha;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read_by_pk]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read_by_pk]
    @fecha date,
    @horario bigint,
    @persona_asignada bigint
AS
BEGIN
    SELECT
       tg.*
    FROM turno_de_guardia tg
    WHERE tg.fecha = @fecha
    AND tg.horario = @horario
    AND tg.persona_asignada = @persona_asignada
    AND borrado = 0

    ORDER BY tg.fecha;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_read_last]    Script Date: 30/06/2025 10:21:54 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_turno_de_guardia_read_last]
AS
BEGIN
SELECT TOP 1 *
FROM dbo.turno_de_guardia
WHERE borrado = 0
ORDER BY id DESC;
END
GO
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
		AND persona_asignada = @persona_asignada
		AND borrado = 0
		AND id <> @id
	);

	IF (@count > 0)
		THROW 51000, 'Turno de guardia existente', 1

    UPDATE turno_de_guardia
    SET hecho = @hecho,
        persona_asignada = @persona_asignada,
        fecha = @fecha,
        horario = @horario
    WHERE id = @id;

	UPDATE persona
    SET ultima_guardia_hecha = (
        SELECT MAX(fecha)
        FROM turno_de_guardia
        WHERE persona_asignada = @persona_asignada
          AND borrado = 0
    )
    WHERE id = @persona_asignada;
END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_create]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_delete]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_read]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_read_by_id]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_read_by_nombre]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_update]    Script Date: 30/06/2025 10:21:54 p. m. ******/
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
