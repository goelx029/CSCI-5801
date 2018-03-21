import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the number of Candidates to be selected");
        int numOfCand = sc.nextInt();
        System.out.println("Please enter the number of Ballots in the file");
        int numOfBallots = sc.nextInt();
        System.out.println("Please give the desired file you would like to run");
        String filePath = sc.nextLine();
        System.out.println("Please enter the number of seats to be filled");
        int seatsFilled = sc.nextInt();
        int s;
        do {
            System.out.println("***************************VoteSys System 1.0************************************\n");
            System.out.println("Select one of the options below :-\n");
            System.out.println("1. Plurality\n2. STV");
            int num = sc.nextInt();
            if(num ==1) {
                for (int i = 0; i < 10; i++) {
                    PluralityVoteCounter counter = new PluralityVoteCounter("./testing/testingCSVFIles/Plurality1000Votes10Candidates.csv", 10);
                    counter.implementVoting();
                    System.out.println("****************************");
                }
            }
            else if (num==2) {
        STVVoteCounter test = new STVVoteCounter("./testing/examplefile.csv", 3);
        test.implementVoting();
            }
            else {
                System.out.println("Please check the input");
            }
            System.out.println("Do you want to have another go?? (Y=1/N=2)");
            s = sc.nextInt();
        } while (s==1);
    }
}
