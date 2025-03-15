package database_class;

import java.sql.*;

public class DataBase {
    private Connection connection;

    public DataBase(Connection connection) {
        this.connection=connection;
    }

    public int addTask(String task, String hour) throws SQLException {
        String insertTask = "INSERT INTO todo.tasks (task_hour, task_info) VALUES (?, ?)";
        String taskHour = hour + ":00";
        int taskId=-1;

        try (PreparedStatement statement = connection.prepareStatement(insertTask, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            statement.setString(1, taskHour);
            statement.setString(2, task);

            statement.execute();
            var rs = statement.getGeneratedKeys();
            if (rs.next()) {
                taskId=rs.getInt(1);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return taskId;
    }


    public boolean deleteTask(int taskId) throws SQLException {
        String deleteQuery = "DELETE FROM todo.tasks WHERE task_id= ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            connection.setAutoCommit(false);

            statement.setInt(1, taskId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public boolean updateTask(String newTask, String newHour, int taskId) throws SQLException {
        boolean returned= false;

        String updateQuery = "UPDATE todo.tasks SET task_info = ?, task_hour = ? WHERE task_id= ?";

        try (PreparedStatement statement= connection.prepareStatement(updateQuery)){
            connection.setAutoCommit(false);
            statement.setString(1, newTask);
            statement.setString(2, newHour);
            statement.setInt(3, taskId );

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                returned = true;
            }
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
        return returned;
    }

    public boolean listAllTask() throws SQLException{
        String query= "SELECT * FROM todo.tasks";

        try (Statement statement= connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute(query);
            var rs= statement.getResultSet();

            if (rs.next()){

                return true;
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}