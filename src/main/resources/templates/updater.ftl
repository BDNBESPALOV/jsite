<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Установка патча</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <link rel="stylesheet" href="/css/style.css">

    <!-- Подключаем стили бутстрапа: -->
    <!-- Подключаем стили для подсветки кода: -->
    <link href="css/prettify.css" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="/js/mainUploadFunction.js"></script>

    <script type="text/javascript">


<!--     var refreshIntervalId = setInterval (function () { -->

<!--    &lt;!&ndash; Обновление блока "Загрузка патча на сервер контроллера"&ndash;&gt;-->
<!--    if ( ((${valueNow} < ${size}) || (${size} == 0)) &&  (${checked?c} == false)  ) {-->
<!--        $('#contentFile').load(document.URL +  ' #contentFile');-->
<!--        $('.successChecked').load(document.URL +  ' .successChecked');-->
<!--     }-->
<!--    &lt;!&ndash; Обновление блока "Загрузка патча на сервер обновления"&ndash;&gt;-->

<!--     if ( ((${valueNowToUploadSP} < ${sizeToUploadSP}) || (${sizeToUploadSP} == 0))  &&  (${checkedToUploadSP?c} == false) ) {-->
<!--        $('#contentFileToUploadSP').load(document.URL +  ' #contentFileToUploadSP');-->
<!--        $('.successCheckedToUploadSP').load(document.URL +  ' .successCheckedToUploadSP');-->
<!--     }  }, 5000);-->

    <!-- Обновление блока "Установка SQL"-->
        var refreshSQL = setInterval (function () {

            $('.successCheckedExecuteSQL').load(document.URL +  ' .successCheckedExecuteSQL');
            $('#roundContentExecuteSQL').load(document.URL +  ' #roundContentExecuteSQL');
             }, 5000);

    <!-- Отключение обновлений блока "Установка SQL"-->
        if( '${checkedSQLType}' == 'success' ){
           clearInterval(refreshSQL);
        }

   </script>



</head>
<body>




<!-- Image and text -->
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="/">
        <img src="gear.png" width="30"
             height="30" class="d-inline-block align-top" alt="">
        Установка патча
    </a>
</nav>

<form id="myUploadPatch" name="uploads" action="pathgz" method="POST">
<div class="input-group">
    <div class="custom-file">
        <@spring.formInput "pathGZ.path" " type='file' name='myfile' class='form-control'  placeholder='Введите путь к патчу или URL ' " "text"/>
    </div>
    <div class="input-group-append">
        <button class="btn btn-outline-secondary" type="submit" id="inputGroupFileAddon04" onclick="location.reload()">Обновить систему</button>
    </div>
</div>
</form>

<!--    <#if (valueNow >= size) && (size > 0)>-->
<!--        <script>-->
<!--            clearInterval(refreshIntervalId);-->
<!--        </script>-->
<!--    </#if>-->



<div class="container">
    <div class="row">
        <div class="col ">
            <table class="table table-hover table-sm">
                <thead>
                    <tr>
                        <th scope="col" >Результат</th>
                        <th scope="col" >Процесс</th>
                        <th scope="col" >Действие</tr>
                    </tr>
                </thead>
            <tbody>

                <tr>
                    <th >
                        <div class="successChecked">
                            <#if checked><IMG src="success.png"></#if>
                        </div>
                    </th>
                    <td class="col-10"> <p>Загрузка патча на сервер контроллера</p>
                        <div id="contentFile" >
                            <div class="progress" >
                                <#if part <= 100>
                                <div  class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow=${valueNow} aria-valuemin="0" aria-valuemax=${size} style="width: ${part}%" >${part}%</div>
                                </#if>
                            </div>
                        </div>
                    </td>
                    <td>
                        <form  method="post" action="executeMainUpload" >
                            <button type="button" class="btn btn-primary btn-sm" onclick="mainUploadFunction()">Выполнить!</button>
                        </form>
                        <form method="post" action="clearMainUpload">
                            <button type="submit" class="btn btn-secondary btn-sm" name="clearMainUpload">Остановить</button>
                        </form>
                    </td>
                </tr>

                <tr>
                    <th>
                        <div class="successCheckedToUploadSP">
                            <#if checkedToUploadSP>
                                <IMG src="success.png">
                            </#if>
                        </div>
                    </th>
                    <td> <p>Загрузка патча на сервер обновления</p>
                        <div id="contentFileToUploadSP" >
                            <div class="progress" >
                                <div  class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow=${valueNowToUploadSP} aria-valuemin="0" aria-valuemax=${sizeToUploadSP} style="width: ${partToUploadSP}%">${partToUploadSP}%</div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <form  method="post" action="executeSPServerSQLvXML" >
                            <button type="submit" class="btn btn-primary btn-sm" >Выполнить!</button>
                        </form>
                        <form method="post" action="clearUploadSPServerSQLvXML">
                            <button type="submit" class="btn btn-secondary btn-sm" name="clearUploadServerSQLvXML">Остановить</button>
                        </form>
                    </td>
                </tr>

<!--//////////////////////////////////-->
<!--///////// Установка SQL //////////-->
<!--//////////////////////////////////-->


            <tr  id="response" class="alert alert-${checkedSQLType}">
                <th>
                    <div class="successCheckedExecuteSQL">
                        <#if checkedSQLType == 'success' >
                        <IMG src="success.png">
                        <#elseif checkedSQLType == 'danger'>
                        <IMG src="danger.png">
                        <#elseif checkedSQLType == 'warning'>
                        <IMG src="upload.png">
                        </#if>
                    </div>
                </th>
            <td>Установка SQL
            <div id="roundContentExecuteSQL">
