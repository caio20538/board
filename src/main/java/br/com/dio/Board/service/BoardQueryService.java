package br.com.dio.Board.service;

import br.com.dio.Board.dto.BoardColumnDTO;
import br.com.dio.Board.dto.BoardDetailsDTO;
import br.com.dio.Board.persistence.dao.BoardColumnDAO;
import br.com.dio.Board.persistence.dao.BoardDAO;
import br.com.dio.Board.persistence.entity.BoardColumnEntity;
import br.com.dio.Board.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.dio.Board.persistence.entity.BoardColumnKindEnum.findByName;

public class BoardQueryService {
    private final Connection connection;

    public BoardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardColumnEntities(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    public Optional<BoardDetailsDTO> showBoardDetails(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            var columns = boardColumnDAO.findByBoardIdWithDetails(entity.getId());
            var dto = new BoardDetailsDTO(entity.getId(), entity.getName(), columns);
            return Optional.of(dto);
        }
        return Optional.empty();
    }


}
