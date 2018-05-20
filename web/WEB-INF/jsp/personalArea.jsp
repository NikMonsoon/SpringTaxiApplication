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
        <title>Personal Area</title>
    </head>
    <h2 align="center">Personal Area</h2>
    <body>
        <form:form action="personalArea">
            <table align="center">
                <tr>
                    <td align="right">Welcome,</td>
                    <td align="left">${name}</td>
                </tr>
                <tr>
                    <td align="center"><button type="submit" formmethod="get" formaction="settings" style="width: 150px"/>Profile Settings</td>
                    <td align="center"><button type="submit" formmethod="get" formaction="route" style="width: 150px"/>Make a Call</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><table border="1">
                        ${tableData}
                    </table></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><button type="submit" formmethod="get" formaction="logging" style="width: 150px"/>Exit Profile</td>
                </tr>
            </table>
        </form:form>
    </body>
</html>