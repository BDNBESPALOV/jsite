Если используются https адреса необходимо добавить сертификат сайта в C:\Program Files\Java\jdk1.8.0_202\jre\lib\security\cacerts иначе будет ошибка “PKIX path building failed” and “unable to find valid certification path to requested target”
keytool -importcert -alias cloud -file A:\temp\cloud.cer -keystore cacerts