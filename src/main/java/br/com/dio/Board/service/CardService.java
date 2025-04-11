package br.com.dio.Board.service;

import br.com.dio.Board.dto.BoardColumnInfoDTO;
import br.com.dio.Board.dto.CardDetailsDTO;
import br.com.dio.Board.exceptions.CardBlockedException;
import br.com.dio.Board.exceptions.CardFinishedException;
import br.com.dio.Board.exceptions.EntityNotFoundException;
import br.com.dio.Board.persistence.dao.CardDAO;
import br.com.dio.Board.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.dio.Board.persistence.entity.BoardColumnKindEnum.FINAL;

public class CardService {

    private final Connection connection;

    public CardService(Connection connection) {
        this.connection = connection;
    }

    public CardEntity insert(final CardEntity entity) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardsColumnsInfo) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);

            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );
            var nextColumn = canBeMove(cardId, boardsColumnsInfo, dto);

            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }

    }

    private BoardColumnInfoDTO canBeMove(Long cardId, List<BoardColumnInfoDTO> boardsColumnsInfo, CardDetailsDTO dto) {
        if (dto.blocked())
            throw new CardBlockedException("O card %s está bloqueado, é necessário desbloquea-lo para mover".formatted(cardId));
        var currentColumn = boardsColumnsInfo.stream()
                .filter(
                        bc -> bc.id().equals(dto.columnId()))
                .findFirst().
                orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
        if (currentColumn.kind().equals(FINAL))
            throw new CardFinishedException("O card está na coluna de itens finalizados");

        var nextColumn = boardsColumnsInfo.stream()
                .filter(bc -> bc.order() == currentColumn.order() + 1)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("O card está cancelado"));
        return nextColumn;
    }

    public void moveToCancelColumn(final Long cardId, final List<BoardColumnInfoDTO> boardsColumnsInfo,final Long cancelColumnId) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);

            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );
            var cancelColumn = canBeMove(cardId, boardsColumnsInfo, dto);
            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }

    }

    
}
