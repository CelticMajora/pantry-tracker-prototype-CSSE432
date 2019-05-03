#!/bin/bash

while true
do
git checkout master
STATUS=git status -uno
if [ $STATUS = "" ]; then
	git up
	gradlew bootRun
fi
sleep 300
done