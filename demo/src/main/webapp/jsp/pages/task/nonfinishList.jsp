<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><%--<fmt:message key="userList.title"/>--%>
        View Tasks</title>
    <meta name="menu" content="IncidentMenu"/>
</head>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>

<div class="col-sm-10">
    <h2>Non-Finished Tasks</h2>
    <display:table name="incidentList" cellspacing="0" cellpadding="0" requestURI=""
                   defaultsort="1" id="incidents" pagesize="25" class="table table-condensed table-striped table-hover"
                   export="false">
        <display:column property="id" escapeXml="true" sortable="true" titleKey="ID" style="width: 25%"/>

        <display:column property="name" escapeXml="true" sortable="true" titleKey="task.name" style="width: 25%"
                        url="/task/completeTask" paramId="id" paramProperty="id"/>

        <display:column property="assignee" escapeXml="true" sortable="true" titleKey="Assignee" style="width: 25%"
                        paramId="id" paramProperty="id"/>

        <display:column property="description" escapeXml="true" sortable="true" titleKey="Description"
                        style="width: 25%"/>

    </display:table>

</div>

