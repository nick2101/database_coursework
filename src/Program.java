public class Program {
    public int id_program;
    public String name;
    public String description;
    public Date date_of_creation;
    public Time duration;

    public Program(int id_program, String name, String description, Date date_of_creation, Time duration) {
        this.id_program = id_program;
        this.name = name;
        this.description = description;
        this.date_of_creation = date_of_creation;
        this.duration = duration;
    }
}
