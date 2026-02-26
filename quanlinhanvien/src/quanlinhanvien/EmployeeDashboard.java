package quanlinhanvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeDashboard extends JFrame {

    private JTable employeeTable;
    private DefaultTableModel model;
    private JTextField searchField;

    public EmployeeDashboard() {
        setTitle("Employee Management System");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Name", "Position", "Base Salary", "Attendance (days)", "Total Salary"};
        Object[][] data = {
            {"1", "Alice Johnson", "Manager", "1000", "20", calculateSalary(1000, 20)},
            {"2", "Bob Smith", "Developer", "800", "22", calculateSalary(800, 22)},
            {"3", "Charlie Brown", "Designer", "750", "18", calculateSalary(750, 18)},
        };

        model = new DefaultTableModel(data, columnNames);
        employeeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // --- Top panel (Search) ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("🔍 Search");
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        // --- Bottom panel (Buttons) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton addBtn = new JButton("➕ Add");
        JButton editBtn = new JButton("✏️ Edit");
        JButton deleteBtn = new JButton("🗑 Delete");
        JButton attendanceBtn = new JButton("📅 Attendance");
        JButton salaryBtn = new JButton("💰 Update Salary");

        bottomPanel.add(addBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(attendanceBtn);
        bottomPanel.add(salaryBtn);

        // --- Button actions ---
        addBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Employee ID:");
            String name = JOptionPane.showInputDialog("Enter Name:");
            String position = JOptionPane.showInputDialog("Enter Position:");
            String baseSalary = JOptionPane.showInputDialog("Enter Base Salary:");
            String attendance = JOptionPane.showInputDialog("Enter Attendance (days):");
            if (id != null && name != null && position != null && baseSalary != null && attendance != null) {
                String totalSalary = calculateSalary(Double.parseDouble(baseSalary), Integer.parseInt(attendance));
                model.addRow(new Object[]{id, name, position, baseSalary, attendance, totalSalary});
            }
        });

        editBtn.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to edit!");
                return;
            }
            String id = JOptionPane.showInputDialog("Edit ID:", model.getValueAt(row, 0));
            String name = JOptionPane.showInputDialog("Edit Name:", model.getValueAt(row, 1));
            String position = JOptionPane.showInputDialog("Edit Position:", model.getValueAt(row, 2));
            String baseSalary = JOptionPane.showInputDialog("Edit Base Salary:", model.getValueAt(row, 3));
            String attendance = JOptionPane.showInputDialog("Edit Attendance:", model.getValueAt(row, 4));

            String totalSalary = calculateSalary(Double.parseDouble(baseSalary), Integer.parseInt(attendance));
            model.setValueAt(id, row, 0);
            model.setValueAt(name, row, 1);
            model.setValueAt(position, row, 2);
            model.setValueAt(baseSalary, row, 3);
            model.setValueAt(attendance, row, 4);
            model.setValueAt(totalSalary, row, 5);
        });

        deleteBtn.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to delete!");
                return;
            }
            model.removeRow(row);
        });

        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a keyword to search!");
                return;
            }
            for (int i = 0; i < model.getRowCount(); i++) {
                String name = model.getValueAt(i, 1).toString().toLowerCase();
                String position = model.getValueAt(i, 2).toString().toLowerCase();
                if (name.contains(keyword) || position.contains(keyword)) {
                    employeeTable.setRowSelectionInterval(i, i);
                    employeeTable.scrollRectToVisible(employeeTable.getCellRect(i, 0, true));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No employee found with keyword: " + keyword);
        });

        attendanceBtn.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to update attendance!");
                return;
            }
            String newAttendance = JOptionPane.showInputDialog("Enter new attendance (days):", model.getValueAt(row, 4));
            if (newAttendance != null) {
                String baseSalary = model.getValueAt(row, 3).toString();
                String totalSalary = calculateSalary(Double.parseDouble(baseSalary), Integer.parseInt(newAttendance));
                model.setValueAt(newAttendance, row, 4);
                model.setValueAt(totalSalary, row, 5);
            }
        });

        salaryBtn.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to update salary!");
                return;
            }
            String baseSalary = JOptionPane.showInputDialog("Enter new base salary:", model.getValueAt(row, 3));
            String attendance = model.getValueAt(row, 4).toString();
            if (baseSalary != null) {
                String totalSalary = calculateSalary(Double.parseDouble(baseSalary), Integer.parseInt(attendance));
                model.setValueAt(baseSalary, row, 3);
                model.setValueAt(totalSalary, row, 5);
            }
        });

        // --- Layout ---
        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String calculateSalary(double baseSalary, int attendance) {
        double salary = baseSalary * attendance / 26.0;
        return String.format("%.2f", salary);
    }
}