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

- Client
  - docker build
  - docker pull
  - docker run
- Docker_Host
  - docker daemon
  - containers
  - images
- Registry
  - nginx
  - redis

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

## Docker 命令

### 镜像命令

### 容器命令

### 操作命令

## Docker 镜像

## Docker 容器数据卷

## DockerFile

## Docker 网络原理

## IDEA 整合 Docker

## Docker Compose

## Docker Swarm

## CI/CD  jenkins