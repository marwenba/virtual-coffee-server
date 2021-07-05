<h1 align="center">
  <br>
  <a href="https://notion-emojis.s3-us-west-2.amazonaws.com/v0/svg-twitter/2615.svg"><img src="https://notion-emojis.s3-us-west-2.amazonaws.com/v0/svg-twitter/2615.svg" alt="Virtual coffee Logo" width="200"></a>
  <br>
      Think-it Java: Virtual coffee - Deamon Server
  <br>
</h1>


This Deamon Server is built using the framework Spring and the Java language. The main goal of this server is to get the available guests from the Think-it's API and process the data then send the response to command line tool!

RPC XML was used to communicate between the server and the command line tool.

The architecture of the main project is : 

 ├──main->java->asklepios->thinkit. </br>
     &nbsp; &nbsp;                              ├── models         &nbsp; &nbsp;     &nbsp;       &nbsp; &nbsp;     &nbsp;     &nbsp; &nbsp;     &nbsp;                       # where we are building the objects that we are working  with</br>
     &nbsp; &nbsp;                              ├── api            &nbsp; &nbsp;     &nbsp;         &nbsp; &nbsp; &nbsp;     &nbsp; &nbsp;     &nbsp;                         # Where we communicate with Open API provided by Think-It</br>
     &nbsp; &nbsp; &nbsp;  &nbsp;                                     └── ScheduleApiController.java     &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp;            # This controller send the request to the Open API and get a list of available thinkiters<br/>
      &nbsp;    &nbsp;                          ├── services        &nbsp; &nbsp;     &nbsp;                            &nbsp; &nbsp; &nbsp;      &nbsp; &nbsp;     &nbsp;    # Contains custom functions and services used by the server deamon <br/>
       &nbsp; &nbsp; &nbsp;    &nbsp;                                └── DataProcessingService.java   &nbsp; &nbsp; &nbsp;    &nbsp; &nbsp;              # This service has all the processing functions and the communication with the client<br/>
      &nbsp; &nbsp;                             └── ServerApplication.java   &nbsp; &nbsp;     &nbsp;                 &nbsp; &nbsp; &nbsp;       &nbsp; &nbsp;     &nbsp;     # The main script that runs the deamon server


***

<h3 align="center" >
  <a href="https://webeha.com/">
    Running the Server
  </a>
</h3>

***

## Build the JAR file

```
./mvn clean package
```

## Run the deamon server

```
java -jar target/deamon-server-1.0.0.jar

```

