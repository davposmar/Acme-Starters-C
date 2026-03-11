<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.milestone.form.label.campaign" path="campaign.name"/>
	<acme:form-textbox code="spokesperson.milestone.form.label.title" path="title"/>
	<acme:form-textarea code="spokesperson.milestone.form.label.achievements" path="achievements"/>
	<acme:form-double code="spokesperson.milestone.form.label.effort" path="effort"/>
	<acme:form-select code="spokesperson.milestone.form.label.kind" path="kind" choices="${kinds}"/>
</acme:form>
