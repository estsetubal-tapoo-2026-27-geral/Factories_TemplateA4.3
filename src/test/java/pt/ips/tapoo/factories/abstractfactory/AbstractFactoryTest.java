package pt.ips.tapoo.factories.abstractfactory;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pt.ips.tapoo.factories.abstractfactory.products.HtmlReportDocument;
import pt.ips.tapoo.factories.abstractfactory.products.HtmlReportTable;
import pt.ips.tapoo.factories.abstractfactory.products.MarkdownReportDocument;
import pt.ips.tapoo.factories.abstractfactory.products.MarkdownReportTable;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.ReportFormat;
import pt.ips.tapoo.factories.model.SampleData;
import static org.junit.jupiter.api.Assertions.*;

/** Stage 3 acceptance tests. Activate with mvn -Pstage3 test. */
@Tag("stage3")
class AbstractFactoryTest {
    @Test
    void htmlFactoryProvidesACompleteHtmlFamily() {
        ReportFactory factory = new HtmlReportFactory();
        assertInstanceOf(HtmlReportDocument.class, factory.createDocument());
        assertInstanceOf(HtmlReportTable.class, factory.createTable());
    }

    @Test
    void markdownFactoryProvidesACompleteMarkdownFamily() {
        ReportFactory factory = new MarkdownReportFactory();
        assertInstanceOf(MarkdownReportDocument.class, factory.createDocument());
        assertInstanceOf(MarkdownReportTable.class, factory.createTable());
    }

    @ParameterizedTest
    @EnumSource(ReportFormat.class)
    void generatorBuildsReportsWithTheSelectedFamily(ReportFormat format) {
        ReportData data = SampleData.classroom();
        ReportFactory factory = format == ReportFormat.HTML
                ? new HtmlReportFactory() : new MarkdownReportFactory();
        String expected = format == ReportFormat.HTML
                ? new pt.ips.tapoo.factories.documents.HtmlReportDocument().render(data)
                : new pt.ips.tapoo.factories.documents.MarkdownReportDocument().render(data);
        assertEquals(expected, new ReportGenerator(factory).generate(data));
    }

    @Test
    void generatorUsesBothProductsSuppliedByItsFactory() {
        ReportData data = SampleData.classroom();
        ReportFactory customFactory = new ReportFactory() {
            @Override
            public ReportDocument createDocument() {
                return (received, tableContent) -> {
                    assertSame(data, received);
                    assertEquals("CUSTOM TABLE", tableContent);
                    return "Custom report for " + received.courseName() + ": " + tableContent;
                };
            }
            @Override
            public ReportTable createTable() {
                return rows -> {
                    assertEquals(data.results(), rows);
                    return "CUSTOM TABLE";
                };
            }
        };
        assertEquals("Custom report for TAPOO: CUSTOM TABLE",
                new ReportGenerator(customFactory).generate(data));
    }
}
