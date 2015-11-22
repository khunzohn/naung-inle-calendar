Naung Inle Calendar
-------------------------------
This repo hosts the source code of Naung Inle Calendar android app.
The repo can be accessed with SSH or with HTTPS.

Procedures to generate ssh key
-------------------------------
1) check whether ssh client is installed on local machine with the command
 > ssh -v
 list default key 
 > ls -a ~/.ssh
2) generate default key
 >ssh-keygen
 (provide a passphrase)
3)load the generated default key to ssh-agent
 >ps -e | grep [s]sh-agent (check whether ssh-agent is running or not)
 if not running use >ssh-agent /bin/bash command
 >ssh-add ~/.ssh/id_rsa (add private key)
 >ssh-add -l (list the key the agent is managing)

4)install your ssh key on bitbucket account 
 >avatar>setting>ssh keys>add key (open ssh key dialog)
 -give key label
 -copy public key and paste it in the public key field 
 on linux use >cat ~/.ssh/id_rsa.pub

5)change existing repo from https to ssh protocol
 -have a look at .git/config file
 -edit the https url to ssh url 'git@bitbucket.org:khunzohn/naunginlecalendar.git' for example
 -commit and push 


