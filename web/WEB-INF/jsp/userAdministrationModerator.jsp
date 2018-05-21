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
    <title>Administration</title>
</head>
<h2 align="center">Users Administration</h2>
<body>
<form:form action="userAdministrationSuperUser">
    <table align="center">
        <td align="center"><table border="1">
                ${tableData}
        </table></td>
        <tr>
            <td align="center"><button type="submit" formmethod="get" formaction="personalAreaModerator" style="width: 150px"/>Back</td>
    </table>
</form:form>
</body>
</html>