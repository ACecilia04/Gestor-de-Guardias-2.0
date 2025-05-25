SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_create_tipo_persona
    @nombre nvarchar(20)
AS
BEGIN
    INSERT INTO tipo_persona (nombre) VALUES (@nombre);
END
GO

CREATE PROCEDURE sp_read_tipo_persona
AS
BEGIN
    SELECT * FROM tipo_persona;
END
GO

CREATE PROCEDURE sp_read_tipo_persona_by_nombre
    @nombre nvarchar(20)
AS
BEGIN
    SELECT * FROM tipo_persona WHERE nombre = @nombre;
END
GO

CREATE PROCEDURE sp_update_tipo_persona
    @nombre nvarchar(20),
    @nuevo_nombre nvarchar(20)
AS
BEGIN
    UPDATE tipo_persona
    SET nombre = @nuevo_nombre
    WHERE nombre = @nombre;
END
GO

CREATE PROCEDURE sp_delete_tipo_persona
    @nombre nvarchar(20)
AS
BEGIN
    DELETE FROM tipo_persona WHERE nombre = @nombre;
END
GO
