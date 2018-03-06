This project is a simple user management application. This applications is being secured using Spring Security4 validation, inegration Mysql database using hibernate. Remember-me functionality is also provided using TokenRepository. Here we are persisting the token in Mysql Db using persistentTokenRepository and hibernate. We are using Spring Jpa transaction. Every configurations are annotation driven. We have used JNDI for fetching datasource.

In pom.xml we have used Spring-boot for importing all spring dependency jars.

1. spring-boot-starter-data-jpa for Spring JPA Hibernate dependency.
2. spring-boot-starter-security for using Spring Security
3. spring-boot-starter-web for Spring MVC
4. spring-security-taglibs

JNDI configuration
-----------------------
Configure the dataSource JNDI as global resource in Tomcat.

tomcat>conf>server.xml within GlobalNamingResources tag add below datasource resource.


	<Resource  name="jdbc/springsecurity"
		  auth="Container"
		  type="javax.sql.DataSource"
		  driverClassName="com.mysql.jdbc.Driver"
		  url="jdbc:mysql://10.135.15.81:3306/springsecurity"
		  username="root"
		  password="admin123"
		  maxActive="20"
		  maxIdle="10"
		  maxWait="-1"/>
     
 For hooking up the resource in Tomcat context, navigate to tomcat>conf>context.xml and add

     <ResourceLink
	  name="jdbc/springsecurity"
	  global="jdbc/springsecurity"
	  type="javax.sql.DataSource"/>


Spring Security Remember-Me service
-----------------------------------------------
Spring security provides the functinality of remember-me service. 
Spring Security comes with two implementation of PersistentTokenRepository : JdbcTokenRepositoryImpl and InMemoryTokenRepositoryImpl.

In-memory : For using InMemoryTokenRepositoryImpl, we have to declare PersistentTokenRepository as
<code>
   @Bean   
    public PersistentTokenRepository tokenRepository(){
    
        InMemoryTokenRepositoryImpl inMemoryTokenRepositoryImpl = new InMemoryTokenRepositoryImpl();
        return inMemoryTokenRepositoryImpl;
	
    }
</code>  
JdbcTokenRepositoryImpl: Since we are using hibernate as persistence API, we will use hibernate for persisting the token in Db. We will be not be using JdbcTokenRepositoryImpl.
We have to implement PersistentTokenRepository in the DAO of persistent-token tracking table.

<code>
	public class PersistentLoginsDao implements PersistentTokenRepository{

	@Override
    	public void createNewToken(PersistentRememberMeToken token)..
    
    	@Override
    	public PersistentRememberMeToken getTokenForSeries(String seriesId)..
    
    	@Override
    	public void removeUserTokens(String username)..
    
   	@Override
   	public void updateToken(String seriesId, String tokenValue, Date lastUsed)..
	}
</code>

Note: If we need to use InMemory remember me services, in that case we have to cooment out implementation of PersistentTokenRepository.
Login: sam
Password: abc125


