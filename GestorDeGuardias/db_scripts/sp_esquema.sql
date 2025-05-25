SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_create_esquema
    @dia_semana tinyint,
    @dia_es_receso bit,
    @tipo_persona nvarchar(20) = NULL,
    @sexo nchar(1) = NULL,
    @cant_personas tinyint
AS
BEGIN
    INSERT INTO esquema (dia_semana, dia_es_receso, tipo_persona, sexo, cant_personas)
    VALUES (@dia_semana, @dia_es_receso, @tipo_persona, @sexo, @cant_personas);
END
GO

CREATE PROCEDURE sp_read_esquema
AS
BEGIN
    SELECT * FROM esquema;
END
GO

CREATE PROCEDURE sp_read_esquema_by_id
    @id bigint
AS
BEGIN
    SELECT * FROM esquema WHERE id = @id;
END
GO

CREATE PROCEDURE sp_read_esquema_by_dia_semana_and_dia_receso
    @dia_semana tinyint,
    @dia_es_receso bit
AS
BEGIN
    SELECT * FROM esquema
    WHERE dia_semana = @dia_semana AND dia_es_receso = @dia_es_receso
END
GO

CREATE PROCEDURE sp_update_esquema
    @id bigint,
    @dia_semana tinyint,
    @dia_es_receso bit,
    @tipo_persona nchar(1) = NULL,
    @sexo nchar(1) = NULL,
    @cant_personas tinyint
AS
BEGIN
    UPDATE esquema
    SET dia_semana = @dia_semana,
        dia_es_receso = @dia_es_receso,
        tipo_persona = @tipo_persona,
        sexo = @sexo,
        cant_personas = @cant_personas
    WHERE id = @id;
END
GO

CREATE PROCEDURE sp_delete_esquema
    @id bigint
AS
BEGIN
    DELETE FROM esquema WHERE id = @id;
END
GO
