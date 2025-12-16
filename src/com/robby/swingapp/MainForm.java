package com.robby.swingapp;

import com.robby.swingapp.entity.Department;
import com.robby.swingapp.entity.Student;
import com.robby.swingapp.service.DepartmentDao;
import com.robby.swingapp.service.StudentDao;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Robby Tan
 */
public class MainForm {
    private JTextField txtId;
    private JTextField txtName;
    private JTextArea txtAddress;
    private JRadioButton rbMale;
    private JComboBox<Department> comboDepartment;
    private JButton btnSubmit;
    private JButton btnClear;
    private JTable tableStudent;
    private JSplitPane rootPanel;
    private List<Department> departments;
    private List<Student> students;
    private StudentDao studentDao;
    private StudentTableModel studentTableModel;
    private DepartmentDao departmentDao;

    public MainForm() {
        this.initData();
        this.initComponents();
        btnSubmit.addActionListener(e -> {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String address = txtAddress.getText().trim();
            boolean isMale = rbMale.isSelected();
            Department department = comboDepartment.getItemAt(comboDepartment.getSelectedIndex());
            try {
                if (studentDao.fetch(id) != null) {
                    JOptionPane.showMessageDialog(rootPanel, "Duplicate ID", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Student student = new Student.Builder()
                            .id(id).name(name).address(address).male(isMale).department(department).build();
                    if (studentDao.addData(student) == 1) {
                        JOptionPane.showMessageDialog(rootPanel, "Student Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTable();
                        clearField();
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnClear.addActionListener(e -> clearField());
    }

    private void initData() {
        departmentDao = new DepartmentDao();
        studentDao = new StudentDao();
        try {
            departments = departmentDao.fetchAll();
            students = studentDao.fetchAll();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            if (SwingUtilities.getWindowAncestor(rootPanel) != null) {
                SwingUtilities.getWindowAncestor(rootPanel).dispose();
            }
        }
    }

    private void initComponents() {
        DefaultComboBoxModel<Department> departmentModel = new DefaultComboBoxModel<>();
        departmentModel.addAll(departments);
        comboDepartment.setModel(departmentModel);

        studentTableModel = new StudentTableModel(students);
        tableStudent.setModel(studentTableModel);
    }

    public void createAndShowUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void clearField() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
    }

    private void refreshTable() {
        students.clear();
        try {
            students.addAll(studentDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        studentTableModel.fireTableDataChanged();
    }

    private static class StudentTableModel extends AbstractTableModel {

        private final List<Student> students;
        private final String[] COLUMNS = {"ID", "Name", "Address", "Gender", "Department"};

        public StudentTableModel(List<Student> students) {
            this.students = students;
        }

        @Override
        public int getRowCount() {
            return students.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> students.get(rowIndex).getId();
                case 1 -> students.get(rowIndex).getName();
                case 2 -> students.get(rowIndex).getAddress();
                case 3 -> students.get(rowIndex).isMale();
                case 4 -> students.get(rowIndex).getDepartment();
                default -> "";
            };
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }
            return Object.class;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }
    }
}
