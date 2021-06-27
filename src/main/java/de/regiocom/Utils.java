package de.regiocom;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Processor;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class Utils {
    /**
     * Creates a strategy, which sets the additional body as a header on the original exchange.
     */
    public static AggregationStrategy headerEnricherStrategy(String headerName) {
        return (original, newExchange) -> {
            original.getIn().setHeader(headerName, newExchange.getIn().getBody());
            return original;
        };
    }

}
