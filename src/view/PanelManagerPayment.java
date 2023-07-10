package view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PanelManagerPayment extends JPanel {
    DefaultTableModel selectBillInDate, selectListBill, selectServiceBill;
    JTable selectBillInDateTable, selectListBillTable, selectServiceBillTable;
    JTextField inputCus;
    JButton btnFind, btnFindBill;
    JDatePickerImpl datePicker;
    JComboBox<String> codeBills;

    public PanelManagerPayment() {
        setName("PanelManagerPayment");
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        add(createPanelBillInDate(), BorderLayout.CENTER);

        JPanel p = new JPanel(new BorderLayout());
        p.add(createPanelListBill(), BorderLayout.NORTH);
        p.add(createPanelServiceBill(), BorderLayout.SOUTH);
        add(p, BorderLayout.SOUTH);

    }
    private JTextField inputCusPlaceholder() {
        JTextField inputCus = new JTextField(20);
        inputCus.setForeground(Color.GRAY);
        inputCus.setPreferredSize(new Dimension(200, 25));
        inputCus.setText("Tên khách hàng...");
        inputCus.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (inputCus.getText().equals("Tên khách hàng...")) {
                    inputCus.setText("");
                    inputCus.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (inputCus.getText().isEmpty()) {
                    inputCus.setForeground(Color.GRAY);
                    inputCus.setText("Tên khách hàng...");
                }
            }
        });
        inputCus.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Không làm gì khi có giá trị được thêm vào JTextField
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (inputCus.getText().isEmpty()) {
                    // Xóa bộ lọc hàng và khôi phục lại tất cả các hàng dữ liệu
                    inputCus.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không làm gì khi có thay đổi giá trị trong JTextField
            }
        });
        return inputCus;
    }
    public JButton getBtnFind() {
        return btnFind;
    }

    public JButton getBtnFindBill() {
        return btnFindBill;
    }
    public void setCodeBills(List<Object[]> billsCus) {
        for (Object[] billCus : billsCus) {
            int codeBill = (int) billCus[6];
            if (!hasCodeBill(codeBill))
                codeBills.addItem(String.valueOf(codeBill));
        }
        sortCodeBill();
    }

    private boolean hasCodeBill(int code) {
        for (int i = 0; i < codeBills.getItemCount(); i++) {
            if (code == Integer.parseInt(codeBills.getItemAt(i)))
                return true;
        }
        return false;
    }

    private void sortCodeBill() {
        Integer[] bills = new Integer[codeBills.getItemCount()];
        for (int i = 0; i < codeBills.getItemCount(); i++) {
            bills[i] = Integer.parseInt(codeBills.getItemAt(i));
        }
        Arrays.sort(bills);
        System.out.println(Arrays.toString(bills));
// Xóa các phần tử trong JComboBox cũ
        codeBills.removeAllItems();

// Thêm các phần tử đã sắp xếp vào JComboBox
        for (Integer bill : bills) {
            codeBills.addItem(String.valueOf(bill));
        }
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

    private JScrollPane createSCrollBarPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 120));
        return scrollPane;
    }

    public void setDataBillInDate(List<Object[]> objects) {
        for (Object[] billCus : objects) {
            selectBillInDate.addRow(new Object[]{
                    billCus[0], billCus[1], billCus[2], billCus[3], billCus[4], billCus[5], billCus[6],
            });
        }

    }

    private JPanel createPanelBillInDate() {

        String[] selectColumnNames = {"STT", "Khách hàng", "Số điện thoại", "Số tiền thanh toán", "Phương thức thanh toán", "Ngày thanh toán", "Số hóa đơn"};
        selectBillInDate = new DefaultTableModel(selectColumnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (row >= 0 && row < getRowCount()) {
                    // chỉ mục hàng hợp lệ, cập nhật giá trị
                    super.setValueAt(aValue, row, column);
                } else {
                    System.out.println("Error at: " + " value " + aValue + " row: " + row + " col: " + column);
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) { // kiểm tra nếu cột chỉ số lượng
                    return Integer.class; // trả về kiểu dữ liệu của cột là Integer
                } else {
                    return super.getColumnClass(columnIndex); // trả về kiểu dữ liệu của các cột khác
                }

            }
        };
        selectBillInDateTable = new JTable(selectBillInDate);

        JPanel panelServicesSelect = new JPanel();
        panelServicesSelect.setLayout(new BorderLayout());

        JLabel jLabel = new JLabel("Các hóa đơn trong ngày");
        jLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelServicesSelect.add(jLabel, BorderLayout.NORTH);
        panelServicesSelect.add(createSCrollBarPane(selectBillInDateTable), BorderLayout.CENTER);
        return panelServicesSelect;
    }

    public void setDataListBill(List<Object[]> objects) {
        // del old data
        selectListBill.setRowCount(0);
        // add new data
        for (Object[] billCus : objects) {
            selectListBill.addRow(new Object[]{
                    billCus[0], billCus[1], billCus[2], billCus[3], billCus[4], billCus[5], billCus[6],
            });
        }

    }

    private JPanel createPanelListBill() {

        String[] selectColumnNames = {"STT", "Khách hàng", "Số điện thoại", "Số tiền thanh toán", "Phương thức thanh toán", "Ngày thanh toán", "Số hóa đơn"};
        selectListBill = new DefaultTableModel(selectColumnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (row >= 0 && row < getRowCount()) {
                    // chỉ mục hàng hợp lệ, cập nhật giá trị
                    super.setValueAt(aValue, row, column);
                } else {
                    System.out.println("Error at: " + " value " + aValue + " row: " + row + " col: " + column);
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) { // kiểm tra nếu cột chỉ số lượng
                    return Integer.class; // trả về kiểu dữ liệu của cột là Integer
                } else {
                    return super.getColumnClass(columnIndex); // trả về kiểu dữ liệu của các cột khác
                }

            }
        };
        selectListBillTable = new JTable(selectListBill);

        JPanel panelServicesSelect = new JPanel();
        panelServicesSelect.setLayout(new BorderLayout());

        JPanel p = new JPanel(new FlowLayout());
        JLabel jLabel = new JLabel("Danh sách hóa đơn");
        jLabel.setBorder(new EmptyBorder(8, 0, 10, 16));

        inputCus = inputCusPlaceholder();
        btnFind = new JButton("Tìm");
        btnFind.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(jLabel);
        p.add(inputCus);
        p.add(btnFind);

        panelServicesSelect.add(p, BorderLayout.LINE_START);
        panelServicesSelect.add(createSCrollBarPane(selectListBillTable), BorderLayout.PAGE_END);

        return panelServicesSelect;
    }

    public void setDataServiceBill(List<Object[]> objects) {
        // del old data
        selectServiceBill.setRowCount(0);
        // add new data
        for (Object[] service : objects) {
            selectServiceBill.addRow(new Object[]{
                    service[0], service[1], service[2], service[3], service[4], service[5], service[6],
            });
        }
    }
    public void removeAllCodeBill(){
        codeBills.removeAllItems();
    }
    private JPanel createPanelServiceBill() {

        String[] selectColumnNames = {"STT", "Số hóa đơn", "Tên", "Ngày thanh toán", "Số lương", "Ngày thanh toán", "Tổng giá"};
        selectServiceBill = new DefaultTableModel(selectColumnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (row >= 0 && row < getRowCount()) {
                    // chỉ mục hàng hợp lệ, cập nhật giá trị
                    super.setValueAt(aValue, row, column);
                } else {
                    System.out.println("Error at: " + " value " + aValue + " row: " + row + " col: " + column);
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) { // kiểm tra nếu cột chỉ số lượng
                    return Integer.class; // trả về kiểu dữ liệu của cột là Integer
                } else {
                    return super.getColumnClass(columnIndex); // trả về kiểu dữ liệu của các cột khác
                }

            }
        };
        selectServiceBillTable = new JTable(selectServiceBill);

        JPanel panelServicesSelect = new JPanel();
        panelServicesSelect.setLayout(new BorderLayout());

        JPanel p = new JPanel(new FlowLayout());
        JLabel jLabel = new JLabel("Dịch vụ hóa đơn");
        codeBills = new JComboBox<>();
        btnFindBill = new JButton("Xem hóa đơn");
        datePicker = createDatePicker();
        btnFindBill.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel.setBorder(new EmptyBorder(8, 0, 10, 16));
        codeBills.setBorder(new EmptyBorder(8, 0, 10, 14));
        datePicker.setBorder(new EmptyBorder(8, 0, 10, 14));

        p.add(jLabel);
//        p.add(datePicker);
        p.add(codeBills);
        p.add(btnFindBill);

        panelServicesSelect.add(p, BorderLayout.LINE_START);
        panelServicesSelect.add(createSCrollBarPane(selectServiceBillTable), BorderLayout.PAGE_END);

        return panelServicesSelect;
    }

    public String getStrCusFind() {
        if (inputCus.getText().isEmpty()) return null;
        return inputCus.getText().trim();
    }

    public int getCodeBillFind() {
        return Integer.parseInt(String.valueOf(codeBills.getSelectedIndex()));
    }

    public static void main(String[] args) {
        PanelManagerPayment panelManagerPayment = new PanelManagerPayment();
        JFrame a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(800, 600);
        a.add(panelManagerPayment);
        a.setVisible(true);
    }


}
