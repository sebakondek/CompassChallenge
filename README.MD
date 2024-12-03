# Compass Challenge

### Problem:  Identify which contacts are potentially duplicates

### Instructions:
You have been asked to take a list of contact information and identify which contacts are
potentially duplicates. You need to write a function that will do the following:
1. Identify which contacts are possible matches.
2. A score for each match that represents how accurate you believe the match is. This
   scoring is defined by you.
3. A contact might have multiple or no matches
4. All processing should be done in working memory (no database).

### Example:
#### Input
   <table>
  <tr>
    <th>Contact ID</th><th>First Name</th><th>Last Name</th><th>Email Address</th><th>Zip Code</th><th>Address</th>
  </tr>
  <tr>
    <td>1001</td><td>C</td><td>F</td><td>mollis.lectus.pede@outlook.net</td><td></td><td>449-6990 Tellus. Rd.</td>
  </tr>
  <tr>
    <td>1002</td><td>C</td><td>French</td><td>mollis.lectus.pede@outlook.net</td><td>39746</td><td>449-6990 Tellus. Rd.</td>
  </tr>
  <tr><td>1003</td><td>Ciara</td><td>F</td><td>non.lacinia.at@zoho.ca</td><td>39746</td><td></td>
  </tr>
</table>

#### Output
   <table>
  <tr>
    <th>ContactID Source</th><th>ContactID Match</th><th>Accuracy</th>
  </tr>
  <tr>
    <td>1001</td><td>1002</td><td>High</td>
  </tr>
  <tr>
    <td>1001</td><td>1003</td><td>Low</td>
  </tr>
</table>

   The input file is included in the assessment packet.
   As part of your solution you will need to produce:
1. The code used to identify the potential duplicates
2. A set of tests that you use to verify the functions
3. Comments in the code are welcome

## Preparation
In order to execute the code you need to have installed Java 21 and Maven.

In the resources directory inside the input folder you can find the sample file that was given for the test.

## Execution
You need to be in the project's root folder for it to build and run. 

Execute in the terminal
### Build
```
mvn clean package
```

### Run
```
java -jar target/CompassTest-1.0-SNAPSHOT.jar
```

## Improvements
- Input file should be sent via http request, or obtained from a storage service.
- Output file should be stored in a storage service.
- For readability, instead of 1 file with all the results, N files may be created for each accuracy level.