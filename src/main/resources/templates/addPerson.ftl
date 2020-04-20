<#import "/spring.ftl" as spring/>

<html>
<head>
    <title>Add Person freemarker</title>
    <link rel="stylesheet"
          type="text/css" href="<@spring.url '/css/style.css'/>"/>
</head>
<body>
<#if errorMessage??>
<div style="color:red;font-style:italic;">
    ${errorMessage}
</div>
</#if>

<!--<div>-->
<!--    <fieldset>-->
<!--        <legend>Add Person</legend>-->
<!--        <form name="person" action="" method="POST">-->
<!--            First Name: <@spring.formInput "personForm.firstName" "" "text"/>    <br/>-->
<!--            Last Name: <@spring.formInput "personForm.lastName" "" "text"/>    <br/>-->
<!--            <input type="submit" value="Create" />-->
<!--        </form>-->
<!--    </fieldset>-->
<!--</div>-->


</body>

</html>