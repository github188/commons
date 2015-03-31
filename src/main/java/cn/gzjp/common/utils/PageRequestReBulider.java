package cn.gzjp.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 
 * @Description: TODO
 * @ClassName: PageRequestReBulider 
 * @author huangzy@gzjp.cn
 * @date 2014年11月10日 下午2:20:34
 */
public class PageRequestReBulider {
	/**
	 * 添加排序字段
	 * @param pageRequest
	 * @param field
	 * @param direction
	 * @return
	 */
	public static PageRequest getNewPageRequest(PageRequest pageRequest,String[] field,String[] direction){
		if(field.length==0||direction.length==0) return pageRequest;
		if(field.length!=direction.length) throw new IllegalArgumentException("排序字段与排序顺序个数必需相等");
		
		Sort sort = null;
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		for(int i=0;i<field.length;i++){
			orders.add(new Sort.Order(Direction.fromString(direction[i]), field[i]));
		}
		sort = new Sort(orders);
		
		PageRequest newPageRequest = null;
		if(pageRequest.getSort()==null){
			newPageRequest = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), sort);
		}else{
			pageRequest.getSort().and(sort);
			newPageRequest = pageRequest;
		}

		return newPageRequest;
	}
}
