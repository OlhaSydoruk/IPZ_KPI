# ia13-okayanchenko-dav
=======

### Installation

1.Copy the repository

```sh
$ git clone https://github.com/OlhaSydoruk/IPZ_KPI.git
```

2.Copying the .env file

```sh
$ cp .env-test .env
```

3.Deploying the project

```sh
$ docker compose -p ipz-lab4-group-15 up -d --build
```

4
```sh
done :)
```

### Additionally

- Stop an ongoing project
```sh
  $ docker compose stop
  ```
- Delete project containers
```sh
  $ docker compose down -v
  ```