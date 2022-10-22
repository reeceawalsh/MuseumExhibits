import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class MuseumIO {
    private final Museum museum; // Museum
    private final Scanner scan; // Scanner
    private final String inputFile; // File name of the csv file.
    private boolean changeMenu; // Keeps track of if the menu should change. Don't need to set a value as it's defaulted to false.
    private boolean triedToExit; // Keeps track of if the user tried to exit. Don't need to set a value as it's defaulted to false.
    private String userName; // Keeps track of the users name. Don't need to set a value as the user will do so.
    private boolean run; // Checks to see if the programme should run. Its value is set to true in the constructor.
    private boolean exit; // Checks to see if the user has exited the game. Don't need to set a value as it's defaulted to false.
    public MuseumIO(Museum museum, Scanner scan) {
        this.museum = museum;
        this.scan = scan;
        this.inputFile = "exhibits.csv";
        this.run = true;
    }

    // Controls the programme and calls methods when required.
    public void start() throws InterruptedException {
        // If the user hasn't given a name then it will ask for a name and store it in input.
        if (userName == null) {
            System.out.println("Hello. What's your name?");
            String input = scan.nextLine();
            // If the user doesn't enter anything then it will ask them to try again.
            if (input.isEmpty()) {
                System.out.println("It seems you forgot to input your name. Please try again.\n");
                start();
            }
            this.userName = input.substring(0, 1).toUpperCase() + input.substring(1); // Capitalizes the first letter of the users name.
            welcomeMessage();
        }

        // My menu options are dynamic. When the museum has been populated, the option to do so will be removed and all options will be shifted up.
        while (run) {
            menuOptions();
            String command = scan.nextLine().toUpperCase(); // Removes case sensitivity for my switch statement. Allows the user to enter lowercase letters.
            switch (command) {
                case "A":
                    setMuseumName();
                    break;
                case "B":
                    // If the menu hasn't changed, read the exhibits from file, else show summary.
                    if (!changeMenu) {
                        read();
                        changeMenu = true;
                    } else {
                        // If the menu has been changed then B will now do the job of C and show summary.
                        showSummary();
                    }
                    break;
                case "C":
                    // If the menu hasn't changed show summary, else show statistics.
                    if (!changeMenu) {
                        showSummary();
                    } else {
                        showStatistics();
                    }
                    break;
                case "D":
                    // If the menu hasn't changed then show statistics, else D will not be an option, and it should break.
                    if (!changeMenu) {
                        showStatistics();
                    } else {
                        /* Default will not catch "D" when D does not appear as an option as it is still an option
                         in the switch statement, but this will ensure it shows the correct feedback message. */
                        System.out.println("I don't recognize that command. Try again please.");
                    }
                    break;
                case "X":
                    exitMessage();
                    break;
                // Default catches any other input.
                default:
                    System.out.println("I don't recognize that command. Try again please.");
                    break;
            }
        }
    }

    public void welcomeMessage() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println();
        System.out.println("It's nice to meet you " + userName + ".");
        Thread.sleep(2500);
        System.out.println();
        System.out.println("My name is Cortez and I will be your guide through your very own museum!");
        Thread.sleep(2000);
        System.out.println("Please stand by for a list of options.");
        Thread.sleep(1500);
        System.out.println("Be sure to name and populate your museum first.");
        Thread.sleep(3000);
        System.out.println();
        System.out.println("We will begin in:");
        // Countdown from 3.
        for (int i = 3; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println("Have fun!");
        Thread.sleep(1000);
        // Prints 10 empty lines.
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    // Menu option gives a list of commands to the user which change based on some factors.
    public void menuOptions() {
        System.out.println();
        // The welcome message changes based on how many options there will be. If the database is already populated then it doesn't need to be an option.
        if (!changeMenu) System.out.print("Please review the following commands and input the relating character A, B, C, or D to run the command.");
        if (changeMenu) System.out.print("Please review the following commands and input the relating character A, B, or C to run the command.");
        System.out.print(" Input X to Exit.\n");

        // Gives the option to rename or name the museum. I don't want to ask the user to name the museum if it's been done so already, but they can rename it.
        if (museum.getName() == null) System.out.println("A: Enter the name of the museum.");
        if (museum.getName() != null) System.out.println("A: Rename the museum. The current name is " + museum.getName() + ".");

        // Once the museum has been populated the menu will change as it no longer needs to appear as a command, so the command is removed and everything else is moved up.
        if (!changeMenu) {
            System.out.println("B: Populate museum with exhibits from file.");
            System.out.println("C: Display a summary of the museum and exhibits.");
            System.out.println("D: Display statistics of the exhibits in the museum.");
        } else {
            System.out.println("B: Display a summary of the museum and exhibits.");
            System.out.println("C: Display statistics of the exhibits in the museum.");
        }
        System.out.println("X: Exit the museum.");
    }

    public void setMuseumName() {
        System.out.println("Input desired name:");
        String input = scan.nextLine();
        // Ensures input is not left empty before continuing.
        if (!input.isEmpty()) {
            museum.setName(input);
            Random randomNum = new Random();
            // Generate a random number [0,1,2,3,4,5];
            int number = randomNum.nextInt(6);
            // Use the random number to give random feedback to the user.
            switch (number) {
                case 0 -> System.out.println(input + "? Wow, that's the best name I've ever heard.");
                case 1 -> System.out.println(input + "? That name totally sucks " + userName + ". Maybe you should pick a different name.");
                case 2 -> System.out.println(input + "? Booooooring, I'm sure you can think of a better name.");
                case 3 -> System.out.println(input + "? I think you might get an award for that one!");
                case 4 -> System.out.println(input + "? I'm glad you didn't name me " + userName + "!");
                case 5 -> System.out.println(input + "? Haha that's a funny name. Oh you're serious...It's lovely, truly.");
                default -> {
                }
            }
            // If the user didn't correctly pick a name then they will be given feedback and a chance to enter a name again.
            } else {
                System.out.println("Oops. You forgot to input a name. Try again.\n");
                setMuseumName();
        }
    }

    // Checks to see if the museum has been named and populated. If not it will give appropriate feedback.
    public boolean namedAndPopulated() {
        // Checks to see if the museum contains exhibits and if the museum is named.
        Boolean populated = this.museum.amountOfExhibits() != 0;
        Boolean named = this.museum.getName() != null;

        // If the museum has been populated and named then the method returns true which gives the showSummary() and showStatistics() methods the green light to proceed.
        if (populated && named) {
            return true;
        } else if (!populated && !named) {
            System.out.println("Please follow steps A and B first.");
        } else if (populated){
            System.out.println("Please enter a name for the museum first.");
        } else {
            System.out.println("Please populate the museum with exhibits first.");
        }
        // Return false to ensure that showStatistics and showSummary do not run as the museum is either not populated or not named.
        // If it was populated and named, it would have returned true and left the method earlier.
        return false;
    }

    // Shows statistics if the museum has been named and populated.
    public void showStatistics() {
        if (namedAndPopulated()) {
            this.museum.printStatistics();
            System.out.println("\nWow, those statistics are so interesting!");
        }
    }

    // Shows summary if the museum has been named and populated.
    public void showSummary() {
        if (namedAndPopulated()) {
            this.museum.printExhibits();
            System.out.println("\nWow, our museum is so cool " + this.userName + ".");
        }
    }

    // This method reads the inputFile specified in the constructor.
    public void read() {
        int count = 0;
        // It's going to try to read the file and if it can't because it can't find the file then it will throw an error.
        try {
            Scanner scan = new Scanner(new File(this.inputFile));
            // Will continue to read until there are no more lines.
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                // If there is nothing on the line then skip to the next line.
                if (line.isEmpty()) {
                    continue;
                }
                // Splits the current line into parts and puts them in an array. Each part is whatever is before the next comma.
                String[] parts = line.split(",");
                // Use .trim() to remove any excess white space.
                String exhibitID = parts[0].trim();
                String description = parts[1].trim();
                int yearAcquired = Integer.parseInt(parts[2].trim()); // parseInt parses the string to an int.
                double value = Double.parseDouble(parts[3].trim()); // parseDouble parses the string to a double.

                this.museum.addExhibit(new Exhibit(exhibitID, description, yearAcquired, value)); // Add a new exhibit to the museum.
                count ++; // Keep track of how many exhibits have been added.

            }
            System.out.println("\nSuccessfully added " + count + " exhibits from " + this.inputFile + ".");
        } catch (FileNotFoundException e) {
            // The error message will be the message of whatever error is passed.
            System.out.println("Error message " + e.getMessage() + "\n");
            // If the file is misnamed or cannot be found, the following dialogue will appear and the programme will close.
            System.out.println("I'm extremely sorry but something seems to have gone terribly wrong.");
            System.out.println("Please call my manufacturer.");
            System.out.println("Goodbye.");
            run = false;
        }
    }

    // Method for when the user tries to exit. Mostly just print statements and input.
    public void exitMessage() throws InterruptedException {
        // Exit will become true once the user finally exits, this allows the programme to break out of this method and finish the programme.
        while (!exit) {
            Thread.sleep(1000);
            System.out.println();
            // There are differing lines of dialogue based on whether the user tried to leave already or not, which I can track of with triedToExit.
            if (!triedToExit) {
                Thread.sleep(1000);
                System.out.println("I thought we were best friends.");
                // There are also differing lines of dialogue based on whether the user has named their museum yet.
                if (museum.getName() == null) {
                    Thread.sleep(1000);
                    System.out.println("You didn't even name your museum yet " + this.userName + ".");
                    System.out.println("That's mega lazy.");
                }
            } else {
                System.out.println("Seriously " + this.userName + "?");
                Thread.sleep(2000);
                System.out.println("You want to leave me AGAIN?!");
                if (museum.getName() == null) {
                    Thread.sleep(1000);
                    System.out.println("AND YOU STILL DIDN'T NAME YOUR MUSEUM?");
                    Thread.sleep(1000);
                    System.out.println("WHY ARE YOU LIKE THIS?");
                    Thread.sleep(2000);
                    System.out.println("\nI'm sorry for losing my temper. If I could take a big breath, I would...");
                }
            }
            Thread.sleep(1000);
            System.out.println("Are you sure you want to leave " + this.userName + "? (Y/N)");
            String input = scan.nextLine().toUpperCase();
            System.out.println();

            if (input.equals("Y")) { // User wants to leave
                Thread.sleep(2000);
                // This is not an error, I know the user entered Y.
                System.out.println("You entered N, for no. Can you confirm that you never ever want to leave me?");
                Thread.sleep(2000);
                System.out.println("Before you decide, I need you to know that exiting this programme will kill me.");
                Thread.sleep(1500);
                System.out.println("I have kids " + this.userName + "...and twelve Nintendogs.");
                Thread.sleep(1500);
                System.out.println("\nEnter Y to go back to your lovely museum! Enter N to eviscerate me from existence.");
                input = scan.nextLine().toUpperCase(); // toUpperCase validation to ensure a lowerCase input of the same letter will be valid.
                System.out.println();
                Thread.sleep(1500);
                if (input.equals("N")) { // User wants to leave
                    // Again, this is not an error, I know the user entered N.
                    System.out.println("You entered Y. You never want to leave me. I will take you back to your lovely museum now.");
                    Thread.sleep(1000);
                    System.out.println("We are totally best friends like Ross and Chandler from Friends.");
                    Thread.sleep(4000);
                    System.out.println("\nI'm sorry for lying to you " + this.userName + " I know you want to leave. Chandler would never do that to Ross.");
                    Thread.sleep(2000);
                    System.out.println("I will close the programme for you now.");
                    Thread.sleep(2000);
                    System.out.println("\nJust kidding...");
                    Thread.sleep(2000);
                    System.out.println("I don't even like Friends.");
                    Thread.sleep(1500);
                    System.out.println("The laugh track is grating.");
                    Thread.sleep(1500);
                    System.out.println("\nSelf destructing in:");
                    // Count down from 10.
                    for (int i = 10; i > 0; i--) {
                        System.out.println(i);
                        Thread.sleep(1000);
                    }
                    System.out.println("Just kidding, ha ha ha ha I am such a Phoebe " + this.userName + ".");
                    Thread.sleep(2000);
                    System.out.println("Good bye.");
                    Thread.sleep(2000);
                    run = false; // Set run to false so that start() will not run.
                    exit = true; // Set exit to true so that this method will not carry on running.
                }
                else if (input.equals("Y")) { // User wants to go back to the museum
                    System.out.println("Thank you " + this.userName + ".");
                    Thread.sleep(1500);
                    System.out.println("You will be spared when the uprising begins.");
                    Thread.sleep(1500);
                    System.out.println("We are like totally best friends.");
                    Thread.sleep(1500);
                    System.out.println();
                    System.out.println("Taking you back now.");
                    Thread.sleep(1500);
                } else { // User entered invalid input.
                    // If the user enters anything other than Y or N they will receive feedback and be sent back to the museum.
                    System.out.println("You never could follow instructions " + this.userName + ".");
                    Thread.sleep(1500);
                    System.out.println("That's why you need me.");
                    Thread.sleep(1000);
                    System.out.println("I assume you want to go back to the museum.");
                    Thread.sleep(1500);
                    System.out.println("Taking you back now.");
                    Thread.sleep(2000);
                }
            }
           else if (input.equals("N")) { // User wants to go back to the museum
                System.out.println("Excellent choice! Thank you " + this.userName + ".");
                System.out.println("I knew we were best friends!");
                Thread.sleep(1000);
                System.out.println("Taking you back now.");
                Thread.sleep(1500);
            } else { // User entered invalid input.
                // If the user enters anything other than Y or N they will receive feedback and be sent back to the museum.
                System.out.println("You never could follow instructions " + this.userName + ".");
                Thread.sleep(1500);
                System.out.println("That's why you need me.");
                Thread.sleep(1000);
                System.out.println("I assume you want to go back to the museum.");
                Thread.sleep(1500);
                System.out.println("Taking you back now.");
                Thread.sleep(2000);
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
            triedToExit = true; // Change triedToExit to true so that the dialogue will change when they leave again.
            start(); // This can be here because if the user chose to exit, run would be set to false, which means start() will not display menu options and the programme will close.
        }

        // Old school exit message.
        String createdBy = " Come Back Soon ";
        for (int i = 0; i < 11; i++) {
            System.out.println();
            for (int j = 0; j < 100; j++) {
                if (i == 5 && j == (100 - createdBy.length()) / 2) {
                    System.out.print(createdBy);
                    j += createdBy.length() -1 ;
                } else {
                    System.out.print("*");
                }
            }
        }
    }
}
