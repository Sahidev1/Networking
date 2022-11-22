#!/bin/bash


javac client/ChatClient.java
javac server/ChatServer.java

echo $'\nUsage:\nRun server: java server.ChatServer [HOST IP] [HOST PORT]
Run client: java client.ChatClient [SERVER IP] [SERVER PORT]\n'

