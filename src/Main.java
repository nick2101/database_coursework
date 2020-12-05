import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import static javax.swing.GroupLayout.Alignment.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    Register register;
    JFrame startFrame;
    JFrame mainFrame;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        register = new Register();
        loadStartWindow();
    }

    private void loadStartWindow() {
        int component_height = 30;
        int component_width = 200;

        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        Component components[] = new Component[7];

        components[0] = new JLabel("Логин:");
        JTextField jTextField = new JTextField();
        jTextField.setToolTipText("Введите логин");
        jTextField.setColumns(5);
        components[1] = jTextField;
        components[2] = new JLabel("Пароль:");
        components[3] = new JPasswordField();
        components[4] = new JLabel("");
        components[5] = new JButton("Войти");
        JButton jButton = new JButton("Гость");
        components[6] = jButton;

        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            component.setFont(font1);
            component.setSize(component_width, component_height);
            component.setLocation(100, (component_height + 10) * i);
            mainPanel.add(component);
        }

        components[0].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        components[5].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JTextField textField = (JTextField) components[1];
                JPasswordField passwordField = (JPasswordField) components[3];
                String login = textField.getText().trim();
                String password = passwordField.getText().trim();
                int sign_in = register.sign_in(login, password);
                if (sign_in == 0)
                {
                    loadMainWindow();
                    startFrame.dispose();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        components[6].setBackground(null);
        components[6].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                register.sign_in("guest", "guest");
                loadMainWindow();
                startFrame.dispose();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        startFrame = new JFrame("Авторизация");
        startFrame.add(mainPanel);
        startFrame.setPreferredSize(new Dimension(400, 400));
        startFrame.pack();
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);
        startFrame.setResizable(false);
        startFrame.setVisible(true);
    }

    private void loadMainWindow() {
        //фрейм
        mainFrame = new JFrame("Спортивная программа");
        //панел
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        if(register.getUserType() < 4)
            main_panel.add(loadProgramPanel(), BorderLayout.CENTER);
        else
            main_panel.add(loadBroadcastPanel(), BorderLayout.CENTER);
        //меню бар
        JMenuBar menu_bar = new JMenuBar();
        //менюшки
        JMenu [] menus = new JMenu[4];
        //подменю
        JMenuItem menu_items[][] = {
                { new JMenuItem("Сотрудники"), new JMenuItem("Логи"), new JMenuItem("Выход")},
                { new JMenuItem("Программы") },
                { new JMenuItem("Эфир") },
                { new JMenuItem("Соревнования"), new JMenuItem("Виды спорта") }
        };
        menu_items[0][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                main_panel.removeAll();
                main_panel.add(loadEmployeePanel(), BorderLayout.CENTER);
                main_panel.revalidate();
                main_panel.repaint();
            }
        });
        menu_items[0][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                main_panel.removeAll();
                main_panel.add(loadLogsPanel(), BorderLayout.CENTER);
                main_panel.revalidate();
                main_panel.repaint();
            }
        });
        menu_items[0][2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainFrame.dispose();
                register.dropUser();
                loadStartWindow();
            }
        });
        menu_items[1][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                main_panel.removeAll();
                main_panel.add(loadProgramPanel(), BorderLayout.CENTER);
                main_panel.revalidate();
                main_panel.repaint();
            }
        });
        menu_items[2][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                main_panel.removeAll();
                main_panel.add(loadBroadcastPanel(), BorderLayout.CENTER);
                main_panel.revalidate();
                main_panel.repaint();
            }
        });
        menu_items[3][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                main_panel.removeAll();
                main_panel.add(loadCompetitionPanel(), BorderLayout.CENTER);
                main_panel.revalidate();
                main_panel.repaint();
            }
        });
        menu_items[3][1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                main_panel.removeAll();
                main_panel.add(loadSportPanel(), BorderLayout.CENTER);
                main_panel.revalidate();
                main_panel.repaint();
            }
        });

        //добаление менюшек
        menus[0] = new JMenu("Управление");
        menus[1] = new JMenu("Программы");
        menus[2] = new JMenu("Эфир");
        menus[3] = new JMenu("Справочники");
        //добавление подменюшек
        for (int i = 0; i < 4; i++)
            for (JMenuItem menu_item: menu_items[i]) {
                menus[i].add(menu_item);
            }
        //добавление менюшек в меню бар
        for (int i = 0; i < 4; i++) {
            if (i == 0 && register.getUserType() > 2) {
                continue;
            }
            if (i == 1 && register.getUserType() > 3) {
                continue;
            }
            menu_bar.add(menus[i]);
        }

        mainFrame.setJMenuBar(menu_bar);
        mainFrame.add(main_panel);
        mainFrame.setPreferredSize(new Dimension(1000, 800));
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private JPanel loadBroadcastPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ArrayList<String[]> arrayList = register.getBroadcast();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {"Программа", "Канал", "Дата", "Время"});
        for (String[] row: arrayList
        ) {
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);

        JButton add = new JButton("Добавить");
        add.addActionListener(e -> {
            int idx = table.getSelectedRow();
            tableModel.insertRow(idx + 1, new String[] {
                    "Товар №" + String.valueOf(table.getRowCount()), "кг", "Цена"
            });
        });
        JButton remove = new JButton("Удалить");
        remove.addActionListener(e -> {
            int idx = table.getSelectedRow();
            tableModel.removeRow(idx);
            if (idx > 0)
                table.setRowSelectionInterval(idx - 1, idx - 1);
            else
                table.setRowSelectionInterval(0, 0);
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel loadEmployeePanel() {

        //главная панель
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setPreferredSize(new Dimension(500, 800));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JLabel searchLabel = new JLabel("Поиск ");
        searchPanel.add(searchLabel);

        JTextField searchField = new JTextField();
        searchPanel.add(searchField);
        firstPanel.add(searchPanel, BorderLayout.NORTH);

        //таблица
        JTable table = new JTable();
        //модель таблицы
        DefaultTableModel tableModel = new DefaultTableModel();
        //идентификаторы колонок
        tableModel.setColumnIdentifiers(new String[] {"Фамилия", "Имя", "Отчество", "Должность"});
        //строки таблицы
        ArrayList<String[]> employeeList = register.getEmployeeList(searchField.getText());
        String[][] tableRows = employeeList.toArray(new String[employeeList.size()][]);
        for (int i = 0; i < tableRows.length; i++)
            tableModel.addRow(tableRows[i]);

        table.setModel(tableModel);
        //модель выделения
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //начальное состояние таблицы
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
            String last_name = (String) tableModel.getValueAt(0, 0);
            String first_name = (String) tableModel.getValueAt(0, 1);
            String middle_name = (String) tableModel.getValueAt(0, 2);
            register.setCurrentEmployee(last_name, first_name, middle_name);
        }

        firstPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(firstPanel, BorderLayout.CENTER);

        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                    tableModel.removeRow(i);
                }
                ArrayList<String[]> employeeList = register.getEmployeeList(searchField.getText());
                String[][] tableRows = employeeList.toArray(new String[employeeList.size()][]);
                for (int i = 0; i < tableRows.length; i++)
                    tableModel.addRow(tableRows[i]);
            }
        });

        //панель сотрудника
        JPanel secondPanel = new JPanel();
        secondPanel.setPreferredSize(new Dimension(400, 800));
        GroupLayout layout = new GroupLayout(secondPanel);
        secondPanel.setLayout(layout);

        //названия полей
        JLabel ll = new JLabel("Фамилия");
        JLabel fl = new JLabel("Имя");
        JLabel ml = new JLabel("Отчество");
        JLabel pl = new JLabel("Должность");
        JLabel sl = new JLabel("Зарплата");
        JLabel dl = new JLabel("Дата рождения");
        JLabel al = new JLabel("Адресс");
        JLabel pnl = new JLabel("Номер телефона");
        JLabel sdl = new JLabel("Дата начала работы");
        JLabel edl = new JLabel("Дата окончания работы");

        //поля
        JTextField l = new JTextField();
        JTextField f = new JTextField();
        JTextField m = new JTextField();
        JTextField p = new JTextField();
        JTextField s = new JTextField();
        JTextField d = new JTextField();
        JTextField a = new JTextField();
        JTextField pn = new JTextField();
        JTextField sd = new JTextField();
        JTextField ed = new JTextField();

        //начальное состояние полей
        if (table.getRowCount() > 0) {
            l.setText(register.employee.getLastName());
            f.setText(register.employee.getFirstName());
            m.setText(register.employee.getMiddleName());
            p.setText(register.employee.getPosition());
            s.setText(register.employee.getSalary());
            d.setText(register.employee.getDateOfBirth().toString());
            a.setText(register.employee.getAddress());
            pn.setText(register.employee.getPhoneNumber());
            sd.setText(register.employee.getStartDate().toString());
            ed.setText(register.employee.getEndDate().toString());
        }

        //добавить сотрудника
        JButton add = new JButton("Добавить");
        add.addActionListener(e -> {
            if (table.getRowCount() == 0) {
                tableModel.addRow(new String[]{"", "", "", ""});
                table.setRowSelectionInterval(0, 0);
            } else {
                if (!tableModel.getValueAt(table.getRowCount() - 1, 0).equals("")) {
                    int rowCount = table.getRowCount();
                    tableModel.addRow(new String[]{"", "", "", ""});
                    table.setRowSelectionInterval(rowCount, rowCount);
                    register.dropCurrentEmployee();
                }
            }
        });

        //сохранить изменения
        JButton save = new JButton("Сохранить");
        save.addActionListener(e -> {
            if (table.getRowCount() != 0) {
                int error = register.saveEmployee(new Employee(l.getText(), f.getText(), m.getText(), p.getText(), s.getText(), new Date(d.getText()), a.getText(), pn.getText(), new Date(sd.getText()), new Date(ed.getText())));
                if (error == 0) {
                    int selectedRow = table.getSelectedRow();
                    tableModel.setValueAt(l.getText(), selectedRow, 0);
                    tableModel.setValueAt(f.getText(), selectedRow, 1);
                    tableModel.setValueAt(m.getText(), selectedRow, 2);
                    tableModel.setValueAt(p.getText(), selectedRow, 3);
                }
            }
        });

        //удалить сотрудника
        JButton remove = new JButton("Удалить");
        remove.addActionListener(e -> {
            if (table.getRowCount() != 0) {
                int selectedRow = table.getSelectedRow();
                String last_name = (String) tableModel.getValueAt(selectedRow, 0);
                String first_name = (String) tableModel.getValueAt(selectedRow, 1);
                String middle_name = (String) tableModel.getValueAt(selectedRow, 2);
                int error;
                if (last_name.equals(""))
                    error = 0;
                else
                    error = register.removeEmployee(last_name, first_name, middle_name);
                if (error == 0) {
                    tableModel.removeRow(selectedRow);
                    if (table.getRowCount() != 0) {
                        if (selectedRow > 0)
                            table.setRowSelectionInterval(selectedRow - 1, selectedRow - 1);
                        else
                            table.setRowSelectionInterval(0, 0);
                    } else {
                        l.setText("");
                        f.setText("");
                        m.setText("");
                        p.setText("");
                        s.setText("");
                        d.setText("");
                        a.setText("");
                        pn.setText("");
                        sd.setText("");
                        ed.setText("");
                    }
                }
            }
        });

        //table listener
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (table.getSelectedRow() != -1) {
                    if (table.getRowCount() > 0) {
                        int id = table.getSelectedRow();
                        String last_name = (String) tableModel.getValueAt(id, 0);
                        String first_name = (String) tableModel.getValueAt(id, 1);
                        String middle_name = (String) tableModel.getValueAt(id, 2);
                        register.setCurrentEmployee(last_name, first_name, middle_name);
                        if (register.employee != null) {
                            l.setText(register.employee.getLastName());
                            f.setText(register.employee.getFirstName());
                            m.setText(register.employee.getMiddleName());
                            p.setText(register.employee.getPosition());
                            s.setText(register.employee.getSalary());
                            d.setText(register.employee.getDateOfBirth().toString());
                            a.setText(register.employee.getAddress());
                            pn.setText(register.employee.getPhoneNumber());
                            sd.setText(register.employee.getStartDate().toString());
                            ed.setText(register.employee.getEndDate().toString());
                        } else {
                            l.setText("");
                            f.setText("");
                            m.setText("");
                            p.setText("");
                            s.setText("");
                            d.setText("");
                            a.setText("");
                            pn.setText("");
                            sd.setText("");
                            ed.setText("");
                        }
                    }
                } else {
                    if (table.getRowCount() > 0)
                        table.setRowSelectionInterval(0, 0);
                }
            }
        });

        JButton zvit1 = new JButton("Отчет за прошедший год");

        zvit1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                JTextArea textArea = new JTextArea();
                textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                String[][] data = register.zvit1();
                if (data.length != 0) {
                    textArea.append("Количество сюжетов подготовленных каждым сотруником за прошедший год\n\n");
                    for (String[] string : data
                    ) {
                        textArea.append(string[0] + "\nПодготовлено сюжетов: " + string[1] + "\n\n");
                    }
                }
                panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
                JButton button = new JButton("Печать");
                button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            FileOutputStream os = new FileOutputStream("LPT1");
                            PrintStream ps = new PrintStream(os);
                            ps.println(textArea.getText());
                            ps.print("\f");
                            ps.close();
                        } catch(Exception e) {
                            System.out.println("Exception occurred: " + e);
                        }
                    }
                });
                panel.add(button, BorderLayout.SOUTH);
                frame.add(panel);
                frame.setPreferredSize(new Dimension(900, 1000));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        JButton zvit3 = new JButton("Сотрудники с з/п выше средней");

        zvit3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                JTextArea textArea = new JTextArea();
                textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                String[][] data = register.zvit3();
                if (data.length != 0) {
                    textArea.append("Сотрудники с з/п выше средней на их должности\n\n");
                    for (String[] string : data
                    ) {
                        textArea.append(string[0] + "\nДолжность: " + string[1] + "\nЗарплата: " + string[2] + "\n\n");
                    }
                }
                panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
                JButton button = new JButton("Печать");
                button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            FileOutputStream os = new FileOutputStream("LPT1");
                            PrintStream ps = new PrintStream(os);
                            ps.println(textArea.getText());
                            ps.print("\f");
                            ps.close();
                        } catch(Exception e) {
                            System.out.println("Exception occurred: " + e);
                        }
                    }
                });
                panel.add(button, BorderLayout.SOUTH);
                frame.add(panel);
                frame.setPreferredSize(new Dimension(900, 1000));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        JButton zvit4 = new JButton("Лучшие худшие сотрудники");

        zvit4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                JTextArea textArea = new JTextArea();
                textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                String[][] data = register.zvit4();
                if (data.length != 0) {
                    for (String[] string : data
                    ) {
                        textArea.append(string[0] + "\n" + string[1] + " (" + string[2] + ")\n\n");
                    }
                }
                panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
                JButton button = new JButton("Печать");
                button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            FileOutputStream os = new FileOutputStream("LPT1");
                            PrintStream ps = new PrintStream(os);
                            ps.println(textArea.getText());
                            ps.print("\f");
                            ps.close();
                        } catch(Exception e) {
                            System.out.println("Exception occurred: " + e);
                        }
                    }
                });
                panel.add(button, BorderLayout.SOUTH);
                frame.add(panel);
                frame.setPreferredSize(new Dimension(900, 1000));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        JLabel bestEmployee = new JLabel();
        bestEmployee.setText("Сотрудник месяца: " + register.getBestEmployee());

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(ll).addComponent(l))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(fl).addComponent(f))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(ml).addComponent(m))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(pl).addComponent(p))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(sl).addComponent(s))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(dl).addComponent(d))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(al).addComponent(a))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl).addComponent(pn))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(sdl).addComponent(sd))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(edl).addComponent(ed))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(add).addComponent(save).addComponent(remove))
                .addGap(75)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(bestEmployee))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(zvit1))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(zvit3))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(zvit4))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(ll).addComponent(l))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(fl).addComponent(f))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(ml).addComponent(m))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(pl).addComponent(p))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(sl).addComponent(s))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(dl).addComponent(d))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(al).addComponent(a))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(pnl).addComponent(pn))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(sdl).addComponent(sd))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(edl).addComponent(ed))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(add).addComponent(save).addComponent(remove))
                .addGap(75)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(bestEmployee))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(zvit1))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(zvit3))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(zvit4))
        );

        panel.add(secondPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel loadLogsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //таблица
        JTable table = new JTable();
        //модель таблицы
        DefaultTableModel tableModel = new DefaultTableModel();
        //идентификаторы колонок
        String[] columnsIdentifiers = new String[] {"Время", "Событие"};
        tableModel.setColumnIdentifiers(columnsIdentifiers);
        //строки таблицы
        ArrayList<String[]> logs = register.getLogs();
        String[][] tableRows = logs.toArray(new String[logs.size()][]);
        for (int i = 0; i < tableRows.length; i++)
            tableModel.addRow(tableRows[i]);

        table.setModel(tableModel);
        table.setCellSelectionEnabled(false);
        table.getColumnModel().getColumn(0).setMinWidth(160);
        table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(0).setMaxWidth(160);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel loadProgramPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setPreferredSize(new Dimension(500, 800));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel1 = new JPanel();
        searchPanel1.setLayout(new BoxLayout(searchPanel1, BoxLayout.X_AXIS));

        JLabel searchNameLabel = new JLabel("Поиск ");
        searchPanel1.add(searchNameLabel);
        JTextField searchNameField = new JTextField();
        searchPanel1.add(searchNameField);
        searchPanel.add(searchPanel1);

        JPanel searchPanel2 = new JPanel();
        searchPanel2.setLayout(new BoxLayout(searchPanel2, BoxLayout.X_AXIS));

        JLabel searchDateLabel1 = new JLabel("Дата создания ");
        searchPanel2.add(searchDateLabel1);
        JTextField searchDateField1 = new JTextField();
        searchPanel2.add(searchDateField1);
        JLabel searchDateLabel2 = new JLabel(" - ");
        searchPanel2.add(searchDateLabel2);
        JTextField searchDateField2 = new JTextField();
        searchPanel2.add(searchDateField2);
        JLabel searchDurationLabel1 = new JLabel("Длительность ");
        searchPanel2.add(searchDurationLabel1);
        JTextField searchDurationField1 = new JTextField();
        searchPanel2.add(searchDurationField1);
        JLabel searchDurationLabel2 = new JLabel(" - ");
        searchPanel2.add(searchDurationLabel2);
        JTextField searchDurationField2 = new JTextField();
        searchPanel2.add(searchDurationField2);
        searchPanel.add(searchPanel2);
        firstPanel.add(searchPanel, BorderLayout.NORTH);

        //модель таблицы
        DefaultTableModel tableModel = new DefaultTableModel();
        //идентификаторы колонок
        tableModel.setColumnIdentifiers(new String[]{"Название", "Описание", "Дата создания", "Длительность"});
        //строки таблицы
        String name = searchNameField.getText();
        String date1 = searchDateField1.getText();
        String date2 = searchDateField2.getText();
        String duration1 = searchDurationField1.getText();
        String duration2 = searchDurationField2.getText();
        ArrayList<Program> programs = register.getProgramsList(name, date1, date2, duration1, duration2);
        if (programs != null) {
            for (Program program : programs) {
                String[] tableRow = new String[4];
                tableRow[0] = program.name;
                tableRow[1] = program.description;
                tableRow[2] = program.date_of_creation.toString();
                tableRow[3] = program.duration.toString();
                tableModel.addRow(tableRow);
            }
        } else {

        }

        //таблица
        JTable table = new JTable(tableModel);
        //модель выделения
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        firstPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(firstPanel, BorderLayout.CENTER);

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                    tableModel.removeRow(i);
                }
                String name = searchNameField.getText();
                String date1 = searchDateField1.getText();
                String date2 = searchDateField2.getText();
                String duration1 = searchDurationField1.getText();
                String duration2 = searchDurationField2.getText();
                ArrayList<Program> programs = register.getProgramsList(name, date1, date2, duration1, duration2);
                if (programs != null) {
                    for (Program program : programs) {
                        String[] tableRow = new String[4];
                        tableRow[0] = program.name;
                        tableRow[1] = program.description;
                        tableRow[2] = program.date_of_creation.toString();
                        tableRow[3] = program.duration.toString();
                        tableModel.addRow(tableRow);
                    }
                }
            }
        };
        searchNameField.addKeyListener(keyListener);
        searchDateField1.addKeyListener(keyListener);
        searchDateField2.addKeyListener(keyListener);
        searchDurationField1.addKeyListener(keyListener);
        searchDurationField2.addKeyListener(keyListener);


        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
        secondPanel.setPreferredSize(new Dimension(400, 800));

        //tabs
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        //информационная панель
        tabbedPane.addTab("Программа", loadProgramInfoPanel());
        //панель с сотрудниками
        tabbedPane.addTab("Авторы", loadProgramEmployeePanel());

        //панель с гостями
        tabbedPane.addTab("Гости программы", loadProgramGuestsPanel());

        //панель с соревнованиями
        tabbedPane.addTab("Соревнования", loadProgramCompetitionPanel());

        //панель с спортсменами
        tabbedPane.addTab("Спортсмены", loadProgramSportsmanPanel());

        //панель с командами
        tabbedPane.addTab("Команды", loadProgramTeamPanel());

        //слушатель
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (table.getSelectedRow() != -1) {
                    if (table.getRowCount() > 0) {
                        int id = table.getSelectedRow();
                        String name = (String) tableModel.getValueAt(id, 0);
                        register.setCurrentProgram(name);

                        tabbedPane.removeAll();
                        //информационная панель
                        tabbedPane.addTab("Программа", loadProgramInfoPanel());
                        //панель с сотрудниками
                        tabbedPane.addTab("Авторы", loadProgramEmployeePanel());
                        //панель с гостями
                        tabbedPane.addTab("Гости программы", loadProgramGuestsPanel());
                        //панель с соревнованиями
                        tabbedPane.addTab("Соревнования", loadProgramCompetitionPanel());
                        //панель с спортсменами
                        tabbedPane.addTab("Спортсмены", loadProgramSportsmanPanel());
                        //панель с командами
                        tabbedPane.addTab("Команды", loadProgramTeamPanel());
                    }
                }
            }
        });

        secondPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel agrPanel = new JPanel();
        agrPanel.setLayout(new BoxLayout(agrPanel, BoxLayout.Y_AXIS));
        JButton zvit2 = new JButton("Отчет по видам спорта");

        zvit2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                JTextArea textArea = new JTextArea();
                textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                String[][] data = register.zvit2();
                if (data.length != 0) {
                    textArea.append("Количество сюжетов подготовленных по каждому виду спорта\n\n");
                    for (String[] string : data
                    ) {
                        textArea.append(string[0] + "\nПодготовлено сюжетов: " + string[1] + "\n\n");
                    }
                }
                panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
                JButton button = new JButton("Печать");
                button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            FileOutputStream os = new FileOutputStream("LPT1");
                            PrintStream ps = new PrintStream(os);
                            ps.println(textArea.getText());
                            ps.print("\f");
                            ps.close();
                        } catch(Exception e) {
                            System.out.println("Exception occurred: " + e);
                        }
                    }
                });
                panel.add(button, BorderLayout.SOUTH);
                frame.add(panel);
                frame.setPreferredSize(new Dimension(900, 1000));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        agrPanel.add(zvit2);

        JLabel previousMonthLabel = new JLabel("Сюжетов подготовлено в предыдущем месяце " + register.getPreviousMonthProgramsCount());
        previousMonthLabel.setFont(new Font("", Font.PLAIN, 15));
        agrPanel.add(previousMonthLabel);
        JLabel currentMonthLabel = new JLabel("Сюжетов подготовлено в текущем месяце " + register.getThisMonthProgramsCount());
        currentMonthLabel.setFont(new Font("", Font.PLAIN, 15));
        agrPanel.add(currentMonthLabel);

        secondPanel.add(agrPanel, BorderLayout.SOUTH);

        panel.add(secondPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel loadProgramInfoPanel() {
        JPanel panel = new JPanel();

        JLabel nameLabel = new JLabel("Название         ");
        JLabel descriptionLabel = new JLabel("Описание         ");
        JLabel date_of_creationLabel = new JLabel("Дата создания");
        JLabel durationLabel = new JLabel("Длительность ");

        JTextField nameField = new JTextField("");
        JTextArea descriptionField = new JTextArea("");
        JTextField date_of_creationField = new JTextField("");
        JTextField durationField = new JTextField("");

        JLabel nameErrorLabel = new JLabel("1");
        JLabel descriptionErrorLabel = new JLabel("2");
        JLabel date_of_creationErrorLabel = new JLabel("3");
        JLabel durationErrorLabel = new JLabel("4");

        JButton add = new JButton("Добавить");
        JButton remove = new JButton("Удалить");

        String[] currentProgramInfo = register.getCurrentProgramInfo();
        if (currentProgramInfo != null) {
            nameField.setText(currentProgramInfo[0]);
            descriptionField.setText(currentProgramInfo[1]);
            date_of_creationField.setText(currentProgramInfo[2]);
            durationField.setText(currentProgramInfo[3]);
        }

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                add.setText("Сохранить");
                remove.setText("Отмена");
            }
        };

        nameField.addKeyListener(keyListener);
        descriptionField.addKeyListener(keyListener);
        date_of_creationField.addKeyListener(keyListener);
        durationField.addKeyListener(keyListener);

        nameErrorLabel.setVisible(false);
        descriptionErrorLabel.setVisible(false);
        date_of_creationErrorLabel.setVisible(false);
        durationErrorLabel.setVisible(false);

        //кнопка добавления программы
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (add.getText().equals("Добавить") ) {
                    register.dropCurrentProgram();
                    nameField.setText("");
                    descriptionField.setText("");
                    date_of_creationField.setText("");
                    durationField.setText("");
                    add.setText("Сохранить");
                    remove.setText("Отмена");
                } else if (add.getText().equals("Сохранить")) {
                    int error = register.saveProgramInfo(nameField.getText(), descriptionField.getText(), date_of_creationField.getText(), durationField.getText());
                    if (error == 0) {
                        add.setText("Добавить");
                        remove.setText("Удалить");
                    }
                    //kostyl
                    JPanel oldPanel = (JPanel) panel.getParent().getParent().getParent().getParent();
                    oldPanel.removeAll();
                    oldPanel.add(loadProgramPanel(), BorderLayout.CENTER);
                    oldPanel.revalidate();
                    oldPanel.repaint();
                }
            }
        });

        //кнопка удаления программы
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (remove.getText().equals("Удалить")) {
                    register.removeCurrentProgram();
                    //kostyl
                    JPanel oldPanel = (JPanel) panel.getParent().getParent().getParent().getParent();
                    oldPanel.removeAll();
                    oldPanel.add(loadProgramPanel(), BorderLayout.CENTER);
                    oldPanel.revalidate();
                    oldPanel.repaint();
                } else if (remove.getText().equals("Отмена")) {
                    add.setText("Добавить");
                    remove.setText("Удалить");
                    register.restoreCurrentProgram();
                    String[] currentProgramInfo = register.getCurrentProgramInfo();
                    if (currentProgramInfo != null) {
                        nameField.setText(currentProgramInfo[0]);
                        descriptionField.setText(currentProgramInfo[1]);
                        date_of_creationField.setText(currentProgramInfo[2]);
                        durationField.setText(currentProgramInfo[3]);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(descriptionField);
        scrollPane.setMaximumSize(new Dimension(500, 100));

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nameLabel).addComponent(nameField))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nameErrorLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(descriptionLabel).addComponent(scrollPane))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(descriptionErrorLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(date_of_creationLabel).addComponent(date_of_creationField))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(date_of_creationErrorLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(durationLabel).addComponent(durationField))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(durationErrorLabel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(add).addComponent(remove))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(nameLabel).addComponent(nameField))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(nameErrorLabel))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(descriptionLabel).addComponent(scrollPane))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(descriptionErrorLabel))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(date_of_creationLabel).addComponent(date_of_creationField))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(date_of_creationErrorLabel))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(durationLabel).addComponent(durationField))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(durationErrorLabel))
                        .addGroup(layout.createParallelGroup(BASELINE)
                                .addComponent(add).addComponent(remove))
        );
        panel.setLayout(layout);
        return panel;
    }

    private JPanel loadProgramEmployeePanel() {
        JPanel panel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        ArrayList<String> currentProgramEmployees = register.getCurrentProgramEmployees();
        if (currentProgramEmployees != null) {
            listModel.addAll(currentProgramEmployees);
        } else {
            listModel.addElement("Список пуст");
        }

        JList<String> list = new JList<>(listModel);
        list.setMinimumSize(new Dimension(370, 100));
        panel.add(list, BorderLayout.NORTH);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        ArrayList<String> notCurrentProgramEmployees = register.getNotCurrentProgramEmployees();
        comboBoxModel.addElement("Выберите сотрудника");
        if (notCurrentProgramEmployees != null) {
            comboBoxModel.addAll(notCurrentProgramEmployees);
        }

        JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
        comboBox.setMaximumSize(new Dimension(500, 25));
        panel.add(comboBox);

        //кнопка добавления сотрудника в авторы
        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox.getSelectedIndex() != 0) {
                    register.addEmployeeToProgram((String) comboBox.getSelectedItem());

                    //обновление списка
                    listModel.removeAllElements();
                    ArrayList<String> currentProgramEmployees = register.getCurrentProgramEmployees();
                    if (currentProgramEmployees != null) {
                        listModel.addAll(currentProgramEmployees);
                    } else {
                        listModel.addElement("Список пуст");
                    }

                    //обновление комбо-бокса
                    comboBoxModel.removeAllElements();
                    ArrayList<String> notCurrentProgramEmployees = register.getNotCurrentProgramEmployees();
                    comboBoxModel.addElement("Выберите сотрудника");
                    if (notCurrentProgramEmployees != null) {
                        comboBoxModel.addAll(notCurrentProgramEmployees);
                    }
                }
            }
        });

        JButton remove = new JButton("Удалить");
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.removeEmployeeFromProgram(list.getSelectedValue());

                //обновление списка
                listModel.removeAllElements();
                ArrayList<String> currentProgramEmployees = register.getCurrentProgramEmployees();
                if (currentProgramEmployees != null) {
                    listModel.addAll(currentProgramEmployees);
                } else {
                    listModel.addElement("Список пуст");
                }

                //обновление комбо-бокса
                comboBoxModel.removeAllElements();
                ArrayList<String> notCurrentProgramEmployees = register.getNotCurrentProgramEmployees();
                comboBoxModel.addElement("Выберите сотрудника");
                if (notCurrentProgramEmployees != null) {
                    comboBoxModel.addAll(notCurrentProgramEmployees);
                }
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (list.getSelectedIndex() != -1) {
                    if(list.getSelectedValue().equals("Список пуст")) {
                        list.clearSelection();
                        remove.setEnabled(false);
                    } else {
                        remove.setEnabled(true);
                    }
                } else {
                    remove.setEnabled(false);
                }
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox)
                ).addGroup(layout.createSequentialGroup()
                        .addComponent(add).addComponent(remove)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(comboBox)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add).addComponent(remove)
                )
        );
        panel.setLayout(layout);

        return panel;
    }

    private JPanel loadProgramGuestsPanel() {
        JPanel panel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        ArrayList<String> currentProgramGuests = register.getCurrentProgramGuests();
        if (currentProgramGuests != null) {
            listModel.addAll(currentProgramGuests);
        } else {
            listModel.addElement("Список пуст");
        }

        JList<String> list = new JList<>(listModel);
        list.setMinimumSize(new Dimension(370, 100));
        panel.add(list, BorderLayout.NORTH);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        ArrayList<String> notCurrentProgramGuests = register.getNotCurrentProgramGuests();
        comboBoxModel.addElement("Выберите гостя");
        if (notCurrentProgramGuests != null) {
            comboBoxModel.addAll(notCurrentProgramGuests);
        }

        JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
        comboBox.setMaximumSize(new Dimension(500, 25));
        panel.add(comboBox);

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox.getSelectedIndex() != 0) {
                    register.addGuestToProgram((String) comboBox.getSelectedItem());

                    //обновление списка
                    listModel.removeAllElements();
                    ArrayList<String> currentProgramGuests = register.getCurrentProgramGuests();
                    if (currentProgramGuests != null) {
                        listModel.addAll(currentProgramGuests);
                    } else {
                        listModel.addElement("Список пуст");
                    }

                    //обновление комбо-бокса
                    comboBoxModel.removeAllElements();
                    ArrayList<String> notCurrentProgramGuests = register.getNotCurrentProgramGuests();
                    comboBoxModel.addElement("Выберите гостя");
                    if (notCurrentProgramGuests != null) {
                        comboBoxModel.addAll(notCurrentProgramGuests);
                    }
                }
            }
        });

        JButton remove = new JButton("Удалить");
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.removeGuestFromProgram(list.getSelectedValue());

                //обновление списка
                listModel.removeAllElements();
                ArrayList<String> currentProgramGuests = register.getCurrentProgramGuests();
                if (currentProgramGuests != null) {
                    listModel.addAll(currentProgramGuests);
                } else {
                    listModel.addElement("Список пуст");
                }

                //обновление комбо-бокса
                comboBoxModel.removeAllElements();
                ArrayList<String> notCurrentProgramGuests = register.getNotCurrentProgramGuests();
                comboBoxModel.addElement("Выберите гостя");
                if (notCurrentProgramGuests != null) {
                    comboBoxModel.addAll(notCurrentProgramGuests);
                }
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (list.getSelectedIndex() != -1) {
                    if(list.getSelectedValue().equals("Список пуст")) {
                        list.clearSelection();
                        remove.setEnabled(false);
                    } else {
                        remove.setEnabled(true);
                    }
                } else {
                    remove.setEnabled(false);
                }
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox)
                ).addGroup(layout.createSequentialGroup()
                        .addComponent(add).addComponent(remove)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(comboBox)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add).addComponent(remove)
                )
        );
        panel.setLayout(layout);

        return panel;
    }

    private JPanel loadProgramCompetitionPanel() {
        JPanel panel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        ArrayList<String> currentProgramCompetitions = register.getCurrentProgramCompetitions();
        if (currentProgramCompetitions != null) {
            listModel.addAll(currentProgramCompetitions);
        } else {
            listModel.addElement("Список пуст");
        }

        JList<String> list = new JList<>(listModel);
        list.setMinimumSize(new Dimension(370, 100));
        panel.add(list, BorderLayout.NORTH);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        ArrayList<String> notCurrentProgramCompetitions = register.getNotCurrentProgramCompetitions();
        comboBoxModel.addElement("Выберите соревнование");
        if (notCurrentProgramCompetitions != null) {
            comboBoxModel.addAll(notCurrentProgramCompetitions);
        }

        JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
        comboBox.setMaximumSize(new Dimension(500, 25));
        panel.add(comboBox);

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox.getSelectedIndex() != 0) {
                    register.addCompetitionToProgram((String) comboBox.getSelectedItem());

                    //обновление списка
                    listModel.removeAllElements();
                    ArrayList<String> currentProgramCompetitions = register.getCurrentProgramCompetitions();
                    if (currentProgramCompetitions != null) {
                        listModel.addAll(currentProgramCompetitions);
                    } else {
                        listModel.addElement("Список пуст");
                    }

                    //обновление комбо-бокса
                    comboBoxModel.removeAllElements();
                    ArrayList<String> notCurrentProgramCompetitions = register.getNotCurrentProgramCompetitions();
                    comboBoxModel.addElement("Выберите соревнование");
                    if (notCurrentProgramCompetitions != null) {
                        comboBoxModel.addAll(notCurrentProgramCompetitions);
                    }
                }
            }
        });

        JButton remove = new JButton("Удалить");
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.removeCompetitionFromProgram(list.getSelectedValue());

                //обновление списка
                listModel.removeAllElements();
                ArrayList<String> currentProgramCompetitions = register.getCurrentProgramCompetitions();
                if (currentProgramCompetitions != null) {
                    listModel.addAll(currentProgramCompetitions);
                } else {
                    listModel.addElement("Список пуст");
                }

                //обновление комбо-бокса
                comboBoxModel.removeAllElements();
                ArrayList<String> notCurrentProgramCompetitions = register.getNotCurrentProgramCompetitions();
                comboBoxModel.addElement("Выберите соревнование");
                if (notCurrentProgramCompetitions != null) {
                    comboBoxModel.addAll(notCurrentProgramCompetitions);
                }
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (list.getSelectedIndex() != -1) {
                    if(list.getSelectedValue().equals("Список пуст")) {
                        list.clearSelection();
                        remove.setEnabled(false);
                    } else {
                        remove.setEnabled(true);
                    }
                } else {
                    remove.setEnabled(false);
                }
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox)
                ).addGroup(layout.createSequentialGroup()
                        .addComponent(add).addComponent(remove)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(comboBox)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add).addComponent(remove)
                )
        );
        panel.setLayout(layout);

        return panel;
    }

    private JPanel loadProgramSportsmanPanel() {
        JPanel panel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        ArrayList<String> currentProgramSportsmen = register.getCurrentProgramSportsmen();
        if (currentProgramSportsmen != null) {
            listModel.addAll(currentProgramSportsmen);
        } else {
            listModel.addElement("Список пуст");
        }

        JList<String> list = new JList<>(listModel);
        list.setMinimumSize(new Dimension(370, 100));
        panel.add(list, BorderLayout.NORTH);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        ArrayList<String> notCurrentProgramSportsmen = register.getNotCurrentProgramSportsmen();
        comboBoxModel.addElement("Выберите спортсмена");
        if (notCurrentProgramSportsmen != null) {
            comboBoxModel.addAll(notCurrentProgramSportsmen);
        }

        JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
        comboBox.setMaximumSize(new Dimension(500, 25));
        panel.add(comboBox);

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox.getSelectedIndex() != 0) {
                    register.addSportsmanToProgram((String) comboBox.getSelectedItem());

                    //обновление списка
                    listModel.removeAllElements();
                    ArrayList<String> currentProgramSportsmen = register.getCurrentProgramSportsmen();
                    if (currentProgramSportsmen != null) {
                        listModel.addAll(currentProgramSportsmen);
                    } else {
                        listModel.addElement("Список пуст");
                    }

                    //обновление комбо-бокса
                    comboBoxModel.removeAllElements();
                    ArrayList<String> notCurrentProgramSportsmen = register.getNotCurrentProgramSportsmen();
                    comboBoxModel.addElement("Выберите спортсмена");
                    if (notCurrentProgramSportsmen != null) {
                        comboBoxModel.addAll(notCurrentProgramSportsmen);
                    }
                }
            }
        });

        JButton remove = new JButton("Удалить");
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.removeSportsmanFromProgram(list.getSelectedValue());

                //обновление списка
                listModel.removeAllElements();
                ArrayList<String> currentProgramSportsmen = register.getCurrentProgramSportsmen();
                if (currentProgramSportsmen != null) {
                    listModel.addAll(currentProgramSportsmen);
                } else {
                    listModel.addElement("Список пуст");
                }

                //обновление комбо-бокса
                comboBoxModel.removeAllElements();
                ArrayList<String> notCurrentProgramSportsmen = register.getNotCurrentProgramSportsmen();
                comboBoxModel.addElement("Выберите спортсмена");
                if (notCurrentProgramSportsmen != null) {
                    comboBoxModel.addAll(notCurrentProgramSportsmen);
                }
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (list.getSelectedIndex() != -1) {
                    if(list.getSelectedValue().equals("Список пуст")) {
                        list.clearSelection();
                        remove.setEnabled(false);
                    } else {
                        remove.setEnabled(true);
                    }
                } else {
                    remove.setEnabled(false);
                }
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox)
                ).addGroup(layout.createSequentialGroup()
                        .addComponent(add).addComponent(remove)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(comboBox)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add).addComponent(remove)
                )
        );
        panel.setLayout(layout);

        return panel;
    }

    private JPanel loadProgramTeamPanel() {
        JPanel panel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        ArrayList<String> currentProgramTeams = register.getCurrentProgramTeams();
        if (currentProgramTeams != null) {
            listModel.addAll(currentProgramTeams);
        } else {
            listModel.addElement("Список пуст");
        }

        JList<String> list = new JList<>(listModel);
        list.setMinimumSize(new Dimension(370, 100));
        panel.add(list, BorderLayout.NORTH);

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        ArrayList<String> notCurrentProgramTeams = register.getNotCurrentProgramTeams();
        comboBoxModel.addElement("Выберите команду");
        if (notCurrentProgramTeams != null) {
            comboBoxModel.addAll(notCurrentProgramTeams);
        }

        JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);
        comboBox.setMaximumSize(new Dimension(500, 25));
        panel.add(comboBox);

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox.getSelectedIndex() != 0) {
                    register.addTeamToProgram((String) comboBox.getSelectedItem());

                    //обновление списка
                    listModel.removeAllElements();
                    ArrayList<String> currentProgramTeams = register.getCurrentProgramTeams();
                    if (currentProgramTeams != null) {
                        listModel.addAll(currentProgramTeams);
                    } else {
                        listModel.addElement("Список пуст");
                    }

                    //обновление комбо-бокса
                    comboBoxModel.removeAllElements();
                    ArrayList<String> notCurrentProgramTeams = register.getNotCurrentProgramTeams();
                    comboBoxModel.addElement("Выберите команду");
                    if (notCurrentProgramTeams != null) {
                        comboBoxModel.addAll(notCurrentProgramTeams);
                    }
                }
            }
        });

        JButton remove = new JButton("Удалить");
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.removeTeamFromProgram(list.getSelectedValue());

                //обновление списка
                listModel.removeAllElements();
                ArrayList<String> currentProgramTeams = register.getCurrentProgramTeams();
                if (currentProgramTeams != null) {
                    listModel.addAll(currentProgramTeams);
                } else {
                    listModel.addElement("Список пуст");
                }

                //обновление комбо-бокса
                comboBoxModel.removeAllElements();
                ArrayList<String> notCurrentProgramTeams = register.getNotCurrentProgramTeams();
                comboBoxModel.addElement("Выберите команду");
                if (notCurrentProgramTeams != null) {
                    comboBoxModel.addAll(notCurrentProgramTeams);
                }
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (list.getSelectedIndex() != -1) {
                    if(list.getSelectedValue().equals("Список пуст")) {
                        list.clearSelection();
                        remove.setEnabled(false);
                    } else {
                        remove.setEnabled(true);
                    }
                } else {
                    remove.setEnabled(false);
                }
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(comboBox)
                ).addGroup(layout.createSequentialGroup()
                        .addComponent(add).addComponent(remove)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(list)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(comboBox)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add).addComponent(remove)
                )
        );
        panel.setLayout(layout);

        return panel;
    }

    private JPanel loadCompetitionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setPreferredSize(new Dimension(500, 800));

        //модель таблицы
        DefaultTableModel tableModel = new DefaultTableModel();
        //идентификаторы колонок
        tableModel.setColumnIdentifiers(new String[]{"Описание", "Дата проведения", "Место проведения", "Время проведения"});
        //строки таблицы
        String[][] list = register.getCompetitionsList();
        if (list != null) {
            for (String[] row: list) {
                tableModel.addRow(row);
            }
        } else {

        }

        //таблица
        JTable table = new JTable(tableModel);
        //модель выделения
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        firstPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(firstPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel loadSportPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setPreferredSize(new Dimension(500, 800));

        //модель таблицы
        DefaultTableModel tableModel = new DefaultTableModel();
        //идентификаторы колонок
        tableModel.setColumnIdentifiers(new String[]{"Вид спорта"});
        //строки таблицы
        ArrayList<String> list = register.getSportList();
        if (list != null) {
            for (String string: list) {
                String[] row = { string };
                tableModel.addRow(row);
            }
        } else {

        }

        //таблица
        JTable table = new JTable(tableModel);
        //модель выделения
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        firstPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(firstPanel, BorderLayout.CENTER);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BorderLayout());
        secondPanel.setPreferredSize(new Dimension(400, 800));

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        secondPanel.add(textArea, BorderLayout.CENTER);

        panel.add(secondPanel, BorderLayout.EAST);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (table.getSelectedRow() != -1) {
                    if (table.getRowCount() > 0) {
                        int id = table.getSelectedRow();
                        String kind = (String) tableModel.getValueAt(id, 0);
                        textArea.setText(register.getSportDescription(kind));
                    } else {
                        textArea.setText("");
                    }
                } else {
                    if (table.getRowCount() > 0)
                        table.setRowSelectionInterval(0, 0);
                }
            }
        });

        return panel;
    }

}
