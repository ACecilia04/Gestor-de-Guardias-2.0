package services;

import model.Esquema;
import utils.abstracts.MainBaseDao;
import utils.abstracts.mappers.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EsquemaServices {

    private final MainBaseDao baseDao;

    public EsquemaServices(MainBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    // CREATE
    public void insertEsquema(int diaSemana, boolean diaEsReceso, Character tipoPersona, Character sexo, int cantPersonas) {
        baseDao.spUpdate("sp_create_esquema(?, ?, ?, ?, ?)",
                diaSemana, diaEsReceso, tipoPersona, sexo, cantPersonas);
    }

    // READ all
    public List<Esquema> getAllEsquemas() {
        return baseDao.spQuery("sp_read_esquema", new EsquemaMapper());
    }

    // READ by primary key
    public Esquema getEsquemaByPk(int diaSemana, boolean diaEsReceso) {
        return baseDao.spQuerySingleObject("sp_read_esquema_by_pk(?, ?)", new EsquemaMapper(), diaSemana, diaEsReceso);
    }

    // UPDATE
    public void updateEsquema(int diaSemana, boolean diaEsReceso, Character tipoPersona, Character sexo, int cantPersonas) {
        baseDao.spUpdate("sp_update_esquema(?, ?, ?, ?, ?)", diaSemana, diaEsReceso, tipoPersona, sexo, cantPersonas);
    }

    // DELETE
    public void deleteEsquema(int diaSemana, boolean diaEsReceso) {
        baseDao.spUpdate("sp_delete_esquema(?, ?)", diaSemana, diaEsReceso);
    }

    // Internal Mapper
    private static class EsquemaMapper implements RowMapper<Esquema> {
        @Override
        public Esquema mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Esquema(
                    rs.getInt("dia_semana"),
                    rs.getBoolean("dia_es_receso"),
                    rs.getString("tipo_persona") != null ? rs.getString("tipo_persona") : null,
                    rs.getString("sexo") != null ? rs.getString("sexo").charAt(0) : null,
                    rs.getInt("cant_personas")
            );
        }
    }
}