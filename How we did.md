{\rtf1\ansi\ansicpg1252\cocoartf1504\cocoasubrtf810
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;\red255\green255\blue255;\red0\green0\blue0;
\red169\green14\blue26;}
{\*\expandedcolortbl;;\csgenericrgb\c0\c0\c0;\cssrgb\c100000\c100000\c100000;\cssrgb\c0\c0\c0;
\cssrgb\c72941\c12941\c12941;}
\paperw11900\paperh16840\margl1440\margr1440\vieww33400\viewh18200\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs22 \cf2 How we did:\
\
Navigate to virtual machine: \
\
cd \'85/db_course_nosql/vm\
\
Start the virtual machine by the following command: \
\
Vagrant up\
\
We connected to the virtual machine by the following command: \
\
Vagrant ssh\
\
Now we have to download a dataset of Twitter tweets from http://help.sentiment140.com/for-students/ by the following command: \
\
\pard\pardeftab720\partightenfactor0
\cf2 \cb3 \expnd0\expndtw0\kerning0
wget http://cs.stanford.edu/people/alecmgo/trainingandtestdata.zip\cb1 \kerning1\expnd0\expndtw0 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf2 \
We install the VM unzip package, because it is not installed by default. We install it via the following command: \
\
\pard\pardeftab720\partightenfactor0
\cf2 \cb3 \expnd0\expndtw0\kerning0
sudo apt-get install unzip\
\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf2 \cb1 \kerning1\expnd0\expndtw0 We uncompress the twitter dataset by the following command:\
\
\pard\pardeftab720\partightenfactor0
\cf2 \cb3 \expnd0\expndtw0\kerning0
unzip trainingandtestdata.zip\
\
\pard\pardeftab720\partightenfactor0
\cf2 \cb1 To make use of the \cb3 --headerline\cb1  switch when importing the data with \cb3 mongo import\cb1 , we add a headerline accordingly:\
\
\pard\pardeftab720\partightenfactor0
\cf2 \cb3 sed -i \cb3 '1s;^;polarity,id,date,query,user,text\\n;'\cb3  training.1600000.processed.noemoticon.csv\
\
To import the data into the mongo database we use the mongoDB CLI import tool. \cb1 \
\
\cb3 mongoimport --drop --db social_net --collection tweets --type csv --headerline --file training.1600000.processed.noemoticon.csv\
\
Now we want to use enter the mongo database, we do that be the following command: \
\
Mongo\
\
In the mongo database we want to give our database a name, we call it social_net, by the following command:\
\
use social_net\
\
To be sure the data have been inserted we want to find and count the number of tweets, we have used the following command the do that: \
\
Db.tweets.find().count()\
\
\
\
\
\cb1 \kerning1\expnd0\expndtw0 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf2 \
}