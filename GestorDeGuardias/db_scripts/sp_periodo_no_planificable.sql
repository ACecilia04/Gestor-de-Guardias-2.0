SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE sp_create_periodo_no_planificable
    @inicio date,
    @fin date
AS
BEGIN
    INSERT INTO periodo_no_planificable (inicio, fin) VALUES (@inicio, @fin);
END
GO

CREATE PROCEDURE sp_read_periodo_no_planificable
AS
BEGIN
    SELECT * FROM periodo_no_planificable;
END
GO

CREATE PROCEDURE sp_read_periodo_no_planificable_by_pk
    @inicio date,
    @fin date
AS
BEGIN
    SELECT * FROM periodo_no_planificable WHERE inicio = @inicio AND fin = @fin;
END
GO

CREATE PROCEDURE sp_update_periodo_no_planificable
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

CREATE PROCEDURE sp_delete_periodo_no_planificable
    @inicio date,
    @fin date
AS
BEGIN
    DELETE FROM periodo_no_planificable WHERE inicio = @inicio AND fin = @fin;
END
GO
