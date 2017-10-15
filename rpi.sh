#!/bin/sh

IP=192.168.1.18

echo 'build and scp to pi at $IP'

./gradlew build fatjar
scp build/libs/jConsoleImageViewer-all-1.0.0.jar pi@$IP:~/
scp viewImage.sh pi@$IP:~/
