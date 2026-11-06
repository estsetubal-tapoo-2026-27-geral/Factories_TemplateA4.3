package pt.ips.tapoo.factories.simplefactory;

import java.util.Objects;
import pt.ips.tapoo.factories.documents.ReportDocument;
import pt.ips.tapoo.factories.model.ReportFormat;

/** Stage 1. Factory: centralises the choice and creation of a concrete product. */
public final class ReportDocumentFactory {
    private ReportDocumentFactory() { }

    /** Returns a product for a non-null format. */
    public static ReportDocument create(ReportFormat format) {
        Objects.requireNonNull(format, "format");
        // TODO SF1: select and create the product identified by format.
        throw new UnsupportedOperationException("TODO SF1: implement create(format)");
    }
}
