# poll-challenge-backend
Java 17 Spring Boot API with Maven for Poll Application Backend

# Prerequisites:
- Eclipse with Java 17 JDK and Tomcat 10.1.x
- Lombok

# Local set up instructions
1. Ensure Eclipse installed with a Java 17 JDK and version of Tomcat that supports Java 17 (Tomcat version 10.1.x or later).
2. Ensure Lombok is installed in Eclipse.
3. In Eclipse, open the git perspective.
4. On the GitHub repository page, click clone and choose the http option and copy it to clipboard.
5. Click the clone button to clone a git repository.
6. Once the repository has been cloned, expand the working tree, right click the poll-challenge-parent project and do "Import Projects".
7. Ensure all three projects are being imported (parent, generated, and application).
8. Now switch to the Java perspective and right click on poll-challenge-parent and do Maven -> Build...
9. In the goals, use "clean install" and tick the "Skip Tests" checkbox. Run the build.
10. Right click pollchallenge-application/src/main/java/Application.java and Run As -> Java Application.