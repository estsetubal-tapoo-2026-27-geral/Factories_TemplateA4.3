package pt.ips.tapoo.factories.simplefactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.ReportFormat;

/** Stage 1. Client: obtains products from ReportDocumentFactory. */
public final class ReportApplication {
    /** Writes UTF-8 content. The destination's parent directory must exist. */
    public void export(ReportData data, ReportFormat format, Path destination)
            throws IOException {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(format, "format");
        Objects.requireNonNull(destination, "destination");
        // TODO SF2: obtain the document from the factory, render and write the file.
        throw new UnsupportedOperationException("TODO SF2: implement the factory client");
    }
}
