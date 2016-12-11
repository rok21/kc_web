#! /bin/bash

echo "cookin.."

mkdir tmp
cd tmp

git clone git@github.com:rok21/kc_web.git

cd kc_web
~/Public/activator-dist-1.3.12/bin/activator dist

ssh -i ~/.ssh/newkey.pem ubuntu@ec2-54-171-173-163.eu-west-1.compute.amazonaws.com << EOF
{
 sleep 2
 echo "trying kill existing process"
 
 sudo fuser -k 80/tcp
 
 echo "sudo fuser -k 80/tcp"
 
 sudo rm -r -f /home/ubuntu/kc_web-1.0
 sudo rm /home/ubuntu/kc_web-1.0.zip
 logout
} || {
 echo "catch exception!"
 logout
} 
EOF

scp -i ~/.ssh/newkey.pem target/universal/kc_web-1.0.zip ubuntu@ec2-54-171-173-163.eu-west-1.compute.amazonaws.com:~/

ssh -i ~/.ssh/newkey.pem ubuntu@ec2-54-171-173-163.eu-west-1.compute.amazonaws.com << EOF
 echo "starting the server.."
 unzip kc_web-1.0.zip
 sudo ./kc_web-1.0/bin/kc_web -Dhttp.port=80 >> weblog &
 logout
EOF

cd ..
cd ..
rm -r -f tmp

echo "all done go see http://54.171.173.163"