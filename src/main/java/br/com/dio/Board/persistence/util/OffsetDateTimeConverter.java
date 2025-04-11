package br.com.dio.Board.persistence.util;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;

public final class OffsetDateTimeConverter {

    private OffsetDateTimeConverter() {
    }

    public static OffsetDateTime toOffsetDateTime(final Timestamp value){
        return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC): null;
    }

}
