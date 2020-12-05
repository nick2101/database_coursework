import java.util.ArrayList;

public class Register {

    private User user;
    public Employee employee;
    private int currentProgram;
    private int oldProgram;

    Register () {
        this.user = null;
        this.employee = null;
        this.currentProgram = -1;
        this.oldProgram = -1;
    }

    //user
    public int getUserType() {
        return user.getUser_type();
    }

    public int sign_in(String login, String password) {
        DBConnector connector = new DBConnector();
        if (!login.equals("") && !password.equals("")) {
            user = new User(connector.getUserByLoginAndPassword(login, password));
        } else {
            return 2;
        }
        connector.log(user.getId_user(), 0, "");
        return 0;
    }

    public int dropUser() {
        this.user = null;
        return 0;
    }

    //broadcast
    public ArrayList<String[]> getBroadcast() {
        DBConnector connector = new DBConnector();
        return connector.getBroadcast();
    }

    //employee
    public void setCurrentEmployee(String last_name, String first_name, String middle_name) {
        DBConnector connector = new DBConnector();
        employee = connector.getEmployeeByFullName(last_name, first_name, middle_name);
    }

    public void dropCurrentEmployee() {
        this.employee = null;
    }

    public ArrayList<String[]> getEmployeeList(String like) {
        DBConnector connector = new DBConnector();
        return connector.getEmployeesLike(like);
    }

    public String getBestEmployee() {
        DBConnector connector = new DBConnector();
        return connector.getBestEmployee();
    }

    public int saveEmployee(Employee employee) {
        DBConnector connector = new DBConnector();
        if (this.employee == null) {
            employee.setIdUser(0);
            int result = connector.addEmployee(employee);
            if (result == 1) {
                connector.log(user.getId_user(), 1, "employees");
                return 0;
            }
        } else {
            employee.setIdEmployee(this.employee.getIdEmployee());
            employee.setIdUser(this.employee.getIdUser());
            int result = connector.updateEmployee(employee);
            if (result == 1) {
                connector.log(user.getId_user(), 2, "employees");
                return 0;
            }
        }
        return 1;
    }

    public int removeEmployee(String last_name, String first_name, String middle_name) {
        DBConnector connector = new DBConnector();
        int result = connector.removeEmployeeByFullName(last_name, first_name, middle_name);
        if (result == 1) {
            connector.log(user.getId_user(), 3, "employees");
            return 0;
        }
        return 1;
    }

    //logs
    public ArrayList<String[]> getLogs() {
        DBConnector connector = new DBConnector();
        ArrayList<String[]> list = connector.getLogs();
        ArrayList<String[]> result_list = new ArrayList<>();
        for (String[] value : list) {
            String action = "Пользователь " + value[1];
            switch (value[2]) {
                case "0":
                    action = action + " вошел в систему";
                    break;
                case "1":
                    action = action + " доавил запись в таблицу ";
                    break;
                case "2":
                    action = action + " изменил запись в таблице ";
                    break;
                case "3":
                    action = action + " удалил запись из таблицы ";
                    break;
                default:
                    action = action + " сделал что-то непонятное с таблицей ";
                    break;
            }
            action = action + value[3];
            String[] strings = new String[2];
            strings[0] = value[0];
            strings[1] = action;
            result_list.add(strings);
        }
        return result_list;
    }

    //program
    public void dropCurrentProgram() {
        this.oldProgram = currentProgram;
        this.currentProgram = -1;
    }

    public void setCurrentProgram(String name) {
        DBConnector connector = new DBConnector();
        this.oldProgram = currentProgram;
        this.currentProgram = connector.getProgramIdByName(name);
    }

    public void restoreCurrentProgram() {
        this.currentProgram = oldProgram;
    }

    public ArrayList<Program> getProgramsList(String name, String date1, String date2, String duration1, String duration2) {
        name = name.trim();
        date1 = date1.trim();
        date2= date2.trim();
        duration1 = duration1.trim();
        duration2 = duration2.trim();
        DBConnector connector = new DBConnector();
        if (new Date(date1).isDate() && new Date(date2).isDate() && new Time(duration1).isTime() && new Time(duration2).isTime()) {
            return connector.getProgramsWithNameDateDuration(name, date1, date2, duration1, duration2);
        } else {
            if (new Date(date1).isDate() && new Date(date2).isDate()) {
                return connector.getProgramsWithNameDateDuration(name, date1, date2, null, null);
            } else if (new Time(duration1).isTime() && new Time(duration2).isTime()) {
                return connector.getProgramsWithNameDateDuration(name, null, null, duration1, duration2);
            } else {
                ArrayList<Program> programs = connector.getProgramsLike(name);
                if (programs.size() > 0)
                    return programs;
            }
        }
        return null;
    }

    public String getThisMonthProgramsCount() {
        DBConnector connector = new DBConnector();
        return "" + connector.getProgramsCountByPeriod("NOW()", "NOW()");
    }

    public String getPreviousMonthProgramsCount() {
        DBConnector connector = new DBConnector();
        return "" + connector.getProgramsCountByPeriod("NOW()", "NOW()");
    }

    public String[] getCurrentProgramInfo() {
        if (currentProgram != -1) {
            DBConnector connector = new DBConnector();
            String[] program = connector.getProgramById(currentProgram);
            return program;
        }
        return null;
    }

