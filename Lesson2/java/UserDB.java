public class UserDB {
    // Отдельный класс пользователей, которые содержат поля: логин, пароль, ник.
    public String login;
    public String pass;
    public String nick;

    public UserDB(String login, String pass, String nick) {
        this.login = login;
        this.pass = pass;
        this.nick = nick;
    }
    @Override
    public String toString() {
        return "UserDB{" +
                "login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

}
