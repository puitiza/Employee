# Employee
 Este es un proyecto backend el cual esta desarrollado en SpringBoot conectandose a una Base de datos en Mysql

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Para correr el proyecto es necesario tener una BD de MySql puede ser en tu docker con este comando
```sh
$ docker pull mysql
$ docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
  (docker run -p 3306:3306 --name mysql22 -e MYSQL_ROOT_PASSWORD=root -d mysql)
```

**Crea un usuario con todos los privilegios para acceder a tu BD !**
<img width="783" alt="image" src="https://user-images.githubusercontent.com/24264799/180367388-3c85ff17-5507-4b3f-91f3-59dcd0219f27.png">

https://medium.com/tech-learn-share/docker-mysql-access-denied-for-user-172-17-0-1-using-password-yes-c5eadad582d3

Listo ya puedes usar la BD


<img width="785" alt="image" src="https://user-images.githubusercontent.com/24264799/180367483-262bc6bf-e4bd-4466-b7ef-356f18624b1e.png">
