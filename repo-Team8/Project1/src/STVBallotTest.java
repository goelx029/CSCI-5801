import static org.junit.jupiter.api.Assertions.*;


class STVBallotTest {
    private STVBallot tester;

    @org.junit.jupiter.api.Test
    void getBallotNo() {
        tester = new STVBallot(11, 0);
        assertEquals(tester.getBallotSerialNo(),11,"Should be 11");

        tester = new STVBallot(9999, 0);
        assertEquals(tester.getBallotSerialNo(),9999,"Should be 9999");
    }

    @org.junit.jupiter.api.Test
    void setBallotNo() {
        tester = new STVBallot(11, 0);

        tester.setBallotSerialNo(12);
        assertEquals(tester.getBallotSerialNo(),12,"Should be 12");

        tester.setBallotSerialNo(99);
        assertEquals(tester.getBallotSerialNo(),99,"Should be 99");
    }

}