# shvaEtlProj - How to invoke it ?

In order to invoke the program. You should locate an input folder according to the value in application.properties -> inputFolder :

for example:

inputFolder=c:\shai\data

the files in c:\shai\data shoud be :

**C:\shai\data\<computer>\<month/year>\<file with date>.wl**

e.g. :
* C:\shai\data\BSGNY\0621\BSGNY_CALC060221.wl
* C:\shai\data\BSGNY\0621\BSGNY_CALC070221.wl
* C:\shai\data\BSGNY\0621\BSGNY_CALC080221.wl

the ETL will picked it up  when the time to be schedule, process(aggregate datas) it and upload the calculate data(transactions) into DB (mySQL)

you can coonfig any DB you want (in application.properties):
***
- spring.jpa.hibernate.ddl-auto=update
- spring.datasource.url=jdbc:mysql://localhost:3306/db
- spring.datasource.username=user
- spring.datasource.password=password
***


run as Java Application -> com.shva.etl.Application.java

**Good Luck!!!**
