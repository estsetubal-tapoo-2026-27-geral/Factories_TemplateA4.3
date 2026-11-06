package pt.ips.tapoo.factories.abstractfactory;

import java.util.List;
import pt.ips.tapoo.factories.model.StudentResult;

/** Abstract Product B. Produces a reusable table in a family-specific syntax. */
public interface ReportTable {
    String render(List<StudentResult> rows);
}
