package pt.ips.tapoo.factories.model;

import java.util.List;

/** Small fictional dataset matching the lesson slides. */
public final class SampleData {
    private SampleData() { }

    public static ReportData classroom() {
        return new ReportData("TAPOO", List.of(
                new StudentResult("Alice", 15),
                new StudentResult("Bob", 11),
                new StudentResult("Carol", 8)));
    }
}
