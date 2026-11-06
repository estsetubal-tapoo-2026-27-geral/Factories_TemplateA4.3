package pt.ips.tapoo.factories.factorymethod;

import pt.ips.tapoo.factories.documents.ReportDocument;

/** Stage 2. Concrete Creator for Markdown. */
public final class MarkdownReportExporter extends ReportExporter {
    @Override
    protected ReportDocument createDocument() {
        // TODO FM3: create the corresponding concrete product.
        throw new UnsupportedOperationException("TODO FM3: implement Markdown product creation");
    }
}
