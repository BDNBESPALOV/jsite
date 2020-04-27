<#import "/spring.ftl" as spring/>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Welcome freemarker</title>
    <link rel="stylesheet"
          type="text/css" href="<@spring.url '/css/style.css'/>"/>
</head>

<body>
<h1>Welcome freemarker</h1>
<#if message??>
<h2>${message}</h2>
</#if>


<!--<a href="<@spring.url '/personList'/>">Person List</a>-->
<!--<a href="<@spring.url '/formFindLog'/>">Path </a>-->

<!--<div>-->
<!--    <fieldset>-->
<!--        <legend>Command console</legend>-->
<!--        <form name="person" action="" method="POST">-->
<!--            Command: <@spring.formInput "jClient.command" "" "text"/>    <br/>-->
<!--            <input type="submit" value="Create" />-->
<!--        </form>-->
<!--    </fieldset>-->
<!--</div>-->

<form method="post"  action="startServer" >
    <button type="submit" name="startServer" value="true" >StartServer</button>
</form>
<br/>
<form method="post"  action="stopServer" >
    <button type="submit" name="stopServer" value="true" >StopServer</button>
</form>

<div>
    <a href="<@spring.url '/'/>">Home</a>
<table border="1">
    <tr>
        <th>Action</th>
        <th>Command</th>
        <th>Name</th>
        <th>Время подключения</th>

    </tr>
    <#list map?keys as key>
    <#assign value=map[key] />
    <tr>
        <td>
            <br/>
            <form method="post"  action="startSP" >
                <button type="submit" name="startSP" value="true" >Start</button>
            </form>
            <br/>
            <form method="post"  action="stopSP" >
                <button type="submit" name="stopSP" value="true" >Stop</button>
            </form>
        </td>
        <td>${value.command}</td>
        <td>${value.name}</td>
        <td>${value.status}</td>

    </tr>
</#list>
</table>

</div>

</body>

</html>