 #SBA Project

There are two applicaitons 

projectmanager is a springBoot applicaiton deveoped using https://start.spring.io/ 
projectmanagerspa is a angular 7 applicaiton deveopeduisng https://cli.angular.io/


Pre-requistes  for using application 
Git Repository 
==========================
https://github.com/mprvn/SBA.git



For spa Install 
    node 10.16.3 version used for development 	
	Angulalr/cli : 7.3.9 version used for development 
	node-sass
	Visual Studio Code IDE for development

For Backend install
    Java version 1.8
	Mvn version 3
	Docker version 19.0.32
	Mysql latest version
	git latest version 
	
Frontend:
===================
For using the project cone the
   > git clone  https://github.com/mprvn/SBA.git
 appclication run 
      > cd projectmanagerspa
	  > npm install 
	  > npm install node-sass 
	  > ng serve

in Browser use httt://localhost:4200 

Backend: 
==============================
Check if maven is installed and path is setup propery 
https://www.mkyong.com/maven/how-to-install-maven-in-windows/
 
 > cd projectmanager 
 > use 'mvn clean install' to download all required dependencies 
 > use 'mvn clean install sonar:sonar' for generaton of reports and test code coverage
 > use 'mvn spring-boot:run' to run the backend applicaitons.
 
 you can access the api with below link, I have configured swagger to give detais about the api. 
 
 http://localhost:9090/swagger-ui.html



Using Docker:
===================

We need to run mysql first as backend is dependent on the mysql server 

>docker run --name=docker-mysql -p 3306:3306 --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=pass@word1" --env="MYSQL_DATABASE=cogdb" mysql
>For Frontend and backend run the docker build commnad to creat the image 
> docker build -t <docker-id>/projectmanager-IO

After creating image you need to run docker run command 
>  docker run --name taskp -p 9090:9090 <docker-id>/projectmanager-IO  --link docker-mysql:mysql
>  --link is deprecated in future and so we might need to access the complete IP of the service 
  you can get ther details by running 'docker inspect docker-mysql' (image name)
> We can run frontend normaly on the local machine or via docker wiht 'ng serve'



Note:
==============
Due to some Version probems with anguar-cli when front end fresh setup we might get some exceptions/warnings so please execute below commands

ng set --global warnings.packageDeprecation=false

If docker mysq does not connect properly 
  Check the images
  docker ps
  docker exec -it <mysql container name> /bin/bash 
  mysql -u root -p

  You wil be able to login into mysql command line
  execute 
  select host, user from mysql.user;
    

  docker stop docker-mysql
  docker start docker-mysql
Check these commands a





