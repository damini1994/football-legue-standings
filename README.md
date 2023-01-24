# football-league-standing
Microservice to find standings of a team playing league football match using country name, league name and team name using football api.

## Requirements
For building and running the application you need:

- [JDK 11](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [Git](https://git-scm.com/downloads)

## Feature:
- JWT Authorization
- APIs Fallback using Netflix-Hytrix
- Swagger Documentation
- JUnit Test Cases


Test URL: http://localhost:8080/api/service/teams/standing?teamName=Enfield%20Town&countryName=England&leagueName=Non%20League%20Premier

output: 
`{
"country": "(44) - England",
"league": "(149) - Non League Premier",
"team": "(3009) - Enfield Town",
"overallPosition": 6
}`

##Browser URL
Open your browser at the followings URL (giving REST interface details):
 
- for Swagger UI: http://localhost:8080/api/service/swagger-ui/index.html
- for api-docs: http://localhost:8080/api/service/v3/api-docs


CI/CD

- Pipeline to be selected as JenkinsFile and Link of the gihub repo
- Assumption is made that Jenkins and Docker host resides on same VM.
- Pipeline with automatically spawn a container running on port 8080. In case of any conflict change the port in Jenkins file.
- Create a Jenkins pipeline job.
