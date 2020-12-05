public class Time {
    private int hours;
    private int minutes;
    private int seconds;

    public Time() {
        this.hours = -1;
        this.minutes = -1;
        this.seconds = -1;
    }

    public Time(int hours, int minutes, int seconds) {
        if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59 && seconds >= 0 && seconds <= 59)
        {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
        else
        {
            this.hours = -1;
            this.minutes = -1;
            this.seconds = -1;
        }
    }

    public Time(String time) {
        this.hours = -1;
        this.minutes = -1;
        this.seconds = -1;
        if (time != null) {
            String times[] = time.split(":");
            if (times.length == 3) {
                int hours = Integer.parseInt(times[0]);
                int minutes = Integer.parseInt(times[1]);
                int seconds = Integer.parseInt(times[2]);
                if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59 && seconds >= 0 && seconds <= 59) {
                    this.hours = hours;
                    this.minutes = minutes;
                    this.seconds = seconds;
                }
            }
        }
    }

    public void setTime(int hours, int minutes, int seconds) {
        if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59 && seconds >= 0 && seconds <= 59)
        {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    public boolean isTime() {
        return hours != -1 && minutes != -1 & seconds != -1;
    }

    @Override
    public String toString() {
        if (hours == -1 || minutes == -1 || seconds == -1)
            return null;
        return hours + ":" + minutes + ":" + seconds;
    }

}