<!--          <pre id="contentExecuteSQL" name="preSQL" class="prettyprint linenums lang-sql" >-->
               <pre id="contentExecuteSQL" name="preSQL">
<#list toExecutionSQL as item>
${item}
 </#list>
                </pre>
                    <#if checkedSQLType == 'execute' >
                        <p>Вы хотите их выполнить?</p>
                            <table>
                                <tr>
                                    <td>
                                        <form method="post" action="executeSQLYes" >
                                            <button type="submit" value="executeSQLYes"  class="btn btn-primary btn-sm">Да</button>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="executeSQLNo" c>
                                            <button  class="btn btn-secondary btn-sm">Нет</button>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="executeSQLInfo" >
                                            <button type="submit" class="btn btn-secondary btn-sm" name="clearUpload">Подробности в новом окне</button>
                                        </form>
                                    </td>
                                </tr>
                            </table>
                    <#elseif checkedSQLType == 'danger'>
                            <table>
                                <tr>
                                    <td>
                                        <form method="post" action=" " >
                                            <button type="submit" class="btn btn-primary btn-sm">(B)reak</button>
                                        </form>
                                    </td>

                                    <td>
                                        <form method="post" action=" " >
                                            <button type="submit" class="btn btn-primary btn-sm">(C)ontinue</button>
                                        </form>
                                    </td>

                                    <td>
                                        <form method="post" action=" " >
                                            <button type="submit" class="btn btn-primary btn-sm">(R)ollback and break</button>
                                        </form>
                                    </td>

                                    <td>
                                        <form method="post" action=" " >
                                            <button type="submit" class="btn btn-primary btn-sm">(RR)ollback and re-run</button>
                                        </form>
                                    </td>
                                </tr>
                            </table>
                    </#if>
                </div>
            </td>

            <td>
                <form method="POST" name="preForm" action="executeSQL">
                    <button type="submit" class="btn btn-primary btn-sm" >Выполнить!</button>
                </form>
                <form method="post" action="stopExecuteSQL">
                    <button  type="submit" class="btn btn-secondary btn-sm"  onclick="clearTestSQL()">Остановить</button>
                </form>
                <form method="post" action="executeSQLInfo" >
                    <button type="submit" class="btn btn-secondary btn-sm" name="clearUpload">Подробно...</button>
                </form>
            </td>
        </tr>




<!--//////////////////////////////////-->
<!--///////// Установка XML //////////-->
<!--//////////////////////////////////-->

        <tr>
            <th >
                <div class="successCheckedExecuteXML">
                    <#if checkedSQLType == 'success' >
                    <IMG src="success.png">
                    <#elseif checkedSQLType == 'danger'>
                    <IMG src="danger.png">
                    <#elseif checkedSQLType == 'warning'>
                    <IMG src="upload.png">
                </#if>
                </div>
            </th>
            <td>Установка XML
                <pre id="contentExecuteSQL" name="preSQL">
<#list toExecutionXML as item>
${item}
 </#list>
                </pre>
            </td>
            <td>
                <form method="post" action="executeXML">
                    <button class="btn btn-primary btn-sm" >Выполнить!</button>
                </form>
                <form method="post" action="clearUpload">
                    <button type="submit" class="btn btn-secondary btn-sm" name="clearUpload"  >Остановить</button>
                </form>
            </td>
        </tr>
        <tr>
            <th ><IMG src="success.png"></th>
            <td>Загрузка патча на все сервера кластера</td>
            <td>
                <form >
                    <button class="btn btn-primary btn-sm">Выполнить!</button>
                </form>
                <form method="post" action="clearUpload">
                    <button type="submit" class="btn btn-secondary btn-sm" name="clearUpload"  >Остановить</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
    </div>
    </div>
</div>


<!--//////////////////////////////////-->
<!--//////////////////////////////////-->
<!--//////////////////////////////////-->
<!--//////////////////////////////////-->






<div class="container">
     <div class="alert alert-success">
        <strong>Success!</strong> You should <a href="#" class="alert-link">read this message</a>.
    </div>
    <div class="alert alert-info">
        <strong>Info!</strong> You should <a href="#" class="alert-link">read this message</a>.
    </div>
    <div class="alert alert-warning">
        <strong>Warning!</strong> You should <a href="#" class="alert-link">read this message</a>.
    </div>
    <div class="alert alert-danger">
        <strong>Danger!</strong> You should <a href="#" class="alert-link">read this message</a>.
    </div>
</div>

<!--<div class="container">-->
<!--    <div class="row">-->
<!--        <div class="col-md-5" style="height: 200px;">-->
<!--            <div class="overflow-auto" >-->
<!--                <p>Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit amet,-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->
<!--                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum-->

<!--                </p>-->
<!--            </div>-->
<!--    </div>-->

<!--        </div>-->


<!--&lt;!&ndash;    <div class="overflow-hidden">...</div>&ndash;&gt;-->
<!--</div>-->

<!--<script src="https://cdn.jsdelivr.net/gh/google/code-prettify@master/loader/run_prettify.js"></script>-->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- Подключаем скрипт бутстрапа: -->
<!-- Подключаем скрипт для подсветки кода: -->
<script src="js/prettify.js"></script>
<!-- Инициализация функции подсветки кода: -->
<!--<button id="t">Ty</button>-->
<!--<script type="text/javascript">-->
<!--&lt;!&ndash;      document.getElementById("t").onclick =&ndash;&gt;-->

<!--      !function ($) {-->
<!--            $(function(){-->
<!--               window.prettyPrint && prettyPrint()-->
<!--            })-->
<!--        }(window.jQuery)-->


<!--    </script>-->

<!--<script src="js/ajax/ajaxSQL.js"></script>-->
<!--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>