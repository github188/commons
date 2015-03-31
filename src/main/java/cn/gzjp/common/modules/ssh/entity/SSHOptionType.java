package cn.gzjp.common.modules.ssh.entity;

import java.util.List;

public class SSHOptionType {
	private String logTypeKey;
	private String logTypeValue;
	private List<SSHServer> sshServerList;
	
	public String getLogTypeKey() {
		return logTypeKey;
	}
	public void setLogTypeKey(String logTypeKey) {
		this.logTypeKey = logTypeKey;
	}
	public String getLogTypeValue() {
		return logTypeValue;
	}
	public void setLogTypeValue(String logTypeValue) {
		this.logTypeValue = logTypeValue;
	}
	public List<SSHServer> getSshServerList() {
		return sshServerList;
	}
	public void setSshServerList(List<SSHServer> sshServerList) {
		this.sshServerList = sshServerList;
	}
	@Override
	public String toString() {
		return "SSHOptionType [logTypeKey=" + logTypeKey + ", logTypeValue="
				+ logTypeValue + ", sshServerList=" + sshServerList + "]";
	}
}
