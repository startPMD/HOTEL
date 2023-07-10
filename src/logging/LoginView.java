package logging;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.geom.Point2D;

public class LoginView extends JFrame {
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginView() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(450, 300);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                Point2D startPoint = new Point2D.Float(0, 0);

                Point endPoint = new Point(w, h);
                GradientPaint gradientPaint = new GradientPaint(startPoint, new Color(142, 197, 252), endPoint, new Color(224, 195, 252));
                g2.setPaint(gradientPaint);
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        add(panel);

        titleLabel = new JLabel("Đăng nhập");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(170, 20, 150, 30);
        panel.add(titleLabel);

        usernameLabel = new JLabel("Tài khoản:");
        usernameLabel.setBounds(50, 70, 70, 20);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 70, 200, 20);
        panel.add(usernameField);

        passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setBounds(50, 110, 70, 20);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 110, 200, 20);
        panel.add(passwordField);

        loginButton = new JButton("Đăng nhập");
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(e -> fireLoginEvent());
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBounds(130, 150, 200, 30);
        panel.add(loginButton);

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.red);
        statusLabel.setBounds(100, 200, 240, 30);
        panel.add(statusLabel);
        add(panel);

    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void removePasswordField() {
        passwordField.setText("");
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void addLoginListener(LoginListener listener) {
        listenerList.add(LoginListener.class, listener);
    }

    public void removeLoginListener(LoginListener listener) {
        listenerList.remove(LoginListener.class, listener);
    }

    protected void fireLoginEvent() {
        LoginListener[] listeners = listenerList.getListeners(LoginListener.class);
        for (LoginListener listener : listeners) {
            listener.loginPerformed(new LoginEvent(this));
        }
    }

    private EventListenerList listenerList = new EventListenerList();
    public static void main(String[] args) {
        new LoginView();
    }
}
