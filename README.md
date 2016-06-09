# MQTT-Images
This program allows the reception of base64 encoded images via the MQTT protocol and is able to send the received images to Social Media sites such as Twitter and Facebook.

# Running the program
**NOTE:** The program requires a running mqtt server to receive messages
```
- java -jar ImagesMQTT.jar [INPUT ...]
  -h --help         shows help
  -i --ip   <arg>   sets MQTT server ip
  -p --port <arg>   sets MQTT server port
  -height   <arg>   specifies image height
  -width    <arg>   specifies image width
  -t --twitter      use twitter
  -f --facebook     use facebook
  -as --automaticSocialMedia  update automatically on Social Media
  -ex --exampleImage          sends example image at the start of the program
```

# Used libraries
```
  -mqqt-client-0.4.0
  -commons-cli-1.3.1
  -hamcrest-core-1.3
  -junit-4.12
  -twitter4j
  -facebook4j
```
