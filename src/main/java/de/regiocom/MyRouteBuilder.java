package de.regiocom;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.impl.DefaultCamelContext;

import static org.apache.camel.Exchange.*;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        getContext().addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://127.0.0.1:61616"));

        // here is a sample which processes the input files
        // (leaving them in place - see the 'noop' flag)
        // then performs content based routing on the message using XPath
        from("file:src/data?noop=true")
                // .enrich("direct:geocoder", Utils.headerEnricherStrategy("ziel"))
                // .log("${body}")
                // .log("Ziel: ${header.ziel}")
                // .to("file:dest");
                .to("activemq:bestellung");

        /*
        from("direct:geocoder")
                .setProperty("plz").jsonpath("$.adresse.plz")
                .setProperty("city").jsonpath("$.adresse.city")
                .log("PLZ: ${exchangeProperty.plz}, Stadt: ${exchangeProperty.city}")
                .setHeader(HTTP_PATH, simple("/geocode"))
                .setHeader(HTTP_QUERY, simple("FreeFormAdress=DE,${exchangeProperty.plz}%20${exchangeProperty.city},DE&MaxResponse=1"))
                .setHeader(HTTP_METHOD, simple("GET"))
                .to("jetty:http://openls.geog.uni-heidelberg.de?bridgeEndpoint=true")
                .log("${body}")
                .setBody(constant("9.0535531, 48.5236164"));
        */

        from("activemq:bestellung")
                .log("${body}");
    }

}
