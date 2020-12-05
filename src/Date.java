public class Date {
    private int year;
    private int month;
    private int day;

    //1000-01-01   -   9999-12-31

    public Date() {
        this.year = 0;
        this.month = 0;
        this.day = 0;
    }

    public Date(int year, int month, int day) {
        if (year >= 1000 && year <= 9999 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
            this.year = year;
            this.month = month;
            this.day = day;
        } else {
            this.year = 0;
            this.month = 0;
            this.day = 0;
        }
    }

    public Date(String date) {
        this.year = 0;
        this.month = 0;
        this.day = 0;
        if (date != null) {
            String dates[] = date.split("-");
            if (dates.length == 3) {
                int year = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);
                if (year >= 1000 && year <= 9999 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                    this.year = year;
                    this.month = month;
                    this.day = day;
                }
            }
        }
    }

    public void setDate(int year, int month, int day) {
        if (year >= 1000 && year <= 9999 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    public boolean isDate() {
        return year != 0 && month != 0 && day != 0;
    }

    @Override
    public String toString() {
        if (year == 0 || month == 0 || day == 0)
            return null;
        return year + "-" + month + "-" + day;
    }
}
