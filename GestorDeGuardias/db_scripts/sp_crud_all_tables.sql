-- ==========================================
-- PROCEDIMIENTOS CRUD PARA TODAS LAS TABLAS
-- Base: Gestor_de_Guardias
-- Autor: Copilot
-- ==========================================

-------------------------------------------------------
-- TABLA: configuracion
-------------------------------------------------------

CREATE PROCEDURE sp_create_configuracion
    @hora_inicio time,
    @hora_fin time,
    @dia_semana tinyint,
    @dia_es_receso bit,
    @actual bit
AS
BEGIN
    INSERT INTO configuracion (hora_inicio, hora_fin, dia_semana, dia_es_receso, actual)
    VALUES (@hora_inicio, @hora_fin, @dia_semana, @dia_es_receso, @actual);
END
GO

CREATE PROCEDURE sp_read_configuracion
AS
BEGIN
    SELECT * FROM configuracion;
END
GO

CREATE PROCEDURE sp_read_configuracion_by_pk
    @hora_inicio time,
    @hora_fin time,
    @dia_semana tinyint,
    @dia_es_receso bit
AS
BEGIN
    SELECT * FROM configuracion
    WHERE hora_inicio = @hora_inicio
      AND hora_fin = @hora_fin
      AND dia_semana = @dia_semana
      AND dia_es_receso = @dia_es_receso;
END
GO

CREATE PROCEDURE sp_update_configuracion
    @hora_inicio time,
    @hora_fin time,
    @dia_semana tinyint,
    @dia_es_receso bit,
    @actual bit
AS
BEGIN
    UPDATE configuracion
    SET actual = @actual
    WHERE hora_inicio = @hora_inicio
      AND hora_fin = @hora_fin
      AND dia_semana = @dia_semana
      AND dia_es_receso = @dia_es_receso;
END
GO

CREATE PROCEDURE sp_delete_configuracion
    @hora_inicio time,
    @hora_fin time,
    @dia_semana tinyint,
    @dia_es_receso bit
AS
BEGIN
    DELETE FROM configuracion
    WHERE hora_inicio = @hora_inicio
      AND hora_fin = @hora_fin
      AND dia_semana = @dia_semana
      AND dia_es_receso = @dia_es_receso;
END
GO

-------------------------------------------------------
-- TABLA: esquema
-------------------------------------------------------

CREATE PROCEDURE sp_create_esquema
    @dia_semana tinyint,
    @dia_es_receso bit,
    @tipo_persona nchar(1) = NULL,
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

CREATE PROCEDURE sp_read_esquema_by_pk
    @dia_semana tinyint,
    @dia_es_receso bit
AS
BEGIN
    SELECT * FROM esquema
    WHERE dia_semana = @dia_semana
      AND dia_es_receso = @dia_es_receso;
END
GO

CREATE PROCEDURE sp_update_esquema
    @dia_semana tinyint,
    @dia_es_receso bit,
    @tipo_persona nchar(1) = NULL,
    @sexo nchar(1) = NULL,
    @cant_personas tinyint
AS
BEGIN
    UPDATE esquema
    SET tipo_persona = @tipo_persona,
        sexo = @sexo,
        cant_personas = @cant_personas
    WHERE dia_semana = @dia_semana
      AND dia_es_receso = @dia_es_receso;
END
GO

CREATE PROCEDURE sp_delete_esquema
    @dia_semana tinyint,
    @dia_es_receso bit
AS
BEGIN
    DELETE FROM esquema
    WHERE dia_semana = @dia_semana
      AND dia_es_receso = @dia_es_receso;
END
GO

-------------------------------------------------------
-- TABLA: horario
-------------------------------------------------------

CREATE PROCEDURE sp_create_horario
    @inicio time,
    @fin time
AS
BEGIN
    INSERT INTO horario (inicio, fin) VALUES (@inicio, @fin);
END
GO

CREATE PROCEDURE sp_read_horario
AS
BEGIN
    SELECT * FROM horario;
END
GO

CREATE PROCEDURE sp_read_horario_by_pk
    @inicio time,
    @fin time
AS
BEGIN
    SELECT * FROM horario WHERE inicio = @inicio AND fin = @fin;
END
GO

CREATE PROCEDURE sp_update_horario
    @inicio time,
    @fin time,
    @nuevo_inicio time,
    @nuevo_fin time
AS
BEGIN
    UPDATE horario
    SET inicio = @nuevo_inicio,
        fin = @nuevo_fin
    WHERE inicio = @inicio AND fin = @fin;
END
GO

CREATE PROCEDURE sp_delete_horario
    @inicio time,
    @fin time
AS
BEGIN
    DELETE FROM horario WHERE inicio = @inicio AND fin = @fin;
