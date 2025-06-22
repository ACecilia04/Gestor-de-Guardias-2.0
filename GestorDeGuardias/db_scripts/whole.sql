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
/****** Object:  StoredProcedure [dbo].[sp_turno_de_guardia_update]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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

END

GO
/****** Object:  StoredProcedure [dbo].[sp_usuario_create]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_delete]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_read]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_read_by_id]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_read_by_nombre]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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
/****** Object:  StoredProcedure [dbo].[sp_usuario_update]    Script Date: 17/06/2025 04:47:02 p. m. ******/
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