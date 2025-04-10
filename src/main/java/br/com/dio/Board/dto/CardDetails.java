package br.com.dio.Board.dto;


import java.time.OffsetDateTime;

public record CardDetails(Long id,
                          boolean blocked,
                          OffsetDateTime bloackedAt,
                          String blockReason,
                          int blocksAmount,
                          Long columnId,
                          String columnName
) {
}
