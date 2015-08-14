<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib
	prefix="s" uri="/struts-tags"%>

	<table>
		<tr>
			<th>Name</th>
			<th>Description</th>
		</tr>	
	<c:forEach var="row" items="${creditPage.table.rowList}" >	
		<tr>
			<td>${row.name}</td>
			<td>${row.description}</td>
		</tr>	
	</c:forEach>
		
	</table>