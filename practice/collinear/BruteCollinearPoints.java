import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        final List<LineSegment> list = new ArrayList<>();
        final int n = points.length;

        for (int _p = 0; _p < n; _p++) {
            final Point p = points[_p];
            if (p == null)
                throw new IllegalArgumentException();
            for (int _q = _p + 1; _q < n; _q++) {
                final Point q = points[_q];
                if (q == null || Double.NEGATIVE_INFINITY == p.slopeTo(q))
                    throw new IllegalArgumentException();
                final double slopePq = p.slopeTo(q);
                for (int _r = _q + 1; _r < n; _r++) {
                    final Point r = points[_r];
                    if (r == null || Double.NEGATIVE_INFINITY == p.slopeTo(r)
                            || Double.NEGATIVE_INFINITY == q.slopeTo(r))
                        throw new IllegalArgumentException();
                    final double slopePr = p.slopeTo(r);
                    if (slopePq != slopePr) continue;
                    for (int _t = _r + 1; _t < n; _t++) {
                        final Point t = points[_t];
                        if (t == null || Double.NEGATIVE_INFINITY == p.slopeTo(t)
                                || Double.NEGATIVE_INFINITY == q.slopeTo(t)
                                || Double.NEGATIVE_INFINITY == r.slopeTo(t))
                            throw new IllegalArgumentException();
                        final double slopePt = p.slopeTo(t);
                        if (slopePq == slopePt) {
                            final Point[] segmentPoints = {p, q, r, t};
                            Arrays.sort(segmentPoints);
                            list.add(new LineSegment(segmentPoints[0], segmentPoints[3]));
                        }
                    }
                }
            }
        }

        segments = new LineSegment[list.size()];
        list.toArray(segments);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments == null ? 0 : segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}