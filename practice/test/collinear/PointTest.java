import com.sg.sort.InsertionSort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {

    @Test
    void testWithPoints_thenCallCompareTo_returnsCorrectValue() {
        final Point p00 = new Point(0, 0);
        final Point p00_ = new Point(0, 0);
        final Point p01 = new Point(0, 1);
        final Point p10 = new Point(1, 0);
        final Point p11 = new Point(1, 1);

        assertEquals(0, p00.compareTo(p00));
        assertEquals(0, p00.compareTo(p00_));

        assertEquals(-1, p00.compareTo(p01));
        assertEquals(-1, p00.compareTo(p10));
        assertEquals(-1, p10.compareTo(p11));

        assertEquals(1, p10.compareTo(p00));
        assertEquals(1, p01.compareTo(p00));
        assertEquals(1, p11.compareTo(p01));
    }

    @Test
    void testWithPoints_thenCallSlopeTo_returnsCorrectValue() {
        final Point p00 = new Point(0, 0);
        final Point p01 = new Point(0, 1);
        final Point p10 = new Point(1, 0);
        final Point p23 = new Point(2, 3);

        assertEquals(Double.NEGATIVE_INFINITY, p00.slopeTo(p00));

        assertEquals(Double.POSITIVE_INFINITY, p00.slopeTo(p01));
        assertEquals(Double.POSITIVE_INFINITY, p01.slopeTo(p00));

        assertEquals(0, p00.slopeTo(p10));
        assertEquals(0, p10.slopeTo(p00));

        assertEquals(3.0 / 2.0, p00.slopeTo(p23));
        assertEquals(3.0 / 2.0, p23.slopeTo(p00));
    }

    @Test
    void testWithPoints_thenSortWithSlopeOrder_returnsCorrectOrder() {
        final Point[] points = {
                new Point(-1, -1),
                new Point(0, -1),
                new Point(1, -1),
                new Point(-1, 0),
                new Point(0, 0),
                new Point(1, 0),
                new Point(-1, 1),
                new Point(0, 1),
                new Point(1, 1),
        };
        final Point p0 = new Point(0, 0);
        InsertionSort.sort(points, p0.slopeOrder());

        assertEquals(Double.NEGATIVE_INFINITY, p0.slopeTo(points[0]));
        assertEquals(-1, p0.slopeTo(points[1]));
        assertEquals(-1, p0.slopeTo(points[2]));
        assertEquals(0, p0.slopeTo(points[3]));
        assertEquals(0, p0.slopeTo(points[4]));
        assertEquals(1, p0.slopeTo(points[5]));
        assertEquals(1, p0.slopeTo(points[6]));
        assertEquals(Double.POSITIVE_INFINITY, p0.slopeTo(points[7]));
        assertEquals(Double.POSITIVE_INFINITY, p0.slopeTo(points[8]));
    }

}