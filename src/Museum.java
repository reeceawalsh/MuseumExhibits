import java.text.DecimalFormat;
import java.util.ArrayList;

public class Museum {
    private String name;
    private ArrayList<Exhibit> exhibits;

    public Museum() {
        this.exhibits = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void addExhibit(Exhibit exhibit) {
        this.exhibits.add(exhibit);
    }

    public Exhibit highestValue() {
        int indexOfMaxValue = 0;
        // The loop starts at index 1, as we don't need to compare index 0 against index 0 as they will be equal.
        for (int i = 1; i < exhibits.size(); i++) {
            // Compare both exhibits values against each other, then set the indexOfMaxValue as the exhibit index with the highest value.
            if (exhibits.get(i).getValue() > exhibits.get(indexOfMaxValue).getValue()) {
                indexOfMaxValue = i;
            }
        }
        return exhibits.get(indexOfMaxValue);
        // One liner which does the same thing.
//      return exhibits.stream().max(Comparator.comparing(x -> x.getValue())).get();
    }

    // Same as above but comparing which is the lowest value of year acquired.
    public Exhibit firstExhibit() {
        int indexOfLowestValue = 0;
        for (int i = 1; i < exhibits.size(); i++) {
            if (exhibits.get(i).getYearAcquired() < exhibits.get(indexOfLowestValue).getYearAcquired()) {
                indexOfLowestValue = i;
            }
        }
        return exhibits.get(indexOfLowestValue);
        // One liner which does the same thing.
//       return exhibits.stream().min(Comparator.comparing(x -> x.getYearAcquired())).get();
    }

    public double averageValue() {
        return exhibits.stream()
                       .mapToDouble(i -> i.getValue()) // Create a double stream of each exhibit's value.
                       .sum() / exhibits.size(); // Sum all the values and divide by size to get the average.
    }

    public void printExhibits() {
        System.out.println();
        System.out.println("Museum name: " + this.name);
        // For each exhibit(x) in exhibits, print out the exhibit.
        exhibits.stream().forEach(x -> System.out.println(x));
    }

    public int amountOfExhibits() {
        return exhibits.size();
    }

    public void printStatistics() {
        DecimalFormat df = new DecimalFormat("#.00"); // Used to format currency to two decimal places.
        System.out.println();
        System.out.println("The statistics on highest value, first exhibit acquired and average value of exhibits:");
        System.out.println("Highest value exhibit: " + highestValue().getDescription() + ". (" + highestValue().getId() + ")," + " £" + df.format(highestValue().getValue()) + ".");
        System.out.println("First exhibit acquired: " + firstExhibit().getDescription() + ". (acquired " + firstExhibit().getYearAcquired() + ").");
        System.out.println("Average value of exhibits: £" + df.format(averageValue()) + ".");
    }
}
