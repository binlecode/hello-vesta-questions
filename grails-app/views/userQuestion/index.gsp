<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userQuestionHistory.label', default: 'UserQuestionHistory')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
%{--    <div class="nav" role="navigation">--}%
%{--        <ul>--}%
            %{-- <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
%{--            <li><g:link class="create" action="requestDailyQuestions">Request daily questions</g:link></li>--}%
%{--        </ul>--}%
%{--    </div>--}%

    <div class="centered">
        <h1>User: Skywalker</h1>
    </div>

    <div class="contentContainer">
    <div id="list-question" class="content scaffold-list">

        <h1>User daily questions
            <a class="btn btn-info float-right" style="text-decoration-line: none;" href="${createLink(action: 'requestDailyQuestions')}">
                Request daily questions
            </a>
        </h1>
        <g:if test="${userDailyQuestionList?.size() > 0}">
                <table>
                    <tbody>
                    <g:each in="${userDailyQuestionList}" var="bean" status="i">
                        <tr>
                            <td width="%25">
                            </td>
                            <td>
                                <span>
                                    ${bean.program.name}
                                </span>
                            </td>
                            <td>
                                <span>
                                    ${bean.title}
                                </span>
                            </td>
                            <td width="25%">
                                <a class="btn btn-outline-info" href="#" data-toggle="tooltip" title="coming soon...">Answer</a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
        </g:if>
        <g:else>
            <p>There are no more questions available for today.</p>
        </g:else>
    </div>
    </div>


    <div class="contentContainer">
    <div id="list-userQuestionHistory" class="content scaffold-list" role="main">
        <h1><g:message code="default.list.label" args="[entityName]"/>
            <a class="btn btn-warning float-right" style="text-decoration-line: none;" href="${createLink(action: 'clearQuestionHistory')}">
               Clear Question History
            </a>
        </h1>

        <table>
            <thead>
            <tr>
                <th>User</th>
                <th>Question</th>
                <th>Last Asked</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${userQuestionHistoryList}" var="bean" status="i">
                <tr>
                    <td>${bean.user.name}</td>
                    <td>${bean.question.title}</td>
                    <td>${bean.dateCreated}</td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <g:if test="${userQuestionHistoryCount && (userQuestionHistoryCount > 10)}">
            <div class="pagination">
                <g:paginate total="${userQuestionHistoryCount ?: 0}"/>
            </div>
        </g:if>
    </div>
    </div>

</body>
</html>
