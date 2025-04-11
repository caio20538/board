package br.com.dio.Board.dto;


import java.time.OffsetDateTime;

public record CardDetailsDTO(Long id,
                             String title,
                             String description,
                             boolean blocked,
                             OffsetDateTime bloackedAt,
                             String blockReason,
                             int blocksAmount,
                             Long columnId,
                             String columnName
) {
}
