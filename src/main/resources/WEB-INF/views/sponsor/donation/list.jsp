<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.donation.list.label.name" path="name" width="80%"/>	
	<acme:list-column code="sponsor.donation.list.label.money" path="money" width="20%"/>
	<acme:list-hidden path="notes"/>
	<acme:list-hidden path="kind"/>
</acme:list>
