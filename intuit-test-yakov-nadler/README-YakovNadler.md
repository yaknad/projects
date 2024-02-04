### Hello reviewer!

# Personal Note
Please forgive my rusty Java.
* I haven't written in Java during the last 4 years, besides a handful of automation tests.
(My main programming language during these 4 years was .net core.)
* I used Spring Boot as a platform for the REST service, but as I'm not very familiar with
Spring / Spring Boot apis / attributes (I've used them in the past but it has been a very long time),
* I probably wrote some redundant code that could be implemented with simple Java syntax or Spring attributes  :)
* I haven't used Lombock as I'm not sufficiently familiar with it and I didn't want to waist time on research

# Assumptions made when solving this exercise
* I haven't implemented any api security since I'm not sure which security method is required - if any.
* PlayerId is unique. In case I had to support multiple players with the same Id, 
  I would have to use a Hashtable<String, List<Player>>> in the in-memory DB and decide which to return in the api.
* PlayerId must be not null. If this assumption is wrong, I have to use a HashMap (not HashTable)
  and support multiple players with the id "null" (see previous bullet).

  

# If I had more time
* I used an in memory collection to imitate a players DB. 
  If I had more time (and remember better how to use NHibernate) I would use a real DB (mysql / sql etc.)
* Of course, if I used a DB, I should've planned for multiple instances of this service running in parallel.
  Meaning that every player I insert to the DB should be InsertOrUpdate - to prevent multiple instances 
  inserting the same player. 
* I could use some caching: If the DB is relatively small, I can use an in memory collection to 
  cache the whole players list (similar to my actual implementation). If it's large, I could still cache
  specific players (in case there are more popular players that are requested frequently)
* I would build a generic parsing component that will parse a string line in a file to a given type.
  It will explore the desired type properties types (reflection) and will try to convert the string accordingly.
* Separate the Data Access Layer into a separate artifact, create DAL entities for the DAL layer and 
  create separate DTO entities in the api artifact and map the DAL entities to DTOs for better encapsulation,
  serialization handling etc.
* Added more tests coverage.
* =======> Implemented the repository and the controller with CompletableFutures to mimic an actual DB call without 
  blocking the thread. Since I'm a bit rusty with Java, I found it too long for this exercise limited time 
  to learn how to implement an equivalent to C#'s async / await with CompletableFuture and handle all the special 
  HttpStatus cases in the controller when handling a future. It requires bit more reading time from me.