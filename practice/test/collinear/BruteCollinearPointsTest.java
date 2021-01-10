import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteCollinearPointsTest {

    @Test
    void testCollinear_withSamePointCollinear_thenExceptionRaised() {
        final Point p0 = new Point(0, 0);
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(3, 3);
        final Point[] points = { p0, p0, p1, p2, p3};

        assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points));
    }

    @Test
    void testCollinear_withNullPointCollinear_thenExceptionRaised() {
        final Point p0 = new Point(0, 0);
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(3, 3);
        final Point[] points = { null, p0, p1, p2, p3};

        assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points));
    }

    @Test
    void testCollinear_with0LineCollinear_thenCorrectResultReturned() {
        final Point p0 = new Point(0, 0);
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(-3, 3);
        final Point[] points = { p0, p1, p2, p3};

        final BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        assertEquals(0, bruteCollinearPoints.numberOfSegments());
    }

    @Test
    void testCollinear_with1LineCollinear_thenCorrectResultReturned() {
        final Point p0 = new Point(0, 0);
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(3, 3);
        final Point[] points = { p0, p1, p2, p3};

        final BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        assertEquals(1, bruteCollinearPoints.numberOfSegments());
    }

    @Test
    void testCollinear_with2LinesCollinear_thenCorrectResultReturned() {
        final Point p0 = new Point(0, 0);
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(-1, -1);
        final Point p4 = new Point(-1, 1);
        final Point p5 = new Point(1, -1);
        final Point p6 = new Point(2, -2);
        final Point[] points = { p0, p1, p2, p3, p4, p5, p6};

        final BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        assertEquals(2, bruteCollinearPoints.numberOfSegments());
    }

}