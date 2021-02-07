<#import "/spring.ftl" as spring/>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <title>Подробноя информация</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!-- Подключаем стили бутстрапа: -->
    <!-- Подключаем стили для подсветки кода: -->
    <link href="css/prettify.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <!-- Подключаем скрипт бутстрапа: -->
    <!-- Подключаем скрипт для подсветки кода: -->
    <script src="js/prettify.js"></script>
    <!-- Инициализация функции подсветки кода: -->
    <script type="text/javascript">
!function ($) {
	$(function(){
		window.prettyPrint && prettyPrint()
	})
}(window.jQuery)
</script>
</head>

<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="/">
        <img src="gear.png" width="30"
             height="30" class="d-inline-block align-top" alt="">
        Установка патча
    </a>
</nav>
<pre class="prettyprint linenums lang-sql">
<#list toExecutionSQL as item>${item}
</#list>
</pre>
<!-- Встраиваемые классы (технологии):
    class="prettyprint lang-html"
    class="prettyprint lang-css"
    class="prettyprint lang-js"
    class="prettyprint lang-php"
    class="prettyprint lang-apache"
    class="prettyprint lang-sh"
    class="prettyprint lang-sql"
-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>