import java.util.ArrayList;
/**
 * <h1>PluralityCandidate</h1>
 * PluralityCandidate - is a class that is responsible to hold the
 * information about a candidate engaged in Plurality Based Voting.
 * The class is responsible to hold information like ID, name, select status,
 * and information about the ballots that are assigned to the candidate.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public class PluralityCandidate implements Candidate, Comparable<PluralityCandidate>  {
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
    private ArrayList<PluralityBallot> candidateBallots;

    /**
     * Creates a new PluralityCandidate object that represents the
     * Candidate engaged in Plurality type voting.
     * @param candidateID The unique id of the candidate
     * @param candidateName The name of the candidate
     */
    public PluralityCandidate(long candidateID, String candidateName) {
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
     * Adds a ballot to the the list of ballots assigned to candidate. This ballots is
     * determined to be assigned to the candidate by the vote counter.
     * @param inputBallot ballot that is voted for the particular candidate
     */
    public void addBallot(PluralityBallot inputBallot) {
        this.candidateBallots.add(inputBallot);
    }

    /**
     * Overriding the compareTo function. Two candidates are compared on the basis of
     * number of votes they earned.
     * @return int - representing whether the current object of the candidate is
     *               less than, equal to or greater than the object passed as the parameter to the function.
     *               -1 represents current (this) object has less number of votes compared to the parameter candidate.
     *               1 represents current (this) object has more number of votes compared to the parameter candidate.
     *               0 represents current (this) object has equal number of votes compared to the parameter candidate.
     */
    @Override
    public int compareTo(PluralityCandidate pluralityCandidate) {
        return Integer.compare(this.getNumberOfVotesEarnedByCandidate(), pluralityCandidate.getNumberOfVotesEarnedByCandidate());
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
