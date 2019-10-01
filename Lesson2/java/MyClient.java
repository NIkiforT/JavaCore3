import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient extends JFrame {

    private final static int SERVER_PORT = 80;
    private final static String SERVER_ADDR = "localhost";

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private JTextField msgInputField;
    private JTextArea chatArea;
    private JTextField loginField;
    private JTextField passField;

    private boolean authorized;

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void prepareGUI() throws Exception {

        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.startsWith("/authok")) {
                                setAuthorized(true);
                                break;
                            }
                            chatArea.append(strFromServer + "\n");
                        }
                        while (true) {
                            String strFromServer = in.readUTF();
                            if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            chatArea.append(strFromServer);
                            chatArea.append("\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!socket.isClosed()) {
                        String msg;
                        msgInputField.setText(msg = msgInputField.getText());
                        out.writeUTF(msg);
                        msgInputField.setText(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void onAuthClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!socket.isClosed()) {
                        String lgn, psw;
                        loginField.setText(lgn = loginField.getText());
                        passField.setText(psw = passField.getText());
                        out.writeUTF("/auth " + lgn + " " + psw);
                        loginField.setText(null);
                        passField.setText(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void MyWindow() {
        // Параметры окна
        setBounds(600, 300, 500, 720);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // поле для логина и пароля
        JPanel passPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new BorderLayout());
        loginField = new JTextField();

        passField = new JPasswordField();

        JButton btnLogin = new JButton("Войти");
        passPanel.add(loginField,BorderLayout.NORTH);
        passPanel.add(passField, BorderLayout.SOUTH);
        inputPanel.add(passPanel, BorderLayout.CENTER);
        inputPanel.add(btnLogin, BorderLayout.EAST);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAuthClick();
            }
        });

        // Текстовое поле для вывода сообщений
        JPanel chatPanel = new JPanel();
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatPanel.add(chatArea);


        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();

        bottomPanel.add(msgInputField, BorderLayout.CENTER);
        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        add(inputPanel, BorderLayout.NORTH);
        add(chatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }


}
