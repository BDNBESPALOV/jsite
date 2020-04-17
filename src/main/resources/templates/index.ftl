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


<a href="<@spring.url '/personList'/>">Person List</a>

</body>

</html>