package br.com.dio.Board.service;


import br.com.dio.Board.dto.CardDetailsDTO;
import br.com.dio.Board.persistence.dao.CardDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CardQueryService {
    private final Connection connection;

    public CardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<CardDetailsDTO> findById(final long id) throws SQLException {
        var dao = new CardDAO(connection);
        return dao.findById(id);
    }
}
