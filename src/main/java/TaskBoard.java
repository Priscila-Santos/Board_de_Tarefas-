import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskBoard {

    private static final String URL = "jdbc:postgresql://localhost:5432/taskboard";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            criarTabelas(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void criarTabelas(Connection connection) throws SQLException {
        String criarTabelaBoard = "CREATE TABLE Board (" +
                "id BIGINT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL" +
                ");";

        String criarTabelaBoarColumn = "CREATE TABLE BoarColumn (" +
                "id BIGINT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "kind VARCHAR(255) NOT NULL," +
                "order INT NOT NULL," +
                "board_id BIGINT REFERENCES Board(id)" +
                ");";

        String criarTabelaCard = "CREATE TABLE Card (" +
                "id BIGINT PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "description TEXT," +
                "created_at TIMESTAMP WITH TIME ZONE NOT NULL," +
                "boarcolumn_id BIGINT REFERENCES BoarColumn(id)" +
                ");";

        String criarTabelaBlock = "CREATE TABLE Block (" +
                "id BIGINT PRIMARY KEY," +
                "block_cause VARCHAR(255) NOT NULL," +
                "block_in TIMESTAMP WITH TIME ZONE NOT NULL," +
                "unblock_cause VARCHAR(255)," +
                "unblock_in TIMESTAMP WITH TIME ZONE," +
                "card_id BIGINT REFERENCES Card(id)" +
                ");";

        try (PreparedStatement stmtBoard = connection.prepareStatement(criarTabelaBoard);
             PreparedStatement stmtBoarColumn = connection.prepareStatement(criarTabelaBoarColumn);
             PreparedStatement stmtCard = connection.prepareStatement(criarTabelaCard);
             PreparedStatement stmtBlock = connection.prepareStatement(criarTabelaBlock)) {

            stmtBoard.executeUpdate();
            stmtBoarColumn.executeUpdate();
            stmtCard.executeUpdate();
            stmtBlock.executeUpdate();
        }
    }
}
