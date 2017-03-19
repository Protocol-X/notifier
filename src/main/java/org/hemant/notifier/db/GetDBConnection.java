package org.hemant.notifier.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.flink.api.java.utils.ParameterTool;

public class GetDBConnection {
	private static Connection connection = null;
	
	public static void main(){
		@SuppressWarnings("unused")
		Connection cs = null;
		cs = getConnection();
		
	}
    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
            	//Properties parameter = new Properties();
                //InputStream inputStream = GetDBConnection.class.getClassLoader().getResourceAsStream();
                //parameter.load(inputStream);
                
                final ParameterTool parameter = ParameterTool.fromPropertiesFile("C:/Users/hemant.HEMANTDINDI/git/notifier/conf/config.ini");
                
                String  dbhost  = parameter.get("dbhost").toString().trim()          				;
                String  dbport  = parameter.get("dbport").toString().trim()                        	;
                String  dbclass = parameter.get("dbdriverclass").toString().trim()                 	;
                String  dbname  = parameter.get("dbname").toString().trim()                        	;
                String  dbuser  = parameter.get("dbuser").toString().trim()                        	;
                String  dbpwd   = parameter.get("dbpassword").toString().trim()                    	;
                String  dburl   = "jdbc:postgresql://"+dbhost+":"+dbport+"/"+dbname     			;
                
                Class.forName(dbclass);
                connection = DriverManager.getConnection(dburl, dbuser, dbpwd);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully connected...");
            return connection;
        }

    }
}

