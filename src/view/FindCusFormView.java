package view;

import model.BookedRoomModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class FindCusFormView extends JFrame {
    public  static FindCusFormView instanceFindCusFormView;
    private JDatePickerImpl startDate,endDate;
    private JPanel panelContainBtn;
    private JTextField jtfValueFind;
    private JButton btnExecuteFind,btnExportFileExcel;
    private DefaultTableModel modelService;
    public FindCusFormView() {
        super("Khách hàng đã đặt phòng");

        panelContainBtn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        createStartDatePicker();
        createEndDatePicker();
        panelContainBtn.add(new JLabel("Từ ngày: "));
        panelContainBtn.add(startDate);
        panelContainBtn.add(new JLabel("    Đến ngày: "));
        panelContainBtn.add(endDate);

        jtfValueFind = inputCusPlaceholder();
        jtfValueFind.setColumns(20);
        panelContainBtn.add(jtfValueFind);

        btnExecuteFind = new JButton("Tìm kiếm");
        btnExecuteFind.setBackground(Color.decode("#009ce9"));
        btnExecuteFind.setForeground(Color.WHITE);
        btnExecuteFind.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExecuteFind.addActionListener(e->buttonEvent("btnExecuteFind"));
         panelContainBtn.add(btnExecuteFind);

        btnExportFileExcel = new JButton("Export Excel");
        btnExportFileExcel.setBackground(Color.GREEN);
        btnExportFileExcel.setForeground(Color.WHITE);
        btnExportFileExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportFileExcel.addActionListener(e->buttonEvent("btnExportFileExcel"));
        panelContainBtn.add(btnExportFileExcel);

        add(panelContainBtn,BorderLayout.NORTH);
        add(createTable(),BorderLayout.CENTER);
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
    public static FindCusFormView getInstanceFindCusFormView(){
        if(instanceFindCusFormView == null)
            instanceFindCusFormView = new FindCusFormView();
        return instanceFindCusFormView;
    }
    public Date getStartDate(){
       return (Date) startDate.getModel().getValue();
    }
    public Date getEndDate(){
        return (Date) endDate.getModel().getValue();
    }
    public String getValueFind(){
        return jtfValueFind.getText();
    }
    private JDatePickerImpl createStartDatePicker(){
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(model, p);
        JFormattedTextField.AbstractFormatter abstractFormatter =  new JFormattedTextField.AbstractFormatter(){
            @Override
            public Object stringToValue(String text) throws ParseException {
                return null;
            }
            @Override
            public String valueToString(Object value) throws ParseException {
                if(value!=null){
                    Calendar calendar = (Calendar) value;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String d = simpleDateFormat.format(calendar.getTimeInMillis());
                    return d;
                }
                return "";
            }
        };
        startDate = new JDatePickerImpl(startDatePanel,abstractFormatter);
        return startDate;
    }
    private JDatePickerImpl createEndDatePicker(){
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl endDatePanel = new JDatePanelImpl(model, p);
        JFormattedTextField.AbstractFormatter abstractFormatter =  new JFormattedTextField.AbstractFormatter(){
            @Override
            public Object stringToValue(String text) throws ParseException {
                return null;
            }
            @Override
            public String valueToString(Object value) throws ParseException {
                if(value!=null){
                    Calendar calendar = (Calendar) value;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String d = simpleDateFormat.format(calendar.getTimeInMillis());
                    return d;
                }
                return "";
            }
        };
        endDate = new JDatePickerImpl(endDatePanel,abstractFormatter);
        return endDate;
    }
    private JScrollPane createTable() {

        String[] columnNames = {"Tên khách hàng", "Điện thoại", "Số chứng minh", "Địa chỉ", "Ngày đến","Ngày đi","Phòng"};
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
        scrollPane.setBorder(new EmptyBorder(5,5,5,5));
       return scrollPane;
    }
    public void setDateCus(BookedRoomModel bookedRoomModel){
        System.out.println(bookedRoomModel.toString());
        modelService.addRow(new Object[]{bookedRoomModel.getCustomerModell().getName(),bookedRoomModel.getCustomerModell().getPhone(),
                bookedRoomModel.getCustomerModell().getIdNumber(),bookedRoomModel.getCustomerModell().getAddress()
        ,bookedRoomModel.getFormatStartDate(),bookedRoomModel.getFormatEndDate(),bookedRoomModel.getNumberRoom()});
    }
    public  void exportExcelFiles(){
        // hiển thị hộp thoại lưu file Excel
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files", "csv"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {

                XSSFWorkbook workbook = new XSSFWorkbook(); // sử dụng HSSFWorkbook để tạo file xls (Excel 97-2003)
                XSSFSheet sheet = workbook.createSheet("Data");
                DefaultTableModel model = modelService;
                // tạo hàng đầu tiên (chứa tiêu đề cột)
                XSSFRow headerRow = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                }

                // tạo các hàng chứa dữ liệu
                for (int i = 0; i < model.getRowCount(); i++) {
                    XSSFRow dataRow = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        XSSFCell cell = dataRow.createCell(j);
                        cell.setCellValue(model.getValueAt(i, j).toString());
                    }
                }
                FileOutputStream outputStream = new FileOutputStream(fileChooser.getSelectedFile()+".csv");
                BufferedOutputStream excelBou = new BufferedOutputStream(outputStream);
                workbook.write(excelBou);
                outputStream.close();
                JOptionPane.showMessageDialog(null, "File đã được lưu");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while saving the file");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while writing to the file");
            }
        }
    }
    public void delAllDateTableCus(){
         modelService.setRowCount(0);
    }
    public boolean tableHasData(){
        return modelService.getRowCount() < 1;
    }
    public boolean hasDateNull(){
        return getStartDate() == null || getEndDate() == null;
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

    public void addButtonListener(ButtonListener listener) {
        listenerList.add(ButtonListener.class, listener);
    }

    protected void buttonEvent(String name) {
        ButtonListener[] listeners = listenerList.getListeners(ButtonListener.class);
        for (ButtonListener listener : listeners) {
            listener.buttonPerformed(new RoomEvent(this, name));
        }
    }


    public void showMessNotFoundCus() {
        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng đặt phòng!");
    }

    public static void main(String[] args) {
        new FindCusFormView().setVisible(true);
    }
}
