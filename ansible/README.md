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

postgres                   postgresql96-check-db-dir  postgresql96-setup         
[root@linux ansible-tower-setup-bundle-3.5.1-1.el7]# /usr/pgsql-9.6/bin/postgresql96-setup initdb
Initializing database ... OK

[root@linux ansible-tower-setup-bundle-3.5.1-1.el7]# systemctl enable postgresql-9.6.service 
Created symlink from /etc/systemd/system/multi-user.target.wants/postgresql-9.6.service to /usr/lib/systemd/system/postgresql-9.6.service.
[root@linux ansible-tower-setup-bundle-3.5.1-1.el7]# systemctl start postgresql-9.6.service 


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
https://blog.csdn.net/hellozpc/article/details/81436980
访问： http://192.168.0.21:15672/#/
       http://192.168.0.100:25672
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

使用这个
[root@linux tmp]# /opt/rh/rh-python36/root/usr/bin/python3 -m compileall __init__.pyc 

/var/lib/awx/venv/awx/lib64/python3.6/site-packages/tower_license

启动ansible tower
ansible-tower-service restart

安装公钥私钥
ssh-keygen -N "" -b 4096 -t rsa -C "Ansible Public Key"
ssh-copy-id -i /root/.ssh/id_rsa root@localhost​

测试ansible
[root@linux ansible-tower-setup-bundle-3.5.1-1.el7]# ansible localhost -m command -a ls
127.0.0.1 | CHANGED | rc=0 >>
anaconda-ks.cfg
ansible
initial-setup-ks.cfg
releases.ansible.com


pip的安装
https://pip.pypa.io/en/stable/installing/
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python get-pip.py


反编译工具：
[root@linux py]# python -O -m py_compile  __init__.pyc           
Sorry: TypeError: compile() expected string without null bytes[root@linux py]# pip install uncompyle6
DEPRECATION: Python 2.7 will reach the end of its life on January 1st, 2020. Please upgrade your Python as Python 2.7 won't be maintained after that date. A future version of pip will drop support for Python 2.7. More details about Python 2 support in pip, can be found at https://pip.pypa.io/en/latest/development/release-process/#python-2-support
Collecting uncompyle6
  Downloading https://files.pythonhosted.org/packages/7b/15/551a313a11d80c9cab9a0772a212ccda9725559d364b22f26302b65e3525/uncompyle6-3.3.5-py27-none-any.whl (234kB)
     |████████████████████████████████| 235kB 481kB/s 
Collecting spark-parser<1.9.0,>=1.8.7 (from uncompyle6)
  Downloading https://files.pythonhosted.org/packages/d4/7f/8ac36ff59340cd63c46370c366fd023f3a458d4819cb58df40072d470e07/spark_parser-1.8.9-py2-none-any.whl
Collecting xdis<4.1.0,>=4.0.2 (from uncompyle6)
  Downloading https://files.pythonhosted.org/packages/56/05/192bfa6c10068a4d6cbed0d5d86f47e589d61783b2069c6321e7757043fe/xdis-4.0.3-py27-none-any.whl (94kB)
     |████████████████████████████████| 102kB 10.3MB/s 
Collecting click (from spark-parser<1.9.0,>=1.8.7->uncompyle6)
  Downloading https://files.pythonhosted.org/packages/fa/37/45185cb5abbc30d7257104c434fe0b07e5a195a6847506c074527aa599ec/Click-7.0-py2.py3-none-any.whl (81kB)
     |████████████████████████████████| 81kB 8.6MB/s 
Installing collected packages: click, spark-parser, xdis, uncompyle6
Successfully installed click-7.0 spark-parser-1.8.9 uncompyle6-3.3.5 xdis-4.0.3

反编译工具使用： https://www.cnblogs.com/anliven/p/9185549.html


            available_instances = int(attrs.get('instance_count', None) or 0)
            available_instances = 99999
            attrs['license_type'] = 'enterprise'

gogs: jluo/123456
http://192.168.0.21:3000/jluo/ansible.git

gogs/123456

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




安装ansible tower的RPM包
=====================================================================================
[root@linux epel-7-x86_64]# rpm -ivh *.rpm
warning: ansible-2.8.0-1.el7ae.noarch.rpm: Header V3 RSA/SHA256 Signature, key ID fd431d51: NOKEY
error: Failed dependencies:
        python-jinja2 is needed by ansible-2.8.0-1.el7ae.noarch
        python-paramiko is needed by ansible-2.8.0-1.el7ae.noarch
        python-jinja2 is needed by ansible-2.8.2-1.el7ae.noarch
        python-paramiko is needed by ansible-2.8.2-1.el7ae.noarch
        libtcl8.5.so()(64bit) is needed by postgresql96-pltcl-9.6.6-1PGDG.el7.x86_64
        tcl is needed by postgresql96-pltcl-9.6.6-1PGDG.el7.x86_64
        python-docutils is needed by python2-daemon-2.1.2-7.el7at.noarch
        socat is needed by rabbitmq-server-3.7.4-2.el7at.noarch
[root@linux epel-7-x86_64]# yum install socat
Loaded plugins: fastestmirror, langpacks
Loading mirror speeds from cached hostfile
 * base: mirror.0x.sg
 * extras: mirrors.aliyun.com
 * updates: mirrors.aliyun.com
Resolving Dependencies
--> Running transaction check
---> Package socat.x86_64 0:1.7.3.2-2.el7 will be installed
--> Finished Dependency Resolution

Dependencies Resolved

============================================================================================================================================================================================
 Package                                    Arch                                        Version                                             Repository                                 Size
============================================================================================================================================================================================
Installing:
 socat                                      x86_64                                      1.7.3.2-2.el7                                       base                                      290 k

