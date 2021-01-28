# Docker

## Docker 概述

- Docker 为什么会出现

  以前是开发人员开发 jar 包，运维做部署

  现在是开发一个人做打包部署上线

  java -> jar -> 打包项目带上环境（Docker 环境）-> Docker 仓库 -> 下载发布的对象直接运行

- Docker 的思想

  隔离，打包装箱，解决多个应用端口冲突

- Docker [文档](https://docs.docker.com/)，[仓库](https://hub.docker.com/) 

- Docker 与 虚拟机不同

  原本虚拟机技术占用资源多（Kernel + Libs + Apps），冗余步骤多，启动慢

  容器化（虚拟化）技术不是一个完整的系统（Kernel + Containers [Libs + Apps]）

  - 传统虚拟机，虚拟出一套硬件，运行一个完整的操作系统，然后再这个系统上安装和运行软件
  - 容器内的应用直接运行在宿主机的内容，容器是没有自己的内核的，也没有虚拟硬件，所以轻便
  - 每个容器间是互相隔离的，每个容器内都有一个属于自己的文件系统，互不影响

- Docker DevOps 开发&运维

  - 更快速地交付和部署

    一件运行打包镜像发布测试，一键运行

  - 更便捷地升级和扩缩容

  - 更简单的系统运维

    在容器化后，开发测试环境高度一致

  - 更高效的计算资源利用

    Docker 是内核级的虚拟化，一个物理机上可以运行很多容器实例

## Docker 安装

__Docker 的基本组成__ 

<img width="75%" src="./images/dockerArchi.png">

镜像 image：Docker 镜像就如同一个模板，可以通过这个模板来创建服务，通过 run 镜像创建多个容器，最终服务运行或者项目运行就是在容器中

容器 container：Docker 利用容器技术，独立运行一个组应用，通过镜像来创建（启动，停止，删除，基本命令），如同简易的 Linux 系统

仓库 repository：存放镜像的地方，分为共有和私有仓库

__安装 Docker__ 

系统版本

```shell
NAME="Ubuntu"
VERSION="16.04.7 LTS (Xenial Xerus)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 16.04.7 LTS"
VERSION_ID="16.04"
HOME_URL="http://www.ubuntu.com/"
SUPPORT_URL="http://help.ubuntu.com/"
BUG_REPORT_URL="http://bugs.launchpad.net/ubuntu/"
VERSION_CODENAME=xenial
UBUNTU_CODENAME=xenial
```

卸载旧版本

```shell
sudo apt-get remove docker docker-engine docker.io containerd runc
```

设置仓库

```shell
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
```

添加 Docker 官方 GPG 密钥

```shell
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
```

添加 Docker 远程仓库

```shell
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
```

或者用 aliyun 仓库

```shell
sudo add-apt-repository \
   "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg \
   $(lsb_release -cs) \
   stable"
## http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg
## https://mirrors.ustc.edu.cn/docker-ce/linux/ubuntu/gpg
```

安装 Docker 引擎

ce - community edition

ee - enterprise edition

```shell
sudo apt-get update ## 要先更新仓库目录才能定位到仓库
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

测试

```shell
sudo docker run hello-world
```

- 寻找本地镜像
- 未找到时去仓库找镜像
- 从仓库拉取镜像

查看下载的镜像

```shell
docker images
```

卸载 Docker

```shell
sudo apt-get purge docker-ce docker-ce-cli containerd.io
sudo rm -rf /var/lib/docker
## Docker 默认工作路径
```

__Hello World 流程__ 

开始 -> 寻找本地寻找镜像 -> 如果有镜像，则使用本地镜像 -> 如果没有去云端下载镜像 -> 如果找不到，则返回错误，如果能找到，则下载镜像到本地并运行

<img width="85%" src="./images/dockerHW.png">

__底层原理__ 

- Docker 工作原理

  Docker 是一个 Client-Server 结构的系统，Docker 的守护进程运行在主机上，通过 Socket 从客户端访问

  DockerServer 接收到 Docker-Client 的指令，就会执行这个指令

  <img width="85%" src="./images/dockerCS.png">

- Docker 为什么比虚拟机快

  - Docker 比虚拟机有更少的抽象层

    <img width="75%" src="./images/dockerAndVM.png">

    所以说，新建一个容器的时候，Docker 不需要像虚拟机一样重新加载一个操作系统，避免引导操作，虚拟机是加载 Guest OS，分钟级别的，而 Docker 是利用宿主机的操作系统，省略了这个复杂的过程，是秒级的

## Docker 命令

- [命令手册](https://docs.docker.com/reference/) 

- 帮助命令

  ```shell
  docker version
  docker info
  docker <command> --help
  ```

- 镜像命令

  ```shell
  docker images # 查看本地主机上的镜像
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker images
  REPOSITORY    TAG       IMAGE ID       CREATED         SIZE
  hello-world   latest    bf756fb1ae65   13 months ago   13.3kB
  
  # REPOSITORY 镜像的仓库源
  # TAG 镜像的标签
  # IMAGE ID 镜像的 ID
  # CREATED 镜像创建时间
  # SIZE 镜像的大小
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker images --help
  
  Usage:  docker images [OPTIONS] [REPOSITORY[:TAG]]
  
  List images
  
  Options:
    -a, --all             # 列出所有镜像
    -q, --quiet           # 只显示镜像 ID
  
  ##########
  docker search # 搜索镜像
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker search mysql
  NAME                              DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
  mysql                             MySQL is a widely used, open-source relation…   10428     [OK]       
  
  #
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker search --help
  
  Usage:  docker search [OPTIONS] TERM
  
  Search the Docker Hub for images
  
  Options:
    -f, --filter=STARS=3000 # 搜索出镜像是 STARS 大于 3000 的
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker search mysql --filter=STARS=3000
  NAME      DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
  mysql     MySQL is a widely used, open-source relation…   10428     [OK]       
  mariadb   MariaDB is a community-developed fork of MyS…   3870      [OK]    
  
  ##########
  docker pull # 下载镜像
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker pull mysql
  Using default tag: latest # 如果不写版本，默认最近
  latest: Pulling from library/mysql
  a076a628af6f: Pull complete # 分层下载，docker image 核心
  f6c208f3f991: Pull complete 
  88a9455a9165: Pull complete 
  406c9b8427c6: Pull complete 
  7c88599c0b25: Pull complete 
  25b5c6debdaf: Pull complete 
  43a5816f1617: Pull complete 
  1a8c919e89bf: Pull complete 
  9f3cf4bd1a07: Pull complete 
  80539cea118d: Pull complete 
  201b3cad54ce: Pull complete 
  944ba37e1c06: Pull complete 
  Digest: sha256:feada149cb8ff54eade1336da7c1d080c4a1c7ed82b5e320efb5beebed85ae8c # 签名
  Status: Downloaded newer image for mysql:latest
  docker.io/library/mysql:latest # 真实地址
  
  docker pull mysql
  docker pull docker.io/library/mysql:latest
  
  ##########
  docker rmi
  
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker rmi -f mysql:latest
  # 根据容器删除
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker rmi -f c8562eaf9d81
  # 根据镜像 ID 删除
  root@iZgw8c1ercy5vvlhqtx2v7Z:~# docker rmi -f $(docker images -aq)
  # 删除所有的镜像
  ```

- 容器命令

- 操作命令

## Docker 镜像

## Docker 容器数据卷

## DockerFile

## Docker 网络原理

## IDEA 整合 Docker

## Docker Compose

## Docker Swarm

## CI/CD  jenkins