# pnia v1.1
##Application
###The application was deployed on heroku, link follows below:

Endpoint: <br />
https://talk-desk-pnia.herokuapp.com/aggregate

Health Check:<br />
https://talk-desk-pnia.herokuapp.com/actuator/health

## How to consume the API
### POST service, the payload is a list of String, below is an example of a CURL call:

```shell
$ curl --location --request POST 'https://talk-desk-pnia.herokuapp.com/aggregate' \
--header 'Content-Type: application/json' \
--data-raw '[
    "+1983236248",
    "+1 7490276403",
    "001382355A",
    "+351917382672",
    "+35191734022"
]' | pd
```

###Expected response:

```json
{
    "1": {
        "Technology": 1,
        "Banking": 1
    },
    "3519173": {
        "Clothing": 2
    }
}
```


