import static org.junit.jupiter.api.Assertions.*;


class PluralityBallotTest {
    private PluralityBallot tester;

    @org.junit.jupiter.api.Test
    void getBallotNo() {
        tester = new PluralityBallot(12345, 0);
        assertEquals(tester.getBallotSerialNo(),12345,"Should be 12345");

        tester = new PluralityBallot(99999, 0);
        assertEquals(tester.getBallotSerialNo(),99999,"Should be 99999");
    }

    @org.junit.jupiter.api.Test
    void setBallotNo() {
        tester = new PluralityBallot(12345, 0);

        tester.setBallotSerialNo(123456);
        assertEquals(tester.getBallotSerialNo(),123456,"Should be 123456");

        tester.setBallotSerialNo(9999);
        assertEquals(tester.getBallotSerialNo(),9999,"Should be 9999");
    }

}