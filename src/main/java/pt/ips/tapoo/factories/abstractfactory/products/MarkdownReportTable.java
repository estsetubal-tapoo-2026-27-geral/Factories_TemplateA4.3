package pt.ips.tapoo.factories.abstractfactory.products;

import java.util.List;
import pt.ips.tapoo.factories.abstractfactory.ReportTable;
import pt.ips.tapoo.factories.model.StudentResult;
import pt.ips.tapoo.factories.rendering.ReportRendering;

/** Supplied concrete table in the Markdown family. */
public final class MarkdownReportTable implements ReportTable {
    @Override
    public String render(List<StudentResult> rows) {
        return ReportRendering.markdownTable(rows);
    }
}
