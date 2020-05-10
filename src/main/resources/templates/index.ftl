<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <title>Server</title>
</head>

<body>
<#include "navbar.ftl">

<div>

<table class="table table-striped table-dark">
    <tr>

        <th scope="col">
            <form method="post"  action="refresh" >
                <button type="submit" name="refresh"  >Обновить</button>
            </form>
        </th>
        <th scope="col">Активные java процессы</th>
        <th scope="col">Имя клиента</th>
        <th scope="col">Время подключения</th>

    </tr>
    <#list jClientMap?keys as key>
    <#assign value=jClientMap[key] />
    <tr>
        <td>
            <br/>
            <form method="post"  action="startSP" id="5555">
                <button type="submit" name="startSP" value=${value.name} >Start</button>
            </form>
            <br/>
            <form method="post"  action="stopSP" >
                <button type="submit" name="stopSP" value=${value.name} >Stop</button>
            </form>
            <br/>
            <form method="post"  action="countJavaProcess" >
                <button type="submit" name="countJavaProcess" value=${value.name} >JavaProcess</button>
            </form>
        </td>
        <td>
            <form method="post" action="clientName">
                <button type="submit"  name="cName" value=${value.name} >${value.size}</button>
            </form>
        </td>
        <td>${value.name}</td>
        <td>${value.date}</td>
    </tr>
</#list>
</table>

</div>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>


</body>

</html>