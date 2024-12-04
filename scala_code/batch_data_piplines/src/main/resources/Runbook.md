### Getting error of class not found
Issue: when spark dependencies are given as provided by defalut intellij is not include them during running
Fix: Edit configuration to run program with provided jars also

### run spark application from spark-submit
spark-submit --class GettingDataBooksDbDataPSql \
--driver-class-path /opt/bitnami/spark/amit/ext_lib/postgresql-42.7.3.jar \
--conf spark.driver.extraJavaOptions=-Dconfig.file=/opt/bitnami/spark/amit/conf/application.conf \
batch_data_piplines-assembly-0.1.0-SNAPSHOT.jar

this config can be used in spark-submit
--conf spark.driver.extraJavaOptions=-Dconfig.file=/opt/bitnami/spark/amit/conf/application.conf \
--conf spark.executor.extraJavaOptions=-Dconfig.file=/opt/bitnami/spark/amit/conf/application.conf \


#### getting error of not stable JDBC driver unless i have added below
--driver-class-path /opt/bitnami/spark/amit/ext_lib/postgresql-42.7.3.jar

#### getting error in connection with postgresql with JDBC url - "jdbc:postgresql://host.docker.internal:5432/booksdb" issue got resolved when i change url to
"jdbc:postgresql://host.docker.internal:5432/booksdb", since my postgresql is running on docker

### Able to run application.conf externally with below spark-submit
```shell
spark-submit --class GettingDataBooksDbDataPSql \
--driver-class-path /opt/bitnami/spark/amit/ext_lib/postgresql-42.7.3.jar \
--conf spark.driver.extraJavaOptions=-Dconfig.file=/opt/bitnami/spark/amit/conf/application.conf \
batch_data_piplines-assembly-0.1.0-SNAPSHOT.jar
```

