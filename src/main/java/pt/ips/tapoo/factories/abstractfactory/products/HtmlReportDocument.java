package pt.ips.tapoo.factories.abstractfactory.products;

import pt.ips.tapoo.factories.abstractfactory.ReportDocument;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.rendering.ReportRendering;

/** Supplied concrete document in the Html family. */
public final class HtmlReportDocument implements ReportDocument {
    @Override
    public String render(ReportData data, String tableContent) {
        return ReportRendering.htmlDocument(data, tableContent);
    }
}
