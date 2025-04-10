package br.com.dio.Board.ui;

import br.com.dio.Board.persistence.entity.BoardColumnEntity;
import br.com.dio.Board.persistence.entity.BoardColumnKindEnum;
import br.com.dio.Board.persistence.entity.BoardEntity;
import br.com.dio.Board.service.BoardQueryService;
import br.com.dio.Board.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.Board.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.Board.persistence.entity.BoardColumnKindEnum.*;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada");

        var optional = -1;
        while (true){
            System.out.println("1- Criar um novo board");
            System.out.println("2- Selecionar um board existente");
            System.out.println("3- excluir um board");
            System.out.println("4 - sair");
            optional = scanner.nextInt();

            switch (optional){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.err.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException{
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board");
        entity.setName(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? se sim informe quantas, se não digite '0'.");
        var additionalCollumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        var initialColumnName = scanner.next();
        var initialColumn = createCollumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalCollumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
            var pendingColumnName = scanner.next();
            var pendingColumn = createCollumn(pendingColumnName, PENDING, i+1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final");
        var finalColumnName = scanner.next();
        var finalColumn = createCollumn(finalColumnName, FINAL, additionalCollumns+1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scanner.next();
        var cancelColumn = createCollumn(cancelColumnName, CANCEL, additionalCollumns+1);
        columns.add(cancelColumn);

        entity.setBoardColumnEntities(columns);
        try (var connection = getConnection()){
            new BoardService(connection).insert(entity);
        }

    }

    private void selectBoard() throws SQLException{
        System.out.println("Informe o id do board que deseja selecionar");
        var id = scanner.nextLong();
        try (var connection = getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b ->new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado o board com id %s\n", id)
                    );

        }

    }

    private void deleteBoard() throws SQLException{
        System.out.println("Informe o id do board que será excluido");
        var id = scanner.nextLong();

        try (var connection = getConnection()){
            var service = new BoardService(connection);
            if(service.delete(id)){
                System.out.printf("O board %s foi excluido\n", id);
            }else {
                System.out.printf("Não foi encontrado o board com id %s\n", id);
            }
        }
    }

    private BoardColumnEntity createCollumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
