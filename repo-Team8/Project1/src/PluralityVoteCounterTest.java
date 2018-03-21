import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PluralityVoteCounterTest {

    private PluralityVoteCounter voteCounter;

    @Test
    void getWinnerCandidates() {
        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes4Candidates.csv", 1);
        voteCounter.implementVoting();
        assertEquals(voteCounter.getWinnerCandidates().get(0).getCandidateName(),"D");

        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality1000Votes10Candidates.csv", 1);
        voteCounter.implementVoting();
        assertEquals(voteCounter.getWinnerCandidates().get(0).getCandidateName(),"F");
    }

    @Test
    void loadCandidatesFromInputFile() {
        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes4Candidates.csv", 1);
        voteCounter.loadCandidatesFromInputFile();
        HashSet<String> candidate_names = new HashSet<>(Arrays.asList("A","B","C","D"));
        HashMap<Long, PluralityCandidate> candidates = voteCounter.getCandidatesRunningInElection();
        HashSet<String> candidateNames = new HashSet<>();
        for(PluralityCandidate c : candidates.values()) {
            candidateNames.add(c.getCandidateName());
        }
        assertEquals(candidate_names.equals(candidateNames),true);

        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes10Candidates.csv", 1);
        voteCounter.loadCandidatesFromInputFile();
        candidate_names = new HashSet<>(Arrays.asList("A","B","C","D","E","F","G","H","I","J"));
        candidates = voteCounter.getCandidatesRunningInElection();
        candidateNames = new HashSet<>();
        for(PluralityCandidate c : candidates.values()) {
            candidateNames.add(c.getCandidateName());
        }
        assertEquals(candidate_names.equals(candidateNames),true);

    }

    @Test
    void getVotedCandidateIDFromBallotArray() {
        String[] line = {"2","3","1"};
        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes10Candidates.csv", 1);
        assertEquals(voteCounter.getVotedCandidateIDFromBallotArray(line),2);

        String[] line2 = {"5","2","3","1","4"};
        assertEquals(voteCounter.getVotedCandidateIDFromBallotArray(line2),3);
    }

    @Test
    void implementVoting() {
        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes10Candidates.csv", 1);
        voteCounter.implementVoting();
        assertEquals(voteCounter.getWinnerCandidates().get(0).getCandidateName(),"A");

        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality200Votes10Candidates.csv", 1);
        voteCounter.implementVoting();
        assertEquals(voteCounter.getWinnerCandidates().get(0).getCandidateName(),"I");
    }

    @Test
    void hasTie() {
        //Give a tie file, assert its not 0
        //No tie file, assert 0
        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes10CandidatesTie.csv", 1);
        voteCounter.implementVoting();
        assertNotEquals(voteCounter.electionHasTie(),0);

        voteCounter = new PluralityVoteCounter("../testing/testingCSVFiles/Plurality50Votes10Candidates.csv", 1);
        voteCounter.implementVoting();
        assertEquals(voteCounter.electionHasTie(),0);
    }

}