public class User {
    private int id_user;
    private String login;
    private int user_type;

    public User() {
        this.id_user = -1;
        this.login = "";
        this.user_type = 9;
    }

    public User(int id_user, String login, int user_type) {
        this.id_user = id_user;
        this.login = login;
        this.user_type = user_type;
    }

    public User(User user) {
        this(user.getId_user(), user.getLogin(), user.getUser_type());
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

}
