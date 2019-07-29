安装ansible tower要保证空间够，默认说要20G的硬盘空间，但是在虚拟机上即使有这么多空间也报错，所以后来挪到实体机上安装了。
安装ansible需要很多辅助的rpm包，例如postgres，rabbitmq，这些都在它的网站中有下载，它默认使用postgres 9.5的，用其他会报错。
可以通过反编译python的代码去破解license。这个要涉及到python反编译工具的安装，其实就是用pip安装一下。
postgresql需要设置相关的账号密码，初始化一个数据库。

安装好后，需要用一个git的仓库保存playbook，我选用了gogs，但是gogs现在网络好像有些问题，不需要翻墙，但是很慢很慢，翻墙会好很多。

我曾经想用vagrant去安装ansible tower的镜像，但是网络不通，没办法。

https://docs.ansible.com/ansible/latest/user_guide/intro_getting_started.html

https://releases.ansible.com/ansible-tower/
http://www.ansible.com.cn/docs/tower.html


安装包：
https://releases.ansible.com/ansible-tower/setup-bundle/ansible-tower-setup-bundle-latest.el6.tar.gz

https://releases.ansible.com/ansible-tower/setup-bundle/


官方入门：
https://www.ansible.com/products/tower

https://docs.ansible.com/ansible-tower/latest/html/quickinstall/download_tower.html#bundled-install
https://docs.ansible.com/ansible-tower/

配置：
https://docs.ansible.com/ansible-tower/latest/html/administration/configure_tower_in_tower.html

安装
https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html

安装erlang
https://blog.csdn.net/fairytalefu217/article/details/84824123


postgresql安装
https://www.postgresql.org/download/linux/redhat/
Select version: 
Select platform: 
Select architecture: 
Install the repository RPM:
yum install https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
Install the client packages:
yum install postgresql11
Optionally install the server packages:
yum install postgresql11-server
Optionally initialize the database and enable automatic start:
/usr/pgsql-11/bin/postgresql-11-setup initdb
systemctl enable postgresql-11
systemctl start postgresql-11


postgres 用户创建
https://blog.csdn.net/eagle89/article/details/80363365


psql (11.4)
Type "help" for help.

postgres=# CREATE ROLE ansible;
CREATE ROLE
postgres=# ALTER ROLE ansible LOGIN ;            
ALTER ROLE
postgres=# ALTER ROLE ansible with password '123456'; 
ALTER ROLE
postgres=# CREATE DATABASE ansible;
CREATE DATABASE
postgres=# GRANT ALL on DATABASE ansible to ansible;
GRANT
postgres=# 

postgres=# ALTER ROLE ansible CREATEDB PASSWORD '123456' LOGIN;   
ALTER ROLE

[root@centos7 ~]# psql -U ansible -W -h 127.0.0.1
Password: 
psql (11.4)
Type "help" for help.

ansible=> 

rabbitmq安装
https://blog.csdn.net/hellozpc/article/details/81436980
访问： http://192.168.0.21:15672/#/
默认账号密码是： guest/guest，只能在本地访问；

[root@i5 tower_license]# ansible-tower-service restart
Restarting Tower
Redirecting to /bin/systemctl stop postgresql-9.6.service
Redirecting to /bin/systemctl stop rabbitmq-server.service
Redirecting to /bin/systemctl stop nginx.service
Redirecting to /bin/systemctl stop supervisord.service
Redirecting to /bin/systemctl start postgresql-9.6.service
Redirecting to /bin/systemctl start rabbitmq-server.service
Redirecting to /bin/systemctl start nginx.service
Redirecting to /bin/systemctl start supervisord.service
[root@i5 tower_license]# 

https://192.168.0.21/#/templates?template_search=page_size:20;order_by:name;type:workflow_job_template,job_template

介绍了ansible的安装和如何让自己免密登录某个主机：
https://www.jianshu.com/p/81f74e5ed821
反编译：
https://www.jianshu.com/p/03fd4cd3f677
用python反编译，然后修改
https://blog.csdn.net/q315686687/article/details/80681642
python -m py_compile __init__.py
python -O -m py_compile __init__.py

启动ansible tower
ansible-tower-service restart

pip的安装
https://pip.pypa.io/en/stable/installing/
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python get-pip.py


gogs: jluo/123456
http://192.168.0.21:3000/jluo/ansible.git

Thank you. We will review your request and get back to you soon.

Verification ID: 7db0a8c6
Message ID: 7a8dbeee-852f-4a3c-9af9-ccf749201be2
license：
https://www.jianshu.com/p/03fd4cd3f677


Thank you. We will review your request and get back to you soon.

Verification ID: bc8d984e
Message ID: 7b79b084-8629-47be-ae7a-6f71254e69e0


ansible的使用：
https://docs.ansible.com/ansible/latest/


$ vagrant init ansible/tower
$ vagrant up --provider virtualbox
$ vagrant ssh


vagrant box add https://mirrors.tuna.tsinghua.edu.cn/ubuntu-cloud-images/bionic/current/bionic-server-cloudimg-amd64-vagrant.box --name ubuntu/bionic
Ansible Tower Deployment
========================

This collection of files provides a complete set of playbooks for deploying
the Ansible Tower software to a single-server installation. It is also to
install Tower to the local machine, or to a remote machine reachable by SSH.

For quickly getting started with installation and setup instructions, refer to:

- Ansible Tower Quick Installation Guide -- http://docs.ansible.com/ansible-tower/latest/html/quickinstall/index.html
- Ansible Tower Quick Setup Guide -- http://docs.ansible.com/ansible-tower/latest/html/quickstart/index.html

For more indepth documentation, refer to:

- Ansible Tower Installation and Reference Guide -- http://docs.ansible.com/ansible-tower/latest/html/installandreference/index.html
- Ansible Tower User Guide -- http://docs.ansible.com/ansible-tower/latest/html/userguide/index.html
- Ansible Tower Administration Guide -- http://docs.ansible.com/ansible-tower/latest/html/administration/index.html
- Ansible Tower API Guide -- http://docs.ansible.com/ansible-tower/latest/html/towerapi/index.html

To install or upgrade, start by editing the inventory file in this directory.
Uncomment and change the password from 'password' for the 3 variables below.
* admin_password
* redis_password
* pg_password

Tower can be installed in 3 different modes:
1. 单机模式 On a single machine. This is the default, and will install in this mode with
   no modifications to the inventory file.
2. 带PostgreSQL的单机模式 On a single machine with a remote PostgreSQL database. Supplying the pg_host
   and pg_port variables will trigger this mode of installation.
3. 高可用模式 High Availability, multiple machines with a remote PostgreSQL database.
   Adding hosts to the [secondary] inventory group will trigger this mode of
   installation. Note that pg_host and pg_port are also required.

Now you are ready to run ./setup.sh. Note that root access to the remote machines is required. With Ansible, this can be achieved in many different.

Below are a few examples.
* ansible_ssh_user=root ansible_ssh_password="your_password_here" inventory
  host or group variables
* ansible_ssh_user=root ansible_ssh_private_key_file="path_to_your_keyfile.pem"
  inventory host or group variables
* ANSIBLE_BECOME_METHOD='sudo' ANSIBLE_BECOME=True ./setup.sh
* ANSIBLE_SUDO=True ./setup.sh

> *WARNING*: The playbook will overwrite the content
> of `pg_hba.conf` and strip all comments from `supervisord.conf`.  Run this
> only on a clean virtual machine if you are not ok with this behavior.