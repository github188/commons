package cn.gzjp.common.modules.ssh.entity;

public class SSHServer {
	private String serverName;
	private String serverIP;
	private String serverUser;
	private String serverPwd;
	private String serverLogDir;
	private String serverLogName;
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getServerUser() {
		return serverUser;
	}
	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}
	public String getServerPwd() {
		return serverPwd;
	}
	public void setServerPwd(String serverPwd) {
		this.serverPwd = serverPwd;
	}
	public String getServerLogDir() {
		return serverLogDir;
	}
	public void setServerLogDir(String serverLogDir) {
		this.serverLogDir = serverLogDir;
	}
	public String getServerLogName() {
		return serverLogName;
	}
	public void setServerLogName(String serverLogName) {
		this.serverLogName = serverLogName;
	}
	@Override
	public String toString() {
		return "SSHServer [serverName=" + serverName + ", serverIP=" + serverIP
				+ ", serverUser=" + serverUser + ", serverLogDir="
				+ serverLogDir + ", serverLogName=" + serverLogName + "]";
	}
	
}
