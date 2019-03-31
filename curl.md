#getAllMeals
curl -X GET http://localhost:8080/topjava/rest/meals

#GetFiltered
curl -X GET -G http://localhost:8080/topjava/rest/meals/filter -d startDate="2015-05-31" -d startTime="" -d endDate="2015-05-31" -d endTime=""

#Get
curl -X GET –HAccept:application/json http://localhost:8080/topjava/rest/meals/100002

#Delete
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

#Add
curl -X POST -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals -d '{"dateTime" : [2015,6,1,13,0],"description" : "add dinner","calories" : 1000}'

#Edit
curl -X PUT -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals/100003 -d '{"dateTime" : [2015,5,30,13,0],"description" : "edit dinner","calories" : 1000}'