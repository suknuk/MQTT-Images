# MQTT-Images
This program allows the reception of base64 encoded images via the MQTT protocol and is able to send the received images to Social Media sites such as Twitter and Facebook.

## Running the program
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

### Default values ###
`-ip`     localhost
`-port`   1883. This is also the defaut port for the MQTT messaging protocol.
`-height` 480
`-width`  320
`-twitter`, `-facebook`, `automaticSocialMedia` are by default set to false
`-exampleImage` false


### Example usage ###
Testing the program with an example image to the local ip. Assumes local MQTT (ex. mosquitto) is running. To verify the correctness of the program, an image should appear in `images/` folder.
```
  java -jar ImagesMQTT.jar -ip 127.0.0.1 -port 1883 -height 512 -width 512 -ex
```

Assuming non-local MQTT server with automatic Twitter and Facebook updates, but default image resolution.
```
  java -jar ImagesMQTT.jar -ip 192.168.2.24 -t -f -as
```


## MQTT specific messaging
The program is by default subscribed to the channel `sourire`, where all incoming communication is handled. This can be changed in the `MainCoffeeClass` at the variable `mqtt_subscribed_topic`.

The incoming images are assumed to be encoded in base64 and in BGR format, where the size of an image is height*width*3 (Blue + Green + Red). An image in the default size is hence `480*320*3 = 460800 bits` or `57.6 kilobytes`. Although the MQTT protol is not made for big data transmission, the maximum size a message can have is 260 megabytes.


### Updating to Social Media ###
If the `automaticSocialMedia` flag is set to false, it is up to the users to send the status update command. This is done in the `sourire` channel with the keyword `Update to Social Media`


## Used libraries
```
  -mqqt-client-0.4.0
  -commons-cli-1.3.1
  -hamcrest-core-1.3
  -junit-4.12
  -twitter4j
  -facebook4j
```

##Tests
All practical testing is done via JUnit with a test suite.
