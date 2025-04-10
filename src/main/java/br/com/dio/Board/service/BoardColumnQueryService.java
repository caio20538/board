package br.com.dio.Board.service;

import br.com.dio.Board.persistence.dao.BoardColumnDAO;
import br.com.dio.Board.persistence.entity.BoardColumnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BoardColumnQueryService {
    private final Connection connection;

    public BoardColumnQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardColumnEntity> findById(final long id) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        return dao.findById(id);
    }

}
