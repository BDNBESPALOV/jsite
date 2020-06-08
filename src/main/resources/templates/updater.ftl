<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Установка патча</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
     var refreshIntervalId = setInterval (function () {

    if ( (${valueNow} < ${size}) || (${size} == 0)) {
        $('#contentFile').load(document.URL +  ' #contentFile');
        $('.successChecked').load(document.URL +  ' .successChecked');

        $('#contentFileToUploadSP').load(document.URL +  ' #contentFileToUploadSP');
        $('.successCheckedToUploadSP').load(document.URL +  ' .successCheckedToUploadSP');

     }

        }, 2000);

        function mainUploadFunction() {
            myUploadPatch.setAttribute("action","executeMainUpload");
            document.getElementById("myUploadPatch").submit();
            location.reload();
        }

    </script>
    <style>
   #myBlock {
    background: #ff; /* Цвет фона */
    /*width: 800px;  Ширина 800px*/
    height: 200px; /* Высота */
    padding: 10px; /* Поля вокруг текста */
    /* Добавляем полосы прокрутки */
    overflow-x: auto;
   }


  </style>
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



<div class="container  ">
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
                    <th class="successChecked">
                        <#if checked>
                        <IMG src="success.png">
                    </#if>
                    </th>
                    <td > <p>Загрузка патча на сервер контроллера</p>
                        <div id="contentFile " >
                            <div class="progress" >
                                <div  class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow=${valueNow} aria-valuemin="0" aria-valuemax=${size} style="width: ${part}%" ></div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <form  method="post" action="executeMainUpload" >
                            <button type="button"  onclick="mainUploadFunction()" >Выполнить!</button>
                        </form>
                        <form method="post" action="clearMainUpload">
                            <button type="submit"  name="clearMainUpload"  >Остановить</button>
                        </form>
                    </td>
                </tr>

                <tr>
                    <th class="successCheckedToUploadSP">
                        <#if checked>
                        <IMG src="success.png">
                    </#if>
                    </th>
                    <td> <p>Загрузка патча на сервер обновления</p>
                        <div id="contentFileToUploadSP" >
                            <div class="progress" >
                                <div  class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow=${valueNowToUploadSP} aria-valuemin="0" aria-valuemax=${sizeToUploadSP} style="width: ${partToUploadSP}%" ></div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <form >
                            <button>Выполнить!</button>
                        </form>
                        <form method="post" action="clearUploadServerSQLvXML">
                            <button type="submit"  name="clearUploadServerSQLvXML"  >Остановить</button>
                        </form>
                    </td>
                </tr>
            <tr>
            <th ><IMG src="success.png"></th>
            <td>Установка SQL

                <div class="bg-dark" id="myBlock">
                    <#list toExecutionSQL as item>
                        <p class="text-light bg-dark" style="font-size: 8pt;">
                            ${item}
                          </p>
                    </#list>
                </div>
            </td>

            <td>
                <form >
                    <button>Выполнить!</button>
                </form>
                <form method="post" action="clearUpload">
                    <button type="submit"  name="clearUpload">Остановить</button>
                </form>
            </td>
        </tr>
        <tr>
            <th ><IMG src="success.png"></th>
            <td>Установка XML</td>
            <td>
                <form >
                    <button>Выполнить!</button>
                </form>
                <form method="post" action="clearUpload">
                    <button type="submit"  name="clearUpload"  >Остановить</button>
                </form>
            </td>
        </tr>
        <tr>
            <th ><IMG src="success.png"></th>
            <td>Загрузка патча на все сервера кластера</td>
            <td>
                <form >
                    <button>Выполнить!</button>
                </form>
                <form method="post" action="clearUpload">
                    <button type="submit"  name="clearUpload"  >Остановить</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
    </div>
    </div>
</div>

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

<div class="container">
    <div class="row">
        <div class="col-md-5" style="height: 200px;">
            <div class="overflow-auto" >
                <p>Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit amet,
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum
                    Lorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsumLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum dolor sit ametLorem ipsum

                </p>
            </div>
    </div>

        </div>


<!--    <div class="overflow-hidden">...</div>-->
</div>



<!--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>