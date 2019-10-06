import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private class Entry {

        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    private List<UserDB> entries;

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }


    public BaseAuthService() throws SQLException {
        entries = DataBase.getAlluser();

    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for (UserDB o : entries) {
            if (o.login.equals(login) && o.pass.equals(pass)) return o.nick;
        }
        return null;
    }
    public String getNick(String login) {
        for (UserDB o : entries) {
            if (o.login.equals(login)) return o.nick;
        }
        return null;
    }
}
