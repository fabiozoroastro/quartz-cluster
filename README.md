Quartz-Cluster
==============
Utilizando QuartzScheduler 2.2.x em ambiente *cluster*.
##Objetivo##
O projeto possui uma implementação simples de agendamento de tarefas com a biblioteca [Quartz Scheduler](http://quartz-scheduler.org/) para um ambiente corporativo.

###Quartz em cluster###
A documentação [avançada](http://quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-11) diz que para a biblioteca funcionar em ambiente *cluster*, o projeto **deve** utilizar *JDBC-JobStore* ou *TerracotaStore*. Neste projeto foi utilizado o *JDBC-JobStore*.
Leia sobre [*JobStores*](http://quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-09).
Existe um *script* compatível com a *JDBC-JobStore* para cada banco de dados. Neste projeto foi utilizado o banco de dados *MySql*.

**Atenção:** [A versão 2.2](http://quartz-scheduler.org/documentation/quartz-2.2.x/new-in-quartz-2_2) do Quartz sofreu algumas alterações que exigem atualizações de banco:
```
for oracle: ALTER TABLE QRTZ_FIRED_TRIGGERS ADD COLUMN SCHED_TIME NUMBER(13) NOT NULL;
for postgresql: ALTER TABLE QRTZ_FIRED_TRIGGERS ADD COLUMN SCHED_TIME BIGINT NOT NULL;
for MySql: ALTER TABLE QRTZ_FIRED_TRIGGERS ADD COLUMN SCHED_TIME BIGINT(13) NOT NULL;
etc.
```
O script pode ser obtido aqui: [download](http://svn.terracotta.org/svn/quartz/tags/quartz-2.1.7/docs/dbTables/tables_mysql_innodb.sql); entretanto, o *script* - para *mysql innodb* - atualizado com a alteração citada acima está na raiz deste projeto com o nome "script-mysql-innodb-quartz2.2.sql".


####Testes####
O projeto foi construído/testado com as seguintes configurações:
- Biblioteca Quartz 2.2.1;
- Banco de dados mysql-5.1.73;
- Java JDK 1.7.0_21;
- Apache Tomcat 5.5(Foram usadas duas instâncias);

Para testar, realize a compilação do projeto utilizando o *apache-maven* e copie o arquivo *war* para os servidores em que você deseja testar. Lembre-se que a hora(hh:mm:ss) das máquinas deve ser idêntica. Ao subir os dois servidores, perceba que apenas um dos servidores fica disparando o trabalho(*job*) e quando um servidor é desligado, o outro assume o acionamento das *jobs*, mostrando, dessa forma, que o balanceamento de carga funciona corretamente!
