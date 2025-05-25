SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

CREATE PROCEDURE sp_create_usuario
    @nombre nvarchar(50),
    @contrasenna nvarchar(50),
    @rol nvarchar(50)
AS
BEGIN
    INSERT INTO usuario (nombre, contrasenna, rol)
    VALUES (@nombre, @contrasenna, @rol);
END
GO

CREATE PROCEDURE sp_read_usuario
AS
BEGIN
    SELECT * FROM usuario;
END
GO

CREATE PROCEDURE sp_read_usuario_by_id
    @id bigint
AS
BEGIN
    SELECT * FROM usuario WHERE id = @id;
END
GO

CREATE PROCEDURE sp_update_usuario
    @nombre nvarchar(50),
    @contrasenna nvarchar(50),
    @nuevo_nombre nvarchar(50),
    @nuevo_contrasenna nvarchar(50),
    @rol nvarchar(50)
AS
BEGIN
    UPDATE usuario
    SET nombre = @nuevo_nombre,
        contrasenna = @nuevo_contrasenna,
        rol = @rol
    WHERE nombre = @nombre AND contrasenna = @contrasenna;
END
GO

CREATE PROCEDURE sp_delete_usuario
    @nombre nvarchar(50),
    @contrasenna nvarchar(50)
AS
BEGIN
    DELETE FROM usuario WHERE nombre = @nombre AND contrasenna = @contrasenna;
END
GO

