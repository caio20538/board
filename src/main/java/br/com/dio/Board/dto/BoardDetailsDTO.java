package br.com.dio.Board.dto;

import java.util.List;

public record BoardDetailsDTO(Long id, String name, List<BoardColumnDTO> columnDTOS) {
}
