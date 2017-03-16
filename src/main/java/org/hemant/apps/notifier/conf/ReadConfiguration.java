package org.hemant.apps.notifier.conf;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfiguration {
	
	String resourcemanagerhost	;
	String resourcemanagerport	;
	String dbhost				;				
	String dbport				;				
	String dbdriverclass		;
	String dbname				;			
	String dbuser				;		
	String dbpassword			;		
	String log4j				;		
	String smtphost				;		
	String smtpport				;		
	String smtpsender			;		
	String alertcc				;		
	
	public String getResourcemanagerhost() 							{		return resourcemanagerhost;						}
	public void setResourcemanagerhost(String resourcemanagerhost) 	{		this.resourcemanagerhost = resourcemanagerhost;	}

	public String getResourcemanagerport() 							{		return resourcemanagerport;						}
	public void setResourcemanagerport(String resourcemanagerport) 	{		this.resourcemanagerport = resourcemanagerport;	}
	
	public String getDbhost() 										{		return dbhost;									}
	public void setDbhost(String dbhost) 							{		this.dbhost = dbhost;							}
	
	public String getDbport() 										{		return dbport;									}
	public void setDbport(String dbport) 							{		this.dbport = dbport;							}

	public String getDbdriverclass() 								{		return dbdriverclass;							}
	public void setDbdriverclass(String dbdriverclass) 				{		this.dbdriverclass = dbdriverclass;				}

	public String getDbname() 										{		return dbname;									}
	public void setDbname(String dbname) 							{		this.dbname = dbname;							}

	public String getDbuser() 										{		return dbuser;									}
	public void setDbuser(String dbuser) 							{		this.dbuser = dbuser;							}

	public String getDbpassword() 									{		return dbpassword;								}
	public void setDbpassword(String dbpassword) 					{		this.dbpassword = dbpassword;					}

	public String getLog4j() 										{		return log4j;									}
	public void setLog4j(String log4j) 								{		this.log4j = log4j;								}

	public String getSmtphost() 									{		return smtphost;								}
	public void setSmtphost(String smtphost) 						{ 		this.smtphost = smtphost; 						}

	public String getSmtpport() 									{ 		return smtpport; 								}
	public void setSmtpport(String smtpport) 						{ 		this.smtpport = smtpport; 						}

	public String getSmtpsender() 									{ 		return smtpsender; 								}
	public void setSmtpsender(String smtpsender) 					{ 		this.smtpsender = smtpsender; 					}

	public String getAlertcc() 										{ 		return alertcc; 								}
	public void setAlertcc(String alertcc) 							{ 		this.alertcc = alertcc; 						}


	@Override
	public String toString() {

		return "ReadConfiguration [resourcemanagerhost=" + resourcemanagerhost + "\n" + "resourcemanagerport="
				+ resourcemanagerport + "\n" + "dbhost=" + dbhost + "\n" + "dbport=" + dbport + "\n" + "dbdriverclass=" + dbdriverclass
				+ ", dbname=" + dbname + "\n" + "dbuser=" + dbuser + "\n" + "dbpassword=" + dbpassword + "\n" + "log4j=" + log4j
				+ ", smtphost=" + smtphost + "\n" + "smtpport=" + smtpport + "\n" + "smtpsender=" + smtpsender + "\n" + "alertcc="
				+ alertcc + "]";
	}

	public ReadConfiguration(){
		Properties prop = new Properties();
		try{
			prop.load(new FileInputStream("C:/Users/hedindi/workspace/eyes/config.ini"));
			this.setResourcemanagerhost(prop.getProperty("resourcemanagerhost").trim());
			this.setResourcemanagerport(prop.getProperty("resourcemanagerport").trim());
			this.setDbhost(prop.getProperty("dbhost").trim());				
			this.setDbport(prop.getProperty("dbport").trim());				
			this.setDbdriverclass(prop.getProperty("dbdriverclass").trim());
			this.setDbname(prop.getProperty("dbname").trim());		
			this.setDbuser(prop.getProperty("dbuser").trim());		
			this.setDbpassword	(prop.getProperty("dbpassword").trim());		
			this.setLog4j(prop.getProperty("log4j").trim());		
			this.setSmtphost(prop.getProperty("smtphost").trim());		
			this.setSmtpport(prop.getProperty("smtpport").trim());		
			this.setSmtpsender	(prop.getProperty("smtpsender").trim());		
			this.setAlertcc(prop.getProperty("alertcc").trim());		
						
			} catch(Exception e){
					e.printStackTrace();
				}
		}
}

