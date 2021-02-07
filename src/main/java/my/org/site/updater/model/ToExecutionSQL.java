package my.org.site.updater.model;

import my.org.site.server.JClientPOJO;
import my.org.site.server.ServerController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class ToExecutionSQL implements ProgressBar{
    /* подключение файла Server.properties  */
    private final Properties property = new Properties();

    private final ArrayList<String> sqlListResult = new ArrayList<>();

    public static boolean onClickYes;
    public boolean onClickNo;
    public boolean onClickInfo;

    public boolean onClickBreak;
    public boolean onClickContinue;
    public boolean onClickRollback;
    public boolean onClickRRollback;

//    private String checkFoundScripts;

    private String checkedSQLType = " ";

    /* переменные для остановки "Цикл ожидания ответа от клиента" */
    private boolean tempVar = true;
    private boolean tempVarError = true;

    /*не используется */
    @Override /* для отображения успеха */
    public boolean ProgressBarChecked(){
       return false;
    }



    public String getCheckedSQLType() {
        return checkedSQLType;
    }

    /* для подсветки результата и инициализации успеха  */
    public void setCheckedSQLType(String resultLine) {
        if(resultLine.contains("local_execute")){
            this.checkedSQLType = "execute";
        } else  if (resultLine.contains("execute SUCCEEDED") ){
            this.checkedSQLType = "success";
        } else  if (resultLine.contains("Script FAILED")){
            this.checkedSQLType = "danger";
        } else {
            this.checkedSQLType = "warning";
        }
    }




    synchronized public void send(){
        try {
            property.load(new FileInputStream("Server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* получение сокета с сервера SPAdmin */
        HashMap<String, JClientPOJO> clientHashMap = ServerController.jClientMap;
        Socket clientSocket = clientHashMap.get(property.getProperty("SPAdmin.ip")).getClientSocket();

            try (
                    /*  */
                    BufferedReader result = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    /* писатель для отправки сообщений клиенту */
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true))
            {
                /* посылаем команду на обновление  */
                out.println("UpdateSP: ");
                /*  Заполняем лист собщениями от клиента */
                String resultLine;

                while ((resultLine = result.readLine()) != null) {
                    sqlListResult.add(resultLine);

                    /* проверка, что нет скриптов для выпоения */
                    if (resultLine.contains("No new scripts")){
                        /* добавить гогику по выводу этой информации */
                        System.out.println(" проверка, что нет скриптов для выпоения ");
                    }

                    /* проверить, что вывился список скриптов для выполения */
                    else if(resultLine.contains("Found") && resultLine.contains("script(s)")){
                        System.out.println("Вывился список скриптов для выполения "+new Date());
                        setCheckedSQLType("local_execute");

                        /* проверка ответа пользователя   */

                        /* Script FAILED! */
                        /* ждем ответа клиента "Цикл ожидания ответа от клиента */
                        while (tempVar  /*&& !resultLine.contains("execute SUCCEEDED") */){
                            /*Костыль! */
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(onClickYes){
                                out.println("UpdateSP: Y"); /* отправляем клиенту команду на выполнение */
                                System.out.println("onClickYes");
                                checkedSQLType = "";  /* убираем мемю действия */
                                tempVar = false; /* остонавливаем цикл */
                            }else if(onClickNo){
                                out.println("UpdateSP: N"); /* отправляем клиенту команду на отмену  */
                                System.out.println("onClickNo");
                                checkedSQLType = ""; /* убираем мемю действия */
                                tempVar = false; /* остонавливаем цикл */
                               // return;
                            } else if(onClickInfo){
                                /* добавить логику  */
                                System.out.println("onClickInfo");
                            }
                        }
                        setCheckedSQLType(resultLine);
                        /* если выполнение sql завершилось ошибкой */
                        sendActionCommandError(resultLine,out);

                    }
                    /* проверка успешноси выполнеия SQL */
                    setCheckedSQLType(resultLine);
                    System.out.println("checkedSQLType "+checkedSQLType);
                    System.out.println(resultLine);


                }




            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public ArrayList<String> getSqlListResult() {
        return sqlListResult;
    }

//    public void setSqlListResult(String str) {
//        this.sqlListResult.add(str);
//    }

    private void sendActionCommandError(String resultLine, PrintWriter out){
        if(resultLine.contains("Script FAILED")){
            while (tempVarError){
                if(onClickBreak) {
                    out.println("FAILED_Break");
                    System.out.println("onClickBreak");
                    checkedSQLType = ""; /* убираем мемю действия */
                    tempVarError = false;
                } else if (onClickContinue){
                    out.println("FAILED_Continue");
                    System.out.println("onClickContinue");
                    checkedSQLType = ""; /* убираем мемю действия */
                    tempVarError = false;
                } else if (onClickRollback){
                    out.println("FAILED_Rollback");
                    System.out.println("onClickRollback");
                    checkedSQLType = ""; /* убираем мемю действия */
                    tempVarError = false;
                } else if (onClickRRollback){
                    out.println("FAILED_RRollback");
                    System.out.println("onClickRRollback");
                    checkedSQLType = ""; /* убираем мемю действия */
                    tempVarError = false;
                }
            }
        }
    }

//    public String getCheckFoundScripts() {
//        return checkFoundScripts;
//    }
//
//    public void setCheckFoundScripts(String checkFoundScripts) {
//        this.checkFoundScripts = checkFoundScripts;
//    }
}