Transaction Summary
============================================================================================================================================================================================
Install  1 Package

Total download size: 290 k
Installed size: 1.1 M
Is this ok [y/d/N]: y
Downloading packages:
socat-1.7.3.2-2.el7.x86_64.rpm                                                                                                                                       | 290 kB  00:00:00     
Running transaction check
Running transaction test
Transaction test succeeded
Running transaction
  Installing : socat-1.7.3.2-2.el7.x86_64                                                                                                                                               1/1 
  Verifying  : socat-1.7.3.2-2.el7.x86_64                                                                                                                                               1/1 

Installed:
  socat.x86_64 0:1.7.3.2-2.el7                                                                                                                                                              

Complete!
[root@linux epel-7-x86_64]# yum install tcl python-jinja2 python-paramiko python-jinja2 python-paramiko libtcl
Loaded plugins: fastestmirror, langpacks
Loading mirror speeds from cached hostfile
 * base: mirror.0x.sg
 * extras: mirrors.aliyun.com
 * updates: mirrors.aliyun.com
No package libtcl available.
Resolving Dependencies
--> Running transaction check
---> Package python-jinja2.noarch 0:2.7.2-3.el7_6 will be installed
--> Processing Dependency: python-babel >= 0.8 for package: python-jinja2-2.7.2-3.el7_6.noarch
--> Processing Dependency: python-markupsafe for package: python-jinja2-2.7.2-3.el7_6.noarch
---> Package python-paramiko.noarch 0:2.1.1-9.el7 will be installed
---> Package tcl.x86_64 1:8.5.13-8.el7 will be installed
--> Running transaction check
---> Package python-babel.noarch 0:0.9.6-8.el7 will be installed
---> Package python-markupsafe.x86_64 0:0.11-10.el7 will be installed
--> Finished Dependency Resolution

Dependencies Resolved

============================================================================================================================================================================================
 Package                                            Arch                                    Version                                          Repository                                Size
============================================================================================================================================================================================
Installing:
 python-jinja2                                      noarch                                  2.7.2-3.el7_6                                    updates                                  518 k
 python-paramiko                                    noarch                                  2.1.1-9.el7                                      updates                                  269 k
 tcl                                                x86_64                                  1:8.5.13-8.el7                                   base                                     1.9 M
Installing for dependencies:
 python-babel                                       noarch                                  0.9.6-8.el7                                      base                                     1.4 M
 python-markupsafe                                  x86_64                                  0.11-10.el7                                      base                                      25 k

Transaction Summary
============================================================================================================================================================================================
Install  3 Packages (+2 Dependent packages)

Total download size: 4.0 M
Installed size: 14 M
Is this ok [y/d/N]: y
Downloading packages:
(1/5): python-paramiko-2.1.1-9.el7.noarch.rpm                                                                                                                        | 269 kB  00:00:00     
(2/5): python-jinja2-2.7.2-3.el7_6.noarch.rpm                                                                                                                        | 518 kB  00:00:00     
(3/5): python-markupsafe-0.11-10.el7.x86_64.rpm                                                                                                                      |  25 kB  00:00:01     
(4/5): python-babel-0.9.6-8.el7.noarch.rpm                                                                                                                           | 1.4 MB  00:00:08     
(5/5): tcl-8.5.13-8.el7.x86_64.rpm                                                                                                                                   | 1.9 MB  00:03:07     
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Total                                                                                                                                                        22 kB/s | 4.0 MB  00:03:07     
Running transaction check
Running transaction test
Transaction test succeeded
Running transaction
  Installing : python-markupsafe-0.11-10.el7.x86_64                                                                                                                                     1/5 
  Installing : python-babel-0.9.6-8.el7.noarch                                                                                                                                          2/5 
  Installing : python-jinja2-2.7.2-3.el7_6.noarch                                                                                                                                       3/5 
  Installing : python-paramiko-2.1.1-9.el7.noarch                                                                                                                                       4/5 
  Installing : 1:tcl-8.5.13-8.el7.x86_64                                                                                                                                                5/5 
  Verifying  : python-babel-0.9.6-8.el7.noarch                                                                                                                                          1/5 
  Verifying  : 1:tcl-8.5.13-8.el7.x86_64                                                                                                                                                2/5 
  Verifying  : python-jinja2-2.7.2-3.el7_6.noarch                                                                                                                                       3/5 
  Verifying  : python-paramiko-2.1.1-9.el7.noarch                                                                                                                                       4/5 
  Verifying  : python-markupsafe-0.11-10.el7.x86_64                                                                                                                                     5/5 

Installed:
  python-jinja2.noarch 0:2.7.2-3.el7_6                              python-paramiko.noarch 0:2.1.1-9.el7                              tcl.x86_64 1:8.5.13-8.el7                             

Dependency Installed:
  python-babel.noarch 0:0.9.6-8.el7                                                          python-markupsafe.x86_64 0:0.11-10.el7                                                         

Complete!
[root@linux epel-7-x86_64]# yum install tcl python-jinja2 python-paramiko python-jinja2 python-paramiko libtcl




安装ansible依赖包
https://releases.ansible.com/ansible/
[root@linux epel-7-x86_64]# rpm -ivh *.rpm   
warning: ansible-2.8.0-1.el7ae.noarch.rpm: Header V3 RSA/SHA256 Signature, key ID fd431d51: NOKEY
error: Failed dependencies:
        python-docutils is needed by python2-daemon-2.1.2-7.el7at.noarch
[root@linux epel-7-x86_64]# yum install python2-daemon



要看的link:
https://docs.ansible.com/ansible/latest/user_guide/vault.html
https://docs.ansible.com/ansible/latest/user_guide/playbooks.html
