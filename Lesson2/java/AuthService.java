public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
    String getNick(String part);
}

