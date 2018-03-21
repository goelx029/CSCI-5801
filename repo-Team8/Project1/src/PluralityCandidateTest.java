import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PluralityCandidateTest {

    PluralityCandidate candidate;

    @Test
    void getCandidateID() {
        candidate = new PluralityCandidate(12345, "Test");
        assertEquals(candidate.getCandidateID(),12345);

        candidate = new PluralityCandidate(9999, "Test");
        assertEquals(candidate.getCandidateID(),9999);
    }

    @Test
    void setCandidateID() {
        candidate = new PluralityCandidate(12345, "Test");
        candidate.setCandidateID(123456);
        assertEquals(candidate.getCandidateID(),123456);

        candidate.setCandidateID(9999);
        assertEquals(candidate.getCandidateID(),9999);
    }

    @Test
    void getName() {
        candidate = new PluralityCandidate(12345, "Test");
        assertEquals(candidate.getCandidateName(),"Test");

        candidate = new PluralityCandidate(12345, "Tester2");
        assertEquals(candidate.getCandidateName(),"Tester2");
    }

    @Test
    void setName() {
        candidate = new PluralityCandidate(12345, "Test");
        candidate.setCandidateName("NewTest");
        assertEquals(candidate.getCandidateName(),"NewTest");
    }

    @Test
    void getNumberOfVotes() {
        candidate = new PluralityCandidate(12345, "Test");
        assertEquals(candidate.getNumberOfVotesEarnedByCandidate(),0);

        candidate.addBallot(new PluralityBallot(123, 0));
        candidate.addBallot(new PluralityBallot(124, 0));
        candidate.addBallot(new PluralityBallot(125, 0));
        assertEquals(candidate.getNumberOfVotesEarnedByCandidate(),3);
    }

    @Test
    void getSelectStatus() {
        candidate = new PluralityCandidate(12345, "Test");
        assertEquals(candidate.getSelectStatus(),false);

        candidate.setSelectStatus(true);
        assertEquals(candidate.getSelectStatus(),true);
    }

    @Test
    void setSelectStatus() {
        candidate = new PluralityCandidate(12345, "Test");
        candidate.setSelectStatus(true);
        assertEquals(candidate.getSelectStatus(),true);
    }

    @Test
    void addBallot() {
        candidate = new PluralityCandidate(12345, "Test");
        candidate.addBallot(new PluralityBallot(123, 0));
        assertEquals(candidate.getNumberOfVotesEarnedByCandidate(),1);

        candidate.addBallot(new PluralityBallot(124, 0));
        candidate.addBallot(new PluralityBallot(125, 0));
        assertEquals(candidate.getNumberOfVotesEarnedByCandidate(),3);
    }

    @Test
    void compareTo() {
        candidate = new PluralityCandidate(12345, "Test");
        PluralityBallot ballot1 = new PluralityBallot(12345, 0);
        PluralityBallot ballot2 = new PluralityBallot(12345, 0);
        PluralityBallot ballot3 = new PluralityBallot(12345, 0);
        PluralityCandidate candidate2 = new PluralityCandidate(123,"Test2");
        candidate.addBallot(ballot1);
        candidate.addBallot(ballot2);
        candidate2.addBallot(ballot3);
        assertEquals(candidate.compareTo(candidate2),1);

    }

}