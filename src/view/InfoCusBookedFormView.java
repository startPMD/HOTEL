package view;

import model.CustomerModel;
import model.RoomBookingFormModel;
import model.ServiceRoomModel;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.table.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InfoCusBookedFormView extends JFrame {
    // ... các thành phần khác của form
    private JPanel panel;
    private JLabel lblGuestRoom, lblNumPhone, lblIdNumber, lblStayDuration, lblStatesPayment, lblPriceRoom;
    private JLabel lblTypeRoom, lblNumRoom, lblCheckInTime, lblBookedTime, lblEndDate;
    private DefaultTableModel modelService;
    private JButton btnChangeRoom, btnCheckOut;
    RoomChangeForm roomChangeForm;

    public InfoCusBookedFormView() {
        super("Thông tin khách hàng đặt phòng");
        roomChangeForm = new RoomChangeForm();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        createC1();
        createC2();
        createC3();
        JPanel panelBtn = new JPanel();
        btnChangeRoom = new JButton();
        btnCheckOut = new JButton();


        btnChangeRoom.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCheckOut.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btnChangeRoom.addActionListener(e -> buttonEvent("btnChangeRoom"));
        btnCheckOut.addActionListener(e -> buttonEvent("btnCheckOut"));

        panelBtn.add(btnChangeRoom);
        panelBtn.add(btnCheckOut);


        panel.add(panelBtn);

        add(panel);
    }

    public String formatStartDate(String start) {
        Timestamp timestamp = Timestamp.valueOf(start);
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(date);
    }

    public RoomChangeForm getRoomChangeForm() {
        return roomChangeForm;
    }

    public String getIdNumber() {
        return lblIdNumber.getText();
    }

    public String getBookedTime() {
        return lblBookedTime.getText();
    }

    private void createC1() {
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Thông tin khách hàng")));

        JLabel nguoiDatPhongLabel = new JLabel("Người đặt phòng:");
        JLabel soDienThoaiLabel = new JLabel("Số điện thoại:");
        JLabel soChungMinhLabel = new JLabel("Số chứng minh:");

        Font font = new Font("Arial", Font.PLAIN, 15);
        lblGuestRoom = new JLabel();
        lblNumPhone = new JLabel();
        lblIdNumber = new JLabel();
        lblStayDuration = new JLabel();
        lblStatesPayment = new JLabel();
        lblPriceRoom = new JLabel();
        lblTypeRoom = new JLabel();
        lblNumRoom = new JLabel();
        lblCheckInTime = new JLabel();
        lblBookedTime = new JLabel();
        lblEndDate = new JLabel();

        lblGuestRoom.setFont(font);
        lblNumPhone.setFont(font);
        lblIdNumber.setFont(font);
        lblStayDuration.setFont(font);
        lblStatesPayment.setFont(font);
        lblPriceRoom.setFont(font);
        lblTypeRoom.setFont(font);
        lblNumRoom.setFont(font);
        lblCheckInTime.setFont(font);
        lblBookedTime.setFont(font);
        lblEndDate.setFont(font);

        infoPanel.add(nguoiDatPhongLabel);
        infoPanel.add(lblGuestRoom);
        infoPanel.add(soChungMinhLabel);
        infoPanel.add(lblIdNumber);
        infoPanel.add(soDienThoaiLabel);
        infoPanel.add(lblNumPhone);
        panel.add(infoPanel);
    }

    private void createC2() {
        // Component 2: Thông tin phòng
        JLabel thoiGianOLabel = new JLabel("Thời gian ở:");
        JLabel trangThaiThanhToanLabel = new JLabel("Trạng thái thanh toán:");
        JLabel giaPhongLabel = new JLabel("Tổng giá phòng:");
        JLabel loaiPhongLabel = new JLabel("Loại phòng:");
        JLabel soPhongLabel = new JLabel("Số phòng:");
        JLabel thoiGianNhanPhongLabel = new JLabel("Thời gian nhận phòng:");
        JLabel thoiGianDatPhongLabel = new JLabel("Thời gian đặt phòng:");
        JLabel thoiGianHetHanDatPhongLabel = new JLabel("Thời gian hết hạn đặt phòng:");

        JPanel infoRoomPanel = new JPanel(new GridLayout(0, 4, 5, 5));
        infoRoomPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Thông tin phòng")));
        infoRoomPanel.add(loaiPhongLabel);
        infoRoomPanel.add(lblTypeRoom);
        infoRoomPanel.add(thoiGianDatPhongLabel);
        infoRoomPanel.add(lblBookedTime);
        infoRoomPanel.add(soPhongLabel);
        infoRoomPanel.add(lblNumRoom);
        infoRoomPanel.add(thoiGianNhanPhongLabel);
        infoRoomPanel.add(lblCheckInTime);
        infoRoomPanel.add(giaPhongLabel);
        infoRoomPanel.add(lblPriceRoom);
        infoRoomPanel.add(thoiGianOLabel);
        infoRoomPanel.add(lblStayDuration);
        infoRoomPanel.add(trangThaiThanhToanLabel);
        infoRoomPanel.add(lblStatesPayment);
        infoRoomPanel.add(thoiGianHetHanDatPhongLabel);
        infoRoomPanel.add(lblEndDate);
        panel.add(infoRoomPanel);
    }

    private void createC3() {
        // Component 3: Các dịch vụ đã yêu cầu
        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder("Các dịch vụ đã yêu cầu")));

        String[] columnNames = {"Tên dịch vụ", "Ngày yêu cầu dịch vụ", "Giá dịch vụ", "Số lượng", "Thành tiền"};
        modelService = new DefaultTableModel(columnNames, 0);

        JTable serviceTable = new JTable(modelService);

        TableColumnModel columnModel = serviceTable.getColumnModel();
        DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();
        { // initializer block
            renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        // align data col
        TableColumn colDateService = columnModel.getColumn(1);
        TableColumn colPriceService = columnModel.getColumn(2);
        TableColumn colQuantityService = columnModel.getColumn(3);
        TableColumn colTotalPriceService = columnModel.getColumn(4);
        colDateService.setCellRenderer(renderRight);
        colPriceService.setCellRenderer(renderRight);
        colQuantityService.setCellRenderer(renderRight);
        colTotalPriceService.setCellRenderer(renderRight);
        serviceTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(serviceTable);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        servicePanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(servicePanel);
    }

    List<ServiceRoomModel> services;
    DecimalFormat formatter = new DecimalFormat("#,###");

    public void setDataServices(List<ServiceRoomModel> serviceRoomModel) {
        services = serviceRoomModel;
        for (ServiceRoomModel service : services) {
            this.modelService.addRow(new Object[]
                    {"" + service.getName(), service.getDateAt() + "", formatter.format(service.getPrice())
                            , service.getQuantityService() + "", formatter.format(service.getTotalPrice())});
        }
    }

    public void setTotalPrice(double totalPrice) {
        this.modelService.addRow(new Object[]{"", "", "", "Tổng tiền", formatter.format(totalPrice)});
    }

    public void setTxtGuestRoom(String nameGuestRoom) {
        this.lblGuestRoom.setText(nameGuestRoom);
    }

    public void setTxtNumPhone(String numPhone) {
        this.lblNumPhone.setText(numPhone);
    }

    public void setTxtIdNumber(String idNumber) {
        this.lblIdNumber.setText(idNumber);
    }

    public void setTxtPriceRoom(String totalPrice) {
        this.lblPriceRoom.setText(totalPrice);
    }

    public void setTxtStayDuration(String stayDuration) {
        this.lblStayDuration.setText(stayDuration);
    }

    public void setTxtStatesPayment(String statesPayment) {
        this.lblStatesPayment.setText(statesPayment);
    }

    public void setTxtTypeRoom(String typeRoom) {
        this.lblTypeRoom.setText(typeRoom);
    }

    public void setTxtNumRoom(String numRoom) {
        this.lblNumRoom.setText(numRoom);
    }

    public String getNumRoom() {
        return lblNumRoom.getText();
    }

    public void setTxtCheckInTime(String checkInTime) {
        this.lblCheckInTime.setText(formatStartDate(checkInTime));
    }

    public void setTxtBookedTime(String bookedTime) {
        this.lblBookedTime.setText(formatStartDate(bookedTime));
    }

    public void setTxtEndDate(String endDate) {
        this.lblEndDate.setText(formatStartDate(endDate));
    }

    public String getStatesPayment() {
        return lblStatesPayment.getText().trim();
    }

    public String getCheckInTime() {
        return lblCheckInTime.getText();
    }

    private EventListenerList listenerList = new EventListenerList();

    public void addButtonListener(ButtonListener listener) {
        listenerList.add(ButtonListener.class, listener);
    }

    protected void buttonEvent(String name) {
        ButtonListener[] listeners = listenerList.getListeners(ButtonListener.class);
        for (ButtonListener listener : listeners) {
            listener.buttonPerformed(new RoomEvent(this, name));
        }
    }

    private int idCus, idRoom;

    public int idCus() {
        return idCus;
    }

    public int idRoom() {
        return idRoom;
    }

    public void setEmptyRoomInformationModel(RoomBookingFormModel roomBookingFormView) {
        // C1
        CustomerModel cus = roomBookingFormView.getCustomerModel();
        idCus = cus.getId();
        setTxtGuestRoom(cus.getName());
        setTxtIdNumber(cus.getIdNumber());
        setTxtNumPhone(cus.getPhone());
        //C2
        RoomBookingFormModel.EmptyRoomInformationModel em = roomBookingFormView.getEmptyRoomInformationModel();
        idRoom = em.getId();
        setTxtTypeRoom(em.getRoomType());
        setTxtNumRoom(em.getRoomCode() + "");
        setTxtPriceRoom(formatter.format(em.getRoomPrice()) + " VND");

        RoomBookingFormModel.InforBookedRoomModel inf = roomBookingFormView.getInforBookedRoomModel();
        System.err.println(inf.toString());
        setTxtStatesPayment(inf.getPaymentStatus());
        setTxtBookedTime(inf.getStartDate() + "");
        setTxtCheckInTime(inf.getCheckInTime() + "");
        setTxtStayDuration(inf.getStayDuration());
        setTxtEndDate(inf.getEndDate() + "");
        //C3
        // dat ma phong cho form doi phog
        roomChangeForm.setCurrentCodeRoomJlb(roomBookingFormView.getEmptyRoomInformationModel().getRoomCode());
    }

    public List<ServiceRoomModel> lsService() {
        List<ServiceRoomModel> ls = new ArrayList<>();
        for (int i = 0; i < modelService.getRowCount() - 1; i++) {
            String name = (String) modelService.getValueAt(i, 0);
            String timestamp = (String) modelService.getValueAt(i, 1);
            int totalPrice = Integer.parseInt(((String) modelService.getValueAt(i, 2)).replace(",",""));
            int quantity = Integer.parseInt((String) modelService.getValueAt(i, 3));
            ls.add(new ServiceRoomModel(-1, name, totalPrice, quantity, timestamp));
        }
        return ls;
    }

    public void showDetail() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    public String getPriceRoom() {
        return lblPriceRoom.getText().trim().substring(0, lblPriceRoom.getText().length() - 4).replace(",", "");
    }

    public double getTotalPricePay() {
        if (modelService.getRowCount() < 1)
            return Double.parseDouble(getPriceRoom());
        return Integer.parseInt(((String) modelService.getValueAt(modelService.getRowCount() - 1, 4)).replace(",","")) + Double.parseDouble(getPriceRoom());
    }

    double VAT;

    public void setTotalVAT(double totalVAT) {
        VAT = totalVAT;
    }

    public double getTotalVAT() {
        return VAT;
    }

    public void setNameBtnLeft(String s) {
        btnChangeRoom.setText(s);
    }

    public void setNameBtnRight(String s) {
        btnCheckOut.setText(s);
    }
}