    public ArrayList<String> getCurrentProgramEmployees() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getProgramEmployeesById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getNotCurrentProgramEmployees() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getNotProgramEmployeesById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getCurrentProgramGuests() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getProgramGuestsById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getNotCurrentProgramGuests() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getNotProgramGuestsById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getCurrentProgramCompetitions() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getProgramCompetitionsById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getNotCurrentProgramCompetitions() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getNotProgramCompetitionsById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getCurrentProgramSportsmen() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getProgramSportsmenById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getNotCurrentProgramSportsmen() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getNotProgramSportsmenById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getCurrentProgramTeams() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getProgramTeamsById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public ArrayList<String> getNotCurrentProgramTeams() {
        DBConnector connector = new DBConnector();
        if (currentProgram != -1) {
            ArrayList<String> list = connector.getNotProgramTeamsById(currentProgram);
            if (list.size() > 0)
                return list;
            else
                return null;
        }
        return null;
    }

    public void addEmployeeToProgram(String fio) {
        DBConnector connector = new DBConnector();
        int id_employee = connector.getEmployeeIdByFIO(fio);
        connector.addEmployeeToProgramById(currentProgram, id_employee);
        connector.log(user.getId_user(), 1, "program_employee");
    }

    public void removeEmployeeFromProgram(String fio) {
        DBConnector connector = new DBConnector();
        int id_employee = connector.getEmployeeIdByFIO(fio);
        connector.removeEmployeeFromProgramById(currentProgram, id_employee);
        connector.log(user.getId_user(), 3, "program_employee");
    }

    public void addGuestToProgram(String fio) {
        DBConnector connector = new DBConnector();
        int id_person = connector.getPersonIdByFIO(fio);
        connector.addGuestToProgramById(currentProgram, id_person);
        connector.log(user.getId_user(), 1, "guests");
    }

    public void removeGuestFromProgram(String fio) {
        DBConnector connector = new DBConnector();
        int id_person = connector.getPersonIdByFIO(fio);
        connector.removeGuestFromProgramById(currentProgram, id_person);
        connector.log(user.getId_user(), 3, "guests");
    }

    public void addCompetitionToProgram(String description) {
        DBConnector connector = new DBConnector();
        int id_competition = connector.getCompetitionIdByDescription(description);
        connector.addCompetitionToProgramById(currentProgram, id_competition);
        connector.log(user.getId_user(), 1, "program_competition");
    }

    public void removeCompetitionFromProgram(String description) {
        DBConnector connector = new DBConnector();
        int id_competition = connector.getCompetitionIdByDescription(description);
        connector.removeCompetitionFromProgramById(currentProgram, id_competition);
        connector.log(user.getId_user(), 3, "program_competition");
    }

    public void addSportsmanToProgram(String fio) {
        DBConnector connector = new DBConnector();
        int id_person = connector.getPersonIdByFIO(fio);
        connector.addSportsmanToProgramById(currentProgram, id_person);
        connector.log(user.getId_user(), 1, "sportsman_comp");
    }

    public void removeSportsmanFromProgram(String fio) {
        DBConnector connector = new DBConnector();
        int id_person = connector.getPersonIdByFIO(fio);
        connector.removeSportsmanFromProgramById(currentProgram, id_person);
        connector.log(user.getId_user(), 3, "sportsman_competition");
    }

    public void addTeamToProgram(String name) {
        DBConnector connector = new DBConnector();
        int id_team = connector.getTeamIdByName(name);
        connector.addTeamToProgramById(currentProgram, id_team);
    }

    public void removeTeamFromProgram(String name) {
        DBConnector connector = new DBConnector();
        int id_team = connector.getTeamIdByName(name);
        connector.removeTeamFromProgramById(currentProgram, id_team);
    }

    public int saveProgramInfo(String name, String description, String date_of_creation, String duration) {
        DBConnector connector = new DBConnector();
        if (currentProgram == -1) {
            int result = connector.addProgram(name, description, new Date(date_of_creation), new Time(duration));
            if (result == 1) {
                connector.log(user.getId_user(), 1, "programs");
                return 0;
            }
        } else {
            int result = connector.updateProgram(name, description, date_of_creation, duration, currentProgram);
            if (result == 1) {
                connector.log(user.getId_user(), 2, "programs");
                return 0;
            }
        }
        return 1;
    }

    public int removeCurrentProgram() {
        if (currentProgram != -1) {
            DBConnector connector = new DBConnector();
            int result = connector.removeProgramById(currentProgram);
            if (result == 1) {
                connector.log(user.getId_user(), 3, "programs");
                return 0;
            }
        }
        return 1;
    }

    public String[][] getCompetitionsList() {
        DBConnector connector = new DBConnector();
        ArrayList<String[]> list = connector.getCompetitionsList();
        String[][] result = list.toArray(new String[list.size()][]);
        return result;
    }

    public ArrayList<String> getSportList() {
        DBConnector connector = new DBConnector();
        return connector.getSportList();
    }

    public String getSportDescription(String name) {
        DBConnector connector = new DBConnector();
        String string = connector.getSportDescription(name);
        if (string == null)
            return "";
        return string;
    }

    public String[][] zvit1() {
        DBConnector connector = new DBConnector();
        ArrayList<String[]> list = connector.zvit1();
        String[][] zvit = list.toArray(new String[list.size()][]);
        return zvit;
    }

    public String[][] zvit2() {
        DBConnector connector = new DBConnector();
        ArrayList<String[]> list = connector.zvit2();
        String[][] zvit = list.toArray(new String[list.size()][]);
        return zvit;
    }

    public String[][] zvit3() {
        DBConnector connector = new DBConnector();
        ArrayList<String[]> list = connector.zvit3();
        String[][] zvit = list.toArray(new String[list.size()][]);
        return zvit;
    }

    public String[][] zvit4() {
        DBConnector connector = new DBConnector();
        ArrayList<String[]> list = connector.zvit4();
        String[][] zvit = list.toArray(new String[list.size()][]);
        return zvit;
    }

}