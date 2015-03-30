package cn.gzjp.common.utils;


/**
 * 为原生sql添加where条件并设置条件值
 * @Description: TODO
 * @ClassName: BuidNativeSQL 
 * @author huangzy@gzjp.cn
 * @date 2015年1月4日 下午5:03:24
 */
public class BuidNativeSQL {
	/**
	 * 
	 * @param filters
	 * @param sql 1、若包含查询条件 原sql必需加 where 1=1;
	 * 			  2、原SQL的最后不能包含有group by、order by、limit等,因为新创建的sql直接在原sql后添加 and fieldName=val
	 * @param afterBuidSQL 生成sql后添加afterBuidSQL(如在原sql中有order by、group by等)
	 * @param converMap 前端页面属性与数据字段映射
	 * @param entityManager
	 * @param retClass
	 * @return
	 */
	/*public static Query buid(Map<String, SearchFilter> filters,	String sql,String afterBuidSQL,Map<String,String> converMap,
			EntityManager entityManager,Class retClass,ResultTransformer transformer){
		
		//添加where条件
		String newSql = buildSQL(filters,converMap, sql,afterBuidSQL);
		
		Query query = buildQuery(newSql,entityManager, retClass);
		query = setResultTransformer(query, transformer);
		
		//设置值
		setParameterFilter(filters,converMap, query);
		
		return query;
	}
	
	public static <T> Page<T> searchByPageUseNativeSQL(Map<String, Object> searchParams,Pageable pageable,Map<String,String> converMap,
			String selectListSQL,String groupBySql,EntityManager entityManager,Class retClass,ResultTransformer transformer){
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		int total = searchByCount(filters, converMap, selectListSQL, groupBySql, entityManager);
		
		Query selectListquery = buid(filters, selectListSQL,groupBySql,converMap,entityManager,retClass,transformer);
		Page<T> page = searchByPage(selectListquery, pageable, total);
		
		return page;
	}
	
	public static Query setResultTransformer(Query query,ResultTransformer transformer){
		ResultTransformer _newResultTransformer = (transformer==null)?Transformers.ALIAS_TO_ENTITY_MAP:transformer;
		query.unwrap(SQLQuery.class).setResultTransformer(_newResultTransformer);
		return query;
	}
	
	public static Query setDefaultResultTransformer(Query query){
		query.unwrap(SQLQuery.class).setResultTransformer(null);
		return query;
	}
	
	private static <T> Page<T> searchByPage(Query query,Pageable pageable,int total){
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		List<T> mapList = (List<T>)query.getResultList();
		Page<T> page = new PageImpl<T>(mapList,pageable,total);
		
		return page;
	}
	
	private static int searchByCount(Map<String, SearchFilter> filters,Map<String,String> converMap,
			String sql,String afterBuidSQL,EntityManager entityManager){
		
		String newSql = buildSQL(filters, converMap, sql,afterBuidSQL);
		newSql = " SELECT COUNT(*) count FROM ("+newSql+")t0";
		
		Query query = buildQuery(newSql,entityManager);
		//设置值
		setParameterFilter(filters,converMap, query);
		
		Map totalMap = (Map)query.getSingleResult();
		int total = ((BigInteger)totalMap.get("count")).intValue();
		
		return total;
	}
	
	private static Query buildQuery(String newSql,EntityManager entityManager){
		Query query = buildQuery(newSql, entityManager,null);
		query = setResultTransformer(query, null);
		return query;
	}
	
	private static Query buildQuery(String newSql,EntityManager entityManager,Class retClass){
		Query query = null;
		if(retClass==null){
			query = entityManager.createNativeQuery(newSql);
		}else{
			query = entityManager.createNativeQuery(newSql,retClass);
		}
		return query;
	}
	
	private static void setParameterFilter(Map<String, SearchFilter> filters,Map<String,String> converMap,Query query){
		for(Map.Entry<String, SearchFilter> entity : filters.entrySet()){
			SearchFilter filter = entity.getValue();
			String fieldName = filter.fieldName;
			
			String valMark = getValMark(filter.operator.name(),fieldName);
			
			Object newVal = filter.value;
			switch (filter.operator) {
				case LIKE:
					newVal = "%"+newVal+"%";
					query.setParameter(valMark, newVal);
					break;
				case OREQ:
					if(newVal instanceof String){
						String[] fieldNames = fieldName.split("&");
						String[] values = ((String)newVal).split("&");
						
						for(int i=0;i<fieldNames.length;i++){
							valMark = getValMark(filter.operator.name(),fieldNames[i]);
							
							query.setParameter(valMark, values[i]);
						}
					}
					break;
				default:
					query.setParameter(valMark, newVal);
					break;
			}
			
		}
	}
	
	private static String converField(Map<String,String> converMap,String fieldName){
		String newFieldName = fieldName;
		if(converMap!=null){
			String tmpFieldName = converMap.get(fieldName);
			if(!StringUtils.isBlank(tmpFieldName)){
				newFieldName = tmpFieldName;
			}
		}
		return newFieldName;
	}
	
	private static String buildSQL(Map<String, SearchFilter> filters,Map<String,String> converMap,String sql,String afterBuidSQL){
		String newSql = buildSQL(filters, converMap, sql);
		if(!StringUtils.isBlank(afterBuidSQL)){
			newSql = newSql +" "+afterBuidSQL;
		}
		return newSql;
	}
	
	private static String buildSQL(Map<String, SearchFilter> filters,Map<String,String> converMap,String sql){
		StringBuilder sb = new StringBuilder(sql);
		for(Map.Entry<String, SearchFilter> entity : filters.entrySet()){
			SearchFilter filter = entity.getValue();
			String fieldName = filter.fieldName;
			
			String newFieldName = converField(converMap, fieldName);
			String valMark = getValMark(filter.operator.name(),fieldName);
			
			Object value = filter.value;
			
			switch (filter.operator) {
				case EQ:
					sb.append(" AND ").append(newFieldName).append(" = ").append(":"+valMark);
					break;
				case LIKE:
					sb.append(" AND ").append(newFieldName).append(" LIKE ").append(":"+valMark);
					break;
				case GT:
					sb.append(" AND ").append(newFieldName).append(" > ").append(":"+valMark);
					break;
				case LT:
					sb.append(" AND ").append(newFieldName).append(" < ").append(":"+valMark);
					break;
				case GTE:
					sb.append(" AND ").append(newFieldName).append(" >= ").append(":"+valMark);
					break;
				case LTE:
					sb.append(" AND ").append(newFieldName).append(" <= ").append(":"+valMark);
					break;
				case OREQ://相对于原意有改动
					if(value instanceof String){
						String[] fieldNames = fieldName.split("&");
						String[] values = ((String)value).split("&");
						if(fieldNames.length!=values.length){
							throw new IllegalArgumentException("参数不正确,参考:{search_OREQ_fieldName1&fieldName2:val1&val2}");
						}
						
						sb.append(" AND( ");
						
						for(int i=0;i<values.length;i++){
							if(i!=0) sb.append(" OR ");
							
							newFieldName = converField(converMap, fieldNames[i]);
							valMark = getValMark(filter.operator.name(), fieldNames[i]);
							
							sb.append(newFieldName).append(" = ").append(":"+valMark);
						}
						sb.append(" ) ");
					}
					break;
				case NOTEQ :
					sb.append(" AND ").append(newFieldName).append(" != ").append(":"+valMark);
					break;
				case NOTLIKE : 
					sb.append(" AND ").append(newFieldName).append(" NOT LIKE ").append(":"+valMark);
					break;
			}
		}
		
		return sb.toString();
	}
	
	private static String getValMark(String operator,String fieldName){
		return operator+fieldName;
	}*/
}
