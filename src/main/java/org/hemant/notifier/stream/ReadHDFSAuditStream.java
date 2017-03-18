package org.hemant.notifier.stream;

import java.util.Properties;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;


public class ReadHDFSAuditStream {

	public static void main(String[] args) throws Exception {
		// create execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// parse user parameters
		
		Properties props = new Properties();
		props.setProperty("zookeeper.connect", "cloudsn.hadoop.com:2181"); // Zookeeper default host:port
		props.setProperty("bootstrap.servers", "cloudsn.hadoop.com:6667"); // Broker default host:port
        props.setProperty("serializer.class", "kafka.serializer.StringEncoder");
        props.setProperty("request.required.acks", "1");
		props.setProperty("auto.offset.reset", "earliest"); 
		props.setProperty("security.protocol", "SASL_PLAINTEXT");	
		props.setProperty("group.id","hadoop");
		
        System.setProperty("java.security.krb5.conf", "E:/krb5.conf");
        System.setProperty("java.security.auth.login.config", "E:/KafkaClient.jaas");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("sun.security.krb5.debug", "true");
        
		DataStream<String> messageStream = env.addSource(new FlinkKafkaConsumer010<>("kafkatopic", new SimpleStringSchema(), props));

		messageStream.rebalance().map(new MapFunction<String, String>() {
			private static final long serialVersionUID = -6867736771747690202L;

			@Override
			public String map(String value) throws Exception {
				return "Kafka and Flink says: " + value;
			}
		}).print();

		env.execute();
	}
}
