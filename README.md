# poll-challenge-backend
Java 17 Spring Boot API with Maven for Poll Application Backend. The front end application built with VueJS can be found [here](https://github.com/Jahanzaib-Alam/poll-challenge-frontend).

# API Usage Instructions
A Postman collection can be downloaded from [here](https://github.com/Jahanzaib-Alam/poll-challenge-backend/blob/main/Poll%20Challenge%20API%20Collection.postman_collection.json) with all of the requests supported by this API.

# Prerequisites for Local Setup
- MySQL Server version 8 running on localhost:3306
- poll_challenge database and user set up as per the instructions at the bottom of this README
- Eclipse with Java 17 JDK and Tomcat 10.1.x
- Lombok

# Local Set Up Instructions
1. Ensure Eclipse installed with a Java 17 JDK and version of Tomcat that supports Java 17 (Tomcat version 10.1.x or later).
2. Ensure Lombok is installed in Eclipse.
3. In Eclipse, open the git perspective.
4. On the GitHub repository page, click clone and choose the http option and copy it to clipboard.
5. Click the clone button to clone a git repository.
6. Once the repository has been cloned, expand the working tree, right click the poll-challenge-parent project and do "Import Projects".
7. Ensure all three projects are imported (parent, generated, and application).
8. Now switch to the Java perspective and right click on poll-challenge-parent and do Maven -> Build...
9. In the goals, use "clean install" and tick the "Skip Tests" checkbox. Run the build.
10. Right click pollchallenge-application/src/main/java/Application.java and Run As -> Java Application.

# Database Set Up Instructions
1. Install MySQL Server version 8.x.x and ensure it is running on localhost:3306.
2. Run the script in [the DATABASE SETUP.sql file](https://github.com/Jahanzaib-Alam/poll-challenge-backend/blob/main/DATABASE%20SETUP.sql) to create the database and required database user along with some test data.
