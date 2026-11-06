package pt.ips.tapoo.factories.foundation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pt.ips.tapoo.factories.documents.HtmlReportDocument;
import pt.ips.tapoo.factories.documents.MarkdownReportDocument;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.ReportFormat;
import pt.ips.tapoo.factories.model.SampleData;
import pt.ips.tapoo.factories.model.StudentResult;
import static org.junit.jupiter.api.Assertions.*;

/** Tests of the supplied foundation. These pass before the activity starts. */
@Tag("foundation")
class FoundationTest {
    @TempDir Path temporaryDirectory;

    @Test
    void sampleDataHasTheExpectedSummary() {
        ReportData data = SampleData.classroom();
        assertEquals("TAPOO", data.courseName());
        assertEquals(3, data.studentCount());
        assertEquals(34.0 / 3, data.average(), 0.000001);
        assertEquals(List.of("Alice", "Bob", "Carol"),
                data.results().stream().map(StudentResult::studentName).toList());
    }

    @Test
    void reportDataProtectsItsCollection() {
        List<StudentResult> source = new ArrayList<>();
        source.add(new StudentResult("Alice", 15));
        ReportData data = new ReportData("TAPOO", source);
        source.clear();
        assertEquals(1, data.studentCount());
        assertThrows(UnsupportedOperationException.class, () -> data.results().clear());
    }

    @Test
    void emptyReportHasNoAverageToDisplay() {
        ReportData data = ReportData.empty("TAPOO");
        assertEquals(0, data.studentCount());
        assertEquals(0, data.average());
        assertTrue(new HtmlReportDocument().render(data).contains("Average: n/a"));
        assertTrue(new MarkdownReportDocument().render(data).contains("Average: n/a"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 21, Double.NaN, Double.POSITIVE_INFINITY})
    void invalidGradesAreRejected(double grade) {
        assertThrows(IllegalArgumentException.class, () -> new StudentResult("Alice", grade));
    }

    @Test
    void htmlEscapesNamesAndTitles() {
        ReportData data = new ReportData("A&B <Course>",
                List.of(new StudentResult("<Alice & Bob>", 15)));
        String html = new HtmlReportDocument().render(data);
        assertTrue(html.contains("A&amp;B &lt;Course&gt;"));
        assertTrue(html.contains("&lt;Alice &amp; Bob&gt;"));
        assertFalse(html.contains("<Alice & Bob>"));
    }

    @Test
    void markdownEscapesTableDelimiters() {
        ReportData data = new ReportData("TAPOO",
                List.of(new StudentResult("Alice | Bob", 15)));
        assertTrue(new MarkdownReportDocument().render(data).contains("Alice \\| Bob"));
    }

    @ParameterizedTest
    @EnumSource(ReportFormat.class)
    void baselineWritesACompleteReport(ReportFormat format) throws Exception {
        ReportData data = SampleData.classroom();
        Path file = temporaryDirectory.resolve("baseline." + format.extension());
        new pt.ips.tapoo.factories.baseline.ReportApplication().export(data, format, file);
        String content = Files.readString(file);
        assertTrue(content.contains("Alice"));
        assertTrue(content.contains("Carol"));
        assertTrue(content.contains("Average: 11.33"));
        assertTrue(content.contains("Students: 3"));
        assertTrue(format == ReportFormat.HTML
                ? content.startsWith("<!doctype html>") : content.startsWith("# TAPOO"));
    }

    @ParameterizedTest
    @EnumSource(ReportFormat.class)
    void suppliedAbstractProductsMatchTheOriginalProducts(ReportFormat format) {
        ReportData data = SampleData.classroom();
        // Deliberately instantiate supplied products, not the unfinished factories.
        String actual;
        String expected;
        if (format == ReportFormat.HTML) {
            String table = new pt.ips.tapoo.factories.abstractfactory.products.HtmlReportTable()
                    .render(data.results());
            actual = new pt.ips.tapoo.factories.abstractfactory.products.HtmlReportDocument()
                    .render(data, table);
            expected = new HtmlReportDocument().render(data);
        } else {
            String table = new pt.ips.tapoo.factories.abstractfactory.products.MarkdownReportTable()
                    .render(data.results());
            actual = new pt.ips.tapoo.factories.abstractfactory.products.MarkdownReportDocument()
                    .render(data, table);
            expected = new MarkdownReportDocument().render(data);
        }
        assertEquals(expected, actual);
    }
}
