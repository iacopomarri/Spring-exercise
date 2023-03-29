# Spring exercise
A back-end maven application written in Java using Spring Tool Suite, using Hybernate and Spring Data JPA.

# DATABASE
   The ER schema of the database is available on the Github repo.
   
   The database is composed of the 4 essential entities:
  -User
  -Task
  -Comment
  -Status
  
  # entities
  STATUS
  An entity is needed to represent the status since both the status himself and a corresponding color are required.
  Status is in a N to 1 relationship with Task.
  Status strictly contains 3 records, which are hardcoded and saved in the DB from the application.java files. Status table cannot be read or modified by any outside   request.
  
  TASK
  Task is composed of a description, an Id_status which is the foreign key linked to the relative status,  and a project, which in a
  more extended solution, would be a Id_project linked to a project table.
  Task is in an N to N relationship with User, and in a N to 1 relationship with Comment.
  
  USER
  User is composed of a unique Username, a Name and a password, which in an ideal solution would be encrypted. 
  User is in a N to N relationship with Task, and in a N to 1 relationship with Comment.
  
  COMMENT
  Comment is composed of a Text and a Creation_timestamp, needed to order the comments. Comment also has a Id_user and Id_task as foreign keys linked to User and Task.
  Comment is in a 1 to N relationship with Usera and in a 1 to N relationship with Task.
  
  # notes
  
   Every entity has a unique identifier called ID, even in those cases where tuples colud be already uniquely identified by an attribute, such as User table with    username attribute. 
   The N to N relationship between User and Task generates a new table called UserTask (task assignment in the ER scheme), which contains an ID itself, and 
   Id_user and Id_task as foreignkeys. It also contains the amount of hours that a user worked on a task.
  
  
  
  
  
 # MVC solution
 
 The back end solution is based on the Model View Controller architecture.
 For each of the entities described above, and for the UserTask N to N relationship, a class is created in the Model package, representing the entities/relationships. 
 Repositories are created for each Model class, which are interfaces extending the JPA repository. These files handle the data access layer performing the CRUD operations requested by the APIs in the controllers.
 Controller classes provide the Restful services which handle the HTTP methods POST, PUT, GET, DELETE.
 
 
 
 
 # POSTMAN
 
 Postman tool is used to create a collection of HTTP requests to send to the back end APIs to test them.
 Postman collection is available on the Github repo.
 
 
