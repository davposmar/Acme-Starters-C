<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.audit-section.form.label.name" path="name"/>
	<acme:form-textarea code="auditor.audit-section.form.label.notes" path="notes"/>
	<acme:form-integer code="auditor.audit-section.form.label.hours" path="hours"/>
	<acme:form-select code="auditor.audit-section.form.label.kind" path="kind" choices= "${kinds}"/>			
</acme:form>

