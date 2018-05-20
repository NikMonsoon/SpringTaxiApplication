<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<style>
    table {
        width: 300px; /* Ширина таблицы */
        margin: auto; /* Выравниваем таблицу по центру окна  */
    }
    textarea {
        resize: none;
    }
</style>
<head>
    <title>Route</title>
</head>
<h2 align="center">Lets Make a Visit</h2>
<body>
<form:form action="route">
    <table align="center">
        <tr>
            <td><form:label path="startPoint">Start Point</form:label></td>
            <td><form:input path="startPoint"/></td>
        </tr>
        <tr>
            <td><form:label path="destination">Destination</form:label></td>
            <td><form:input path="destination"/></td>
        </tr>
        <tr>
            <td><form:label path="date">Date</form:label></td>
            <td><form:input path="date"/></td>
        </tr>
        <tr>
            <td><form:label path="payment">Payment</form:label></td>
            <td><form:input path="payment"/></td>
        </tr>
        <tr>
            <td><button type="submit" formmethod="post" formaction="personalArea" style="width: 95px"/>Back</td>
            <td><input type="submit" value="Enter" formmethod="post" style="width: 175px"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>