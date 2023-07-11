package view;

import model.AccountAndEmployeeModel;
import model.EmployeeAccountModel;
import model.EmployeeModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelManagerEmployee extends JPanel {
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, changePasswordButton, findButton;
    private JTable employeeTable;
    private DefaultTableModel model;
    private EmpoyeeForm empoyeeForm;
    private ChangePasswordForm changePasswordForm;
    private EditForm editForm;
    private TableRowSorter<DefaultTableModel> sorter;
    private List<EmployeeModel> employeeModels;
    public PanelManagerEmployee() {
        setName("PanelManagerEmployee");
        // Set layout for the panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
        createSearchPanel();
        createTablePanel();
        empoyeeForm = new EmpoyeeForm();
        changePasswordForm = new ChangePasswordForm();
        editForm = new EditForm();
        employeeModels = new ArrayList<>();
        setActionBtn();

    }

    private void setActionBtn() {
        findButton.addActionListener(e -> {

            // Lọc các hàng dữ liệu dựa trên từ khóa tìm kiếm
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                List<RowFilter<Object, Object>> filters = new ArrayList<>();
                filters.add(RowFilter.regexFilter(searchText));
                RowFilter<Object, Object> filter = RowFilter.andFilter(filters);
                sorter.setRowFilter(filter);
            }

        });

        addButton.addActionListener(e -> empoyeeForm.showForm(true));
        editButton.addActionListener(e -> buttonEvent("editButton"));
        deleteButton.addActionListener(e -> buttonEvent("deleteButton"));
        empoyeeForm.getSaveButton().addActionListener(e -> buttonEvent("saveEmployeeButton"));
        changePasswordButton.addActionListener(e -> buttonEvent("changePasswordButton"));
    }

    public EmpoyeeForm getUserForm() {
        return empoyeeForm;
    }

    public ChangePasswordForm getChangePasswordForm() {
        return changePasswordForm;
    }

    public EditForm getEditForm() {
        return editForm;
    }

    public void removeRowEmployee(String nameAccount) {
        for (int i = 0; i < employeeTable.getRowCount(); i++) {
            String nameAccountRow = (String) employeeTable.getValueAt(i, 1);
            if (nameAccountRow.equalsIgnoreCase(nameAccount)) model.removeRow(i);
        }
    }

    private void createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());

        JPanel searchPanel1 = new JPanel();
        Cursor c = new Cursor(Cursor.HAND_CURSOR);
        searchFieldCustom();
        findButton = new JButton("Tìm");
        findButton.setCursor(c);
        searchPanel1.add(searchField);
        searchPanel1.add(findButton);
        searchPanel.add(searchPanel1, BorderLayout.LINE_START);

        JPanel searchPanel2 = new JPanel();
        addButton = new JButton("Thêm mới");
        addButton.setCursor(c);
        searchPanel2.add(addButton);

        editButton = new JButton("Sửa");
        editButton.setCursor(c);
        searchPanel2.add(editButton);

        deleteButton = new JButton("Xóa");
        editButton.setCursor(c);
        searchPanel2.add(deleteButton);

        changePasswordButton = new JButton("Đổi mật khẩu");
        editButton.setCursor(c);
        searchPanel2.add(changePasswordButton);
        searchPanel.add(searchPanel2, BorderLayout.LINE_END);
        add(searchPanel, BorderLayout.NORTH);
    }

    private void createTablePanel() {
        // Add table to the panel
        String[] columnNames = {"STT", "Tài khoản", "Họ tên", "Email", "Điện thoại", "Địa chỉ", "Vị trí", "Trạng thái", ""};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 8)
                    return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };

        employeeTable = new JTable(model);
        employeeTable.getColumnModel().getColumn(7).setCellRenderer(new CustomRenderer());
        // Tạo một TableRowSorter để lọc các hàng dữ liệu
        sorter = new TableRowSorter<>(model);
        employeeTable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

    }
    public void delAllRow(){
        model.setRowCount(0);
    }
    public void setDataEmployeeTable(AccountAndEmployeeModel ae) {
        // save list employee
        employeeModels.add(ae.getEmployee());
        model.addRow(new Object[]{"", ae.getAccount().getNameAccount(), ae.getEmployee().getName(), ae.getEmployee().getEmail(),
                ae.getEmployee().getNumberPhone(), ae.getEmployee().getAddress(),
                ae.getEmployee().getPosition(), ae.getAccount().isActiveAccount() ? customJlbEnable(Color.GREEN) : customJlbEnable(Color.RED), false});
        updateRowNumbers(employeeTable);
    }

    private void updateRowNumbers(JTable table) {
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (i >= 0 && i < table.getRowCount())
                table.setValueAt(i + 1, i, 0);
        }
    }

    public List<AccountAndEmployeeModel> getCustomerSelected() {
        List<AccountAndEmployeeModel> l = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean hasOption = (boolean) model.getValueAt(i, 8);
            if (hasOption) {
                //các điều kiện dùng dể xóa nhân viên
                String account = (String) model.getValueAt(i, 1);
                EmployeeAccountModel ac = new EmployeeAccountModel(account, null);

                String email = (String) model.getValueAt(i, 3);
                String phone = (String) model.getValueAt(i, 4);
                EmployeeModel e = new EmployeeModel();
                e.setEmail(email);
                e.setNumberPhone(phone);

                AccountAndEmployeeModel a = new AccountAndEmployeeModel(ac, e);
                l.add(a);
            }
        }
        return l;
    }
    public boolean selectedRowOne(){
        int item = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean hasOption = (boolean) model.getValueAt(i, 8);
            if (hasOption) item++;
        }
        return item == 1;
    }
    public void loadDataEmployeeToEditForm(){
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean hasOption = (boolean) model.getValueAt(i, 8);

            System.err.println(employeeModels.get(i));
            if (hasOption){

                int index = (int) model.getValueAt(i, 0);
                EmployeeModel employeeModel = employeeModels.get(index-1);
                editForm.setInfoEmployeeCurrent(employeeModel);
            }
        }
    }

    public String getUserName(){
        String name = null;
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean hasOption = (boolean) model.getValueAt(i, 8);
            if (hasOption){
                name = (String) model.getValueAt(i, 1);
                break;
            }
        }
        return name;
    }
    private void searchFieldCustom() {
        searchField = new JTextField(20);
        searchField.setForeground(Color.GRAY);
        searchField.setPreferredSize(new Dimension(200, 25));
        searchField.setText("Tên nhân viên tìm kiếm...");
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Tên nhân viên tìm kiếm...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Tên nhân viên tìm kiếm...");
                }
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Không làm gì khi có giá trị được thêm vào JTextField
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (searchField.getText().isEmpty()) {
                    // Xóa bộ lọc hàng và khôi phục lại tất cả các hàng dữ liệu
                    sorter.setRowFilter(null);
                    searchField.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không làm gì khi có thay đổi giá trị trong JTextField
            }
        });
    }

    private JLabel customJlbEnable(Color state) {
        JLabel enable = new JLabel("Kích hoạt", JLabel.CENTER);
        enable.setForeground(Color.WHITE);
        enable.setOpaque(true);
        enable.setBackground(state);
        return enable;
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

    private static class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Component) {
                return (Component) value;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public static void main(String[] args) {

        JFrame a = new JFrame();
        a.setSize(500,700);
        a.add(new PanelManagerEmployee());
        a.setVisible(true);

    }
}