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
<h2 align="center">Calls Administration</h2>
<body>
    <form:form action="callAdministration">
        <table align="center">
            <tr>
                <td colspan="3"><table border="1">
                        ${tableData}
                </table></td>
            </tr>
            <tr>
                <td align="center"><button type="submit" formmethod="get" formaction="personalAreaSuperUser" style="width: 150px"/>Back</td>
                <td align="center"><button type="submit" formmethod="get" formaction="callDelete" style="width: 150px"/>Delete Call</td>
                <td align="center"><button type="submit" formmethod="get" formaction="callAdd" style="width: 150px"/>Add Call</td>
            </tr>
        </table>
    </form:form>
</body>
</html>