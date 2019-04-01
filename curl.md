#getAllMeals
curl -X GET http://localhost:8080/topjava/rest/meals

#GetFiltered
curl -X GET -G http://localhost:8080/topjava/rest/meals/filter -d startDate="2015-05-31" -d startTime="" -d endDate="2015-05-31" -d endTime=""

#Get meal
curl -X GET –HAccept:application/json http://localhost:8080/topjava/rest/meals/100002

#Delete meal
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

#Add meal
curl -X POST -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals -d '{"dateTime" : [2015,6,1,13,0],"description" : "add dinner","calories" : 1000}'

#Edit meal
curl -X PUT -H "Content-Type: application/json" http://localhost:8080/topjava/rest/meals/100003 -d '{"dateTime" : [2015,5,30,13,0],"description" : "edit dinner","calories" : 1000}'

#GetAllUsers
curl -X GET http://localhost:8080/topjava/rest/admin/users

#Get User
curl -X GET –HAccept:application/json http://localhost:8080/topjava/rest/admin/users/100000

#Get By Email
curl -X GET -G –HAccept:application/json http://localhost:8080/topjava/rest/admin/users/by -d email="admin@gmail.com"

#Delete
curl -X DELETE http://localhost:8080/topjava/rest/admin/users/100000

#Add user
curl -X POST -H "Content-Type: application/json" http://localhost:8080/topjava/rest/admin/users -d '{"email" : "user2@gmail.com", "name" : "User2", "enabled" : true, "caloriesPerDay" : 2000, "password" : "hello"}'

#Edit user
curl -X PUT -H "Content-Type: application/json" http://localhost:8080/topjava/rest/admin/users/100000 -d '{"id" : 100000, "email" : "user@gmail.com", "name" : "User", "enabled" : true, "caloriesPerDay" : 2000, "password" : "passw"}'
