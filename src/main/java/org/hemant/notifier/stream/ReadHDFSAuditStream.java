package org.hemant.notifier.stream;

import java.util.Properties;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import org.hemant.notifier.db.GetDBConnection;
import java.sql.Connection;
import java.sql.Statement;

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
        
		DataStream<String> messageStream = env.addSource(new FlinkKafkaConsumer010<>("hdfsauditstream", new SimpleStringSchema(), props));
		
		DataStream<AuditPOJO> auditStream = messageStream.rebalance().flatMap(
					new ReadAuditStream()).keyBy("cmd").filter(
							new FilterFunction<AuditPOJO>() {
								private static final long serialVersionUID = 1L;

								@Override
								public boolean filter(AuditPOJO arg0) throws Exception {
									// TODO Auto-generated method stub
									//* INFO FSNamesystem.audit: allowed* ugi=y* auth* ip=* cmd=*  src=* dst=* perm=* proto=* callerContext=*																																			
									return true;
								}
								
							}).name("Process-HDFS-Audit-Records");
//		StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
		auditStream.addSink(new SinkFunction<AuditPOJO>() {

			@Override
			public void invoke(AuditPOJO record) throws Exception {
				// TODO Auto-generated method stub
				Connection conn = null;

				conn = GetDBConnection.getConnection();
				Statement   stmt    = null;
				String query = "insert into hdfsaudit(logdate,allowed,ugi,auth,ip,cmd,src,dst,perm,proto) values (" +
						"'" +  record.getLogdate()	+ "'," +
						"'" +  record.getAllowed()  + "'," +
						"'" +  record.getUgi()      + "'," +
						"'" +  record.getAuth()     + "'," +
						"'" +  record.getIp()       + "'," +
						"'" +  record.getCmd()      + "'," +
						"'" +  record.getSrc()      + "'," +
						"'" +  record.getDst()      + "'," +
						"'" +  record.getPerm()     + "'," +
						"'" +  record.getProto()    + "'" +
						")";
				System.out.println(query);
				try{
                    stmt = conn.createStatement();
                    stmt.execute(query);
                    System.out.println("Inserted 1 log");
                }catch (Exception e){
                    //analyse_app_log.error("Load Phase : Error during inserting data"+e);
                	e.printStackTrace();
                }
				
			} }).name("Load-To-DB");
		auditStream.print();
		env.execute();
	}
	
    public static class ReadAuditStream extends RichFlatMapFunction<String, AuditPOJO> { 
    	 /**
		 * 
		 */
		private static final long serialVersionUID = -59267092950996431L;
		
		@Override
         public void flatMap(String value, Collector<AuditPOJO> out) throws Exception {
    		 AuditPOJO apj = new AuditPOJO();
				try{
						String []mainMessage = value.split("FSNamesystem.audit: ");
						String []auditMessage = mainMessage[1].split("\\s+");
						String []parser;
						
						apj.setLogdate(mainMessage[0].replace(",", ".").substring(0, 23).toString());
						parser = auditMessage[0].split("=");
						apj.setAllowed(parser[1]);							
						parser = auditMessage[1].split("=");
						apj.setUgi(parser[1]);						
						parser = auditMessage[2].replace("(", "").replace(")", "").replace(":", "=").split("=");
						apj.setAuth(parser[1]);						
						parser = auditMessage[3].split("=");
						apj.setIp(parser[1]);						
						parser = auditMessage[4].split("=");
						apj.setCmd(parser[1]);						
						parser = auditMessage[5].split("=");
						apj.setSrc(parser[1]);						
						parser = auditMessage[6].split("=");
						apj.setDst(parser[1]);						
						parser = auditMessage[7].split("=");
						apj.setPerm(parser[1]);						
						parser = auditMessage[8].split("=");
						apj.setProto(parser[1]);
				}
				catch (Exception e){		
					}
				out.collect(apj);
    	 }
    	 
    }
}
