import java.util.*;
import java.io.*;
/**
 * <h1>PluralityVoteCounter</h1>
 * PluralityVoteCounter - is a class that is responsible to actually
 * implement the vote counting from an input file and determine winner candidate(s).
 *
 * The class object is responsible to load the candidates from the file, load
 * the ballots from the file, assign every ballot to the correct candidate,
 * determine winner(s) and resolve any tie if there.
 *
 * The tie condition is marked when more than 1 candidate has equal number of votes and
 * the number of votes is actually highest among all the candidates. To resolve a tie,
 * the vote counter selects the candidate(s) randomly from among the candidates resolved in the tie.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public class PluralityVoteCounter {
    /**
     * Holds the name of the input file (relative path).
     * This relative path points to the file from where
     * all the information about the election would be extracted.
     */
    private String inputFileName;

    /**
     * Holds the number of seats that need to be filled in the election.
     */
    private int numOfSeatsToBeFilled;

    /**
     * Holds the candidates that are engaged in the election.
     * Enforces mapping from candidate ID to candidate objects
     * for faster retrieval of information.
     */
    private HashMap<Long, PluralityCandidate> candidatesRunningInElection;

    /**
     * Holds the information about all the winner candidates.
     * Winner candidates include the top candidates required to
     * fill all the seats.
     */
    private ArrayList<PluralityCandidate> winnerCandidates;

    /**
     * Holds the information about all the ballots cast in the election.
     */
    private ArrayList<PluralityBallot> electionBallots;

    /**
     * Creates a new PluralityVoteCounter object that is used in the
     * main function to actually implement the voting.
     * @param inputFileName Name of the input file from which the data has to be fed.
     *                      This filename should be a relative path to the file.
     * @param numberOfSeats Number of seats (winners) that need to be filled in the election.
     */
    public PluralityVoteCounter(String inputFileName, int numberOfSeats) {
        this.inputFileName = inputFileName;
        this.numOfSeatsToBeFilled = numberOfSeats;
        this.candidatesRunningInElection = new HashMap<>();
        this.winnerCandidates = new ArrayList<>();
        this.electionBallots = new ArrayList<>();
    }

    /**
     * Creates a new PluralityVoteCounter object that is used in the
     * main function to actually implement the voting. Overrides the
     * general constructor with the default number of seats = 1 in
     * Plurality Voting.
     * @param inputFileName Name of the input file from which the data has to be fed.
     *                      This filename should be a relative path to the file.
     */
    public PluralityVoteCounter(String inputFileName) {
        this(inputFileName, 1);
    }

    /**
     * Returns the list of winner candidates of the election.
     * Information about the election is extracted from the particular input file.
     * @return ArrayList{PluralityCandidate} - The list of winner candidates.
     */
    public ArrayList<PluralityCandidate> getWinnerCandidates() {
        return this.winnerCandidates;
    }

    /**
     * Returns the map of all the candidates running in election.
     * @return HashMap{Long, PluralityCandidate} - The map of candidates.
     */
    public HashMap<Long, PluralityCandidate> getCandidatesRunningInElection() {
        return candidatesRunningInElection;
    }

    /**
     * Loads the information about each and every candidate running in the election.
     * This function populates the hash map of candidates. This hash map member variable
     * maps IDs of candidates to their respective objects.
     */
    public void loadCandidatesFromInputFile() {
        BufferedReader buffer;
        String fileLine;
        try {
            buffer = new BufferedReader(new FileReader(this.inputFileName));
            // This variable is used to check whether the file is empty or not.
            // Also this variable helps to make sure that we just read one line (first line) from the file.
            int checkForReadingOneLine = 0;
            while (checkForReadingOneLine < 1 && (fileLine = buffer.readLine()) != null) {
                // String array includes all candidate names at different indices.
                String[] candidateNameArray = fileLine.split(",");
                for (int candidateID = 0; candidateID < candidateNameArray.length; candidateID++) {
                    // Adding the candidate objects into the hash map.
                    this.candidatesRunningInElection.put((long) candidateID, new PluralityCandidate((long) candidateID, candidateNameArray[candidateID]));
                }
                checkForReadingOneLine++;
            }
            // Condition below will be true if the while loop terminates because there is
            // nothing present in the file (empty file).
            if (checkForReadingOneLine == 0) {
                System.out.println("Buffer not able to read the line. File is empty!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception when reading from file");
            e.printStackTrace();
        }
    }

    /**
     * Returns the ID of the candidate to which the ballot is assigned.
     * @param ballotArray String array of the input line from file separated by ",".
     *                    Each index contains either spaces (denoting no choice made)
     *                    or empty string or "1" (corresponding to the candidate to which
     *                    the ballot is assigned)
     * @return long - Candidate ID.
     */
    public long getVotedCandidateIDFromBallotArray(String[] ballotArray) {
        // Loop through the whole array until it finds the ID of the candidate
        // to which the ballot is assigned.
        for (int candidateId = 0; candidateId < ballotArray.length; candidateId++) {
            // "1" represents that the ballots was cast for the particular candidate (ID).
            if (ballotArray[candidateId].compareTo("1") == 0) {
                return (long) candidateId;
            }
        }
        // If the ballot is empty the function will return -1, meaning the ballot is illegal.
        // Currently the file is assumed to have perfect format and thus this condition will
        // never be met.
        return -1;
    }

    /**
     * Returns a new PluralityBallot type object.
     * @param serialNo Serial number that should be assigned to the ballot.
     * @param selectedCandidateId ID of the candidate to which the ballot is assigned.
     * @return PluralityBallot - a new PluralBallot type object.
     */
    private PluralityBallot generatePluralityBallot(int serialNo, long selectedCandidateId) {
        return new PluralityBallot(serialNo, selectedCandidateId);
    }

    /**
     * Loads all the ballots cast in the election (input file) into a list of ballots.
     * Helps in storing the information about all the electionBallots.
     * The vote counter iterates through these ballots and aggregates their
     * votes into corresponding candidates.
     */
    private void loadBallotsFromInputFIle() {
        BufferedReader buffer;
        String fileLine;
        int ballotSerialNumber = 1;
        try {
            buffer = new BufferedReader(new FileReader(this.inputFileName));
            // Skipping first line, because first line contains information
            // about the candidates engaged in the election.
            if (buffer.readLine() == null) {
                System.out.println("File is empty!");
                return;
            }
            // Loop through all other lines in the file and parse the information and add new ballots accordingly.
            while ((fileLine = buffer.readLine()) != null) {
                // ballotArray represents the array of strings after the line in file is split using the "," as a delimiter.
                String[] ballotArray = fileLine.split(",");
                // First get the corresponding ID of the candidate to which the ballot is assigned.
                // Use the serial number and candidate ID to generate a new ballot, and add this to the list of ballots.
                this.electionBallots.add(this.generatePluralityBallot(ballotSerialNumber, getVotedCandidateIDFromBallotArray(ballotArray)));
                ballotSerialNumber++;
            }
            if (ballotSerialNumber == 1) {
                System.out.println("File does not contain any ballots.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception when reading from file");
            e.printStackTrace();
        }
    }

    /**
     * Returns the PluralityCandidate type object which has the corresponding
     * candidate ID as supplied in the input parameter.
     * @param candidateID unique candidate ID.
     * @return PluralityCandidate - PluralityCandidate type object
     */
    private PluralityCandidate getCandidate(long candidateID) {
        // first check whether the Hash Map contains the object. If not then return null. Else return the corresponding candidate object.
        if (this.candidatesRunningInElection.containsKey(candidateID)) {
            return this.candidatesRunningInElection.get(candidateID);
        }
        return null;
    }

    /**
     * Implement voting algorithm (Plurality type) on the election (information loaded through the input file).
     * First loads all the candidates that are running in the election from the input file.
     * Then it load all the ballots cast in the election from the input file.
     * Then it iterates through all the ballots and aggregating the respective ballots' vote to
     * the particular candidate it is assigned to. After the function processes all the ballots
     * it determines the winner(s) of the election based on the number of seats that need to be filled.
     */
    public void implementVoting() {
        // load all the candidates that are running in the election from the input file.
        loadCandidatesFromInputFile();
        // load all the ballots cast in the election from the input file.
        loadBallotsFromInputFIle();
        // aggregate the votes of each ballot cast in the election to the corresponding candidate.
        for (PluralityBallot electionBallot : this.electionBallots) {
            Objects.requireNonNull(this.getCandidate(electionBallot.getCandidateIdFromBallot())).addBallot(electionBallot);
        }
        // After all the votes have been aggregated, determine the correct winner.
        this.determineWinner();
        // Print all the election results.
        this.printElectionResults();
    }

    /**
     * This function determines whether there is a TIE between candidates running in election.
     * @return int - If there is a tie the function returns the number of votes the tied
     *               candidates have. If there is no tie then the function returns 0.
     */
    public int electionHasTie() {
        // get the list of all the candidates running in election.
        ArrayList<PluralityCandidate> candidatesRunningInElection = new ArrayList<>(this.candidatesRunningInElection.values());
        // get the highest number of votes that a candidate has earned among all the candidates.
        int highestNumberOfVotes = Collections.max(this.candidatesRunningInElection.values()).getNumberOfVotesEarnedByCandidate();
        int numOfCandidatesTied = 0;
        // find the number of candidates that have the highest number of votes.
        for (PluralityCandidate candidate : candidatesRunningInElection) {
            if (candidate.getNumberOfVotesEarnedByCandidate() == highestNumberOfVotes) {
                numOfCandidatesTied++;
            }
        }
        // If only one person has the highest number of votes then there is no tie
        if (numOfCandidatesTied < 2) {
            return 0;
        }
        return highestNumberOfVotes;
    }

    /**
     * Determines the winner(s) of the election and populates the winner candidate list accordingly.
     * The number of winners that need to be determined is based off the number of seats that need to be filled.
     * First it checks for a tie condition and then proceed accordingly.
     * If there is a tie then it randomly chooses the appropriate number of candidate(s)
     * If there is no tie then the winner(s) are the first few candidates required to fill the seats with the highest number of votes.
     */
    private void determineWinner() {
        int highestNumberOfVotes = this.electionHasTie();
        // if the election does not have any tie within the candidates
        if (highestNumberOfVotes == 0) {
            // sort the candidates based on the number of votes in decreasing order (Highest number of votes first)
            ArrayList<PluralityCandidate> candidatesRunningInElection = new ArrayList<>(this.candidatesRunningInElection.values());
            Collections.sort(candidatesRunningInElection);
            Collections.reverse(candidatesRunningInElection);
            // populate the winner candidate list with the correct number of candidates (number of seats to be filled).
            this.winnerCandidates.addAll(candidatesRunningInElection.subList(0, this.numOfSeatsToBeFilled));
        } else {    // if there is a tie then resolve the tie and determine the winners accordingly.
            resolveElectionTie(highestNumberOfVotes);
        }
    }

    /**
     * Responsible to load the list of winner candidates with the appropriate candidates based
     * on the number of seats that need to be fulfilled.
     * @param highestNumberOfVotes The number of votes that the tied candidates have.
     */
    private void resolveElectionTie(int highestNumberOfVotes) {
        // load the list of all candidates in election and iterate through it to determine all the candidates who have tied.
        ArrayList<PluralityCandidate> allCandidatesRunningInElection = new ArrayList<>(this.candidatesRunningInElection.values());
        ArrayList<PluralityCandidate> tiedCandidates = new ArrayList<>();
        for (PluralityCandidate candidate : allCandidatesRunningInElection) {
            if (candidate.getNumberOfVotesEarnedByCandidate() == highestNumberOfVotes) {
                tiedCandidates.add(candidate);
            }
        }
        // Shuffle the tied candidates to make sure the winner candidates are chosen randomly from the tied candidates.
        Collections.shuffle(tiedCandidates);
        // if the number of seats that need to be filled are more than or equal to the number of candidates that are tied.
        if (tiedCandidates.size() >= this.numOfSeatsToBeFilled) {
            // just add the first required number of candidates to fill all the seats.
            this.winnerCandidates.addAll(tiedCandidates.subList(0, this.numOfSeatsToBeFilled));
        } else {    // else add all the tied candidates first.
            this.winnerCandidates.addAll(tiedCandidates.subList(0, tiedCandidates.size()));
            // sort the candidates based on the number of votes in decreasing order (Highest number of votes first)
            ArrayList<PluralityCandidate> candidatesRunningInElection = new ArrayList<>(this.candidatesRunningInElection.values());
            Collections.sort(candidatesRunningInElection);
            Collections.reverse(candidatesRunningInElection);
            // populate the winner candidate list with the correct number of candidates (remaining number of seats to be filled).
            this.winnerCandidates.addAll(candidatesRunningInElection.subList(tiedCandidates.size(), this.numOfSeatsToBeFilled));
        }
    }

    /**
     * Print the election results to the standard output (Terminal).
     */
    private void printElectionResults() {
        System.out.println("Winner Candidates - ");
        for (PluralityCandidate winnerCandidate : this.winnerCandidates) {
            System.out.println(winnerCandidate);
        }
        System.out.println("Dropped Candidates - ");
        ArrayList<PluralityCandidate> candidatesRunningInElection = new ArrayList<>(this.candidatesRunningInElection.values());
        Collections.sort(candidatesRunningInElection);
        Collections.reverse(candidatesRunningInElection);
        for (PluralityCandidate droppedCandidate : candidatesRunningInElection.subList(this.numOfSeatsToBeFilled, candidatesRunningInElection.size())) {
            System.out.println(droppedCandidate);
        }
    }
}
