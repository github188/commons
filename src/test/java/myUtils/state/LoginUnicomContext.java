package myUtils.state;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUnicomContext {
	
	private LoginState state;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public LoginUnicomContext(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public void doAction() throws Exception{
		doAction(this);
	}
	
	//暂时设为private
	private void doAction(LoginUnicomContext context) throws Exception{
		this.state.action(context);
	}
	
	public LoginUnicomContext setState(LoginState state) {
		this.state = state;
		
		return this;
	}
	
	public void setAndDoAction(LoginState state) throws Exception{
		setState(state).doAction();
	}
	
	public LoginState getState() {
		return state;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
}
