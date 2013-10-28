<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Insert title here</title>
<%@ include file="taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="javascript" type="text/javascript"  src="${ctx}/resource/js/jquery-1.3.2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#test1").click(function() {
		test1(); 
	});
	$("#test2").click(function() {
		test2();
	});
	$("#test3").click(function() {
		test3();
	});
});
function test1() {
	var url = 'http://localhost:8080/spring-rest/employeeTest/';
	var query = $('#id').val()+".json";
	url += query;
	$.get(url, function(data) {
		data=eval('('+data+')');
		alert("id: " + data.id + "\nname: " + data.name + "\nemail: "+ data.email);
	});
}
function test2() {
	var mydata = '{"name":"' + $('#name').val() + '","id":"'+ $('#id').val() + '","email":"' + $('#email').val() + '"}';
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'http://localhost:8080/spring-rest/employee/'+$('#id').val(),
		processData : false,
		dataType : 'json',
		data : mydata,
		success : function(data) {
			alert("id: " + data.id + "\nname: " + data.name + "\nemail: "+ data.email);
		},
		error : function() {
			alert('Err...');
		}
	});
}
function test3() {
	var mydata = '{"count":2,"employees":[{"id":1,"name":"snowolf","email":"true"},{"id":2,"name":"Wu Dong Fei","email":"wudongf@cn.ibm.com"}]}';
	alert(mydata);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'http://localhost:8080/spring-rest/employees',
		processData : false,
		dataType : 'json',
		data : mydata,
		success : function(data) {
			alert(data);
			alert("id: " + data["employees"][0].id + "\nname: " + data["employees"][0].name + "\nemail: "+ data["employees"][0].email
					+ "\nid: " + data["employees"][1].id +"\nname: " + data["employees"][1].name + "\nemail: "+ data["employees"][1].email
					+ "\ncount: " + data.count); 
		},
		error : function() {
			alert('Err...');
		}
	});
}
</script>
</head>
<body>
	<table>
		<tr>
			<td>id</td>
			<td><input id="id" value="1" /></td>
		</tr>
		<tr>
			<td>name</td>
			<td><input id="name" value="snowolf" /></td>
		</tr>
		<tr>
			<td>status</td>
			<td><input id="email" value="true" /></td>
		</tr>
	</table>
	<input type="button" id="test1"  value="getjson-POST" />
	<input type="button" id="test2"  value="putjson-POST" />
	<input type="button" id="test3"  value="putjsons-POST" />
</body>
</html>
