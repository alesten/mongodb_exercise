# Result for Mongo Database Exercise

This document contains both the result and how we did to get the data up and running on the VM

## Number of Twitter accounts
1. 659178

### Most active users
1. lost_dog, 549
2. webwoke, 345
3. tweetpet, 310
4. SallytheShizzle, 281
5. VioletsCRUK, 279
6. mcraddictal, 276
7. tsarnick, 248
8. what_bugs_u, 246
9. Karen230683, 238
10. DarkPiano, 236

### Most linked users
1. lost_dog, 549
2. tweetpet, 310
3. VioletsCRUK, 251
4. what_bugs_u, 246
5. tsarnick, 245
6. SallytheShizzle, 229
7. mcraddictal, 217
8. Karen230683, 216
9. keza34, 211
10. DarkPiano, 202

### Top happy users
1. Wingman29, 4
2. becca210, 4
3. _EmilyYoung, 4
4. katarinka, 4
5. ersle, 4

### Top grumpy users
1. ElleCTF, 0
2. Karoli, 0
3. scotthamilton, 0
4. _TheSpecialOne_, 0
5. mattycus, 0

## How we did:

Navigate to virtual machine: 

cd …/db_course_nosql/vm

Start the virtual machine by the following command: 

Vagrant up

Connect to the virtual machine by the following command: 

Vagrant ssh

Now we have to download a dataset of Twitter tweets from http://help.sentiment140.com/for-students/ we done that by the following command: 

wget http://cs.stanford.edu/people/alecmgo/trainingandtestdata.zip

We install the VM unzip package, because it is not installed by default. We install it by the following command: 

sudo apt-get install unzip

We uncompress the twitter dataset by the following command:

unzip trainingandtestdata.zip

To make use of the --headerline switch when importing the data with mongo import, we add a headerline accordingly:

sed -i '1s;^;polarity,id,date,query,user,text\n;' training.1600000.processed.noemoticon.csv

To import the data into the mongo database we use the mongoDB CLI import tool. 

mongoimport --drop --db social_net --collection tweets --type csv --headerline --file training.1600000.processed.noemoticon.csv

Now we want to use enter the mongo database, we do that be the following command: 

Mongo

In the mongo database we want to give our database a name, we call it social_net, by the following command:

use social_net

To be sure the data have been inserted we want to find and count the number of tweets, we have used the following command the do that: 

Db.tweets.find().count()
