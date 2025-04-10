package br.com.dio.Board.service;

import br.com.dio.Board.persistence.dao.BoardDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardService {

    private final Connection connection;

    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public boolean delete(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        try {
            if(!dao.exists(id))
                return false;
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

}
