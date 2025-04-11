package br.com.dio.Board.dto;

import br.com.dio.Board.persistence.entity.BoardColumnKindEnum;

public record BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {
}
