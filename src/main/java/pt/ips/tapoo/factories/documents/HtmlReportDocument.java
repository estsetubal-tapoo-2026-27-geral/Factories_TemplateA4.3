package pt.ips.tapoo.factories.documents;

import java.util.Objects;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.rendering.ReportRendering;

/** Supplied concrete product for the Html format. */
public final class HtmlReportDocument implements ReportDocument {
    @Override
    public String render(ReportData data) {
        Objects.requireNonNull(data, "data");
        String table = ReportRendering.htmlTable(data.results());
        return ReportRendering.htmlDocument(data, table);
    }
}
