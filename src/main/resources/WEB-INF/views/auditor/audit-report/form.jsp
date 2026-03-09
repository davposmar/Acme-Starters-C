<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="auditor.audit-report.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="auditor.audit-report.form.label.name" path="name"/>
	<acme:form-textarea code="auditor.audit-report.form.label.description" path="description"/>
	<acme:form-moment code="auditor.audit-report.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="auditor.audit-report.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="auditor.audit-report.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textbox code="auditor.audit-report.form.label.auditor.identity.fullName" path="auditor.identity.fullName"/>
	<acme:form-double code="auditor.audit-report.form.label.monthsActive" path="monthsActive"/>
	<acme:form-double code="auditor.audit-report.form.label.hours" path="hours"/>
	
	<acme:button code="auditor.audit-report.form.button.audit-sections" action="/auditor/audit-section/list?auditReportId=${id}"/>
</acme:form>
