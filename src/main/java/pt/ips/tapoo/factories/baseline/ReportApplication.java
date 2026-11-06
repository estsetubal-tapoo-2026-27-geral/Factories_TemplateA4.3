package pt.ips.tapoo.factories.baseline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import pt.ips.tapoo.factories.documents.HtmlReportDocument;
import pt.ips.tapoo.factories.documents.MarkdownReportDocument;
import pt.ips.tapoo.factories.documents.ReportDocument;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.ReportFormat;

/** Executable starting point: the client selects and constructs concrete products. */
public final class ReportApplication {
    /** Writes UTF-8 content. The destination's parent directory must exist. */
    public void export(ReportData data, ReportFormat format, Path destination)
            throws IOException {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(format, "format");
        Objects.requireNonNull(destination, "destination");
        ReportDocument document;
        switch (format) {
            case HTML: document = new HtmlReportDocument(); break;
            case MARKDOWN: document = new MarkdownReportDocument(); break;
            default: throw new IllegalArgumentException("Unsupported format: " + format);
        }
        Files.writeString(destination, document.render(data));
    }
}
