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
    private Properties property = new Properties();

    private HashMap<String, JClientPOJO> clientHashMap;
    private Socket clientSocket;
    private ArrayList<String> sqlListResult = new ArrayList<>();

    public boolean onClickYes;
    public boolean onClickNo;
    public boolean onClickInfo;

    private boolean checkFoundScripts;
    private boolean checkedSQL = false;

    @Override
    public boolean ProgressBarChecked(){
        if (onClickYes && checkedSQL){
            return true;
        }
        return false;
    }



   synchronized public void send(){
        try {
            property.load(new FileInputStream("Server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* получение сокета с сервера SPAdmin */
        this.clientHashMap = ServerController.jClientMap;
        this.clientSocket = clientHashMap.get(
                property.getProperty("SPAdmin.ip")).getClientSocket();

            try (
                    /*  */
                    BufferedReader result = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    /* писатель для отправки сообщений клиенту */
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            )

            {
                /* посылаем команду на обновление  */
                out.println("UpdateSP: ");

                /*  Заполняем лист собщениями от клиента */
                String resultLine;
                boolean tempVar= true;
                while ((resultLine = result.readLine()) != null) {
                    sqlListResult.add(resultLine);

                    /* проверить, что вывился список скриптов для выполения */
                    if(resultLine.contains("Found") && resultLine.contains("script(s)")){
                        System.out.println("Вывился список скриптов для выполения "+new Date());
                        setCheckFoundScripts(true);

                        /* проверка ответа пользователя   */
                        while (tempVar){
                            if(onClickYes){
                                out.println("UpdateSP: Y");
                                System.out.println("onClickYes");
                                tempVar = false;
                            }else if(onClickNo){
                                out.println("UpdateSP: N");
                                System.out.println("onClickNo");
                                tempVar = false;
                               // return;
                            } else if(onClickInfo){
                                System.out.println("onClickInfo");
                            }else {
                                System.out.println("onClickYes "+onClickYes+" onClickNo "+onClickNo+" tempVar "+tempVar+"Текущий поток"+Thread.currentThread() +" "+new Date());
                            }
                        }

                    }
                    /* проверка успешноси выполнеия SQL */
                    if (checkedSQL = resultLine.contains("execute SUCCEEDED") );
                    //{
                        //  checkedSQL = true;
                    //}


                }

                /* вывод содержимого sqlListResult */
//                System.out.println("вывод содержимого sqlListResult:");
//                for(int i = 0; i < sqlListResult.size(); i++){
//                    System.out.println("----------------------------");
//                    System.out.println(i+"--> "+sqlListResult.get(i));
//                }


            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public ArrayList<String> getSqlListResult() {
        return sqlListResult;
    }

    public void setSqlListResult(String str) {
        this.sqlListResult.add(str);
    }

    public boolean getCheckFoundScripts() {
        return checkFoundScripts;
    }

    public void setCheckFoundScripts(boolean checkFoundScripts) {
        this.checkFoundScripts = checkFoundScripts;
    }
}