END
GO

-------------------------------------------------------
-- TABLA: periodo_no_planificable
-------------------------------------------------------

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

-------------------------------------------------------
-- TABLA: persona
-------------------------------------------------------

CREATE PROCEDURE sp_create_persona
    @nombre nvarchar(50),
    @apellido nvarchar(50),
    @sexo char(1),
    @carnet nvarchar(11),
    @ultima_guardia_hecha date = NULL,
    @guardias_de_recuperacion int,
    @baja date = NULL,
    @reincorporacion date = NULL,
    @tipo nvarchar(20),
    @activo bit
AS
BEGIN
    INSERT INTO persona (nombre, apellido, sexo, carnet, ultima_guardia_hecha, guardias_de_recuperacion, baja, reincorporacion, tipo, activo)
    VALUES (@nombre, @apellido, @sexo, @carnet, @ultima_guardia_hecha, @guardias_de_recuperacion, @baja, @reincorporacion, @tipo, @activo);
END
GO

CREATE PROCEDURE sp_read_persona
AS
BEGIN
    SELECT * FROM persona;
END
GO

CREATE PROCEDURE sp_read_persona_by_id
    @id bigint
AS
BEGIN
    SELECT * FROM persona WHERE id = @id;
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

CREATE PROCEDURE sp_update_persona
    @id bigint,
    @nombre nvarchar(50),
    @apellido nvarchar(50),
    @sexo char(1),
    @carnet nvarchar(11),
    @ultima_guardia_hecha date = NULL,
    @guardias_de_recuperacion int,
    @baja date = NULL,
    @reincorporacion date = NULL,
    @tipo nvarchar(20),
    @activo bit
AS
BEGIN
    UPDATE persona
    SET nombre = @nombre,
        apellido = @apellido,
        sexo = @sexo,
        carnet = @carnet,
        ultima_guardia_hecha = @ultima_guardia_hecha,
        guardias_de_recuperacion = @guardias_de_recuperacion,
        baja = @baja,
        reincorporacion = @reincorporacion,
        tipo = @tipo,
        activo = @activo
    WHERE id = @id;
END
GO

CREATE PROCEDURE sp_delete_persona
    @id bigint
AS
BEGIN
    DELETE FROM persona WHERE id = @id;
END
GO

-------------------------------------------------------
-- TABLA: rol
-------------------------------------------------------

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

-------------------------------------------------------
-- TABLA: tipo_persona
-------------------------------------------------------

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

-------------------------------------------------------
-- TABLA: turno_de_guardia
-------------------------------------------------------

CREATE PROCEDURE sp_create_turno_de_guardia
    @hora_inicio time,
    @hora_fin time,
    @persona_asignada bigint,
    @hecho bit = NULL,
    @fecha date
AS
BEGIN
    INSERT INTO turno_de_guardia (hora_inicio, hora_fin, persona_asignada, hecho, fecha)
    VALUES (@hora_inicio, @hora_fin, @persona_asignada, @hecho, @fecha);
END
GO

CREATE PROCEDURE sp_read_turno_de_guardia
AS
BEGIN
    SELECT * FROM turno_de_guardia;
END
GO

CREATE PROCEDURE sp_read_turno_de_guardia_by_pk
    @hora_inicio time,
    @hora_fin time,
    @fecha date
AS
BEGIN
    SELECT * FROM turno_de_guardia
    WHERE hora_inicio = @hora_inicio
      AND hora_fin = @hora_fin
      AND fecha = @fecha;
END
GO

CREATE PROCEDURE sp_update_turno_de_guardia
    @hora_inicio time,
    @hora_fin time,
    @fecha date,
    @persona_asignada bigint,
    @hecho bit
AS
BEGIN
    UPDATE turno_de_guardia
    SET persona_asignada = @persona_asignada,
        hecho = @hecho
    WHERE hora_inicio = @hora_inicio
      AND hora_fin = @hora_fin
      AND fecha = @fecha;
END
GO

CREATE PROCEDURE sp_delete_turno_de_guardia
    @hora_inicio time,
    @hora_fin time,
    @fecha date
AS
BEGIN
    DELETE FROM turno_de_guardia
    WHERE hora_inicio = @hora_inicio
      AND hora_fin = @hora_fin
      AND fecha = @fecha;
END
GO

-------------------------------------------------------
-- TABLA: usuario
-------------------------------------------------------

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

CREATE PROCEDURE sp_read_usuario_by_pk
    @nombre nvarchar(50),
    @contrasenna nvarchar(50)
AS
BEGIN
    SELECT * FROM usuario WHERE nombre = @nombre AND contrasenna = @contrasenna;
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

-- ==========================================
-- Fin del archivo CRUD para todas las tablas
-- ==========================================