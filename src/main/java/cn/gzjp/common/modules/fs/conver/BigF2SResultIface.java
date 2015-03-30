package cn.gzjp.common.modules.fs.conver;

/**
 * 大文件分批读取
 * @Description: TODO
 * @ClassName: BigF2SResultIface 
 * @author huangzy@gzjp.cn
 * @date 2014年9月29日 下午1:39:31
 */
public interface BigF2SResultIface extends F2SResultIface{
	/**分批*/
	public boolean batche();
}
