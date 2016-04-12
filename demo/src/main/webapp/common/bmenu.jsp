<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav">

        <c:choose>
            <c:when test="${empty pageContext.request.remoteUser}">
                <li class="active">
                    <a href="<c:url value="/login"/>"><fmt:message key="login.title"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false"><fmt:message key="task.title"/>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/task/todoTask'/>"><fmt:message key="task.todo.title"/></a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<c:url value='/task/nonfinishTask'/>"><fmt:message
                                key="task.non_finish.title"/></a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false"><fmt:message key="incident.title"/>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/process/toStartProcess'/>"><fmt:message
                                key="incident.regist.title"/></a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<c:url value='/process/listProcDef'/>"><fmt:message key="incident.list.title"/></a>
                        </li>

                    </ul>
                </li>
                <li><a href="<c:url value='/logout'/>"><fmt:message key="user.logout"/></a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>


