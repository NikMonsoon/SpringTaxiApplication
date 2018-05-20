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
    <h2 align="center">Super User Personal Area</h2>
    <body>
        <form:form action="personalAreaSuperUser">
            <table align="center">
                <tr>
                    <td align="center"><button type="submit" formmethod="get" formaction="logging" style="width: 150px"/>Exit Profile</td>
                    <td align="center"><button type="submit" formmethod="get" formaction="userAdministrationSuperUser" style="width: 150px"/>Users Info</td>
                    <td align="center"><button type="submit" formmethod="get" formaction="taxiAdministration" style="width: 150px"/>Taxi Info</td>
                    <td align="center"><button type="submit" formmethod="get" formaction="callAdministration" style="width: 150px"/>Calls Info</td>
                </tr>
            </table>
        </form:form>
    </body>
</html>