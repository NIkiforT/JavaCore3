import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Window extends JFrame {
    private JPanel panel;
    private JButton sendButton;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JTextField textField;
    private JScrollPane scroll;
    private JFrame frame;
    private String userName;


    public Window(){
        super("My Chat");
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setBounds(400,200,700,700);
        frame.setResizable(false);

        panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        userName = "Никифор: ";

        textField = new JTextField(60);
        panel.add(textField, BorderLayout.CENTER);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()== KeyEvent.VK_ENTER) {
                    listModel.add(0, userName + textField.getText());
                    textField.setText(null);
                }
            }
        });

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setBackground(Color.WHITE);
        list.setBorder(BorderFactory.createRaisedBevelBorder());
        frame.add(list, BorderLayout.CENTER);

        scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scroll, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.add(0, userName + textField.getText());
                textField.setText(null);
            }
        });

        frame.setVisible(true);
    }
}
