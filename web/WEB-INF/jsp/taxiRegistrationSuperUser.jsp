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
    <title>Registration</title>
</head>
<h2 align="center">Registration</h2>
<body>
<form:form action="taxiRegistrationSuperUser">
    <table align="center">
        <tr>
            <td><form:label path="driverName">Driver Name</form:label></td>
            <td><form:input path="driverName"/></td>
        </tr>
        <tr>
            <td><form:label path="carNumber">Car Number</form:label></td>
            <td><form:input path="carNumber"/></td>
        </tr>
        <tr>
            <td><form:label path="carModel">Car Model</form:label></td>
            <td><form:input path="carModel"/></td>
        </tr>
        <tr>
            <td><button type="submit" formmethod="get" formaction="taxiAdministrationSuperUser" style="width: 95px"/>Back</td>
            <td><input type="submit" value="Enter" formmethod="post" formaction="taxiRegistrationSuperUser" style="width: 175px"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>