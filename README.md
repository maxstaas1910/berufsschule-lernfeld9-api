# lf9api
This is an api that reads the network data from a pkt file and offers it in json form through an API GET endpoint. Changes to the pkt file are automatically updated.

Usage:

- In terminal: 
  - ```cd pka2xml/```
  - ```chmod +x xml_reader.sh``` (This step is only required once)
  - ```./xml_reader.sh```
- Now start the api. The GET Endpoint is reachable at:
  - ```http://localhost:8080/network-devices```
- If there are changes made to the network.pkt file located in the pka2xml directory, the api will automatically be updated.