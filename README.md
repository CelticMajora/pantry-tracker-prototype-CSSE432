# pantry-tracker-prototype-CSSE432
CSSE432 Term Project - Spring 2018-2019

My team-mates for this project were Ana Huerta and Keith Hanson

## Usage:

First time database stuff:
 
* Go into application.properties in 'src/main/resources'
* Change 'spring.jpa.hibernate.ddl-auto=update' to 'spring.jpa.hibernate.ddl-auto=create'
* Note, change it back after first time running server application

Run the server:

* Option 1: Run ./gradlew bootRun
* Option 2: Import into eclipse and run api.Application as a java application

Run the command line client:

* Import into eclipse and run ui.Main as a java application

Run the webpage(for use on local machine):

* Download npm
* Run 'npm install http-server -g'
* Run 'http-server [PATH_TO_src/main/java/views] -p 8081'
* Set up a version of chrome following instructions at https://alfilatov.com/posts/run-chrome-without-cors/
* Open that version of chrome and navigate to 'http://127.0.0.1:8081/pantry.html'

