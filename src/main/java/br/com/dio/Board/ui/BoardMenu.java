package br.com.dio.Board.ui;

import br.com.dio.Board.persistence.entity.BoardEntity;
import br.com.dio.Board.service.BoardQueryService;

import java.sql.SQLException;
import java.util.Scanner;

import static br.com.dio.Board.persistence.config.ConnectionConfig.getConnection;

public class BoardMenu {
    private final BoardEntity entity;
    private final Scanner scanner = new Scanner(System.in);

    public BoardMenu(BoardEntity entity) {
        this.entity = entity;
    }

    public void execute() {
        try {
            System.out.printf("Bem vindo ao board %s, selecione a operação desejada", entity.getId());

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

    private void createCard() {
    }

    private void moveCardToNextColumn() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
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

    private void showColumn() {
    }

    private void showCard() {
    }
}