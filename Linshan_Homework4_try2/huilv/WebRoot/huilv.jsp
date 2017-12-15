<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>汇率转换</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	var xmlHttp;//用于保存XMLHttpRequest对象的全局变量
	
	//用于创建XMLHttpRequest对象
	function createXmlHttp(){
	//根据window.XMLHttpRequest对象是否存在使用不同的创建方式
		if(window.XMLHttpRequest){ //firefox,opera浏览器支持的创建方式 
			xmlHttp = new XMLHttpRequest();
		}else{	//IE浏览器支持的创建方式 
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
	}
	
	function submitPost(){
		var money = document.getElementById("money").value;//币种
		var currency = gettype();//金额
		//document.getElementByName("currency").value;
		displayStatus("正在提交...");
		createXmlHttp(); //创建XMLHttpRequest对象
		xmlHttp.onreadystatechange = submitPostCallBack;//设置回调函数
		var url = "<%=basePath%>huilv?money="+money+"&currency="+currency;
		xmlHttp.open("POST",url,true); //发送post请求、
		xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//设置post请求类型
		xmlHttp.send(null); 
	}
	
	//回调函数 
	function submitPostCallBack(){
		if(xmlHttp.readyState==4&&xmlHttp.status==200){
			var rtn_obj = eval("("+xmlHttp.responseText+")");
			var msg="";
			if(rtn_obj.flg=="1"){
				msg="<font color=green>"+rtn_obj.msg+"</font>"
			}
			if(rtn_obj.flg=="0"){
				msg="<font color=red>"+rtn_obj.msg+"</font>"
			}
			displayStatus(msg);
		}
	
	}
	//显示函数
	function displayStatus(info){
		var statusDiv = document.getElementById("statusDiv");
		statusDiv.innerHTML=info;
		statusDiv.style.display="";
	}
	//获得币种
	function gettype(){
		var obj=document.getElementsByName("currency");//数组
		var resualt=null;
		for(var i=0;i<obj.length;i++)
		{
			if(obj[i].checked)//挨个判定哪个被选中
			{
				resualt=obj[i].value;
			}
		}
		return resualt;
	}
	</script>
  </head>
  
  <body>
	<br>请选择币种：
		<input type="radio" name="currency" value="US" checked="checked" />美元
		<input type="radio" name="currency" value="EU" />欧元
		<input type="radio" name="currency" value="JP" />日元
		<input type="radio" name="currency" value="HK" />港币
	<br><br><br><br>
	<br>请输入金额：<input type="text" id="money" name="money">元
	<br><br><br><br>
	<input type="button" onclick="submitPost()"  value="转换">
	<br>
	
	<div id="statusDiv" style="display: none;"></div>
  </body>
</html>
