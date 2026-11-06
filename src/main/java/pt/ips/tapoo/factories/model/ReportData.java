package pt.ips.tapoo.factories.model;

import java.util.List;
import java.util.Objects;

/** Immutable input shared by all three variants of the application. */
public final class ReportData {
    private final String courseName;
    private final List<StudentResult> results;

    public ReportData(String courseName, List<StudentResult> results) {
        this.courseName = Objects.requireNonNull(courseName, "courseName").strip();
        if (this.courseName.isEmpty()) {
            throw new IllegalArgumentException("Course name must not be blank");
        }
        this.results = List.copyOf(Objects.requireNonNull(results, "results"));
    }

    /** Named creation method used in the introductory slides. */
    public static ReportData empty(String courseName) {
        return new ReportData(courseName, List.of());
    }

    public String courseName() { return courseName; }
    public List<StudentResult> results() { return results; }
    public int studentCount() { return results.size(); }

    /** Returns the arithmetic mean, or 0 for an empty collection. */
    public double average() {
        double sum = 0;
        for (StudentResult result : results) {
            sum += result.grade();
        }
        return results.isEmpty() ? 0 : sum / results.size();
    }
}
