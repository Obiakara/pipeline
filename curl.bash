#!/bin/bash

let FOUND=0

last_pid=$!

let KILL=9

sleep 2

rm -Rf HTTPGETHello.txt
rm -Rf GETHello.txt
rm -Rf POSTProperty201.txt
rm -Rf POSTProperty400.txt
rm -Rf POSTProperty403.txt
rm -Rf GETPropertyId200.txt
rm -Rf GETPropertyId400.txt
rm -Rf GETPropertyId404.txt
rm -Rf DELETEPropertyId401.txt
rm -Rf PUTPropertyId200.txt

#HTTP
curl --silent -k -X GET "http://ec2-52-87-228-197.compute-1.amazonaws.com:12166/hello" -H "accept: application/json" > HTTPGETHello.txt

#HTTPS
curl --silent -k -X GET "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/hello" -H "accept: application/json" > GETHello.txt

#GET PROPERTY
curl -k -X GET "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties" -H "accept: application/json"

#POST PROPERTY (201 - Added Sucessfully)
curl --silent -k -X POST "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties" -H "accept: application/json" -H "apiAuth: cs4783FTW" -H "Content-Type: application/json" -d "{ \"address\": \"123 Curl Ave\", \"state\": \"TX\", \"city\": \"San Curl\", \"zip\": 78222}" > POSTProperty201.txt

#POST PROPERTY (404 - Bad Request || State more than 2 letters )
curl --silent -k -X POST "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties" -H "accept: application/json" -H "apiAuth: cs4783FTW" -H "Content-Type: application/json" -d "{ \"address\": \"999 Test Ave\", \"state\": \"TEXAS\", \"city\": \"Dallas\", \"zip\": 78222}" > POSTProperty400.txt


#POST PROPERTY (404 - Not Found || Zip more than 5 characters)
curl --silent -k -X POST "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties" -H "accept: application/json" -H "apiAuth: cs4783FTW" -H "Content-Type: application/json" -d "{ \"address\": \"999 Test Ave\", \"state\": \"TX\", \"city\": \"Dallas\", \"zip\": 782522}" > POSTProperty404.txt


#POST PROPERTY (403 - Forbidden || w/out API-KEY)
curl --silent -k -X POST "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"address\": \"123 Test Ave\", \"state\": \"TX\", \"city\": \"San Antonio\", \"zip\": 78222}" >POSTProperty403.txt


#GET PROPERTY<id> (200 - Success)
curl --silent -k -X GET "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties/11" -H "accept: application/json" > GETPropertyId200.txt

#GET PROPERTY<id> (400 - Bad Request || id  != int)
curl --silent -k -X GET "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties/x" -H "accept: application/json" > GETPropertyId400.txt

#GET PROPERTY<id> (404 - Not found)
curl --silent -k -X GET "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties/6000" -H "accept: application/json" > GETPropertyId404.txt

#DELETE PROPERTY<id> (403 - Forbidden)
curl --silent -k -X DELETE "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties/16" -H "accept: application/json" > DELETEPropertyId401.txt

#PUT PROPERTY<id>(200)
curl --silent -k -X PUT "https://ec2-52-87-228-197.compute-1.amazonaws.com:12165/properties/13" -H "accept: application/json" -H "apiAuth: cs4783FTW" -H "Content-Type: application/json" -d "{ \"address\": \"22 Jills Crescent\", \"state\": \"VA\", \"city\": \"San Antonio\", \"zip\": 49863}" > PUTPropertyId200.txt

if grep "Hello Yourself" HTTPGETHello.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: Hello HTTP failed"

    kill -KILL $last_pid

    exit 1

fi

if grep "Hello Yourself" GETHello.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: Hello HTTPS failed"

    kill -KILL $last_pid

    exit 1

fi


if grep "added" POSTProperty201.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property post 1 failed"

    kill -KILL $last_pid

    exit 1

fi



if grep "State is more than" POSTProperty400.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property failed State more than 2 characters"

    kill -KILL $last_pid

    exit 1

fi

if grep "Zip is not within range" POSTProperty404.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property post failed, Zip out of range"

    kill -KILL $last_pid

    exit 1

fi

if grep "Missing request header" POSTProperty403.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property post failed, missing API header"

    kill -KILL $last_pid

    exit 1

fi


if grep "666 Horizon Hill Blvd" GETPropertyId200.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property by ID failed"

    kill -KILL $last_pid

    exit 1

fi



if grep "Missing request header" DELETEPropertyId401.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property delete failed, wrong API key"

    kill -KILL $last_pid

    exit 1

fi

if grep "Property updated" PUTPropertyId200.txt; then
    let FOUND=1

else

    let FOUND=0

fi

if [ $FOUND = 0 ]; then

    echo "CURL TEST ERROR: property update failed"

    kill -KILL $last_pid

    exit 1

fi



echo "CURL TEST SUCCESS"

kill -KILL $last_pid


exit 0
