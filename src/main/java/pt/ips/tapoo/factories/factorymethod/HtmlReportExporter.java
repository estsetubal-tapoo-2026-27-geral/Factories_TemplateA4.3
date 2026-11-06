package pt.ips.tapoo.factories.factorymethod;

import pt.ips.tapoo.factories.documents.ReportDocument;

/** Stage 2. Concrete Creator for Html. */
public final class HtmlReportExporter extends ReportExporter {
    @Override
    protected ReportDocument createDocument() {
        // TODO FM2: create the corresponding concrete product.
        throw new UnsupportedOperationException("TODO FM2: implement Html product creation");
    }
}
