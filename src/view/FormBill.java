package view;

import model.CustomerModel;
import model.HotelInfoModel;
import model.ServiceRoomModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

public class FormBill extends JFrame {
    JLabel jlbNameHotel, jlbAddress, jlbNumberPhone, jlbDNS;
    JLabel jlbNameBill, jlbDateCreate, jlbNumberBill;
    //----------------
    JLabel jlbNameCus, jlbAddressCus, jlbNumberPhoneCus, jlbTaxCode, jlbNumberRoom;
    JLabel jlbArrivalDate, jlbDateDepartment, jlbCashier, jlbNumberNight, jlbPay, jlbTotalPrice;
    JLabel jlb_Underline3,jlb_TotalPrice,jlb_TaxVAT,jlb_SignatureCashier,jlb_SignatureCus,jlbBackground;
    JPanel panelServices;
    Font f;
    JButton btnCheckOut, btnCheckOut_Print;

    //---------------
    public void setProfileHotel(HotelInfoModel hotelInfoModel) {
        jlbNameHotel.setText(hotelInfoModel.getHotelName());
        jlbAddress.setText(hotelInfoModel.getAddress());
        jlbNumberPhone.setText(hotelInfoModel.getPhoneNumber());
        jlbDNS.setText(hotelInfoModel.getWebsiteDomain());
    }

    public void setInfoCus(CustomerModel cus) {
        jlbNameCus.setText(cus.getName());
        jlbAddressCus.setText(cus.getAddress());
        jlbNumberPhoneCus.setText(cus.getPhone());
    }

    public void setInfoBooked(String numRoom, String bookedTime) {
        jlbNumberRoom.setText(numRoom);
        jlbArrivalDate.setText(bookedTime);
        jlbNumberNight.setText(numberNight(bookedTime, jlbDateDepartment.getText()) + "");
    }

    public long numberNight(String startStr, String endStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        // Chuyển đổi chuỗi thành đối tượng LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.parse(startStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endStr, formatter);

        // Tính số ngày giữa hai thời điểm
        long daysBetween = ChronoUnit.DAYS.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
        return daysBetween;
    }

    public void setNumberBill(int numberBill) {
        jlbNumberBill.setText(numberBill + "");
    }

    public String formatStartDate(String start) {
        Timestamp timestamp = Timestamp.valueOf(start);
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(date);
    }

    Date dateCreateBill;

