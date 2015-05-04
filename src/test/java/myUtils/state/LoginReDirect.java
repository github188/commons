package myUtils.state;

/**
 * 
 * @Description: TODO
 * @ClassName: LoginReDirect 
 * @author huangzy@gzjp.cn
 * @date 2015年5月4日 上午10:46:49
 */
public interface LoginReDirect extends LoginState{
	/**跳转到其它url*/
	public void reDirect(LoginUnicomContext context) throws Exception;
}
