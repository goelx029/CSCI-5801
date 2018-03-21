/**
 * <h1>Candidate</h1>
 * Candidate - is an interface for the voting system which declares function names and parameters for the
 * two different types of candidate classes that will be used in the voting. The two types of candidate classes are:
 * <ul>
 *     <li> PluralityCandidate - Specialised Candidate to store information for Plurality based Voting </li>
 *     <li> STVCandidate - Specialised Candidate to store information for STV based Voting </li>
 * </ul>
 * Candidates are expected to at least hold information like a unique ID, name, select status etc.
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public interface Candidate {
    /**
     * Returns the unique ID of the candidate.
     * @return long - Unique ID of the candidate.
     */
    public long getCandidateID();

    /**
     * Modifies the unique ID of the candidate to some other value as passed through the parameter to the function.
     * @param newCandidateID New ID of the candidate.
     */
    public void setCandidateID(long newCandidateID);

    /**
     * Returns the name of the candidate.
     * @return String - Name of the candidate.
     */
    public String getCandidateName();

    /**
     * Modifies the name of the candidate.
     * @param newCandidateName New name of the candidate.
     */
    public void setCandidateName(String newCandidateName);

    /**
     * Returns the number of votes the candidate has accumulated. This is calculated by
     * aggregating the number of ballots assigned to the particular candidate.
     * @return int - Number of votes earned by the candidate.
     */
    public int getNumberOfVotesEarnedByCandidate();

    /**
     * Returns the select status of a candidate.
     * Select status tells whether a candidate has been declared a winner or not.
     * @return boolean - Status of a candidate.
     */
    public boolean getSelectStatus();

    /**
     * Modifies the select status of the candidate.
     * @param status New status of the candidate.
     */
    public void setSelectStatus(boolean status);
}
