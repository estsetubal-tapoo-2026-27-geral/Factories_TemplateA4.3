package pt.ips.tapoo.factories.factorymethod;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import pt.ips.tapoo.factories.documents.ReportDocument;
import pt.ips.tapoo.factories.model.ReportData;

/** Stage 2. Creator: owns the common export process. */
public abstract class ReportExporter {
    /** Writes UTF-8 content. The destination's parent directory must exist. */
    public void export(ReportData data, Path destination) throws IOException {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(destination, "destination");
        // TODO FM1: obtain the product through the factory method, render and write.
        throw new UnsupportedOperationException("TODO FM1: implement export(data, destination)");
    }

    /** Factory Method: subclasses decide the concrete product. */
    protected abstract ReportDocument createDocument();
}
