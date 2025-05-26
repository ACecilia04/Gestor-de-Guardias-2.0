-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

CREATE PROCEDURE sp_create_persona
    @nombre NVARCHAR(50),
    @apellido NVARCHAR(50),
    @sexo CHAR(1),
    @carnet NVARCHAR(11),
    @tipo NVARCHAR(20)
AS
BEGIN
    INSERT INTO persona (nombre, apellido, sexo, carnet, tipo)
    VALUES (@nombre, @apellido, @sexo, @carnet, @tipo);
    
    RETURN;
END
GO

CREATE PROCEDURE sp_read_persona
AS
BEGIN
    SELECT * FROM persona;
END
GO

CREATE PROCEDURE sp_read_persona_by_ci
    @carnet nvarchar(11)
AS
BEGIN
    SELECT * FROM persona WHERE carnet = @carnet;
END
GO

CREATE PROCEDURE sp_read_persona_by_tipo
    @tipo NVARCHAR(20)
AS
BEGIN
    SELECT * FROM persona
    WHERE tipo = @tipo;
END
GO

CREATE PROCEDURE sp_read_persona_by_baja
    @baja date
AS
BEGIN
    SELECT * FROM Persona
    WHERE baja = @baja;
END
GO

CREATE PROCEDURE sp_update_persona
    @id BIGINT,
    @nombre NVARCHAR(50) = NULL,
    @apellido NVARCHAR(50) = NULL,
    @sexo CHAR(1) = NULL,
    @carnet NVARCHAR(11) = NULL,
    @ultima_guardia_hecha DATE = NULL,
    @guardias_de_recuperacion INT = NULL,
    @baja DATE = NULL,
    @reincorporacion DATE = NULL,
    @tipo NVARCHAR(20) = NULL,
    @activo BIT = NULL
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM persona WHERE id = @id)
    BEGIN
    -- seguro hay una mejor manera de manejar esto
        PRINT 'Error: El identificador de esta persona no se ha encontrado.';
        RETURN;
    END
    -- si algo entra nulo no lo cambia
    UPDATE persona
    SET nombre = COALESCE(@nombre, nombre),
        apellido = COALESCE(@apellido, apellido),
        sexo = COALESCE(@sexo, sexo),
        carnet = COALESCE(@carnet, carnet),
        ultima_guardia_hecha = COALESCE(@ultima_guardia_hecha, ultima_guardia_hecha),
        guardias_de_recuperacion = COALESCE(@guardias_de_recuperacion, guardias_de_recuperacion),
        baja = COALESCE(@baja, baja),
        reincorporacion = COALESCE(@reincorporacion, reincorporacion),
        tipo = COALESCE(@tipo, tipo),
        activo = COALESCE(@activo, activo)
    WHERE id = @id;
    
    PRINT 'Persona actualizada.'
END
GO

CREATE PROCEDURE sp_delete_persona_by_ci
    @carnet nvarchar(11)
AS
BEGIN
    DELETE FROM persona WHERE carnet = @carnet;
END
GO

CREATE PROCEDURE sp_delete_persona_by_id
    @id nvarchar(11)
AS
BEGIN
    DELETE FROM persona WHERE id = @id;
END
GO
