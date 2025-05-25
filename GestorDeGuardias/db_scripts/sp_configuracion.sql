
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

CREATE PROCEDURE sp_create_configuracion
    @esquema bigint,
    @horario bigint
AS
BEGIN
    INSERT INTO configuracion (esquema, horario)
    VALUES (@esquema, @horario);
END
GO

CREATE PROCEDURE sp_read_configuracion
AS
BEGIN
    SELECT * FROM configuracion;
END
GO

CREATE PROCEDURE sp_read_configuracion_by_pk
    @esquema bigint,
    @horario bigint
AS
BEGIN
    SELECT * FROM configuracion
    WHERE esquema = @esquema AND horario = @horario;
END
GO

CREATE PROCEDURE sp_update_configuracion
    @esquema bigint,
    @horario bigint,
    @actual bit
AS
BEGIN
    UPDATE configuracion
    SET actual = @actual
    WHERE esquema = @esquema AND horario = @horario;
END
GO

CREATE PROCEDURE sp_delete_configuracion
    @esquema bigint,
    @horario bigint
AS
BEGIN
    DELETE FROM configuracion
    WHERE esquema = @esquema AND horario = @horario;
END
GO
