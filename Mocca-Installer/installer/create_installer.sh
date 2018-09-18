#!/bin/bash

# check version and snapshot

WORKINGDIR=($PWD)
echo $WORKINGDIR
cd ../../BKUWebStart/target
VERSIONNUMBER=$(find -name "BKUWebStart*.zip")
VERSIONNUMBER=${VERSIONNUMBER#.*-}
LENGTH=${#VERSIONNUMBER}
VERSION=${VERSIONNUMBER:0:LENGTH-4}


cd $WORKINGDIR
echo Version number $VERSION

array=(win linux mac tmp)

for i in "${array[@]}"
do
	echo creating directory $i
	rm -rf $i
	mkdir $i
	mkdir $i/bin
done

cp ../../BKUWebStart/target/BKUWebStart-$VERSION.zip tmp/mocca-$VERSION.zip
cd tmp 
unzip mocca-$VERSION.zip

# remove unnecessary files 
rm -f mocca.jnlp
rm -f player.jnlp
rm -f mocca-$VERSION.zip
rm -rf bin
rm -rf img
mv BKUWebStart-1.3.35-SNAPSHOT.jar mocca.jar

#prepare packages 
echo preparing packages...
cd .. 
cp -r tmp/* win/bin
cp -r tmp/* linux/bin
cp -r tmp/* mac/bin 
cp files/win/mocca_win.bat win/mocca_win.bat
cp files/linux/mocca_linux.sh linux/mocca_linux.sh 
cp -r files/mac/Mocca.app mac/Mocca.app
cp -r files/icon win/
echo preparing packages... done!
echo preparing windows package... done!

#generating linux package
echo generating linux zip...
cd linux 
chmod -R +x * 
zip -r mocca-$VERSION.zip * 
cd ..
echo generating linux zip... done!


#generating mac package 
echo generating mac app...
cd mac
rm -rf Mocca.app/Contents/Java/*
cp -r bin/ Mocca.app/Contents/Java/
echo generating mac app... done!

#cleanup
rm -rf tmp



