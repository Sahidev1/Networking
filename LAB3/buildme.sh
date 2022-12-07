#!/bin/bash
set -e

javac Main.java
javac MailReader.java
javac MailSender.java

echo "Build completed successfully!"

