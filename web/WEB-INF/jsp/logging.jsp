<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<style>
    table {
        width: 300px; /* Ширина таблицы */
        margin: auto; /* Выравниваем таблицу по центру окна  */
    }
    td {
        text-align: center; /* Выравниваем текст по центру ячейки */
    }
</style>

<head>
    <title>Logging</title>
</head>
<h2 align="center">Logging</h2>
<body>
<form:form action="logging">
    <table align="center">
        <tr>
            <td><form:label path="login">Login</form:label></td>
            <td><form:input path="login" /></td>
        </tr>
        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:input path="password" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Enter" formmethod="post" style="width: 95px"/></td>
            <td><button type="submit" formmethod="get" formaction="registration" style="width: 175px"/>Registration</td>
        </tr>
    </table>
</form:form>
</body>
</html>