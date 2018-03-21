import java.util.ArrayList;
/**
 * <h1>STVBallot</h1>
 * STVBallot - is a class that abstracts the working of a ballot used in STV based Voting
 * This type of ballot is responsible to store much more information as compared to PluralityBallot.
 * The information this ballot holds include serial number of ballot, order number of ballot, order of candidate
 * for which the ballot was voted, way to check whether the ballot has been used.
 * The most important information includes the order of candidates which is hold in the Array list of candidateIDs.
 * This member variable holds the id of the candidates in order of the preferences of candidates voted.
 *
 * Also this class makes a clear distinction between ballot serial and order number because ballots are shuffled
 * in STV based voting and they are given new order number based on the order in which they are extracted after
 * shuffling. This order number may or may not be equal to the serial number but both of them are useful in different
 * parts of voting. Serial number is used to identify a ballot, while the order number is used to resolve tie
 * between candidates. This is why they are kept as different member variables.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public class STVBallot implements Ballot {
    /**
     * Holds the serial number of the ballot.
     * Serial number uniquely identifies a ballot cast in election.
     */
    private int ballotSerialNo;

    /**
     * Holds the order number of the ballot.
     * Order number represents the relative number in which the ballot was
     * processed after all the ballots were shuffled.
     */
    private int ballotOrderNo;

    /**
     * Holds the ID of the candidate in the order of their preference.
     * Holding the order is really important because it involves extracting
     * the candidate IDs in order of preference made by the person using the ballot.
     */
    private ArrayList<Long> candidateIDS;

    /**
     * Holds the status of the ballot indicating whether the
     * ballot has been used in the voting or not.
     */
    private boolean credited;

    /**
     * Creates a new STVBallot object that represents the Ballot used in STV type voting.
     * @param ballotSerialNo The serial number of the ballot.
     * @param ballotOrderNo The order number of the ballot.
     */
    public STVBallot(int ballotSerialNo, int ballotOrderNo) {
        this.ballotSerialNo = ballotSerialNo;
        this.ballotOrderNo = ballotOrderNo;
        this.candidateIDS = new ArrayList<>();
        this.credited = false;
    }

    @Override
    public int getBallotSerialNo() {
        return this.ballotSerialNo;
    }

    @Override
    public int getBallotOrderNo() {
        return this.ballotOrderNo;
    }

    @Override
    public void setBallotSerialNo(int ballotSerialNo) {
        this.ballotSerialNo = ballotSerialNo;
    }

    @Override
    public void setBallotOrderNo(int ballotOrderNo) {
        this.ballotOrderNo = ballotOrderNo;
    }

    /**
     * Adds a new ID of the candidate (in order of preference) to the Array List of candidate IDs.
     * Preserving the order makes it easier to extract the preferred candidate from all the
     * candidates remaining in the election.
     * @param newcandidateID ID of a candidate running in election.
     */
    public void addCandidateID(long newcandidateID) {
        this.candidateIDS.add(newcandidateID);
    }

    /**
     * Returns the list of all the candidate IDs that the ballot corresponds to.
     * This list is maintained in an order of preferences for candidates.
     * @return ArrayList{Long} - List of all the candidate IDs.
     */
    public ArrayList<Long> getCandidateIDS() {
        return candidateIDS;
    }

    /**
     * Returns the credited status of the ballot.
     * @return boolean - credit status of the ballot.
     */
    public boolean getCredited() {
        return this.credited;
    }

    /**
     * Modifies the credit status of the ballot.
     * @param newCredited new credit status of the ballot.
     */
    public void setCredited(boolean newCredited) {
        this.credited = newCredited;
    }

    /**
     * Overriding the toString method to print meaningful information about the ballot.
     * @return String - String representation of the ballot.
     */
    @Override
    public String toString() {
        String ballotInformation = "";
        ballotInformation += "Serial No: ";
        ballotInformation += Integer.toString(this.ballotSerialNo);
        ballotInformation += "\nOrder No: ";
        ballotInformation += Integer.toString(this.ballotOrderNo);
        ballotInformation += "\nNumber of votes: ";
        ballotInformation += Integer.toString(this.candidateIDS.size());
        return ballotInformation;
    }
}
