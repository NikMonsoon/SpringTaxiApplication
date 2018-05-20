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
    <title>Settings</title>
</head>
<h2 align="center">Profile Settings</h2>
<body>
<form:form action="settings">
    <table align="center">
        <tr>
            <td><form:label path="login">Login</form:label></td>
            <td><form:input path="login"/></td>
        </tr>
        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:input path="password"/></td>
        </tr>
        <tr>
            <td><form:label path="name">Name</form:label></td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td><form:label path="birthday">Birthday</form:label></td>
            <td><form:input path="birthday" placeholder="YYYY-MM-DD"/></td>
        </tr>
        <tr>
            <td><form:label path="phoneNumber">Phone Number</form:label></td>
            <td><form:input path="phoneNumber"/></td>
        </tr>
        <tr>
            <td><button type="submit" formmethod="post" formaction="personalArea" style="width: 95px"/>Back</td>
            <td><input type="submit" value="Enter" formmethod="post" style="width: 175px"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>