import main_classes.AppFrame;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static int DATABASE_NOT_FOUND_ERR_CODE = 1049;
    private static String USE_SCHEMA = "USE todo";
    private static String userName= System.getenv("MYSQL_USER");
    private static String password= System.getenv("MYSQL_PASS");

    public static void main(String[] args) {
        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(userName);
        dataSource.setPassword(password);

        try (Connection connection = dataSource.getConnection()) {
            if (!checkSchema(connection)) {
                System.out.println("Creating todo schema");
                createDataBaseIfNoExist(connection);
            }
            new AppFrame();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkSchema(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(USE_SCHEMA);
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn.getMetaData().getDatabaseProductName().equals("MySQL") && e.getErrorCode() == DATABASE_NOT_FOUND_ERR_CODE) {
                System.out.println("Database is no exist");
                return false;
            } else throw e;
        }
        System.out.println("Already todo schema created in database!");
        return true;
    }


    public static void createDataBaseIfNoExist(Connection conn) {
        String createSchema = "CREATE SCHEMA todo";
        String createTable = """
                CREATE TABLE todo.tasks(
                task_id int NOT NULL AUTO_INCREMENT,
                task_hour TIME NOT NULL,
                task_info TEXT,
                PRIMARY KEY (task_id)
                )""";

        try (Statement statement = conn.createStatement()) {
            statement.execute(createSchema);
            statement.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}