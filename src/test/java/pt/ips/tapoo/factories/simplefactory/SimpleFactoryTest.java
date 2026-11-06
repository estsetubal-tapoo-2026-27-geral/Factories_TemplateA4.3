package pt.ips.tapoo.factories.simplefactory;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pt.ips.tapoo.factories.documents.HtmlReportDocument;
import pt.ips.tapoo.factories.documents.MarkdownReportDocument;
import pt.ips.tapoo.factories.documents.ReportDocument;
import pt.ips.tapoo.factories.model.ReportFormat;
import pt.ips.tapoo.factories.model.SampleData;
import static org.junit.jupiter.api.Assertions.*;

/** Stage 1 acceptance tests. Activate with mvn -Pstage1 test. */
@Tag("stage1")
class SimpleFactoryTest {
    @TempDir Path temporaryDirectory;

    @ParameterizedTest
    @EnumSource(ReportFormat.class)
    void factorySelectsTheRequestedProduct(ReportFormat format) {
        ReportDocument document = ReportDocumentFactory.create(format);
        if (format == ReportFormat.HTML) {
            assertInstanceOf(HtmlReportDocument.class, document);
        } else {
            assertInstanceOf(MarkdownReportDocument.class, document);
        }
    }

    @ParameterizedTest
    @EnumSource(ReportFormat.class)
    void clientWritesTheRequestedReport(ReportFormat format) throws Exception {
        Path file = temporaryDirectory.resolve("simple." + format.extension());
        var data = SampleData.classroom();
        new ReportApplication().export(data, format, file);
        String expected = format == ReportFormat.HTML
                ? new HtmlReportDocument().render(data) : new MarkdownReportDocument().render(data);
        assertEquals(expected, Files.readString(file));
    }

    @Test
    void nullFormatIsRejected() {
        assertThrows(NullPointerException.class, () -> ReportDocumentFactory.create(null));
    }
}