    private String dateCreateBill() {
        Date currentDate = new Date(System.currentTimeMillis());
        dateCreateBill = currentDate;
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));

        return formatter.format(currentDate);
    }

    public Date getDateCreateBill() {
        return dateCreateBill;
    }

    private String createDateDepartment() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(date);

    }

    public void setTaxCode(String taxCode) {
        jlbTaxCode.setText(taxCode);
    }

    public void setCashier(String cashier) {
        jlbCashier.setText(cashier);
    }

    public void setPay(String pay) {
        jlbPay.setText(pay);
    }

    public FormBill() throws HeadlessException {
        super("Bill");
        setSize(625, 600);
        setLocationRelativeTo(null);
        setLayout(null);


        jlbNameHotel = new JLabel();
        JLabel jlb_Address = new JLabel("Địa chỉ: ");
        JLabel jlb_NumberPhone = new JLabel("Điện thoại: ");
        JLabel jlb_DNS = new JLabel("Website: ");

        jlbAddress = new JLabel();
        jlbNumberPhone = new JLabel();
        jlbDNS = new JLabel();


        jlbNameHotel.setBounds(205, 14, 150, 25);
        jlb_Address.setBounds(205, 35, 150, 25);
        jlb_NumberPhone.setBounds(205, 56, 150, 25);
        jlb_DNS.setBounds(205, 73, 150, 25);

        jlbAddress.setBounds(260, 35, 400, 25);
        jlbNumberPhone.setBounds(277, 56, 150, 25);
        jlbDNS.setBounds(260, 73, 150, 25);

        f = new Font("Times New Roman", Font.ITALIC, 14);

        jlbNameHotel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        jlb_Address.setFont(f);
        jlb_NumberPhone.setFont(f);
        jlb_DNS.setFont(f);
        jlbAddress.setFont(f);
        jlbNumberPhone.setFont(f);
        jlbDNS.setFont(f);

        add(jlbNameHotel);
        add(jlb_Address);
        add(jlb_NumberPhone);
        add(jlb_DNS);
        add(jlbAddress);
        add(jlbNumberPhone);
        add(jlbDNS);

        jlbNameBill = new JLabel("HÓA ĐƠN THANH TOÁN DỊCH VỤ");
        jlbNameBill.setBounds(170, 99, 300, 25);
        jlbNameBill.setFont(new Font("Times New Roman", Font.BOLD, 17));
        add(jlbNameBill);

        jlbDateCreate = new JLabel(dateCreateBill());
        jlbDateCreate.setBounds(37, 123, 150, 25);
        jlbDateCreate.setFont(f);
        add(jlbDateCreate);

        JLabel jlb_NumberBill = new JLabel("Số hóa đơn:");
        jlb_NumberBill.setBounds(416, 123, 80, 25);
        jlbNumberBill = new JLabel();
        jlbNumberBill.setBounds(507, 123, 80, 25);
        add(jlb_NumberBill);
        add(jlbNumberBill);

        JLabel jlb_Underline = new JLabel("--------------------------------------------------------" +
                "----------------------------------------------------------------------------------------");
        jlb_Underline.setBounds(16, 142, 585, 25);
        add(jlb_Underline);

        JLabel jlb_NameCus = new JLabel("Tên: ");
        JLabel jlb_AddressCus = new JLabel("Địa chỉ: ");
        JLabel jlb_NumberPhoneCus = new JLabel("Điện thoại: ");
        JLabel jlb_TaxCode = new JLabel("Mã số thuế: ");
        JLabel jlb_NumberRoom = new JLabel("Phòng: ");

        jlbNameCus = new JLabel();
        jlbAddressCus = new JLabel();
        jlbNumberPhoneCus = new JLabel();
        jlbTaxCode = new JLabel();
        jlbNumberRoom = new JLabel();

        JPanel p1 = new JPanel();
        p1.setBackground(Color.WHITE);
        p1.setLayout(new GridLayout(5, 1));
        p1.setBounds(19, 157, 80, 100);

        jlb_NameCus.setFont(f);
        jlb_AddressCus.setFont(f);
        jlb_NumberPhoneCus.setFont(f);
        jlb_TaxCode.setFont(f);
        jlb_NumberRoom.setFont(f);

        p1.add(jlb_NameCus);
        p1.add(jlb_AddressCus);
        p1.add(jlb_NumberPhoneCus);
        p1.add(jlb_TaxCode);
        p1.add(jlb_NumberRoom);
        add(p1);

        JPanel p3 = new JPanel();
        p3.setBackground(Color.WHITE);
        p3.setLayout(new GridLayout(5, 1));
        p3.setBounds(100, 157, 200, 100);

        jlbNameCus.setFont(f);
        jlbAddressCus.setFont(f);
        jlbNumberPhoneCus.setFont(f);
        jlbTaxCode.setFont(f);
        jlbNumberRoom.setFont(f);

        p3.add(jlbNameCus);
        p3.add(jlbAddressCus);
        p3.add(jlbNumberPhoneCus);
        p3.add(jlbTaxCode);
        p3.add(jlbNumberRoom);
        add(p3);


        JLabel jlb_ArrivalDate = new JLabel("Ngày đến: ");
        JLabel jlb_Department = new JLabel("Ngày đi: ");
        JLabel jlb_Cashier = new JLabel("Thu ngân: ");
        JLabel jlb_NumberNight = new JLabel("Số đêm: ");
        JLabel jlb_Pay = new JLabel("Thanh toán: ");
        jlbArrivalDate = new JLabel();
        jlbDateDepartment = new JLabel(createDateDepartment());
        jlbNumberNight = new JLabel();
        jlbCashier = new JLabel("Quản trị");
        jlbPay = new JLabel();

        JPanel p2 = new JPanel();
        p2.setBackground(Color.WHITE);
        p2.setLayout(new GridLayout(5, 1));
        p2.setBounds(350, 157, 80, 100);

        jlb_ArrivalDate.setFont(f);
        jlb_Department.setFont(f);
        jlb_Cashier.setFont(f);
        jlb_NumberNight.setFont(f);
        jlb_Pay.setFont(f);

        p2.add(jlb_ArrivalDate);
        p2.add(jlb_Department);
        p2.add(jlb_Cashier);
        p2.add(jlb_NumberNight);
        p2.add(jlb_Pay);
        add(p2);

        JPanel p4 = new JPanel();
        p4.setBackground(Color.WHITE);
        p4.setLayout(new GridLayout(5, 1));
        p4.setBounds(430, 157, 120, 100);

        jlbArrivalDate.setFont(f);
        jlbDateDepartment.setFont(f);
        jlbCashier.setFont(f);
        jlbNumberNight.setFont(f);
        jlbPay.setFont(f);

        p4.add(jlbArrivalDate);
        p4.add(jlbDateDepartment);
        p4.add(jlbCashier);
        p4.add(jlbNumberNight);
        p4.add(jlbPay);
        add(p4);

        JLabel jlb_Underline2 = new JLabel("--------------------------------------------------------" +
                "----------------------------------------------------------------------------------------");
        jlb_Underline2.setBounds(16, 255, 585, 25);
        add(jlb_Underline2);

        JLabel jlb_Dae = new JLabel("NGÀY");
        JLabel jlb_Detail = new JLabel("CHI TIẾT");
        JLabel jlb_Price = new JLabel("SÔ TIỀN");

        JPanel panelNameServices = new JPanel();
        panelNameServices.setBackground(Color.WHITE);
        panelNameServices.setLayout(new GridLayout(1, 3));
        panelNameServices.setBounds(19, 270, this.getWidth(), 25);
        panelNameServices.add(jlb_Dae);
        panelNameServices.add(jlb_Detail);
        panelNameServices.add(jlb_Price);

        Font ff = new Font("Times New Roman", Font.BOLD, 14);
        jlb_Dae.setFont(ff);
        jlb_Detail.setFont(ff);
        jlb_Price.setFont(ff);
        add(panelNameServices);

        panelServices = new JPanel();
        panelServices.setBackground(Color.WHITE);
        add(panelServices);


            jlb_Underline3 = new JLabel("------------------------------------------------------------------------------------");
//        jlb_Underline3.setBounds(253, 343, 576, 25);
        add(jlb_Underline3);

        jlb_TotalPrice = new JLabel("Tổng cộng:");
//        jlb_TotalPrice.setBounds(363, 357, 100, 25);
        add(jlb_TotalPrice);

        jlbTotalPrice = new JLabel();
        add(jlbTotalPrice);

         jlb_TaxVAT = new JLabel("Đã bao gồm thuế VAT");
//        jlb_TaxVAT.setBounds(51, 368, 200, 25);
        add(jlb_TaxVAT);

         jlb_SignatureCashier = new JLabel("Thu ngân");
//        jlb_SignatureCashier.setBounds(107, 395, 80, 25);
        add(jlb_SignatureCashier);

         jlb_SignatureCus = new JLabel("Khách");
//        jlb_SignatureCus.setBounds(392, 395, 80, 25);
        add(jlb_SignatureCus);

        btnCheckOut = new JButton("Trả phòng");
        btnCheckOut.setActionCommand("btnCheckOut");
        btnCheckOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnCheckOut);

        btnCheckOut_Print = new JButton("Trả phòng và In");
        btnCheckOut_Print.setActionCommand("btnCheckOut_Print");
        btnCheckOut_Print.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnCheckOut_Print);

        jlbBackground = new JLabel();
        jlbBackground.setBackground(Color.WHITE);
        jlbBackground.setOpaque(true);
        add(jlbBackground);
    }

    public JButton getBtnCheckOut_Print() {
        return btnCheckOut_Print;
    }

    public JButton getBtnCheckOut() {
        return btnCheckOut;
    }

    DecimalFormat formatter = new DecimalFormat("#,###");

    public void setService(ServiceRoomModel service) {
        JLabel jlbDate = new JLabel(service.getDateAtStr().substring(0, service.getDateAtStr().length() - 5));
        JLabel jlbName = new JLabel(service.getName() + " (" + service.getQuantityService() + ")");
        JLabel jlPrice = new JLabel(formatter.format(service.getTotalPrice()));
        jlbDate.setFont(f);
        jlbName.setFont(f);
        jlPrice.setFont(f);

        panelServices.add(jlbDate);
        panelServices.add(jlbName);
        panelServices.add(jlPrice);

    }

    public void setTotalPrice(double totalPrice) {
        this.jlbTotalPrice.setText(formatter.format(totalPrice));
    }

    public String getTotalPrice() {
        return jlbTotalPrice.getText().trim().replace(",", "");
    }

    public void setServicePay(List<ServiceRoomModel> serviceRoomModel) {
        panelServices.setBounds(19, 295, this.getWidth(), serviceRoomModel.size() * 25);
        setLocation();
        panelServices.setLayout(new GridLayout(serviceRoomModel.size() + 1, 3));

        for (ServiceRoomModel service : serviceRoomModel) {
            setService(service);
        }
    }
    public void setLocation () {
        jlb_Underline3.setBounds(253, panelServices.getLocation().y+panelServices.getHeight(), 576, 25);
        jlb_TotalPrice.setBounds(363,panelServices.getLocation().y+panelServices.getHeight()+25, 100, 25);
        jlbTotalPrice.setBounds(493, panelServices.getLocation().y+panelServices.getHeight()+25, 100, 25);
        jlb_TaxVAT.setBounds(51, panelServices.getLocation().y+panelServices.getHeight()+50, 200, 25);
        jlb_SignatureCashier.setBounds(107, panelServices.getLocation().y+panelServices.getHeight()+80, 80, 25);
        jlb_SignatureCus.setBounds(392, panelServices.getLocation().y+panelServices.getHeight()+80, 80, 25);
        btnCheckOut.setBounds(130, panelServices.getLocation().y+panelServices.getHeight()+200, 190, 30);
        btnCheckOut_Print.setBounds(300, panelServices.getLocation().y+panelServices.getHeight()+200, 150, 30);
        jlbBackground.setBounds(0, 0, getWidth(), getHeight()+panelServices.getLocation().y);
        setSize(625,getHeight()+panelServices.getLocation().y-150);
    }
    public static void main(String[] args) {
        new FormBill().setVisible(true);
    }


}
