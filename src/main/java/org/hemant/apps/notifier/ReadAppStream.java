package org.hemant.apps.notifier;
import org.hemant.apps.notifier.conf.ReadConfiguration;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.Duration;

@SuppressWarnings("deprecation")
public class ReadAppStream {
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String []args){
		ReadConfiguration rc =new ReadConfiguration();	
		String master = "local[2]";
		JavaSparkContext sc = new JavaSparkContext(master, "StreamingLogInput");
		JavaStreamingContext jssc = new JavaStreamingContext(sc, new Duration(1000));
	    
		String url      = "http://" + rc.getResourcemanagerhost() + ":" + rc.getResourcemanagerport() + "/ws/v1/cluster/apps";		

			}
	
	}


