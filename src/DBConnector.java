import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {

    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME = "sport";
    static final String DB_PAR = "?characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "12345678";

    DBConnector()
    {
        connectToDB();
    }

    private Connection connection = null;
    private Statement statement = null;

    private int connectToDB() {
        //connect to database
        try {
            connection = DriverManager.getConnection(DB_URL + DB_NAME + DB_PAR, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        return 0;
    }

    //user
    public User getUserByLoginAndPassword(String login, String password) {
        try {
            String select = "SELECT * FROM users WHERE login = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User(resultSet.getInt(1), login, resultSet.getInt(4));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //employee
    public Employee getEmployeeByFullName(String last_name, String first_name, String middle_name) {
        try {
            String select = "SELECT * FROM employees WHERE last_name = ? AND first_name = ? AND middle_name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, last_name);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, middle_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        last_name,
                        first_name,
                        middle_name,
                        resultSet.getString(6),
                        resultSet.getString(7),
                        new Date(resultSet.getString(8)),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        new Date(resultSet.getString(11)),
                        new Date(resultSet.getString(12))
                );
                return employee;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getEmployeeIdByFIO(String fio) {
        try {
            String select = "SELECT id_employee FROM employees WHERE concat(last_name , ' ',first_name, ' ', middle_name) = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, fio);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String getBestEmployee() {
        try {
            String select = "SELECT concat(last_name,' ',first_name,' ',middle_name) as fio, COUNT(*) as cnt " +
                    "FROM employees INNER JOIN program_employee USING(id_employee) GROUP BY fio " +
                    "HAVING cnt >= ALL " +
                    "( SELECT COUNT(*) FROM employees INNER JOIN program_employee USING(id_employee) GROUP BY id_employee );";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<String[]> getEmployeesLike(String like) {
        ArrayList<String[]> list = new ArrayList<>();
        try {
            String select = "SELECT last_name, first_name, middle_name, position FROM employees WHERE concat(last_name , ' ',first_name, ' ', middle_name) LIKE ? ORDER BY last_name;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, "%" + like + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String[] row = new String[4];
                row[0] = resultSet.getString(1);
                row[1] = resultSet.getString(2);
                row[2] = resultSet.getString(3);
                row[3] = resultSet.getString(4);
                list.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int addEmployee(Employee employee) {
        try {
            String insert = "INSERT INTO employees (id_user, last_name, first_name, middle_name, position, salary, date_of_birth, address, phone_number, start_date, end_date) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, employee.getIdUser());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getMiddleName());
            preparedStatement.setString(5, employee.getPosition());
            preparedStatement.setString(6, employee.getSalary());
            preparedStatement.setString(7, employee.getDateOfBirth().toString());
            preparedStatement.setString(8, employee.getAddress());
            preparedStatement.setString(9, employee.getPhoneNumber());
            preparedStatement.setString(10, employee.getStartDate().toString());
            preparedStatement.setString(11, employee.getEndDate().toString());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateEmployee(Employee employee) {
        try {
            String insert = "UPDATE employees SET id_user = ?, last_name = ?, first_name = ?, middle_name = ?, position = ?, salary = ?, date_of_birth = ?, address = ?, phone_number = ?, start_date = ?, end_date = ? WHERE id_employee = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, employee.getIdUser());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getMiddleName());
            preparedStatement.setString(5, employee.getPosition());
            preparedStatement.setString(6, employee.getSalary());
            preparedStatement.setString(7, employee.getDateOfBirth().toString());
            preparedStatement.setString(8, employee.getAddress());
            preparedStatement.setString(9, employee.getPhoneNumber());
            preparedStatement.setString(10, employee.getStartDate().toString());
            preparedStatement.setString(11, employee.getEndDate().toString());
            preparedStatement.setInt(12, employee.getIdEmployee());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeEmployeeByFullName(String last_name, String first_name, String middle_name) {
        try {
            String delete = "DELETE FROM employees WHERE last_name = ? AND first_name = ? AND middle_name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setString(1, last_name);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, middle_name);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //logs
    public ArrayList<String[]> getLogs() {
        ArrayList<String[]> list = new ArrayList<>();
        try {
            String select = "SELECT datetime_log, login, id_action, table_name FROM logs LEFT JOIN users USING(id_user) ORDER BY datetime_log DESC;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] row = new String[4];
                row[0] = resultSet.getString(1);
                row[1] = resultSet.getString(2);
                row[2] = resultSet.getString(3);
                row[3] = resultSet.getString(4);
                list.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int log(int id_user, int action, String table_name) {
        try {
            String insert = "INSERT INTO logs (id_user, id_action, table_name, datetime_log) VALUES (?,?,?, NOW());";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, action);
            preparedStatement.setString(3, table_name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //program
    public ArrayList<Program> getProgramsLike(String like) {
        ArrayList<Program> list = new ArrayList<>();
        try {
            String select = "SELECT * FROM programs WHERE name LIKE ? ORDER BY name;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, like + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id_program = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                Date date_of_creation = new Date(resultSet.getString(4));
                Time duration = new Time(resultSet.getString(5));
                Program program = new Program(id_program, name, description, date_of_creation, duration);
                list.add(program);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Program> getProgramsWithNameDateDuration(String name, String date1, String date2, String duration1, String duration2) {
        ArrayList<Program> list = new ArrayList<>();
        try {
            String select = null;
            if (date1 != null && duration1 != null) {
                select = "SELECT * FROM programs WHERE name LIKE ? AND date_of_creation BETWEEN ? AND ? AND duration BETWEEN ? AND ? ORDER BY name;";
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                preparedStatement.setString(1, name + "%");
                preparedStatement.setString(2, date1);
                preparedStatement.setString(3, date2);
                preparedStatement.setString(4, duration1);
                preparedStatement.setString(5, duration2);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    int id_program = resultSet.getInt(1);
                    String n = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Date date_of_creation = new Date(resultSet.getString(4));
                    Time duration = new Time(resultSet.getString(5));
                    Program program = new Program(id_program, n, description, date_of_creation, duration);
                    list.add(program);
                }
            } else {
                if (date1 != null) {
                    select = "SELECT * FROM programs WHERE name LIKE ? AND date_of_creation BETWEEN ? AND ? ORDER BY name;";
                    PreparedStatement preparedStatement = connection.prepareStatement(select);
                    preparedStatement.setString(1, name + "%");
                    preparedStatement.setString(2, date1);
                    preparedStatement.setString(3, date2);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()) {
                        int id_program = resultSet.getInt(1);
                        String n = resultSet.getString(2);
                        String description = resultSet.getString(3);
                        Date date_of_creation = new Date(resultSet.getString(4));
                        Time duration = new Time(resultSet.getString(5));
                        Program program = new Program(id_program, n, description, date_of_creation, duration);
                        list.add(program);
                    }
                } else {
                    select = "SELECT * FROM programs WHERE name LIKE ? AND duration BETWEEN ? AND ? ORDER BY name;";
                    PreparedStatement preparedStatement = connection.prepareStatement(select);
                    preparedStatement.setString(1, name + "%");
                    preparedStatement.setString(2, duration1);
                    preparedStatement.setString(3, duration2);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()) {
                        int id_program = resultSet.getInt(1);
                        String n = resultSet.getString(2);
                        String description = resultSet.getString(3);
                        Date date_of_creation = new Date(resultSet.getString(4));
                        Time duration = new Time(resultSet.getString(5));
                        Program program = new Program(id_program, n, description, date_of_creation, duration);
                        list.add(program);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getProgramsCountByPeriod(String month, String year) {
        try {
            String select = "SELECT COUNT(*) FROM programs WHERE MONTH(date_of_creation) = ? AND YEAR(date_of_creation) = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, month);
            preparedStatement.setString(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getProgramIdByName(String name) {
        try {
            String select = "SELECT id_program FROM programs WHERE name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String[] getProgramById(int id_program) {
        try {
            String select = "SELECT * FROM programs WHERE id_program = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String[] strings = new String[4];
                for (int i = 0; i < 4; i++) {
                    strings[i] = resultSet.getString(i + 2);
                }
                return strings;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int addProgram(String name, String description, Date date_of_creation, Time duration) {
        try {
            String insert = "INSERT INTO programs (name, description, date_of_creation, duration) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, date_of_creation.toString());
            preparedStatement.setString(4, duration.toString());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateProgram(String name, String description, String date_of_creation, String duration, int id_program) {
        try {
            String update = "UPDATE programs SET name = ?, description = ?, date_of_creation = ?, duration = ? WHERE id_program = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, date_of_creation);
            preparedStatement.setString(4, duration);
            preparedStatement.setInt(5, id_program);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeProgramById(int id_program) {
        try {
            String delete = "DELETE FROM programs WHERE id_program = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id_program);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<String> getProgramEmployeesById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT concat(last_name , ' ',first_name, ' ', middle_name) AS fio FROM program_employee LEFT JOIN employees USING(id_employee) LEFT JOIN programs USING(id_program) WHERE id_program = ? ORDER BY fio;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getNotProgramEmployeesById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT concat(last_name , ' ',first_name, ' ', middle_name) AS fio FROM employees WHERE " +
                    "id_employee NOT IN (SELECT id_employee FROM program_employee LEFT JOIN employees USING(id_employee) LEFT JOIN programs USING(id_program) WHERE id_program = ?) AND position = 'журналист' ORDER BY fio;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getProgramGuestsById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT concat(last_name , ' ',first_name, ' ', middle_name) AS fio FROM guests LEFT JOIN persons USING(id_person) LEFT JOIN programs USING(id_program) WHERE id_program = ? ORDER BY fio;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getNotProgramGuestsById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT concat(last_name , ' ',first_name, ' ', middle_name) AS fio FROM persons WHERE " +
                    "id_person NOT IN (SELECT id_person FROM guests LEFT JOIN persons USING(id_person) LEFT JOIN programs USING(id_program) WHERE id_program = ?) ORDER BY fio;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getProgramCompetitionsById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT competitions.description as d FROM program_competition LEFT JOIN competitions USING(id_competition) LEFT JOIN programs USING(id_program) WHERE id_program = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getNotProgramCompetitionsById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT competitions.description as d FROM competitions WHERE " +
                    "id_competition NOT IN (SELECT id_competition FROM program_competition LEFT JOIN competitions USING(id_competition) LEFT JOIN programs USING(id_program) WHERE id_program = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getProgramSportsmenById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT concat(last_name , ' ',first_name, ' ', middle_name) AS fio FROM program_sportsman LEFT JOIN persons USING(id_person) LEFT JOIN programs USING(id_program) WHERE id_program = ? ORDER BY fio;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getNotProgramSportsmenById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT concat(last_name , ' ',first_name, ' ', middle_name) AS fio FROM persons " +
                    "WHERE id_person NOT IN (" +
                    "SELECT id_person FROM program_sportsman LEFT JOIN persons USING(id_person) LEFT JOIN programs USING(id_program) WHERE id_program = ?) " +
                    "ORDER BY fio;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getProgramTeamsById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT teams.name as name FROM program_team LEFT JOIN teams USING(id_team) LEFT JOIN programs USING(id_program) WHERE id_program = ? ORDER BY name;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> getNotProgramTeamsById(int id_program) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT teams.name AS n FROM teams WHERE " +
                    "id_team NOT IN (SELECT id_team FROM program_team LEFT JOIN teams USING(id_team) LEFT JOIN programs USING(id_program) WHERE id_program = ?) ORDER BY n;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id_program);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int addEmployeeToProgramById(int id_program, int id_employee) {
        try {
            String insert = "INSERT INTO program_employee (id_program, id_employee) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_employee);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeEmployeeFromProgramById(int id_program, int id_employee) {
        try {
            String delete = "DELETE FROM program_employee WHERE id_program = ? AND id_employee = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_employee);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addGuestToProgramById(int id_program, int id_person) {
        try {
            String insert = "INSERT INTO guests (id_program, id_person) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_person);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeGuestFromProgramById(int id_program, int id_person) {
        try {
            String delete = "DELETE FROM guests WHERE id_program = ? AND id_person = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_person);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addCompetitionToProgramById(int id_program, int id_competition) {
        try {
            String insert = "INSERT INTO program_competition (id_program, id_competition) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_competition);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeCompetitionFromProgramById(int id_program, int id_competition) {
        try {
            String delete = "DELETE FROM program_competition WHERE id_program = ? AND id_competition = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_competition);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addSportsmanToProgramById(int id_program, int id_person) {
        try {
            String insert = "INSERT INTO program_sportsman (id_program, id_person) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_person);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeSportsmanFromProgramById(int id_program, int id_person) {
        try {
            String delete = "DELETE FROM program_sportsman WHERE id_program = ? AND id_person = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_person);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addTeamToProgramById(int id_program, int id_team) {
        try {
            String insert = "INSERT INTO program_team (id_program, id_team) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_team);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeTeamFromProgramById(int id_program, int id_team) {
        try {
            String delete = "DELETE FROM program_team WHERE id_program = ? AND id_team = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id_program);
            preparedStatement.setInt(2, id_team);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //broadcast
    public ArrayList<String[]> getBroadcast() {
        ArrayList<String[]> list = new ArrayList<>();
        try {
            String select = "SELECT name, channel, date_broadcast, time_broadcast FROM broadcast JOIN programs USING (id_program) ORDER BY date_broadcast, time_broadcast;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] row = new String[4];
                row[0] = resultSet.getString(1);
                row[1] = resultSet.getString(2);
                row[2] = resultSet.getString(3);
                row[3] = resultSet.getString(4);
                list.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    //person
    public int getPersonIdByFIO(String fio) {
        try {
            String select = "SELECT id_person FROM persons WHERE concat(last_name , ' ',first_name, ' ', middle_name) = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, fio);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    //team
    public int getTeamIdByName(String name) {
        try {
            String select = "SELECT id_team FROM teams WHERE name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    //sport
    public ArrayList<String> getSportList() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String select = "SELECT kind FROM sports;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
               list.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public String getSportDescription(String name) {
        try {
            String select = "SELECT description FROM sports WHERE kind = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //competition
    public int getCompetitionIdByDescription(String description) {
        try {
            String select = "SELECT id_competition FROM competitions WHERE description = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, description);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public ArrayList<String[]> getCompetitionsList() {
        ArrayList<String[]> list = new ArrayList<>();
        try {
            String select = "SELECT competitions.description, date_competition, location, kind FROM competitions JOIN sports USING(id_sport);";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] strings = { resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4) };
                list.add(strings);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String[]> zvit1() {
        ArrayList<String[]> zvit = new ArrayList<>();
        try {
            String select =
                    "SELECT concat(last_name,' ',first_name,' ',middle_name) as fio, COUNT(*) as count FROM program_employee LEFT JOIN employees USING(id_employee) LEFT JOIN programs USING(id_program) " +
                    "WHERE position = 'журналист' GROUP BY fio " +
                    "UNION " +
                    "SELECT concat(last_name,' ',first_name,' ',middle_name) as fio, 0 as count " +
                    "FROM employees WHERE position = 'журналист' AND id_employee NOT IN " +
                    "(SELECT id_employee FROM program_employee);";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] string = { resultSet.getString(1), resultSet.getString(2) };
                zvit.add(string);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zvit;
    }

    public ArrayList<String[]> zvit2() {
        ArrayList<String[]> zvit = new ArrayList<>();
        try {
            String select =
                    "SELECT kind, COUNT(*) as count " +
                    "FROM program_competition LEFT JOIN competitions USING(id_competition) LEFT JOIN sports USING(id_sport) " +
                    "GROUP BY kind " +
                    "UNION " +
                    "SELECT kind , 0 as count " +
                    "FROM sports WHERE id_sport NOT IN " +
                    "(SELECT id_sport FROM program_competition LEFT JOIN competitions USING(id_competition) LEFT JOIN sports USING(id_sport));";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] string = { resultSet.getString(1), resultSet.getString(2) };
                zvit.add(string);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zvit;
    }

    public ArrayList<String[]> zvit3() {
        ArrayList<String[]> zvit = new ArrayList<>();
        try {
            String select =
                    "SELECT concat(e.last_name,' ',e.first_name,' ',e.middle_name) AS fio, e.position, e.salary " +
                    "FROM employees e " +
                    "WHERE e.salary >= " +
                    "(SELECT AVG(em.salary) FROM employees em WHERE em.position = e.position);";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] string = { resultSet.getString(1), resultSet.getString(2), resultSet.getString(3) };
                zvit.add(string);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zvit;
    }

    public ArrayList<String[]> zvit4() {
        ArrayList<String[]> zvit = new ArrayList<>();
        try {
            String select =
                    "SELECT 'Подготовил наибольшее количество сюжетов' as cmnt, concat(last_name , ' ',first_name, ' ', middle_name) as fio, COUNT(*) as cnt\n" +
                            "FROM employees INNER JOIN program_employee USING(id_employee)\n" +
                            "GROUP BY fio\n" +
                            "HAVING cnt >= ALL\n" +
                            "(\n" +
                            "  SELECT COUNT(*) FROM employees INNER JOIN program_employee USING(id_employee)\n" +
                            "  GROUP BY id_employee\n" +
                            ") UNION \n" +
                            "SELECT 'Не подготовил ни одного сюжета', concat(last_name , ' ',first_name, ' ', middle_name) as fio, 0 as cnt\n" +
                            "FROM employees\n" +
                            "WHERE id_employee NOT IN\n" +
                            "(\n" +
                            "  SELECT id_employee FROM program_employee LEFT JOIN employees USING(id_employee)\n" +
                            ") AND position = 'журналист';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while(resultSet.next()) {
                String[] string = { resultSet.getString(1), resultSet.getString(2), resultSet.getString(3) };
                zvit.add(string);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zvit;
    }

}