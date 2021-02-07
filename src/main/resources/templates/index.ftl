<#import "/spring.ftl" as spring/>
<#import "macros/main.ftl" as h>

<@h.head>
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

</@h.head>