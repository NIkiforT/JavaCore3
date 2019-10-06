import org.sqlite.JDBC;

import java.security.Key;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

        // Класс для работы с базой данный. Содержит в себе методы вывода всех пользователей и записи их в коллекцию.
        // И метод добавления в БД нового пользователя.
public class DataBase {
    private static DataBase instnce = null;
    static Connection con;
    static Statement stmt;

    public static synchronized DataBase getInstance() throws SQLException{
        if(instnce== null){
            instnce = new DataBase();
        }
        return instnce;
    }
    private DataBase() throws SQLException {
        DriverManager.registerDriver(new JDBC());
    }
    //Метод вывода всех пользователей из базы.
    public static List<UserDB> getAlluser() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:users.db");
        stmt = con.createStatement();
        List<UserDB> user = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery("SELECT login, pass, nick FROM users");

        while (resultSet.next()){
            user.add(new UserDB(resultSet.getString("login"),
                                resultSet.getString("pass"),
                                resultSet.getString("nick")));
        }
        return user;
    }
        //Метод добавление нового пользователя в базу.
    public void addUser(UserDB user) {
        try (PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO users('login', 'pass', 'nick')" +
                            "VALUES(?, ?, ?)")){
            statement.setObject(1, user.login);
            statement.setObject(2, user.pass);
            statement.setObject(3, user.nick);
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
