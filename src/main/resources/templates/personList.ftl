<#import "/spring.ftl" as spring/>

<html>
<head>
    <title>Person List freemarker</title>
    <link rel="stylesheet"
          type="text/css"
          href="<@spring.url '/css/style.css'/>"/>
</head>
<body>
<h3>Person List</h3>
<a href="addPerson">Add Person</a>
<br><br>
<div>
    <a href="<@spring.url '/'/>">Home</a>
    <table border="1">
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        <#list persons as person>
        <tr>
            <td>${person.path}</td>
            <td>${person.logFilter}</td>
        </tr>
    </#list>
    </table>
</div>
</body>
</html>