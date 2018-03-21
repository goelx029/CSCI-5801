/**
 * <h1>Ballot</h1>
 * Ballot - is an interface for the voting software which declares function names and parameters for the
 * two different types of ballot that will be used in the voting. The two types of ballots are:
 * <ul>
 *     <li> PluralityBallot - Specialised Ballot for handling Plurality based Voting </li>
 *     <li> STVBallot - Specialized Ballot for handling STV based Voting </li>
 * </ul>
 *
 * @author  Team 8 (Saksham Goel - goelx029 | Kunal Munjal - munja004 | Abhishek Sairavi - saira005)
 * @version 1.0
 * @since   2018-03-13
 *
 */
public interface Ballot {
    /**
     * Returns the serial number of a ballot. Serial number of a ballot is the unique identifier for the ballot.
     * @return int - Serial number of the ballot.
     */
    int getBallotSerialNo();

    /**
     * Modifies the serial number of a ballot the ballot to some other value as passed through the parameters.
     * @param ballotSerialNo New serial number of the ballot.
     */
    void setBallotSerialNo(int ballotSerialNo);

    /**
     * Returns the order number of a ballot. Order number represents the order in which this ballot was credited during the election.
     * @return int - Order number of the ballot.
     */
    int getBallotOrderNo();

    /**
     * Modified the order number of a ballot to some other value as passed through the parameters.
     * @param ballotOrderNo New order number of the ballot.
     */
    void setBallotOrderNo(int ballotOrderNo);
}
