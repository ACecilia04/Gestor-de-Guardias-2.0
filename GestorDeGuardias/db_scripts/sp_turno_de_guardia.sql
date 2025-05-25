SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE sp_create_turno_de_guardia
    @persona_asignada bigint,
    @fecha date,
    @horario bigint
AS
BEGIN
    INSERT INTO turno_de_guardia (persona_asignada, fecha, horario)
    VALUES (@persona_asignada, @fecha, @horario);
END
GO

CREATE PROCEDURE sp_read_turno_de_guardia
AS
BEGIN
    SELECT * FROM turno_de_guardia;
END
GO

CREATE PROCEDURE sp_read_turno_de_guardia_by_pk
    @persona_asignada bigint,
    @fecha date,
    @horario bigint
AS
BEGIN
    SELECT * FROM turno_de_guardia
    WHERE persona_asignada = @persona_asignada
      AND fecha = @fecha
      AND horario = @horario;
END
GO

CREATE PROCEDURE sp_update_turno_de_guardia
    @persona_asignada bigint,
    @fecha date,
    @horario bigint,
    @hecho bit
AS
BEGIN
    UPDATE turno_de_guardia
    SET hecho = @hecho
    WHERE persona_asignada = @persona_asignada
      AND fecha = @fecha
      AND horario = @horario;
END
GO

CREATE PROCEDURE sp_delete_turno_de_guardia
    @persona_asignada bigint,
    @fecha date,
    @horario bigint
AS
BEGIN
    DELETE FROM turno_de_guardia
    WHERE persona_asignada = @persona_asignada
      AND fecha = @fecha
      AND horario = @horario;
END
GO
