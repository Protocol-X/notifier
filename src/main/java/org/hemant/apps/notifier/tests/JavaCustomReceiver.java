package org.hemant.apps.notifier.tests;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.receiver.Receiver;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;

import org.hemant.apps.notifier.conf.ReadConfiguration;

@SuppressWarnings("deprecation")
public class JavaCustomReceiver extends Receiver<String> {
  /**
	 * 
	 */
	private static final long serialVersionUID = 6684029985777329626L;
	public static void main(String[] args) {
	  
	String master = "local[2]";
	JavaSparkContext sc = new JavaSparkContext(master, "StreamingLogInput");
    JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(5000));

    JavaReceiverInputDStream<String> lines = ssc.receiverStream(new JavaCustomReceiver());
    lines.print();
    ssc.start();
    ssc.awaitTermination();
  }

  public JavaCustomReceiver() {
    super(StorageLevel.DISK_ONLY());
  }

  public void onStart() {
    // Start the thread that receives data over a connection
    new Thread()  {
      @Override public void run() {
        receive();
      }
    }.start();
  }

  public void onStop() {
    // There is nothing much to do as the thread calling receive()
    // is designed to stop by itself isStopped() returns false
  }

  
  private void receive() {
    try {
      BufferedReader reader = null;
      String userInput = null;
	  ReadConfiguration rc =new ReadConfiguration();
	  DefaultHttpClient httpClient  = null;
      try {
        // connect to the server
    	  
  		String url      = "http://" + rc.getResourcemanagerhost() + ":" + rc.getResourcemanagerport() + "/ws/v1/cluster/apps";
  		
  		 try {
  				httpClient = new DefaultHttpClient();
  				HttpGet getRequest = new HttpGet(url);
  				getRequest.addHeader("accept", "application/json");

  				HttpResponse response = httpClient.execute(getRequest);

  				if (response.getStatusLine().getStatusCode() != 200) {
  					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
  				}

  				reader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
  			  } catch (ClientProtocolException e) {

  				e.printStackTrace();

  			  } catch (IOException e) {
  				e.printStackTrace();
  			  }
        // Until stopped or connection broken continue reading
        while (!isStopped() && (userInput = reader.readLine()) != null) {
          System.out.println("Received data '" + userInput + "'");
          //store(userInput);
        }
      } finally {
        reader.close();
			httpClient.close();
			httpClient.getConnectionManager().shutdown();
      }
      // Restart in an attempt to connect again when server is active again
      restart("Trying to connect again");
    } catch(ConnectException ce) {
      // restart if could not connect to server
      restart("Could not connect", ce);
    } catch(Throwable t) {
      restart("Error receiving data", t);
    }
  }
}