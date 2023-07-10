package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class PanelLeftView extends JPanel {
    private JLabel jlRoomManager, jlServiceManager, jlPayManager, jlEmployeeManager, jlbNameE, jlbJobPosition, nameE, jobPosition, logOut;
    private JPanel panelFuncName;
    private Color c;

    public PanelLeftView() {
        setLayout(new BorderLayout());
        c = new Color(0, 0, 0, 16);
        createLogoHotel();


        panelFuncName = new JPanel() {
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
        createNameFunc();
    }

    private void createNameFunc() {
        panelFuncName.setLayout(new BoxLayout(panelFuncName, BoxLayout.Y_AXIS));

        panelFuncName.add(jlRoomManager = createFuncName    ("Quản lý phòng                  "));
        panelFuncName.add(jlServiceManager = createFuncName ("Quản lý dịch vụ                "));
        panelFuncName.add(jlPayManager = createFuncName     ("Quản lý thanh toán           "));
        panelFuncName.add(jlEmployeeManager = createFuncName("Quản lý nhân viên            "));

        JLabel padding = new JLabel("");
//        padding.setBorder(new EmptyBorder(0, 0, 0, 80));
        panelFuncName.add(padding);
        EmptyBorder emptyBorder = new EmptyBorder(10, 5, 10, 0);
        jlRoomManager.setBorder(emptyBorder);
        jlServiceManager.setBorder(emptyBorder);
        jlPayManager.setBorder(emptyBorder);
        jlEmployeeManager.setBorder(emptyBorder);


        jlRoomManager.setBackground(c);
        jlRoomManager.setOpaque(true);

        jlRoomManager.setName("QLP");
        jlServiceManager.setName("QLDV");
        jlPayManager.setName("QLTT");
        jlEmployeeManager.setName("QLNV");

        this.add(panelFuncName, BorderLayout.WEST);
    }

    public void connectEventPanelLeftToPanelRight(PanelRightView panelRightView) {
        // Đăng ký sự kiện cho funcName
        for (int i = 0; i < panelFuncName.getComponentCount(); i++) {
            JLabel funcNmae = (JLabel) panelFuncName.getComponent(i);
            ((JLabel) panelFuncName.getComponent(i)).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setBackgroundFuncName(funcNmae);
                    panelRightView.visiblePanelFunc(funcNmae.getName());

                }
            });
        }
    }

    private void createLogoHotel() {
// Tạo hình ảnh và đặt vào phía trên của panel
        ImageIcon imgLogo = new ImageIcon(new ImageIcon("img\\user_icon.jpg").getImage().getScaledInstance(55, 55, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(imgLogo);
        imageLabel.setBorder(new EmptyBorder(10, 10, 10, 15));

        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setLayout(new BorderLayout());
        jlbNameE = new JLabel("Tên: ");
        nameE = new JLabel();

        jlbJobPosition = new JLabel("Vị trí: ");
        jobPosition = new JLabel();

        logOut = new JLabel("Đăng xuất");
        logOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logOut.setBorder(new EmptyBorder(0, 0, 10, 15));
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        panelInfo.setBorder(new EmptyBorder(15, 0, 0, 0));
        panelInfo.add(jlbNameE, BorderLayout.NORTH);
        panelInfo.add(jlbJobPosition, BorderLayout.CENTER);

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBackground(Color.WHITE);
        panelMain.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)
                , new EmptyBorder(10, 0, 0, 0)));

        panelMain.add(imageLabel, BorderLayout.LINE_START);
        panelMain.add(panelInfo, BorderLayout.CENTER);

        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(new BorderLayout());
        p.add(new JLabel(""), BorderLayout.CENTER);
        p.add(logOut, BorderLayout.EAST);
        panelMain.add(p, BorderLayout.PAGE_END);


        this.add(panelMain, BorderLayout.NORTH);
    }

    public void setNameE(String name) {
        nameE.setText(name);
        jlbNameE.setText(jlbNameE.getText() + nameE.getText());

    }

    public void setInfoEmployee(String name, String position) {
        setNameE(name);
        setJobPosition(position);
    }

    public JLabel getLogOut() {
        return logOut;
    }

    public void setJobPosition(String position) {
        jobPosition.setText(position);
        jlbJobPosition.setText(jlbJobPosition.getText() + jobPosition.getText());
    }

    public JLabel createFuncName(String funcName) {
        JLabel txtFunc = new JLabel(funcName);
        txtFunc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtFunc.setBorder(new EmptyBorder(5, 5, 5, 80));
        txtFunc.setFont(new Font("Serif", Font.ROMAN_BASELINE, 19));
        return txtFunc;
    }

    public void setBackgroundFuncName(JLabel txtFunc) {
        jlRoomManager.setOpaque(false);
        jlServiceManager.setOpaque(false);
        jlPayManager.setOpaque(false);
        jlEmployeeManager.setOpaque(false);
        repaint();
        txtFunc.setBackground(c);
        txtFunc.setOpaque(true);

    }


    public static void main(String[] args) {
        PanelLeftView e = new PanelLeftView();
        JFrame a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(250, 600);
        a.add(e);
        a.setVisible(true);
    }
}
