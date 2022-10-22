import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws InterruptedException  {
        // Creates a museum, scanner and museumInterface then starts the programme.
        Museum museum = new Museum();
        Scanner scan = new Scanner(System.in);
        MuseumIO museumInterface = new MuseumIO(museum, scan);
        museumInterface.start();
    }
}