package view;

import model.ServiceRoomModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelManagerServiceView extends JPanel {
    private JComboBox<String> serviceComboBox, selectComboBox;
    private JTable serviceTable, selectTable, addedTable;
    private DefaultTableModel serviceModel, selectModel, addedModel;
    private JLabel jlbListService;
    private JButton btnAdđServiceOption, btnSaveService;
    private Map<Integer, Double> priceServices;


    public PanelManagerServiceView() {
        setName("PanelManagerServiceView");
        setLayout(new BorderLayout());

        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBorder(new EmptyBorder(5, 5, 5, 5));

        p1.add(createPanelServices(), BorderLayout.WEST);
        p1.add(createPanelServicesSelect(), BorderLayout.CENTER);
        add(p1, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(createPanelAddedServicesSelect(), BorderLayout.CENTER);

        btnSaveService = new JButton("Lưu");
        btnSaveService.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSaveService.addActionListener(e -> buttonEvent("btnSaveService"));

        p2.add(btnSaveService, BorderLayout.SOUTH);
        add(p2, BorderLayout.SOUTH);

        btnAdđServiceOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectComboBox.getItemCount()<1){
                    JOptionPane.showMessageDialog(null,"Không tìm thấy phòng thêm dịch vụ");
                    return;
                }
                else {
                    btnSaveService.setEnabled(true);
                }

                if (selectModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn các dịch vụ cần thêm!");
                    return;
                }
                int numberRoom = Integer.parseInt((String) selectComboBox.getModel().getSelectedItem());
                int productNameColumnIndex = 1;
                int quantityColumnIndex = 2;
                int totalPriceColumnIndex = 4;

                for (int i = 0; i < selectModel.getRowCount(); i++) {
                    String productName = (String) selectModel.getValueAt(i, productNameColumnIndex);
                    int quantity = (int) selectModel.getValueAt(i, quantityColumnIndex);
                    long totalPrice = formatStrNum((String) selectModel.getValueAt(i, totalPriceColumnIndex));
                    setDataAddedModel(numberRoom, productName, quantity, totalPrice);
                }
                selectModel.setRowCount(0);
                // dac nhan so luong dịc vu da chon
                setNumAddedService(addedTable.getRowCount());
            }

        });
        // -------------------------------------------------------------------

        priceServices = new HashMap<>();

    }
    public void offSaveService(){
         btnSaveService.setEnabled(false);
    }
    private JPanel createPanelServices() {
        // Init dropdowns
        String[] serviceOptions = {"Tất cả sản phẩm", "Đồ ăn", "Nước"};
        serviceComboBox = new JComboBox<>(serviceOptions);
        serviceComboBox.addActionListener(e_ -> buttonEvent("serviceComboBox"));

        // Init tables
        String[] serviceColumnNames = {"STT", "Tên sản phẩm", ""};
        serviceModel = new DefaultTableModel(serviceColumnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Chỉ cho phép chỉnh sửa cột "Thêm"
            }
        };
        serviceTable = new JTable(serviceModel);
        serviceTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer("img/add.png"));
        serviceTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), "addBtn"));

        // Init panels
        JPanel panelServices = new JPanel();
        panelServices.setBorder(new EmptyBorder(0, 0, 0, 20));
        // Add components to panels
        panelServices.setLayout(new BorderLayout());
        panelServices.add(serviceComboBox, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(serviceTable);
        Dimension tableSize = new Dimension(300, 100);
        scrollPane.setPreferredSize(tableSize);
        panelServices.add(scrollPane, BorderLayout.CENTER);

        return panelServices;
    }

    private JPanel createPanelServicesSelect() {
        selectComboBox = new JComboBox<>();

        String[] selectColumnNames = {"STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Bỏ"};
        selectModel = new DefaultTableModel(selectColumnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 5; // Chỉ cho phép chỉnh sửa cột "Thêm"
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
                }
                else {
                    return super.getColumnClass(columnIndex); // trả về kiểu dữ liệu của các cột khác
                }

            }
        };
        selectTable = new JTable(selectModel);
        selectTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("img/minus.png"));
        selectTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "minusBtn"));

        JPanel panelServicesSelect = new JPanel();
        panelServicesSelect.setLayout(new BorderLayout());
        panelServicesSelect.add(selectComboBox, BorderLayout.NORTH);
        panelServicesSelect.add(new JScrollPane(selectTable), BorderLayout.CENTER);

        return panelServicesSelect;
    }

    private void setNumAddedService(int num) {
        jlbListService.setText("Danh sách địch vụ đã thêm (" + num + ")");
    }

    private JPanel createPanelAddedServicesSelect() {
        JPanel panelAddedServices = new JPanel(new BorderLayout());
        panelAddedServices.setBorder(new EmptyBorder(0, 5, 5, 5));

        jlbListService = new JLabel();
        setNumAddedService(0);

        btnAdđServiceOption = new JButton("Thêm dịch vụ");
        btnAdđServiceOption.setCursor(new Cursor(Cursor.HAND_CURSOR));

        String[] addCol = {"STT", "Phòng", "Tên dịch vụ", "Số lượng", "Thời gian", "Tổng tiền"};
        addedModel = new DefaultTableModel(addCol, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Chỉ cho phép chỉnh sửa cột "Thêm"
            }
        };
        addedTable = new JTable(addedModel);
        panelAddedServices.add(jlbListService, BorderLayout.LINE_START);
        panelAddedServices.add(btnAdđServiceOption, BorderLayout.LINE_END);

        JScrollPane scrollPane = new JScrollPane(addedTable);
        Dimension tableSize = new Dimension(400, 160);
        scrollPane.setPreferredSize(tableSize);
        scrollPane.setBorder(new EmptyBorder(5, 0, 0, 0));
        panelAddedServices.add(scrollPane, BorderLayout.PAGE_END);

        return panelAddedServices;
    }

    public void setCBDateNumberRoom(List<Integer> integers) {
        for (Integer integer : integers) {
            this.selectComboBox.addItem(String.valueOf(integer));
        }
    }
    public boolean hasNumberRoom() {
            return this.selectComboBox.getItemCount()>0;
    }
    public void setCBDateNumberRoom(Integer integer) {
        this.selectComboBox.addItem(String.valueOf(integer));
    }

    public String getOptionServiceCb() {
        return (String) this.serviceComboBox.getSelectedItem();
    }

    public void setDataServiceModel(ServiceRoomModel serviceRoomModel) {
        this.serviceModel.addRow(new Object[]{
                "", serviceRoomModel.getName()});
        updateRowNumbers(serviceTable);
        // luu lai gia tam thoi gia tien san pham
        if (!priceServices.containsKey(serviceRoomModel.getId())) {
            priceServices.put(serviceRoomModel.getId(), serviceRoomModel.getPrice());
        }
    }

    public void setDataSelectModel(String nameProduct, double price) {
        // cập nhật số lượng sản phẩ nếu click vào sản phẩm tồn tại
        int r = getRowValue(this.selectTable, 1, nameProduct);
        if (r > -1) {
            int numPro = (int) this.selectModel.getValueAt(r, 2);
            this.selectModel.setValueAt(++numPro, r, 2);
            return;
        }

        BigDecimal big = new BigDecimal(price);
        this.selectModel.addRow(new Object[]{
                "", nameProduct, 1,salaryFormat(big) ,salaryFormat(big)});
    }
    public String salaryFormat(BigDecimal decimal) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String formattedValue = formatter.format(decimal);
        return formattedValue;
    }
    public void setDataAddedModel(int numberRoom, String nameProduct, int number, long price) {
        BigDecimal totalPrice = new BigDecimal(price );

        this.addedModel.addRow(new Object[]{
                "", numberRoom, nameProduct, number, new Timestamp(System.currentTimeMillis()),salaryFormat(totalPrice)});
        updateRowNumbers(addedTable);
        // xoa du lieu tu bang selectModel sao khi them du lieu
    }
    public List<List<Object>> getListAddedService() {
        List<List<Object>> result = new ArrayList<>();
        int roomColumnIndex = 1;
        int serviceNameColumnIndex = 2;
        int quantityColumnIndex = 3;
        int timeColumnIndex = 4;
        int totalPriceColumnIndex = 5;
        for (int i = 0; i < addedTable.getRowCount(); i++) {
            int numRoom = (int) addedTable.getValueAt(i, roomColumnIndex);
            String serviceName = (String) addedTable.getValueAt(i, serviceNameColumnIndex);
            int quantity = (int) addedTable.getValueAt(i, quantityColumnIndex);
            Timestamp time = (Timestamp) addedTable.getValueAt(i, timeColumnIndex);
            long totalPrice = formatStrNum((String) addedTable.getValueAt(i, totalPriceColumnIndex));

            List<Object> service = new ArrayList<>();
            service.add(numRoom);
            service.add(serviceName);
            service.add(quantity);
            service.add(time);
            service.add(totalPrice);
            result.add(service);
        }
        return result;
    }

    public void delAllDateServiceModel() {
        this.serviceModel.setRowCount(0);
    }

    private int getRowValue(JTable table, int col, String data) {
        int r = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            Object value = table.getValueAt(i, col);
            if (value.equals(data)) {
                r = i;
                break;
            }
        }
        return r;
    }

    public void delAllDateAddedModel() {
        this.addedModel.setRowCount(0);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        String img;

        public ButtonRenderer(String img) {
            this.img = img;
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setIcon(new ImageIcon(new ImageIcon(this.img).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            setBorderPainted(false);
            setBackground(Color.WHITE);
            return this;
        }
    }

    // Tạo một lớp JButton tùy chỉnh để xử lý sự kiện nhấp chuột trên nút
    class ButtonEditor extends DefaultCellEditor {
        protected String typeButton;
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditor(JCheckBox checkBox, String typeButton) {
            super(checkBox);
            this.typeButton = typeButton;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                int stt = 1;

                public void actionPerformed(ActionEvent e) {
                    switch (typeButton) {
                        case "addBtn": {
                            System.out.println("ddddddddđ");
                            int idProduct = (int) serviceModel.getValueAt(row, 0);
                            String nameProduct = (String) serviceModel.getValueAt(row, 1);
                            if (priceServices.containsKey(idProduct)) {
                                double price = priceServices.get(idProduct);
                                setDataSelectModel(nameProduct, price);
                                updateRowNumbers(selectTable);
                            }
                            break;
                        }
                        case "minusBtn": {
                            int selectedRowIndex = selectTable.getSelectedRow();
                            if (selectedRowIndex != -1) {
                                selectModel.removeRow(selectedRowIndex);
                                updateRowNumbers(selectTable);
                            }
                            break;
                        }
                    }

                    setListenerChangeQuantity();
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Perform action when button is pushed
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
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

    private void updateRowNumbers(JTable table) {
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (i >= 0 && i < table.getRowCount())
                table.setValueAt(i + 1, i, 0);
        }
    }
    private long formatStrNum(String numFormat){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        try {
            Number number = decimalFormat.parse(numFormat);
            return number.longValue();
        } catch (Exception e) {
            System.out.println("Invalid number format");
        }
        return -1;
    }
    public void setListenerChangeQuantity() {
        selectTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Kiểm tra xem sự kiện thay đổi giá trị có phải là từ cột "Số lượng" không
                if (e.getColumn() == 2) { // 2 là số thứ tự cột "Số lượng"
                    int row = e.getFirstRow(); // Lấy số hàng tương ứng với giá trị đã thay đổi
                    int soLuong = (int) selectModel.getValueAt(row, 2);
//                    double donGia =   Double.parseDouble();
                    double thanhTien = soLuong * formatStrNum((String) selectModel.getValueAt(row, 3));
                    selectModel.setValueAt(salaryFormat(BigDecimal.valueOf(thanhTien)), row, 4);
                }
            }
        });
    }

}
