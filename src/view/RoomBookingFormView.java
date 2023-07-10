package view;

import model.RoomBookingFormModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class RoomBookingFormView extends JFrame {
    private JLabel lblPosition, lblRoomCode, lblRoomType, lblMaxGuest, lblRoomStatus, lblEquipment, lblPrice;
    private JLabel lblGuestName, lblPhone, lblIDNumber;
    private JTextField txtGuestName, txtPhone, txtEmail, txtIDNumber, txtNumGuest;
    private JComboBox<String> cboPaymentStatus, cbNationality;//, cboCheckInTime
    private JDatePickerImpl datePicker,datePickerCheckIn;
    private JButton saveRoomBooking;
    private double priceRoom;

    public RoomBookingFormView() {
        super("Room Booking Form");

        // Tạo một panel chứa các thành phần để nhập thông tin phòng
        JPanel roomPanel = new JPanel(new GridLayout(7, 2));
        roomPanel.setBorder(createBorder("Thông tin phòng"));

        lblPosition = new JLabel("Vị trí: ");
        lblRoomCode = new JLabel("Mã phòng: ");
        lblRoomType = new JLabel("Loại phòng: ");
        lblMaxGuest = new JLabel("Số người ở tối đa: ");
        lblRoomStatus = new JLabel("Trạng thái phòng: ");
        lblEquipment = new JLabel("Trang thiết bị: ");
        lblPrice = new JLabel("Giá phòng: ");

        roomPanel.add(lblPosition);
        roomPanel.add(lblRoomCode);
        roomPanel.add(lblRoomType);
        roomPanel.add(lblMaxGuest);
        roomPanel.add(lblRoomStatus);
        roomPanel.add(lblEquipment);
        roomPanel.add(lblPrice);

        // Tạo một panel chứa các thành phần để nhập thông tin người đặt phòng
        JPanel bookingPanel = new JPanel(new GridLayout(9, 2, 3, 3));
        bookingPanel.setBorder(createBorder("Nhập thông tin người đặt phòng"));

        lblGuestName = new JLabel("Người đặt phòng*:");
        txtGuestName = new JTextField(1);


        lblPhone = new JLabel("Số điện thoại*:");
        txtPhone = new JTextField();

        lblIDNumber = new JLabel("Số chứng minh*:");
        txtIDNumber = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);

        JLabel lblNationality = new JLabel("Quốc gia:");
        String[] nationalities = {"Vietnam", "USA", "Japan", "Korea", "China", "Thailand",
                "Indonesia", "Philippines", "Malaysia", "Singapore", "India", "Australia",
                "Canada", "France", "Germany", "Italy", "Spain", "UK"};
        cbNationality = new JComboBox<String>(nationalities);
        cbNationality.setMaximumRowCount(5);

        JLabel lblCheckInTime = new JLabel("Thời gian nhận phòng:");
//        cboCheckInTime = new JComboBox<>(timeBookedRoom());

        JLabel lblStayDuration = new JLabel("Thời gian ở:");
        JLabel lblNumGuest = new JLabel("Số người:");
        txtNumGuest = new JTextField();

        JLabel lblPaymentStatus = new JLabel("Trạng thái thanh toán:");
        String[] paymentStatus = {"Chưa thanh toán", "Đã thanh toán"};
        cboPaymentStatus = new JComboBox<>(paymentStatus);

        bookingPanel.add(lblGuestName);
        bookingPanel.add(txtGuestName);
        bookingPanel.add(lblPhone);
        bookingPanel.add(txtPhone);
        bookingPanel.add(lblIDNumber);
        bookingPanel.add(txtIDNumber);
        bookingPanel.add(lblEmail);
        bookingPanel.add(txtEmail);
        bookingPanel.add(lblNationality);
        bookingPanel.add(cbNationality);
        bookingPanel.add(lblCheckInTime);
        bookingPanel.add(datePickerCheckIn = createDatePicker());
        bookingPanel.add(lblStayDuration);
        bookingPanel.add(datePicker = createDatePicker());
        bookingPanel.add(lblNumGuest);
        bookingPanel.add(txtNumGuest);
        bookingPanel.add(lblPaymentStatus);
        bookingPanel.add(cboPaymentStatus);

        JPanel panelTop = new JPanel(new GridLayout(2, 1));
        panelTop.setBorder(new EmptyBorder(0, 0, 7, 0));
        panelTop.add(roomPanel);
        panelTop.add(bookingPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.add(panelTop, BorderLayout.CENTER);

        saveRoomBooking = new JButton("Lưu");
        saveRoomBooking.setCursor(new Cursor(Cursor.HAND_CURSOR));

        saveRoomBooking.addActionListener(e -> {
            buttonSavaCusEvent();
        });
        mainPanel.add(saveRoomBooking, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    public String getTxtGuestName() {
        return lblGuestName.getText().substring(0, lblGuestName.getText().length() - 1);
    }

    public String getTxtPhone() {
        return lblPhone.getText().substring(0, lblPhone.getText().length() - 1);
    }

    public String getTxtIDNumber() {
        return lblIDNumber.getText().substring(0, lblIDNumber.getText().length() - 1);
    }

    public String getInputGuestName() {
        return txtGuestName.getText();
    }

    public String getInputPhone() {
        return txtPhone.getText();
    }

    public String getInputEmail() {
        return txtEmail.getText();
    }

    public String getInputIDNumber() {
        return txtIDNumber.getText();
    }

    public int getInputNumGuest() {
        return Integer.parseInt(txtNumGuest.getText());
    }

    public double getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(double priceRoom) {
        this.priceRoom = priceRoom;
    }

    public Timestamp getJtfCheckInTime() {
        return formatDatePicker(datePickerCheckIn);
    }
    public String[] timeBookedRoom() {
        ArrayList<String> times = new ArrayList<>();
        times.add("Ngay bây giờ");
        LocalTime time = LocalTime.of(0, 0);
        while (time.isBefore(LocalTime.of(23, 50))) {
            times.add(time.format(DateTimeFormatter.ofPattern("hh:mm")));
            time = time.plusMinutes(10);
        }
        return times.toArray(new String[0]);
    }

    public Timestamp getCheckInTime() {
        if (getJtfCheckInTime() == null)
            return new Timestamp(System.currentTimeMillis());
        return getJtfCheckInTime();
    }

    public String getSelectedPaymentStatus() {
        return (String) cboPaymentStatus.getSelectedItem().toString();
    }

    public String getSelectedNationality() {
        return (String) cbNationality.getSelectedItem().toString();
    }

    public boolean hasEmptyValue() {
        if (txtNumGuest.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtIDNumber.getText().trim().isEmpty())
            return true;
        return false;
    }

    public void setEmptyRoomInformationModel(RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModel) {
        this.lblPosition.setText(this.lblPosition.getText() + emptyRoomInformationModel.getLocation());
        this.lblRoomCode.setText(this.lblRoomCode.getText() + emptyRoomInformationModel.getRoomCode());
        this.lblRoomType.setText(this.lblRoomType.getText() + emptyRoomInformationModel.getRoomType());
        this.lblMaxGuest.setText(this.lblMaxGuest.getText() + emptyRoomInformationModel.getNumGuest());
        this.lblRoomStatus.setText(this.lblRoomStatus.getText() + emptyRoomInformationModel.getRoomStatus());
        this.lblEquipment.setText(this.lblEquipment.getText() + emptyRoomInformationModel.getEquipment());
        this.lblPrice.setText(this.lblPrice.getText() + emptyRoomInformationModel.getRoomPrice() + " VND");
        setPriceRoom(emptyRoomInformationModel.getRoomPrice());
    }

    private JDatePickerImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                return null;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar calendar = (Calendar) value;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String d = simpleDateFormat.format(calendar.getTimeInMillis());
                    return d;
                }
                return "";
            }
        });
        return datePicker;
    }
    private Timestamp formatDatePicker( JDatePickerImpl datePicker){
        try {
            LocalTime localTime = LocalTime.now();
            LocalDate localDate = LocalDate.parse(String.valueOf(datePicker.getModel().getValue()));

            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            return timestamp;
        }
        catch (DateTimeException dateTimeException){
            return null;
        }


    }
    public Timestamp getEndDate() {
       return formatDatePicker(datePicker);
    }

    public Border createBorder(String str) {
        return BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(str)
                , BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void showDetail() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void offDetail() {
        this.setVisible(false);
    }

    private EventListenerList listenerList = new EventListenerList();

    public void addButtonSaveInfroCustomerListener(ButtonListener listener) {
        listenerList.add(ButtonListener.class, listener);
    }

    protected void buttonSavaCusEvent() {
        ButtonListener[] listeners = listenerList.getListeners(ButtonListener.class);
        for (ButtonListener listener : listeners) {
            listener.buttonPerformed(new RoomEvent(this));
        }
    }

    public static void main(String[] args) {
        RoomBookingFormView a = new RoomBookingFormView();
        a.saveRoomBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(a.getCheckInTime());
                System.out.println(a.getEndDate());
            }
        });
        a.showDetail();
    }


}