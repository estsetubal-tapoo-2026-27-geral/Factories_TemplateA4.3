package pt.ips.tapoo.factories.factorymethod;

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
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.ReportFormat;
import pt.ips.tapoo.factories.model.SampleData;
import static org.junit.jupiter.api.Assertions.*;

/** Stage 2 acceptance tests. Activate with mvn -Pstage2 test. */
@Tag("stage2")
class FactoryMethodTest {
    @TempDir Path temporaryDirectory;

    @Test
    void htmlCreatorProvidesAnHtmlProduct() {
        assertInstanceOf(HtmlReportDocument.class, new HtmlReportExporter().createDocument());
    }

    @Test
    void markdownCreatorProvidesAMarkdownProduct() {
        assertInstanceOf(MarkdownReportDocument.class, new MarkdownReportExporter().createDocument());
    }

    @ParameterizedTest
    @EnumSource(ReportFormat.class)
    void commonProcessWritesBothFormats(ReportFormat format) throws Exception {
        ReportData data = SampleData.classroom();
        Path file = temporaryDirectory.resolve("method." + format.extension());
        ReportExporter exporter = format == ReportFormat.HTML
                ? new HtmlReportExporter() : new MarkdownReportExporter();
        exporter.export(data, file);
        String expected = format == ReportFormat.HTML
                ? new HtmlReportDocument().render(data) : new MarkdownReportDocument().render(data);
        assertEquals(expected, Files.readString(file));
    }

    @Test
    void commonProcessUsesTheOverriddenFactoryMethod() throws Exception {
        ReportData data = SampleData.classroom();
        Path file = temporaryDirectory.resolve("custom.txt");
        ReportExporter customExporter = new ReportExporter() {
            @Override
            protected ReportDocument createDocument() {
                return received -> {
                    assertSame(data, received);
                    return "Custom product: Patrícia";
                };
            }
        };
        customExporter.export(data, file);
        assertEquals("Custom product: Patrícia", Files.readString(file));
    }
}
