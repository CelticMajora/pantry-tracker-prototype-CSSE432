# Pantry Tracker

[![pipeline status](https://ada.csse.rose-hulman.edu/donohoc1/pantry-tracker/badges/master/pipeline.svg)](https://ada.csse.rose-hulman.edu/donohoc1/pantry-tracker/commits/master)

Computer Networks Term Project

Link to repository: https://ada.csse.rose-hulman.edu/donohoc1/pantry-tracker.git

Link to slideshow presentation: https://docs.google.com/presentation/d/15pV5Vktk-NdWcAq_OQAXK__zSnMlgS5OuGPKrNhsd1Q/edit?usp=sharing

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

