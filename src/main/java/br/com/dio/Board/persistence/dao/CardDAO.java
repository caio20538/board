package br.com.dio.Board.persistence.dao;

import br.com.dio.Board.dto.CardDetails;

import java.sql.Connection;

public class CardDAO {
    private final Connection connection;

    public CardDAO(Connection connection) {
        this.connection = connection;
    }

    public CardDetails findById(final Long id){
        return null;
    }

}
