# Spring Boot Calculus Web Service
A web service that takes a base64 encoded math expression string and gives solution to the calculation in JSON form.

### Accessing the api
The service is deployed to heroku.  Use the following url to access the the api.
  > https://app-futurice-calculus.herokuapp.com/api/v1/calculus?query=[input]
  
### Accepted input
  - UTF-8 with BASE64 encoding
  - Supported operations: + - * / ( )
  - e.g. given 2 * (23/(33))- 23 * (23), use https://app-futurice-calculus.herokuapp.com/api/v1/calculus?query=MiAqICgyMy8oMzMpKS0gMjMgKiAoMjMp
 
### Expected output
  - Expect a JSON response with a corresponding status
  - Status 200 when successful along with the JSON 
    - https://app-futurice-calculus.herokuapp.com/api/v1/calculus?query=NSArIDM=
  ```sh
  {
    "error": false,
    "result": 8
  }
  ```
  - Status 422 when something goes wrong in the parsing with the following JSON
    - https://app-futurice-calculus.herokuapp.com/api/v1/calculus?query=NSArIDMl
  ```sh
  {
    "error": true,
    "message": "Unknown character %"
  }
  ```
  
## Deployment
The service is deployed to heroku cloud.  To deploy to heroku using Git follow these steps:
```sh
$ git init
$ git add .
$ git commit -m "initial commit"
```
Now you need push the code to the Heroku-hosted remote.
```sh
$ heroku create
$ git push heroku master
```
