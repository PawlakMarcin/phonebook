package pl.agh.phonebook.model;

public class ModelCall {
    private String number;
    private String duration;
    private String date;
    private int imageFlag;

    public ModelCall(String number, String duration, String date) {
        this.number = number;
        this.duration = duration;
        this.date = date;
    }

    public ModelCall(String number, String duration, String date, int imageFlag) {
        this.number = number;
        this.duration = duration;
        this.date = date;
        this.imageFlag = imageFlag;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageFlag() {
        return imageFlag;
    }

    public void setImageFlag(int imageFlag) {
        this.imageFlag = imageFlag;
    }
}
