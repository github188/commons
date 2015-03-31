/**
 * 文章列表
 * 
 * data: json obj
 * columns: [{field,jqStr,function(jqObj,val){}},{*******}]
 * templetaObj: div模板
 * 
 * by huangzy
 * data 2014/11/28
 */

var asl = asl||{};
$(document).ready(function(){
	
	asl.list = function(data,columns,templetaObj){
		if($.isArray(data)){
			var allObj;
			$.each(data,function(idx,obj){
				var currentObj = templetaObj.clone();
				if(allObj){
					allObj.after(currentObj);
				}else{
					allObj = currentObj;
				}
				
				$.each(columns,function(i,col){
					//取得值
					if(!col) return;
					var val = obj[col.field];
					if(col.field.indexOf(".")>0){
						var fields = col.field.split(".");
						var tmpVal = $.extend({},obj);
						$.each(fields,function(i,f){
							//TODO
							val=tmpVal[f];
							tmpVal=val;
						});
					}
					//设置值
					var jqObj = currentObj.find(col.jqStr);
					if(col.fn){
						col.fn(jqObj,val,obj);
					}else{
						jp.common.setVal(jqObj,val);
					}
				});
				currentObj.css('display','block');
			});
			
			return allObj;
		}
	}
	
	asl.loadList = function(url,param,fn){
		if(!url){
			url = config.url.byIdxUrl;
		}
		if($.isFunction(param)){
			fn=param;
			param = {};
		}
		if(!param) param = {};
		
		if(!param.limit){
			param.limit = 10;
		}
		var cfg = {
    			targetId:".page",
    			param:param,
    			url:url,
    			//加载页码后回调
    			callback:callBack,
    			clickEvent:function(idx){
    				$(window).scrollTop(0);
    	        }
    	}
		if(fn){
			cfg.afterProccessData=fn
		}
		
    	paging.pageInit(cfg);
	}
	
	//###################以下与页面相关##########################
	var templetaObj = $(".Articlelist");
	var insertBeforeObj = $(".page");
	
	var templetaObjClone = $(templetaObj).clone();
	templetaObj.remove();
	
	function callBack(data){
		jp.remove(".Articlelist");
		
		var divListObj = getDivList(templetaObjClone,data);
		
		if(divListObj){
			divListObj.insertBefore(insertBeforeObj);
		}
	}
	
	function getDivList(templetaObj,data){
		var columns=[
						{field:"shareAuthor.headImg",jqStr:".userimg a img",fn:function(jqObj,val,record){
							jp.common.setSrc(jqObj,ctx+val);
							jp.common.setHref(jqObj.parent(),ctx+"/front/article/category?url="+config.url.byAuthorUrl+record.shareAuthor.id);
						}},
						{field:"titleImg",jqStr:".Txtimg img",fn:function(jqObj,val,record){
							jqObj.hide();
							if(val){
								jp.common.setSrc(jqObj,ctx+val);
								jp.common.setHref(jqObj.parent(),config.url.detailUrl+record.id);
								jqObj.show();
							}
							
						}},
						{field:"isTop",jqStr:".userimg span",fn:function(jqObj,val){
							jqObj.hide();
							if(val==1){
								jqObj.show();
							}
						}},
						//TODO 文章详情
						{field:"title",jqStr:".title>a",fn:function(jqObj,val,record){
							jp.common.setHtml(jqObj,val);
							jp.common.setHref(jqObj,config.url.detailUrl+record.id);
						}},
						
						{field:"shareAuthor.nick",jqStr:".Author .Author_left li:nth-child(1) a",fn:function(jqObj,val,record){
							jp.common.setHtml(jqObj,val);
							
							jp.common.setAttr(jqObj,"data-url",config.url.byAuthorUrl+record.shareAuthor.id);
							jp.common.setHref(jqObj,"javascript:void(0)");
							addEvent(jqObj);
						}},
						{field:"shareArticleType.name",jqStr:".Author .Author_left li:nth-child(2) a",fn:function(jqObj,val,record){
							jp.common.setHtml(jqObj,val);
							
							jp.common.setAttr(jqObj,"data-url",config.url.byCategoryUrl+record.shareArticleType.id);
							jp.common.setHref(jqObj,"javascript:void(0)");
							addEvent(jqObj);
						}},
						{field:"createTime",jqStr:".Author .Author_left li:nth-child(3)",fn:function(jqObj,val){
							jp.common.setHtml(jqObj,jp.dateFormat(val));
						}},
						{field:"scoreNum",jqStr:".Author_left2 li:nth-child(1)",fn:function(jqObj,val){
							var _val = val+"";
							if(_val.indexOf(".")<0){
								_val = _val+".0";
							}
							jp.common.setHtml(jqObj,"评分 "+_val);
						}},
						{field:"visitCount",jqStr:".Author_left2 li:nth-child(2)",fn:function(jqObj,val){
							jp.common.setHtml(jqObj,"浏览 "+val);
						}},
						{field:"commentCount",jqStr:".Author_left2 li:nth-child(3)",fn:function(jqObj,val){
							jp.common.setHtml(jqObj,"评论 "+val);
						}},
						{field:"content",jqStr:".Article_txt",fn:function(jqObj,val,record){
							jp.common.setHtml(jqObj,val);
							jqObj.append('<a href="'+config.url.detailUrl+record.id+'" class="color_r">阅读全文 >></a>');
						}}
			]
		var chainObj = asl.list(data,columns,templetaObj);
		return chainObj;	
	}
	
	function addEvent(jqObj){
		jqObj.click(function(){
			var url = $(this).attr("data-url");
			if(url){
				//asl.loadList(url);
				window.location.href=ctx+"/front/article/category?url="+url;
			}
		});
	}
});