package br.com.dio.Board.dto;

import br.com.dio.Board.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id,
                             String name,
                             BoardColumnKindEnum kind,
                             int cards_amount) {
}
