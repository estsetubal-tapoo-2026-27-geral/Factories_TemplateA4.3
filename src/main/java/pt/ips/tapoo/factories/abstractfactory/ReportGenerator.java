package pt.ips.tapoo.factories.abstractfactory;

import java.util.Objects;
import pt.ips.tapoo.factories.model.ReportData;

/** Stage 3. Client: uses a factory and abstract products supplied by that factory. */
public final class ReportGenerator {
    private final ReportFactory factory;

    public ReportGenerator(ReportFactory factory) {
        this.factory = Objects.requireNonNull(factory, "factory");
    }

    public String generate(ReportData data) {
        Objects.requireNonNull(data, "data");
        // TODO AF5: obtain both products from factory and compose the report.
        throw new UnsupportedOperationException("TODO AF5: implement generate(data)");
    }
}
