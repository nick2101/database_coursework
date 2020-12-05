public class Employee {
    private int id_employee;
    private int id_user;
    private String last_name;
    private String first_name;
    private String middle_name;
    private String position;
    private String salary;
    private Date date_of_birth;
    private String address;
    private String phone_number;
    private Date start_date;
    private Date end_date;

    public Employee(int id_employee, int id_user, String last_name, String first_name, String middle_name, String position, String salary, Date date_of_birth, String address, String phone_number, Date start_date, Date end_date) {
        this.id_employee = id_employee;
        this.id_user = id_user;
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.position = position;
        this.salary = salary;
        this.date_of_birth = date_of_birth;
        this.address = address;
        this.phone_number = phone_number;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Employee(String last_name, String first_name, String middle_name, String position, String salary, Date date_of_birth, String address, String phone_number, Date start_date, Date end_date) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.position = position;
        this.salary = salary;
        this.date_of_birth = date_of_birth;
        this.address = address;
        this.phone_number = phone_number;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Employee(Employee employee) {
        this(
                employee.getIdEmployee(),
                employee.getIdUser(),
                employee.getLastName(),
                employee.getFirstName(),
                employee.getMiddleName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDateOfBirth(),
                employee.getAddress(),
                employee.getPhoneNumber(),
                employee.getStartDate(),
                employee.getEndDate()
        );
    }

    public Employee() {

    }

    public int getIdEmployee() {
        return id_employee;
    }

    public void setIdEmployee(int id_employee) {
        this.id_employee = id_employee;
    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public void setMiddleName(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }
}
