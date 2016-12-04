#! /bin/bash

echo "cookin.."

mkdir tmp
cd tmp

git clone git@github.com:rok21/kc_web.git

cd kc_web
~/Public/activator-dist-1.3.12/bin/activator dist


scp -i ~/Downloads/testKey1.pem remotescript.sh ubuntu@ec2-54-229-142-68.eu-west-1.compute.amazonaws.com:~/
echo "remotescript.sh copied"
sleep 2

ssh -i ~/Downloads/testKey1.pem ubuntu@ec2-54-229-142-68.eu-west-1.compute.amazonaws.com << EOF
{
 sleep 2
 echo "trying kill existing process"
 
 chmod +x remotescript.sh
 ./remotescript.sh
 rm remotescript.sh
 
 rm -r -f /home/ubuntu/kc_web-1.0
 rm /home/ubuntu/kc_web-1.0.zip
 logout
} || {
 echo "catch exception!"
 logout
} 
EOF

scp -i ~/Downloads/testKey1.pem target/universal/kc_web-1.0.zip ubuntu@ec2-54-229-142-68.eu-west-1.compute.amazonaws.com:~/

ssh -i ~/Downloads/testKey1.pem ubuntu@ec2-54-229-142-68.eu-west-1.compute.amazonaws.com << EOF
 echo "starting the server.."
 unzip kc_web-1.0.zip
 ./kc_web-1.0/bin/kc_web >> weblog &
 logout
EOF

cd ..
cd ..
rm -r -f tmp

echo "all done go see http://54.229.142.68:9000"