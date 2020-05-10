<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="/">Контроллер</a>

    <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
<!--            <li class="nav-item active">-->
<!--                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--                <a class="nav-link" href="#">Link</a>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--                <a class="nav-link disabled" href="#">Disabled</a>-->
<!--            </li>-->
            <form method="post"  action="startServer" >
                <button class="nav-link" type="submit" name="startServer" value="true" >StartServer</button>
            </form>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#"> <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <form method="post"  action="stopServer" >
                    <button class="nav-link " type="submit" name="stopServer" value="true" >StopServer</button>
                </form>
            </li>

        </ul>
        <form class="form-inline my-2 my-lg-0">

                  <#if start>
                    <span class="navbar-text text-success">
                    Контроллер запущен и готов принемать соединения !
                   </span>
                    <#else>
                     <span class="navbar-text text-danger">
                    Контроллер остановлен и не может принимать соединения !
                     </span>
                </#if>

        </form>
    </div>
</nav>