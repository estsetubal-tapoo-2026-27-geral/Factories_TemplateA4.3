package pt.ips.tapoo.factories.abstractfactory;

/** Stage 3. Concrete Factory for the Html family. */
public final class HtmlReportFactory implements ReportFactory {
    @Override
    public ReportDocument createDocument() {
        // TODO AF1: create the document belonging to this family.
        throw new UnsupportedOperationException("TODO AF1: implement Html document creation");
    }

    @Override
    public ReportTable createTable() {
        // TODO AF2: create the table belonging to this family.
        throw new UnsupportedOperationException("TODO AF2: implement Html table creation");
    }
}
