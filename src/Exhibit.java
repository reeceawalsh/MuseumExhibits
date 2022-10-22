import java.text.DecimalFormat;

public class Exhibit {
    private String id;
    private String description;
    private int yearAcquired;
    private double value;

    public Exhibit(String id, String description, int yearAcquired, double value) {
        this.id = id;
        this.description = description;
        this.yearAcquired = yearAcquired;
        this.value = value;
    }

    public double getValue() {return value;}
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public int getYearAcquired() {
        return yearAcquired;
    }

    /* The setters are not used in this assignment but are useful to have for the future,
       the value of an Exhibit is likely to change with time for example. */
    public void setDescription(String description) {this.description = description;}
    public void setId(String id) {this.id = id;}
    public void setValue(double value) {this.value = value;}
    public void setYearAcquired(int yearAcquired) {this.yearAcquired = yearAcquired;}

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00"); // Used to format currency to two decimal places.
        return "Exhibit Id: " + getId() + ". Description: " + getDescription() + ". Year acquired: " + getYearAcquired() + ". Value: £" + df.format(getValue()) + ".";
    }
}
