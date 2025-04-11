package br.com.dio.Board.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import static br.com.dio.Board.persistence.entity.BoardColumnKindEnum.INITIAL;

public class BoardEntity {

    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumnEntities = new ArrayList<>();

    public BoardColumnEntity getInitalColumn(){
        return boardColumnEntities.stream()
                .filter(bc -> bc.getKind().equals(INITIAL))
                .findFirst().orElseThrow();
    }
    public List<BoardColumnEntity> getBoardColumnEntities() {
        return boardColumnEntities;
    }

    public void setBoardColumnEntities(List<BoardColumnEntity> boardColumnEntities) {
        this.boardColumnEntities = boardColumnEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BoardEntity{" +
                "id=" + id +
                ", name='" + name + '\'';
    }
}
