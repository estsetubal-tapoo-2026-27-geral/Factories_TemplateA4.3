package pt.ips.tapoo.factories.documents;

import java.util.Objects;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.rendering.ReportRendering;

/** Supplied concrete product for the Markdown format. */
public final class MarkdownReportDocument implements ReportDocument {
    @Override
    public String render(ReportData data) {
        Objects.requireNonNull(data, "data");
        String table = ReportRendering.markdownTable(data.results());
        return ReportRendering.markdownDocument(data, table);
    }
}
