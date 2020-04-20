<#import "/spring.ftl" as spring/>

<html>
<head>
    <title>Path</title>
    <link rel="stylesheet"
          type="text/css" href="<@spring.url '/css/style.css'/>"/>
</head>
<body>
<#if errorMessage??>
<div style="color:red;font-style:italic;">
    ${errorMessage}
</div>
</#if>
<a href="<@spring.url '/'/>">Home</a>
<div>
    <fieldset>
        <legend>Add path</legend>
        <form name="person" action="" method="POST">
            Path: <@spring.formInput "findInLog.path" "" "text"/>    <br/>
            Path: <@spring.formInput "findInLog.logFilter" "" "text"/>    <br/>
            <input type="submit" value="Create" />
        </form>
    </fieldset>
</div>
</body>
</html>