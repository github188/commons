/**
 * 放一些常用工具
 * 使用方式如:
 * $.jp.post(url,data,function(data){}); 
 * 或
 * jp.post(url,data,function(data){}); 
 * by huangzy data:2014/11/27
 */
var jp = jp||{};
jQuery['jp']=jp;
$(document).ready(function(){
	/**判断一个input type=text 是否为空(只有默认值,则认为是空.)*/
	jp.isBlank = function(obj){
		if($.trim(obj.val())==''){
			return true;
		}
		if(obj[0]&&obj[0].defaultValue==obj.val()){
			return true;
		}
		return false;
	}
	
	jp.post = function(url,param,fn,type){
		var _type = type?type:"json";
		
		//只有两个参数的情况jp.post(url,fn)
		if($.isFunction(param)){
			fn=param;
			param = "";
		}
		$.post(url,param,function(data, textStatus, jqXHR){
			fn(data);
		},_type);
	}
	
	//ctx为在外部定义
	var _ctx = '';
	try{
		_ctx=ctx;
	}catch(err){
		var pathname=window.document.location.pathname;
		var projectname=pathname.substring(0,pathname.substr(1).indexOf('/')+1);
		
		_ctx = projectname;
	}
	//默认上传配制
	var uploadDefualtCfg = {
			//'buttonText' : '选择文件', 
			//buttonClass: "fileall_bth",
			'fileObjName' : 'file',
			method : 'post',
			'fileTypeExts':'*.gif; *.jpg; *.png',
			'auto':true,    //选择一个文件是否自动上传
            'multi':false,    //是否允许多选(这里多选是指在弹出对话框中是否允许一次选择多个，但是可以通过每次只上传一个的方法上传多个文件)
            swf : _ctx+'/static/uploadify/uploadify.swf',
            cancelImg:_ctx+'/static/uploadify/uploadify-cancel.png',
            'onUploadSuccess' : function(file, data, response) {    //上传成功后触发的事件
                //var json = JSON.parse(data);
            }
	}
	
	/**
	 * 上传单个文件
	 * select 为选择器字符器,如#id
	 * url 上传请求地址
	 * buttonText 按钮名称
	 * buttonClass 按钮 class
	 * fn 回调函数
	 * 
	 * 示例:
	 * jp.upload("#imgId","/gd-share/someimg/uploadImg","选择文件","testBtnClass",function(data){
	 * 	console.info("上传成功,服务器返回:"+data.msg);
	 * });
	 */
	jp.upload = function(select,url,buttonText,buttonClass,fn){
		var uploadCfg = $.extend({},uploadDefualtCfg);
		uploadCfg.uploader=url;
		uploadCfg.onUploadSuccess=function(file, data, response){
			 var json = JSON.parse(data);
             fn(json);
		}
		
		if(buttonText){
			uploadCfg.buttonText=buttonText;
		}
		if(buttonClass){
			uploadCfg.buttonClass=buttonClass;
		}
		$(select).uploadify(uploadCfg);
	}
	
	jp.uploadUseCfg = function(select,cfg){
		var uploadCfg = $.extend(cfg,uploadDefualtCfg);
		$(select).uploadify(uploadCfg);
	}
	
	jp.dateFormat = function(timeVal){
		 var data = new Date(timeVal);  
	     var year = data.getFullYear();
	     var month = data.getMonth() + 1;
	     var day = data.getDate();
	     
	     month = dateAdd0(month);
	     day = dateAdd0(day);
	     
	     return year+"-"+month+"-"+day;
	}
	
	jp.dateTimeFormat = function(timeVal){
		 var data = new Date(timeVal);  
	     var year = data.getFullYear();
	     var month = data.getMonth() + 1;
	     var day = data.getDate();
	     month = dateAdd0(month);
	     day = dateAdd0(day);
	     
	     var hours = data.getHours();
	     var minutes = data.getMinutes();
	     var sec = data.getSeconds() 
	     
	     hours = dateAdd0(hours);
	     minutes = dateAdd0(minutes);
	     sec = dateAdd0(sec);
	        
	     return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+sec;
	}
	
	function dateAdd0(val){
		if(val<10){
			return "0"+val;
		}
		return val;
	}
	
	jp.remove = function(select){
		if($(select)){
			$(select).remove();
		}
	}
	
	jp.common = {
			setHtml:function(jqObj,val){
				jqObj.html(val);
			},
			setVal:function(jqObj,val){
				jqObj.val(val);
			},
			setAttr:function(jqObj,attr,val){
				jqObj.attr(attr,val);
			},
			setSrc:function(jqObj,val){
				//图片地址为空,不显示图片.
				if(!val||val==''){
					jqObj.hide();
					return;
				}
				jp.common.setAttr(jqObj,"src",val);
			},
			setHref:function(jqObj,val){
				jp.common.setAttr(jqObj,"href",val);
			},
	}
	
	jp.loadScript = function(sScriptid, sScriptSrc, callbackfunction) {
	    //gets document head element
	    var oHead = document.getElementsByTagName('head')[0];
	    if(oHead) {
	        //creates a new script tag
	        var oScript = document.createElement('script');

	        //adds src and type attribute to script tag
	        oScript.setAttribute('src', sScriptSrc);
	        oScript.setAttribute('type', 'text/javascript');
	        oScript.setAttribute('charset','utf8');
	        oScript.setAttribute('id', sScriptid);
	        
	        if(callbackfunction){
	        	//calling a function after the js is loaded (IE)
		        var loadFunction = function() {
		            if(this.readyState == 'complete' || this.readyState == 'loaded') {
		                callbackfunction();
		            }
		        };
		        oScript.onreadystatechange = loadFunction;

		        //calling a function after the js is loaded (Firefox)
		        oScript.onload = callbackfunction;
	        }
	        //append the script tag to document head element
	        oHead.appendChild(oScript);
	    }
	}
	
	jp.cloneTempleta = function(select){
		var templetaObj =  $(select);
		var templetaObjClone = templetaObj.clone();
		templetaObj.remove(); 
		
		return templetaObjClone;
	}
})
