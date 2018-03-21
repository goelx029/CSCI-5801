import java.util.ArrayList;
/**
 * <h1>STVCandidate</h1>
 * STVCandidate - is a class that is responsible to hold the
 * information about a candidate engaged in STV Based Voting.
 * The class is responsible to hold information like ID, name, select status,
 * and information about the ballots that are assigned to the candidate.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public class STVCandidate implements Candidate, Comparable<STVCandidate>  {
    /**
     * Holds the unique ID of a candidate.
     * The ID is used to identify a candidate object.
     * This member variable is what defines a candidate hence is
     * required as an input parameter for the constructor.
     */
    private long candidateID;

    /**
     * Holds the name of a candidate.
     * Name of a candidate is important and is expected to be present in the first row
     * of the input file. The candidate name is also required when constructing a
     * candidate object hence is required as an input parameter to the constructor.
     */
    private String candidateName;

    /**
     * Holds the selected status of a candidate.
     * Selected status determines whether a candidate is declared a winner or not.
     */
    private boolean selected;

    /**
     * Holds all the ballots that are assigned to the candidate.
     * The candidate to which a ballot is assigned is determined by the
     * vote counter and then added to the candidate accordingly.
     */
    private ArrayList<STVBallot> candidateBallots;

    /**
     * Creates a new STVCandidate object that represents the
     * Candidate engaged in STV type voting.
     * @param candidateID The unique id of the candidate
     * @param candidateName The name of the candidate
     */
    public STVCandidate(long candidateID, String candidateName) {
        this.candidateID = candidateID;
        this.candidateName = candidateName;
        this.selected = false;
        this.candidateBallots = new ArrayList<>();
    }

    @Override
    public long getCandidateID() {
        return this.candidateID;
    }

    @Override
    public void setCandidateID(long newCandidateID) {
        this.candidateID = newCandidateID;
    }

    @Override
    public String getCandidateName() {
        return this.candidateName;
    }

    @Override
    public void setCandidateName(String newCandidateName) {
        this.candidateName = newCandidateName;
    }

    @Override
    public int getNumberOfVotesEarnedByCandidate() {
        return this.candidateBallots.size();
    }

    @Override
    public boolean getSelectStatus() {
        return this.selected;
    }

    @Override
    public void setSelectStatus(boolean selectStatus) {
        this.selected = selectStatus;
    }

    /**
     * Adds a STVBallot type object to the list of ballots for the candidate.
     * The ballots is assigned to the candidate who is still running in the
     * election and is the first available preference.
     * @param inputBallot ballot that is voted for the particular candidate.
     */
    public void addBallot(STVBallot inputBallot) {
        this.candidateBallots.add(inputBallot);
    }

    /**
     * Resets the ballots of the candidate.
     * Clears all the ballots that the candidate holds.
     */
    public void resetAllBallotsOfCandidate() {
        this.candidateBallots.clear();
    }

    /**
     * Mark all the ballots that the candidate has as been credited (used).
     * This is important to mark a distinction between ballots that should
     * not be used when the vote counter pass through ballots for another iteration.
     * These ballots are credited only when a certain candidate is declared winner.
     */
    public void creditAllBallotsOfCandidate() {
        for (STVBallot ballot : this.candidateBallots) {
            ballot.setCredited(true);
        }
    }

    /**
     * Returns the order number of the first ballot allocated to the candidate.
     * This order number helps to resolve Tie situations in the STV based voting.
     * @return int - order number of the first ballot.
     */
    public int getOrderNoOfFirstBallotOfCandidate() {
        return this.candidateBallots.get(0).getBallotOrderNo();
    }

    /**
     * Returns the List of ballots that are assigned to the candidate.
     * @return ArrayList{STVBallot} - Ballots associated to the candidate.
     */
    public ArrayList<STVBallot> getAllBallotsOfCandidate() {
        return this.candidateBallots;
    }

    /**
     * Overriding the compareTo function. Two candidates are compared on
     * the basis of number of votes they have earned.
     * @return int - representing whether the current object of the candidate is
     *               less than, equal to or greater than the object passed as the parameter to the function.
     *               -1 represents current (this) object has less number of votes compared to the parameter candidate.
     *               1 represents current (this) object has more number of votes compared to the parameter candidate.
     *               0 represents current (this) object has equal number of votes compared to the parameter candidate.
     */
    @Override
    public int compareTo(STVCandidate stvCandidate) {
        return Integer.compare(this.getNumberOfVotesEarnedByCandidate(), stvCandidate.getNumberOfVotesEarnedByCandidate());
    }

    /**
     * Overriding the toString method to print meaningful information about the candidate.
     * @return String - String representation of the candidate.
     */
    @Override
    public String toString() {
        String candidateInformation = this.getCandidateName();
        candidateInformation += " : ";
        candidateInformation += Integer.toString(this.getNumberOfVotesEarnedByCandidate());
        return candidateInformation;
    }
}
