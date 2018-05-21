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
<h2 align="center">Taxis Administration</h2>
<body>
<form:form action="taxiAdministration">
    <table align="center">
        <tr>
            <td colspan="3"><table border="1">
                    ${tableData}
            </table></td>
        </tr>
        <tr>
            <td align="center"><button type="submit" formmethod="get" formaction="personalAreaSuperUser" style="width: 150px"/>Back</td>
            <td align="center"><button type="submit" formmethod="post" formaction="taxiDelete" style="width: 150px"/>Delete Car</td>
            <td align="center"><button type="submit" formmethod="get" formaction="taxiRegistrationSuperUser" style="width: 150px"/>Add Car</td>
        </tr>
    </table>
</form:form>
</body>
</html>