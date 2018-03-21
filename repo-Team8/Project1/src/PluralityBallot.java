/**
 * <h1>PluralityBallot</h1>
 * PluralityBallot - is a class that abstracts the working of a ballot used in Plurality based Voting.
 * This type of ballot is responsible to store information about the serial number of the ballot,
 * candidate ID of the candidate to which the ballot is assigned,
 * credited status that determines whether the ballot has been used in the election or not.
 * The ballot order number doesn't matter in Plurality based Voting because it involves just calculating the total number of votes a candidate has
 * and thus in the class ballot order number is same as ballot serial number in all aspects.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public class PluralityBallot implements Ballot {
    /**
     * Holds the serial number of the ballot.
     * Serial Number of the Ballot is a unique identifier for the ballot.
     * Serial number is required when initializing the ballot hence is
     * required in the constructor as an input parameter.
     */
    private int ballotSerialNo;

    /**
     * Holds the ID of the candidate to which the ballot is assigned.
     * Candidate ID is required when initializing a ballot type object
     * hence is an input parameter in the constructor.
     */
    private long candidateID;

    /**
     * Holds the status of the ballot.
     * Depicts whether the ballot has been used in the election.
     */
    private boolean credited;

    /**
     * Creates a new PluralityBallot object that represents the
     * Ballot used in plurality type voting.
     * @param serialNo The serial number of the ballot.
     * @param candidateID The candidate ID of the candidate
     *                    to which this ballot is assigned.
     */
    public PluralityBallot(int serialNo, long candidateID) {
        this.ballotSerialNo = serialNo;
        this.candidateID = candidateID;
        this.credited = false;
    }

    @Override
    public int getBallotSerialNo() {
        return this.ballotSerialNo;
    }

    @Override
    public int getBallotOrderNo() {
        return this.ballotSerialNo;
    }

    @Override
    public void setBallotSerialNo(int ballotSerialNo) {
        this.ballotSerialNo = ballotSerialNo;
    }

    @Override
    public void setBallotOrderNo(int ballotOrderNo) {
        this.ballotSerialNo = ballotOrderNo;
    }

    /**
     * Returns the Candidate id of the candidate to which the ballot is assigned.
     * @return long - Id of a candidate.
     */
    public long getCandidateIdFromBallot() {
        return this.candidateID;
    }

    /**
     * Modifies the candidate ID to reflect a new candidate
     * to which this ballot is assigned.
     * @param candidateID new Candidate ID of the candidate
     */
    public void setCandidateIdForBallot(long candidateID) {
        this.candidateID = candidateID;
    }

    /**
     * Returns the credited status of the ballot.
     * @return boolean - Credit status of the ballot.
     */
    public boolean getCredited() {
        return this.credited;
    }

    /**
     * Modifies the credit status of the ballot.
     * @param newCredited New credit status of the ballot.
     */
    public void setCredited(boolean newCredited) {
        this.credited = newCredited;
    }

    /**
     * Overriding the toString method to print meaningful information
     * about the ballot.
     * @return String - String representation of ballot.
     */
    @Override
    public String toString() {
        String ballotInformation = "";
        ballotInformation += "Serial No: ";
        ballotInformation += Integer.toString(this.ballotSerialNo);
        ballotInformation += "\nOrder No: ";
        ballotInformation += Integer.toString(this.ballotSerialNo);
        return ballotInformation;
    }
}
