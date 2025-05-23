package utils;

import utils.abstracts.LongMapper;

import java.util.List;

public class GrupoDao {
    private final MainBaseDao baseDao = new MainBaseDao(); // mejor ponerla en un Singleton
//
//    public List<Grupo> retrieveAll() {
//        String query =
//                "SELECT * " +
//                        "FROM grupo";
//
//        return baseDao.query(query, new GrupoMapper());
//    }
//
//    public Grupo retrieveOne(Long id) {
//        String query =
//                "SELECT * " +
//                        "FROM grupo " +
//                        "WHERE id = ?";
//
//        return baseDao.querySingleObject(query, new GrupoMapper(), id);
//    }
//
//    public Long add(Grupo grupo) {
//        String query =
//                "DECLARE @Ids_tbl TABLE ([ID] INT) " +
//                        "INSERT INTO grupo (" +
//                        "numero," +
//                        "cant_estudiantes) " +
//                        "OUTPUT INSERTED.ID INTO @Ids_tbl(ID) " +
//                        "VALUES(?, ?)" +
//                        "SELECT [ID] FROM @Ids_tbl";
//
//        Long lastInsertedId = baseDao.querySingleObject(query, new LongMapper("id"),
//                grupo.getNumero(),
//                grupo.getCantEstudiantes());
//
//        return lastInsertedId;
//    }
//
//    public void update(Grupo grupo) {
//        String query =
//                "UPDATE grupo SET " +
//                        "numero = ?," +
//                        "cant_estudiantes = ? " +
//                        "WHERE id = ?";
//
//        baseDao.update(query,
//                grupo.getNumero(),
//                grupo.getCantEstudiantes(),
//                grupo.getId());
//    }
//
//    public void remove(Long id) {
//        String query =
//                "DELETE FROM grupo " +
//                        "WHERE id = ?";
//
//        baseDao.update(query, id);
//    }
}
