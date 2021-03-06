import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        Arrays.sort(points);
        final List<LineSegment> list = new ArrayList<>();
        final int n = points.length;
        final Point[] tempPoints = new Point[n];

        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            tempPoints[i] = points[i];
            for (int j = i + 1; j < n; j++) {
                if (Double.NEGATIVE_INFINITY == points[i].slopeTo(points[j]))
                    throw new IllegalArgumentException();
            }
        }

        for (final Point point : points) {
            Arrays.sort(tempPoints, point.slopeOrder());
            for (int s = 1, e = s + 1; s < n; e++) {
                if (e == n || point.slopeTo(tempPoints[s]) != point.slopeTo(tempPoints[e])) {
                    if (e - s >= 3) {
                        final int count = e - s;
                        final Point[] lineSegmentPoints = new Point[count + 1];
                        for (int k = 0; k < count; k++) {
                            lineSegmentPoints[k] = tempPoints[s + k];
                        }
                        lineSegmentPoints[count] = point;
                        Arrays.sort(lineSegmentPoints);
                        if (Double.NEGATIVE_INFINITY == point.slopeTo(lineSegmentPoints[0])) {
                            list.add(new LineSegment(point, lineSegmentPoints[count]));
                        } else {
//                     StdOut.println("ignore");
                        }
                    }
                    s = e;
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
//      StdDraw.setXscale(0, 6);
//      StdDraw.setYscale(0, 6);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("Number of segments: " + collinear.numberOfSegments());
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