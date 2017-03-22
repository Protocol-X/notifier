package org.hemant.notifier.applerts;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class HTTPJSONStream implements SourceFunction<FlinkJSONObject> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int maxDelayMsecs;
    private final String urlLink;
    private transient boolean status;

    public HTTPJSONStream(String urlLink) {
        this(urlLink, 60);
    }

    public HTTPJSONStream(String urlLink, int maxEventDelaySecs) {
        if(maxEventDelaySecs < 0) {
            throw new IllegalArgumentException("Max event delay must be positive");
        }
        this.urlLink = urlLink;
        this.maxDelayMsecs = maxEventDelaySecs * 1000;
    }

    public void run(SourceContext<FlinkJSONObject> sourceContext) throws Exception {
        do {
            FlinkJSONObject fjo = new FlinkJSONObject(this.urlLink);
            sourceContext.collect(fjo);
            Thread.sleep(this.maxDelayMsecs); // maxDelayMsecs to be used here
            this.status=fjo.isAccessible;
        } while(this.status);
    }

    public void cancel() {
       this.status=false;
    }

}