package pt.ips.tapoo.factories.abstractfactory;

/** Stage 3. Concrete Factory for the Markdown family. */
public final class MarkdownReportFactory implements ReportFactory {
    @Override
    public ReportDocument createDocument() {
        // TODO AF3: create the document belonging to this family.
        throw new UnsupportedOperationException("TODO AF3: implement Markdown document creation");
    }

    @Override
    public ReportTable createTable() {
        // TODO AF4: create the table belonging to this family.
        throw new UnsupportedOperationException("TODO AF4: implement Markdown table creation");
    }
}
