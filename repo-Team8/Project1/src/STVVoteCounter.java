import java.util.*;
import java.io.*;
/**
 * <h1>STVVoteCounter</h1>
 * STVVoteCounter - is a class that is responsible to actually
 * implement the vote counting from an input file and determine winner candidate(s).
 *
 * The class object is responsible to load the candidates from the file, load
 * the ballots from the file, assign every ballot to the correct candidate,
 * determine winner(s) and resolve any tie if there.
 *
 * The tie condition is marked when more than 1 candidate has equal number of votes and
 * the number of votes is actually highest among all the candidates. To resolve a tie,
 * the vote counter drops the candidate who earned the last ballot in terms of order.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public class STVVoteCounter {
    /**
     * Holds the name of the input file (relative path).
     * This relative path points to the file from where
     * all the information about the election would be extracted.
     */
    private String inputFileName;

    /**
     * Holds the candidates that are engaged in the election.
     * Enforces mapping from candidate ID to candidate objects
     * for faster retrieval of information. This Hash Map holds information
     * about all candidates that were ever involved in the election.
     */
    private HashMap<Long, STVCandidate> allCandidatesRunningInElection;

    /**
     * Holds the candidates that are engaged in the election.
     * Enforces mapping from candidate ID to candidate objects
     * for faster retrieval of information. This Hash Map is required
     * to only contain information about the candidates which are still
     * running in the elections (Not winner or dropped candidate).
     */
    private HashMap<Long, STVCandidate> candidatesRunningInElection;

    /**
     * Holds the total number of candidates that are running in the election.
     * Is equal to the number of candidates loaded in the Hash Map - allCandidatesRunningInElection.
     */
    private int numOfCandidatesRunningInElection;

    /**
     * Holds the information about all the ballots cast in the election.
     */
    private ArrayList<STVBallot> electionBallots;

    /**
     * Holds the number of seats that need to be filled in the election.
     */
    private int numOfSeatsToBeFilled;

    /**
     * Holds the droop quota of the election.
     */
    private int droopQuota;

    /**
     * Holds the information about all the winner candidates.
     * Winner candidates include the top candidates required to
     * fill all the seats. They are determined every time a candidate
     * reaches the droop quota.
     */
    private ArrayList<STVCandidate> winnerCandidates;

    /**
     * Holds the information about all the dropped candidates.
     * Dropped candidates include the candidates dropped in each pass
     * of the ballots. Dopeed candidates include candidates with least
     * number of votes in each pass through ballots.
     */
    private ArrayList<STVCandidate> droppedCandidates;

    /**
     * Holds the content that needs to be written as part of the report file
     * for the election.
     */
    private StringBuilder reportContent;

    /**
     * Holds the information about whether report content should be output into a file.
     */
    private boolean reportGenerateFlag;

    /**
     * Holds the information about whether the user wants the shuffle feature to be On or Off!
     */
    private boolean shuffleFeatureFlag;

    /**
     * Creates a new STVVoteCounter object that is used in the
     * main function to actually implement the voting.
     * @param inputFileName Name of the input file which contains the data about the election.
     *                      This filename should be a relative path to the file.
     * @param numOfSeatsToBeFilled Number of winners required from the election.
     * @param reportGenerateFlag Describes whether the Vote Counter should output the content
     *                           of the report into some file.
     * @param shuffleFeatureFlag Describe whether the Vote Counter should shuffle the ballots or not.
     *                           Testing Feature is on when Shuffle feature is off.
     */
    public STVVoteCounter(String inputFileName, int numOfSeatsToBeFilled, boolean reportGenerateFlag, boolean shuffleFeatureFlag) {
        this.inputFileName = inputFileName;
        this.allCandidatesRunningInElection = new HashMap<>();
        this.candidatesRunningInElection = new HashMap<>();
        this.numOfCandidatesRunningInElection = 0;
        this.electionBallots = new ArrayList<>();
        this.numOfSeatsToBeFilled = numOfSeatsToBeFilled;
        this.droopQuota = 0;
        this.winnerCandidates = new ArrayList<>();
        this.droppedCandidates = new ArrayList<>();
        this.reportContent = new StringBuilder();
        this.reportGenerateFlag = reportGenerateFlag;
        this.shuffleFeatureFlag = shuffleFeatureFlag;
    }

    /**
     * Overloading the constructor to marks the report generate flag and shuffle feature flag have default values.
     * @param inputFileName Name of the input file which contains the data about the election.
     *                      This filename should be a relative path to the file.
     * @param numOfSeatsToBeFilled Number of winners required from the election.
     */
    Scanner sc = new Scanner(System.in);
    System.out.println("Do you want to generate reports? Yes - 1 // No - 2");
    int reportFlaginput = sc.nextInt();
    if(reportFlaginput==2) {
    System.out.println("Do you want the shuffle On(1) or off(2)?");
    int shuffleFlaginput = sc.nextInt();
    if(shuffleFlaginput == 1) {
    public STVVoteCounter(String inputFileName, int numOfSeatsToBeFilled) {
        this(inputFileName, numOfSeatsToBeFilled, false, true);
    }
}
else if(shuffleFlaginput==2) {
        public STVVoteCounter(String inputFileName, int numOfSeatsToBeFilled) {
        this(inputFileName, numOfSeatsToBeFilled, false, false);
    }
}
}
else if(reportFlaginput==1) {
        System.out.println("Do you want the shuffle On(1) or off(2)?");
    int shuffleFlaginput = sc.nextInt();
    if(shuffleFlaginput == 1) {
    public STVVoteCounter(String inputFileName, int numOfSeatsToBeFilled) {
        this(inputFileName, numOfSeatsToBeFilled, true, true);
    }
}
else if(shuffleFlaginput==2) {
        public STVVoteCounter(String inputFileName, int numOfSeatsToBeFilled) {
        this(inputFileName, numOfSeatsToBeFilled, true, false);
    }
}
}

    /**
     * Returns the Hash Map of all the candidates involved in the election.
     * @return HashMap{Long, STVCandidate} - Hash Map that contains all the candidates
     *                                       that were ever involved in the election.
     */
    public HashMap<Long, STVCandidate> getAllCandidatesRunningInElection() {
        return allCandidatesRunningInElection;
    }

    /**
     * Loads the information about each and every candidate running in the election.
     * This function populates the hash map of candidates. This hash map member variable
     * maps IDs of candidates to their respective objects.
     */
    private void loadCandidatesFromInputFile() {
        BufferedReader buffer;
        String fileLine;
        try {
            buffer = new BufferedReader(new FileReader(this.inputFileName));
            // This variable is used to check whether the file is empty or not.
            // Also this variable helps to make sure that we just read one line (first line) from the file.
            int checkForReadOneLine = 0;
            while (checkForReadOneLine < 1 && (fileLine = buffer.readLine()) != null) {
                // String array includes all candidate names at different indices.
                String[] candidateNameArray = fileLine.split(",");
                for (int candidateId = 0; candidateId < candidateNameArray.length; candidateId++) {
                    // Adding the candidate objects into the hash maps.
                    this.allCandidatesRunningInElection.put((long) candidateId, new STVCandidate((long) candidateId, candidateNameArray[candidateId]));
                    this.candidatesRunningInElection.put((long) candidateId, new STVCandidate((long) candidateId, candidateNameArray[candidateId]));
                }
                checkForReadOneLine++;
                // determining the number of candidates running in the election.
                numOfCandidatesRunningInElection = candidateNameArray.length;
            }
            // Condition below will be true if the while loop terminates because there is
            // nothing present in the file (empty file).
            if (checkForReadOneLine == 0) {
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
     * Loads the candidate id's in order of their preferences (input from ballotArray)
     * to the ballot object.
     * @param ballotArray String array of the input line from file separated by ",".
     *                    Each index contains either spaces (denoting no choice made)
     *                    or empty string or some number denoting the preference of the
     *                    corresponding candidate.
     * @param newBallot Ballot object that needs to be loaded with Candidate IDs in order
     *                  of their preference
     */
    private void addCandidateIDsInOrderOfPreference(STVBallot newBallot, String[] ballotArray) {
        // variable that represents the number of candidates for which preferences were given in the ballot.
        int noForWhichVoted = 0;
        // iterate through the strings in ballotArray and check whether they are not blank.
        for (String preferenceNumberOfCandidate : ballotArray) {
            if (!preferenceNumberOfCandidate.isEmpty() && !preferenceNumberOfCandidate.trim().isEmpty()) {
                noForWhichVoted++;
            }
        }
        // this loop iterates through the ballot Array until it gets the corresponding candidate ID of
        // each and every candidate for which there is a vote assigned in the ballot. Then this
        // loop adds the candidate ID to the ballots in order (first add the ID of the candidate
        // marked as 1 preference and so on until last preference)
        for(int preferenceNumber = 0; preferenceNumber < noForWhichVoted; preferenceNumber++) {
            int candidateId = 0;
            boolean loopBreak = false;
            // find the actual candidate id corresponding to the particular preference.
            while(candidateId < ballotArray.length && !loopBreak) {
                if (!ballotArray[candidateId].isEmpty() && !ballotArray[candidateId].trim().isEmpty() && Integer.parseInt(ballotArray[candidateId]) == preferenceNumber+1) {
                    // add the candidate ID to the ballot.
                    newBallot.addCandidateID(candidateId);
                    // break off the loop as soon as you find the correct candidate ID.
                    loopBreak = true;
                }
                candidateId++;
            }
        }
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
        try {
            buffer = new BufferedReader(new FileReader(this.inputFileName));
            // Skipping first line, because first line contains information
            // about the candidates engaged in the election.
            if (buffer.readLine() == null) {
                System.out.println("File is empty!");
                return;
            }
            int ballotSerialNumber = 1;
            // Loop through all other lines in the file and parse the information and add new ballots accordingly.
            while ((fileLine = buffer.readLine()) != null) {
                // ballotArray represents the array of strings after the line in file is split using the "," as a delimiter.
                String[] ballotArray = fileLine.split(",");
                STVBallot newBallot;
                // First generate a STVBallot type object with a particular unique serial number.
                newBallot = new STVBallot(ballotSerialNumber, ballotSerialNumber);
                // Then load the ballots with the IDs of the candidates in order of their preference.
                addCandidateIDsInOrderOfPreference(newBallot, ballotArray);
                // Add the ballots into the list of ballots.
                this.electionBallots.add(newBallot);
                ballotSerialNumber += 1;
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
     * Sets the droop quota for the election. Uses the predefined formula
     * as stated in the writeup to set the droopQuota of a particular election.
     */
    private void setDroopQuota() {
        this.droopQuota = (int) (Math.floor(this.electionBallots.size()/(this.numOfSeatsToBeFilled + 1)) + 1);
    }

    /**
     * Modifies the order number of all the ballots to some new order number
     * which is representative of how the ballots would be extracted from the
     * list of ballots after they have been shuffled.
     */
    private void setBallotOrderNumbersAfterShuffling() {
        int currentOrderNumber = 1;
        for (STVBallot ballot : this.electionBallots) {
            ballot.setBallotOrderNo(currentOrderNumber);
            currentOrderNumber += 1;
        }
    }

    /**
     * Reset all the ballots associated with all the candidates
     * running in election. This is done again and again to
     * redistribute the ballots still left in the election.
     *
     * This function generally clears the ballots of the candidates
     * remaining in the election after a pass through all the ballots.
     * These candidates denote those candidates who have not been declared
     * as a winner or dropped and this will be participating in another pass
     * through the ballots.
     */
    private void resetBallotsAssignedToCandidate() {
        for (STVCandidate candidate : this.candidatesRunningInElection.values()) {
            candidate.resetAllBallotsOfCandidate();
        }
    }

    /**
     * Returns the first available candidate (status undeclared - not winner or dropped)
     * in the voting based on the preferences marked by ballot.
     * @param candidateIDList The list of Candidate IDs in order of preference.
     *                        This list is taken from the ballot.
     * @return STVCandidate - Returns the first available candidate which
     *                        matches the candidate ID in the list.
     */
    private STVCandidate getCandidate(ArrayList<Long> candidateIDList) {
        // Iterate through the all the IDs of the candidate.
        for(long candidateId : candidateIDList) {
            // because the list is in order so find the first available candidate
            // still running in the election and return it.
            if (this.candidatesRunningInElection.containsKey(candidateId)) {
                return this.candidatesRunningInElection.get(candidateId);
            }
        }
        // if all the candidates for which the preferences were given have already
        // been either declared winner or dropped then just return null.
        return null;
    }

    /**
     * Redistribute the ballots of the dropped candidate. Because the candidate to
     * which the ballots belong to have been dropped they are no longer running
     * in the election and thus this function takes advantage of this fact and distributes
     * the ballot vote to next available candidate.
     * @param droppedCandidate Candidate dropped most recently from the election.
     */
    private void redistributeDroppedCandidatesVotes(STVCandidate droppedCandidate) {
        for (STVBallot ballotOfDroppedCandidate : droppedCandidate.getAllBallotsOfCandidate()) {
            // Find the candidate which is second preference after the dropped candidate and is still running in the elction.
            STVCandidate laterPreferenceCandidate = getCandidate(ballotOfDroppedCandidate.getCandidateIDS());
            // Check whether there exists such a candidate.
            if (laterPreferenceCandidate != null) {
                // Write the ballot report corresponding to the ballot.
                writeBallotReport(ballotOfDroppedCandidate, laterPreferenceCandidate);
                // Add the ballot to the corresponding candidate.
                laterPreferenceCandidate.addBallot(ballotOfDroppedCandidate);
                // check if the Current candidate has reached droop and declare the winner accordingly.
                if (laterPreferenceCandidate.getNumberOfVotesEarnedByCandidate() == this.droopQuota) {
                    declareWinner(laterPreferenceCandidate);
                }
            }
        }
    }

    /**
     * Add the candidate to the winners list and remove them from the election,
     * Remove from Hash Map of candidatesRunningInElection.
     * @param winnerCandidate Winner candidate of a particular pass through ballots.
     */
    private void declareWinner(STVCandidate winnerCandidate)
    {
        // First generate the report content for the Winner Candidate.
        writeWinnerCandidateReport(winnerCandidate);
        // Credit all the ballots belonging to the Winner Candidate.
        // Will not be used in further passes through ballots.
        winnerCandidate.creditAllBallotsOfCandidate();
        // Add the winner candidate to the list of winner candidates.
        this.winnerCandidates.add(winnerCandidate);
        // Remove from the Hash Map of candidates still running in election.
        this.candidatesRunningInElection.remove(winnerCandidate.getCandidateID());
    }

    /**
     * Distributes all the active election ballots to appropriate candidates
     * which are still running in election.
     *
     * Active election ballots are all those ballots that have not been credited.
     * These ballots generally belong to the candidates not declared winner.
     */
    private void distributeBallots() {
        // Go through all the ballots that were ever cast in the election.
        for (STVBallot electionBallot : this.electionBallots) {
            // Make sure the ballot doesn't belong to a winner candidate.
            if (!electionBallot.getCredited()) {
                // Get the candidate to which this ballot should be assigned.
                STVCandidate currentCandidate = getCandidate(electionBallot.getCandidateIDS());
                // Check that the candidate actually is still running in the election.
                if (currentCandidate != null) {
                    // Generate the report about the ballot.
                    writeBallotReport(electionBallot, currentCandidate);
                    // Add the ballot to the corresponding candidate.
                    currentCandidate.addBallot(electionBallot);
                    // Check if the current candidate has reached droop and declare them winner accordingly.
                    if (currentCandidate.getNumberOfVotesEarnedByCandidate() == this.droopQuota) {
                        declareWinner(currentCandidate);
                    }
                }
            }
        }
    }

    /**
     * Drops a candidate in the election after one pass through all the election ballots.
     * Is also responsible to check whether there is a tie and call appropriate methods
     * to resolve that.
     */
    private void dropCandidateFromElection() {
        List<STVCandidate> candidatesRunningInElection = new ArrayList<>(this.candidatesRunningInElection.values());
        // Find the minimum number of votes a candidate has among all the candidates
        // after one pass through the ballots.
        int minimumNumberOfVotes = Collections.min(candidatesRunningInElection).getNumberOfVotesEarnedByCandidate();
        List<STVCandidate> tieCandidateList = new ArrayList<>();
        // Iterate through all the candidates running in election and find the candidates with the least number of votes.
        for (STVCandidate candidate : candidatesRunningInElection) {
            // Populate the tie candidate list accordingly.
            if (candidate.getNumberOfVotesEarnedByCandidate() == minimumNumberOfVotes) {
                tieCandidateList.add(candidate);
            }
        }
        // If there is no tie (Only one candidate with the least number of votes)
        if (tieCandidateList.size() == 1) {
            // First add the report content about the dropped candidate.
            writeDroppedCandidateReport(tieCandidateList.get(0));
            // Add the corresponding candidate to the list of dropped candidates.
            this.droppedCandidates.add(tieCandidateList.get(0));
            // Drop the candidate from the Hash Map of candidates still running in the election.
            this.candidatesRunningInElection.remove(tieCandidateList.get(0).getCandidateID());
            // Re distribute the ballots of the dropped candidate.
            redistributeDroppedCandidatesVotes(tieCandidateList.get(0));
        } else {    // There is Tie if there exist more then one candidate with the least number of votes.
            // Resolve the tie by calling the appropriate method.
            resolveElectionTie(tieCandidateList);
        }
    }

    /**
     * Resolve a tie among the candidates that need to be dropped from the election.
     *
     * A tie condition holds when multiple candidates have the same number of votes and
     * the number of votes they have is the least among all candidates.
     *
     * To resolve the tie first find the candidate which received the last vote in order
     * of parsing through the election ballots. The candidate with the last order
     * number of the ballot is dropped.
     *
     * @param tieCandidateList List of candidates which are tied.
     */
    private void resolveElectionTie(List<STVCandidate> tieCandidateList) {
        int lastBallotOrderNo = 0;
        // Candidate object representing the candidate who will be dropped.
        STVCandidate candidateToBeDropped = null;
        // Iterate through all the candidates in the tie.
        for (STVCandidate candidateInATie : tieCandidateList) {
            int orderNo = candidateInATie.getOrderNoOfFirstBallotOfCandidate();
            // If the order number of first ballots assigned to the candidate is greater than the lastBallotOrderNo
            // then replace the value and determine the index of the candidate.
            if (lastBallotOrderNo < orderNo) {
                lastBallotOrderNo = orderNo;
                candidateToBeDropped = candidateInATie;
            }
        }
        // Add the content about the dropped candidate to the report content.
        if (candidateToBeDropped != null) {
            writeDroppedCandidateReport(candidateToBeDropped);
            // Add the candidate to the list of dropped candidates.
            this.droppedCandidates.add(candidateToBeDropped);
            // Drop the candidate from the election.
            this.candidatesRunningInElection.remove(candidateToBeDropped.getCandidateID());
            // Redistribute the votes of the dropped candidate.
            redistributeDroppedCandidatesVotes(candidateToBeDropped);
        }
    }

    /**
     * Implement voting algorithm (STV type) on the election (information loaded through the input file).
     * First loads all the candidates that are running in the election from the input file.
     * Then it load all the ballots cast in the election from the input file.
     * Then it iterates through all the ballots and aggregating the respective ballots' vote to
     * the particular candidate it is assigned to in order of preferences. After the function processes all the ballots
     * it determines the candidate to be dropped in each iteration and if possible a winner(s) candidate(s).
     * These iterations are repeated until the number of seats that need to be filled are filled with the winner candidates.
     */
    public void implementVoting() {
        // load all the candidates that are running in the election from the input file.
        loadCandidatesFromInputFile();
        // load all the ballots cast in the election from the input file.
        loadBallotsFromInputFIle();

        if (this.shuffleFeatureFlag) {
            // Shuffle all the ballots to ensure fair election.
            Collections.shuffle(this.electionBallots, new Random());
        }

        // Calculate the droop quota for the election.
        setDroopQuota();
        // Set the order number of each ballot after they have been shuffled.
        setBallotOrderNumbersAfterShuffling();

        // Repeat the process of distributing ballots to candidates and determining winner and
        // dropped candidates of a particular pass through the ballots, until all the required seats are filled.
        while (this.droppedCandidates.size() < this.numOfCandidatesRunningInElection - this.numOfSeatsToBeFilled) {
            //reset the ballots assigned to each candidate before distributing electionBallots.
            resetBallotsAssignedToCandidate();
            // Distribute the election ballots.
            distributeBallots();
            // Drop a candidate from the election.
            dropCandidateFromElection();
        }
        // Fill the required number of seats with the appropriate number of candidates.
        if (this.winnerCandidates.size() < this.numOfSeatsToBeFilled) {
            this.winnerCandidates.addAll(this.candidatesRunningInElection.values());
        }
        // Reversing the dropped candidate list to print the correct order of candidates.
        Collections.reverse(droppedCandidates);
        // Print the results of the election to the Standard Output.
        printElectionResults();
        if (this.reportGenerateFlag) {
            // Write the report to an output file.
            writeReportToFile();
        }
    }

    /**
     * Print the election results to the standard output (Terminal).
     */
    private void printElectionResults() {
        System.out.print("Droop Quota = ");
        System.out.println(this.droopQuota);
        System.out.println("Winner Candidates - ");
        for (STVCandidate winnerCandidate : this.winnerCandidates) {
            System.out.println(winnerCandidate);
        }
        System.out.println("Dropped Candidates - ");
        for (STVCandidate droppedCandidate : this.droppedCandidates) {
            System.out.println(droppedCandidate);
        }
    }

    /**
     * Adds the content to reportcontent member variable about particular
     * ballot that was assigned to a candidate in the voting.
     * @param candidate The candidate to which the ballot was assigned.
     * @param ballot The current ballot for which the information needs to be added.
     */
    private void writeBallotReport(STVBallot ballot, STVCandidate candidate)
    {
        reportContent.append("\nBallot No. ");
        reportContent.append(ballot.getBallotSerialNo());
        reportContent.append(" is assigned to Candidate - ");
        reportContent.append(candidate.getCandidateName());
        reportContent.append("\n---------------");
    }

    /**
     * Adds the content to reportcontent member variable about particular
     * candidate that was declared as a winner.
     * @param winnerCandidate The candidate which was declared winner.
     */
    private void writeWinnerCandidateReport(STVCandidate winnerCandidate)
    {
        reportContent.append("\nCandidate - ");
        reportContent.append(winnerCandidate.getCandidateName());
        reportContent.append(" is a winner!");
        reportContent.append("\n---------------");
    }

    /**
     * Adds the content to reportcontent member variable about particular
     * candidate that was declared as a dropped candidate.
     * @param droppedCandidate The candidate which was declared dropped.
     */
    private void writeDroppedCandidateReport(STVCandidate droppedCandidate)
    {
        reportContent.append("\nCandidate - ");
        reportContent.append(droppedCandidate.getCandidateName());
        reportContent.append(" has been dropped!");
        reportContent.append("\n---------------");
    }

    /**
     * Function responsible to write all the data of the report to an output file.
     */
    private void writeReportToFile()
    {
        File file = new File("Project 1 Waterfall Report.txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(reportContent.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }

    }
}
