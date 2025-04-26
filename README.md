create a new game:
POST http://localhost:8080/game

guess a letter or whole word (if it's more than 1 character it's considered - guess whole word)
POST http://localhost:8080/game/{gameid}/guess

get the status of the game
GET http://localhost:8080/game/{gameId}

forfiet the game
POST http://localhost:8080/game/{gameId}/end
