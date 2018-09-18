#!/bin/sh

#This is a start script for mocca on linux
java -Djavax.net.ssl.trustStoreType=jks -Djavax.net.ssl.keyStoreType=jks -jar "bin/mocca.jar"
