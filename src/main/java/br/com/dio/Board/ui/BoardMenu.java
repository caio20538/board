package br.com.dio.Board.ui;

import br.com.dio.Board.dto.BoardColumnInfoDTO;
import br.com.dio.Board.persistence.dao.CardDAO;
import br.com.dio.Board.persistence.entity.BoardColumnEntity;
import br.com.dio.Board.persistence.entity.BoardEntity;
import br.com.dio.Board.persistence.entity.CardEntity;
import br.com.dio.Board.service.BoardColumnQueryService;
import br.com.dio.Board.service.BoardQueryService;
import br.com.dio.Board.service.CardQueryService;
import br.com.dio.Board.service.CardService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.Board.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.Board.persistence.entity.BoardColumnKindEnum.INITIAL;

public class BoardMenu {
    private final BoardEntity entity;
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public BoardMenu(BoardEntity entity) {
        this.entity = entity;
    }

    public void execute() {
        try {
            System.out.printf("Bem vindo ao board %s, selecione a operação desejada\n", entity.getId());

            var optional = -1;
            while (optional != 9) {
                System.out.println("1- Criar um card");
                System.out.println("2- Mover um card");
                System.out.println("3- bloquear um card");
                System.out.println("4- Desbloquear um card");
                System.out.println("5- Cancelar um card");
                System.out.println("6- Visualizar board");
                System.out.println("7- Visualizar colunas com cards");
                System.out.println("8- Visualizar card");
                System.out.println("9- Voltar para o menu anterior");
                System.out.println("0 - sair");
                optional = scanner.nextInt();

                switch (optional) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("voltando para o menu anterior");
                    case 0 -> System.exit(0);
                    default -> System.err.println("Opção inválida, informe uma opção do menu");
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private void createCard() throws SQLException{
        var card = new CardEntity();

        System.out.println("Informe o título do card");
        card.setTitle(scanner.next());

        System.out.println("Informe a descrição do card");
        card.setDescription(scanner.next());

        card.setBoardColumn(entity.getInitalColumn());

        try (var connection = getConnection()){
            new CardService(connection).insert(card);
        }

    }

    private void moveCardToNextColumn() throws SQLException{
        System.out.println("Informe o id do card que deseja mover para a próxima coluna");
        var cardId = scanner.nextLong();
        var boardsColumnsInfo = getBoardColumnInfoDTOS();

        try (var connection = getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardsColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private List<BoardColumnInfoDTO> getBoardColumnInfoDTOS() {
        var boardsColumnsInfo = entity.getBoardColumnEntities().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        return boardsColumnsInfo;
    }

    private void blockCard() throws SQLException{
        System.out.println("Informe o id card que será bloqueado");
        var cardId = scanner.nextLong();
        System.out.println("Informe o motivo do bloqueio do card");
        var reason = scanner.next();

       try (var connection = getConnection()){
           new CardService(connection).block(cardId, reason, getBoardColumnInfoDTOS());
       } catch (RuntimeException e){
           System.out.println(e.getMessage());
       }
    }

    private void unblockCard() throws SQLException{
        System.out.println("Informe o id card que será desbloqueado");
        var cardId = scanner.nextLong();
        System.out.println("Informe o motivo do desbloqueio do card");
        var reason = scanner.next();

        try (var connection = getConnection()){
            new CardService(connection).unBlock(cardId, reason);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void cancelCard() throws SQLException{
        System.out.println("Informe o id do card que deseja mover para a coluna de cancelar");
        var cardId = scanner.nextLong();

        var cancelColumn = entity.getCancelColumn();

        var boardsColumnsInfo = getBoardColumnInfoDTOS();

        try (var connection = getConnection()){
            new CardService(connection).moveToCancelColumn(cardId,boardsColumnsInfo, cancelColumn.getId());
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void showBoard() throws SQLException {
        try (var connection = getConnection()){
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b ->{
                System.out.printf("Board [%s, %s]\n", b.id(), b.name());
                b.columnDTOS().forEach(c ->{
                    System.out.printf("Coluna [%s] tipo: [%s] tem %s cards\n", c.name(), c.kind(), c.cards_amount());
                });
            });


        }
    }

    private void showColumn() throws SQLException{
        var columnsIds = entity.getBoardColumnEntities().stream().map(BoardColumnEntity::getId).toList();
        var selectedColumn = -1L;

        while (!columnsIds.contains(selectedColumn)){
            System.out.printf("Escolha uma coluna do board %s\n", entity.getName());
            entity.getBoardColumnEntities().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getName(), c.getKind()));
            selectedColumn = scanner.nextLong();
        }

        try (var connection = getConnection()){
           var column = new BoardColumnQueryService(connection).findById(selectedColumn);

           column.ifPresent(co -> {
               System.out.printf("Coluna %s tipo %s\n", co.getName(), co.getKind());
               co.getCards().forEach(ca -> System.out.printf("Card %s : %s\nDescrição: %s\n", ca.getId(), ca.getTitle(), ca.getDescription()));
           });
        }
    }

    private void showCard() throws SQLException{
        System.out.println("Informe o id do card que deseja visualizar");
        var selectedCardId = scanner.nextLong();

        try (var connection = getConnection()){
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                            c -> {
                                System.out.printf("Card %s - %s.\n", c.id(), c.title());
                                System.out.printf("Descrição: %s\n", c.description());
                                System.out.println(c.blocked() ? "Está bloqueado. Motivo "+ c.blockReason()  : "Não está bloqueado.");
                                System.out.printf("Já foi bloqueado %s vezes\n", c.blocksAmount());
                                System.out.printf("Está no momento na coluna %s - %s\n", c.columnId(), c.columnName());
                            }
                            , () -> System.out.printf("Não existe um card com o id %s\n", selectedCardId));
        }
    }
}