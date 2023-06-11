Movie Service has dependency wirh Actor service

Prerequisits:

1. Run Actor Service
2. Run Movie Service


swagger
http://localhost:9595/swagger-ui/

Expected results from GET Movie , id = 1

{
"id": 1,
"title": "Forrest Gump",
"director": "Robert Zemeckis",
"actors": [
{
"id": 1,
"firstName": "Tom",
"lastName": "Hanks",
"rating": 1,
"oscarPrized": true
},
{
"id": 3,
"firstName": "Meryl",
"lastName": "Streep",
"rating": 5,
"oscarPrized": true
},
{
"id": 6,
"firstName": "Brad",
"lastName": "Pitt",
"rating": 3,
"oscarPrized": true
}
]
}
