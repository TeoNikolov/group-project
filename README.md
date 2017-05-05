Official GitHub repository for the compass website, including back-end source code.

# Dependency Remarks
Among the included dependencies within the repo, there is one extra dependency by Standford CoreNLP, which is the "models" dependency, being at about 250MB.

# Overview
Compass is a centralized, open-domain question-answering system that we have set to be local to Cardiff. Due to DBPedia's nature, currently we include a broad domain for basic questions such as "Who is Gareth Bale?".

When the program executes a user query, relevant API calls may be made and the database linked to the program updated. Therefore, it is crucial to include the details of your database before running the code.

The back-end operates on Java 1.8, which is ran by a recent version of the Apache Tomcat, a web server implementing the Java Server Pages (JSP) technology. There is a level of dependency on PHP for the feedback system to work, though the majority of the program runs on Java and is handled visually primarily via Javascript and CSS, using Bootstrap for responsiveness.
