package org.hemant.notifier.stream;
public class AuditPOJO {
	String logdate	;
	String allowed	;
	String ugi		;
	String auth		;
	String ip		;
	String cmd		;
	String src		;
	String dst		;
	String perm		;
	String proto	;
	
	public AuditPOJO(){}
	
	public String getLogdate() {
		return logdate;
	}
	public void setLogdate(String logdate) {
		this.logdate = logdate;
	}
	public String getAllowed() {
		return allowed;
	}
	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}
	public String getUgi() {
		return ugi;
	}
	public void setUgi(String ugi) {
		this.ugi = ugi;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getPerm() {
		return perm;
	}
	public void setPerm(String perm) {
		this.perm = perm;
	}
	public String getProto() {
		return proto;
	}
	public void setProto(String proto) {
		this.proto = proto;
	}
	
	@Override
	public String toString() {
		return "AuditPOJO [logdate=" + logdate + ", allowed=" + allowed + ", ugi=" + ugi + ", auth=" + auth + ", ip="
				+ ip + ", cmd=" + cmd + ", src=" + src + ", dst=" + dst + ", perm=" + perm + ", proto=" + proto + "]";
	}
}
