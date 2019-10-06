
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Stack;


public class NewClient {
    private final String SERVER_IP = "localhost";
    private final int PORT = 80;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    JTextArea accepted;
    private Thread threadForRead;
    private boolean isConnect;
    private boolean isAuth = true;
    private String nick;
    private String historyFile;
    private int countLoadLines;


    public NewClient() {
        try {
            connect();
            go();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        socket = new Socket(SERVER_IP, PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    private void authentication() {
        try {
            String strFromServer;
            while (true) {
                strFromServer = in.readUTF();
                if (strFromServer.equals("quit"))
                    break;
                    if (strFromServer.contains("/authok")) {
                    nick = strFromServer.substring(0, strFromServer.indexOf(" "));
                    historyFile = nick + ".msg";
                    isConnect = true;
                    isAuth = false;
                    break;
                } else if (strFromServer.equals("/timeout")) {
                    accepted.append(" kiked " + "\n");
                    out.writeUTF(strFromServer);
                    isAuth = false;
                    break;

                }else if(strFromServer.equals("/end")){
                    saveMassageHistory();
                    closeConnection();
                    out.writeUTF(strFromServer);
                    break;
                } else {
                    accepted.append(strFromServer + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void go() {
        JFrame frame = new JFrame("Chat");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 400;
        int height = 350;
        frame.setSize(width, height);
        int startX = dimension.width / 2 - width / 2;
        int startY = dimension.height / 2 - height / 2;
        frame.setLocation(startX, startY);
        JPanel mainPanel = new JPanel();
        accepted = new JTextArea(15, 25);
        accepted.setLineWrap(true);
        accepted.setWrapStyleWord(true);
        accepted.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(accepted);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JTextField outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isConnect || isAuth) {
                    try {
                        out.writeUTF(outgoing.getText());
                        outgoing.setText("");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        mainPanel.add(scrollPane);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);

        threadForRead = new Thread(() ->{
            authentication();
            loadMessageHistory();
            String message;
            while (true) {
                try {
                    if (!isConnect) {
                        closeConnection();
                        break;
                    }
                    message = in.readUTF();
                    if (message.equals("quit")) {
                        closeConnection();
                        isConnect = false;
                        break;
                    }
                    accepted.append(message + "\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForRead.start();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(BorderLayout.CENTER, mainPanel);
        frame.setVisible(true);
    }

    private void saveMassageHistory(){
        Path pathTmp = null;
        if(historyFile == null){
            return;
        }
        Path pathHistory = Paths.get(historyFile);

        try {
            pathTmp = Files.createTempFile("Message-", ".tmp");
            if(!Files.exists(pathHistory))
                Files.createFile(pathHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileChannel source = new FileInputStream(historyFile).getChannel();
             FileChannel dest = new FileOutputStream(pathTmp.toFile().getName()).getChannel()) {
                 dest.transferFrom(source, 0, source.size());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            new FileWriter(historyFile);
        }catch (Exception e){
            e.printStackTrace();
        }

        Stack<String> messages = new Stack<>();
        String[] lines = accepted.getText().split("\n");
        for(int i = countLoadLines; i < lines.length; i++)
            messages.add(lines[i]);
        try (BufferedReader reader = new BufferedReader(new FileReader(pathTmp.toFile().getName()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile, true))){
            Iterator iterator = messages.iterator();
            while (iterator.hasNext())
                writer.write(messages.pop()+"\n");

            reader.lines().forEach(s ->{
                try {
                    writer.write(s+"\n");
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void loadMessageHistory(){
        int countMessangeForLoad = 100;
        accepted.setText("");
        Path pathHistory = Paths.get(historyFile);
        Stack<String> messanges = new Stack<>();
        if(Files.exists(pathHistory)){
            try (BufferedReader reader = new BufferedReader(new FileReader(pathHistory.toFile()));
            ) {
                for (int i = 0; i < countMessangeForLoad; i++) {
                    if (reader.ready())
                        messanges.add(reader.readLine());
                    else break;
                }
                countLoadLines = messanges.size();
                Iterator iterator = messanges.iterator();
                while (iterator.hasNext()) {
                    accepted.append(messanges.pop() + "\n");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    private void closeConnection(){
        try {
            in.close();
            out.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NewClient();
    }
}
