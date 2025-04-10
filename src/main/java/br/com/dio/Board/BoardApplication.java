package br.com.dio.Board;

import br.com.dio.Board.persistence.migration.MigrationStrategy;
import br.com.dio.Board.ui.MainMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

import static br.com.dio.Board.persistence.config.ConnectionConfig.getConnection;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) throws SQLException{
		try (var connection = getConnection()){
			new MigrationStrategy(connection).executeMigration();
		}

		new MainMenu().execute();
    }

}
