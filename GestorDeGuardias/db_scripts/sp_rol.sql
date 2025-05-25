
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_create_rol
    @nombre nvarchar(50)
AS
BEGIN
    INSERT INTO rol (nombre) VALUES (@nombre);
END
GO

CREATE PROCEDURE sp_read_rol
AS
BEGIN
    SELECT * FROM rol;
END
GO

CREATE PROCEDURE sp_read_rol_by_nombre
    @nombre nvarchar(50)
AS
BEGIN
    SELECT * FROM rol WHERE nombre = @nombre;
END
GO

CREATE PROCEDURE sp_update_rol
    @nombre nvarchar(50),
    @nuevo_nombre nvarchar(50)
AS
BEGIN
    UPDATE rol
    SET nombre = @nuevo_nombre
    WHERE nombre = @nombre;
END
GO

CREATE PROCEDURE sp_delete_rol
    @nombre nvarchar(50)
AS
BEGIN
    DELETE FROM rol WHERE nombre = @nombre;
END
GO
