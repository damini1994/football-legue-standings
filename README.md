# football-league-standing
Microservice to find standings of a team playing league football match using country name, league name and team name using football api.

## Requirements
For building and running the application you need:

- [JDK 11](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [Git](https://git-scm.com/downloads)

## Feature:
- JWT token based authentication 
- APIs Fallback using Netflix-Hytrix
- Swagger Documentation
- JUnit Test Cases

## Core Code

1. `JwtTokenFilter`
2. `JwtTokenFilterConfigurer`
3. `JwtTokenProvider`
4. `MyUserDetails`
5. `WebSecurityConfig`
6. `SwaggerConfig`
7. `TeamsStandingServiceImpl`
8. `TeamsStandingFallback`
9. `LoggingAspect`


**JwtTokenFilter**

The `JwtTokenFilter` filter is applied to each API (`/**`) with exception of the signin token endpoint (`/authenticate`) and singup endpoint (`/users/signup`).

This filter has the following responsibilities:

1. Check for access token in Authorization header. If Access token is found in the header, delegate authentication to `JwtTokenProvider` otherwise throw authentication exception
2. Invokes success or failure strategies based on the outcome of authentication process performed by JwtTokenProvider

```java
String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
if (token != null && jwtTokenProvider.validateToken(token)) {
  Authentication auth = jwtTokenProvider.getAuthentication(token);
  SecurityContextHolder.getContext().setAuthentication(auth);
}
filterChain.doFilter(req, res);
```

**JwtTokenFilterConfigurer**

Adds the `JwtTokenFilter` to the `DefaultSecurityFilterChain` of spring boot security.

```java
JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
```

**JwtTokenProvider**

The `JwtTokenProvider` has the following responsibilities:

1. Verify the access token's signature
2. Extract identity and authorization claims from Access token and use them to create UserContext
3. If Access token is malformed, expired or simply if token is not signed with the appropriate signing key Authentication exception will be thrown

**MyUserDetails**

Implements `UserDetailsService` in order to define our own custom *loadUserbyUsername* function. The `UserDetailsService` interface is used to retrieve user-related data. It has one method named *loadUserByUsername* which finds a user entity based on the username and can be overridden to customize the process of finding the user.

It is used by the `DaoAuthenticationProvider` to load details about the user during authentication.

**WebSecurityConfig**

The `WebSecurityConfig` class extends `WebSecurityConfigurerAdapter` to provide custom security configuration.

Following beans are configured and instantiated in this class:

1. `JwtTokenFilter`
3. `PasswordEncoder

**SwaggerConfig**

Create swagger config class SwaggerConfig.java To integrate Swagger with Spring Boot we need to include the following maven dependencies in our pom.xml file.

```
<!-- swagger dependencies -->
 <dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
</dependency>

<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
</dependency>
```


##Browser URL
Open your browser at the followings URL (giving REST interface details):
 
- for Swagger UI: http://localhost:8080/api/service/swagger-ui/index.html
- for api-docs: http://localhost:8080/api/service/v3/api-docs


Test URL: http://localhost:8080/api/service/teams/standing?teamName=Enfield%20Town&countryName=England&leagueName=Non%20League%20Premier

output: 
`{
"country": "(44) - England",
"league": "(149) - Non League Premier",
"team": "(3009) - Enfield Town",
"overallPosition": 6
}`


CI/CD

- Pipeline to be selected as JenkinsFile and Link of the gihub repo
- Assumption is made that Jenkins and Docker host resides on same VM.
- Pipeline with automatically spawn a container running on port 8080. In case of any conflict change the port in Jenkins file.
- Create a Jenkins pipeline job.



##Design Pattern Used
-> Factory Design Pattern
