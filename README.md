## Overview :
This project is a poc  for an issue found in Maven dependency management.

## Related Issue :
[Maven Dependency / PluginMDEP-610](https://issues.apache.org/jira/browse/MDEP-610?page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel&focusedCommentId=16465886#comment-16465886) : Reported issue in Apache Jira

## Current Behavior
For maven, trasitive dependency is taking precedence over declared dependency in the current pom.
From the documentation :
[Maven](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html) - Introduction to dependency mechanism
> the current pom's declaration takes precedence over its parent's declaration.

However, current pom declaration is not taking precedence when ordered differently. (**my-app/pom.xml**)

## Expected Behavior
The dependency is picked from current pom.

## Example Scenario :
Download this project and execute mvn clean install.
```sh
git clone
cd maven-dependency-issue-poc
mvn clean install
```
It will fail with error :
```
cannot find symbol
[ERROR] symbol:   method setHttpOnly(boolean)
```

##### A look at maven-dependency-issue-poc/my-app/pom.xml which is causing build failure :
```xml
    <dependency>
      <groupId>com.mycompany.app</groupId>
      <artifactId>your-app</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
    </dependency>
```
###### Notes :
* your-app/pom.xml declares a dependency of **servlet-api:2.5**
* my-app/pom.xml depends on **your-app** and **javax.servlet-api:3.1.0**
* my-app/src/main/java/com/mycompany/app/App.java imports **javax.servlet.http.Cookie** :
```java
    Cookie cookie = new Cookie("test", "test");
    cookie.setHttpOnly(true);
```
* **Cookie.setHttpOnly(boolean)** is not present in **2.5**
* Therefore, executing **mvn clean install** fails

The above can be fixed, if we **change the order of decalaration.** (ie : declare javax.servlet-api before your-app declaration (in my-app/pom.xml) and run **mvn clean install** again.


## Test Environment
```sh
mvn -v
Apache Maven 3.3.9
Maven home: /usr/share/maven
Java version: 1.8.0_162, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-8-openjdk-amd64/jre
Default locale: en_SG, platform encoding: UTF-8
OS name: "linux", version: "4.13.0-39-generic", arch: "amd64", family: "unix"
```
Note : I have not verified with latest version of maven. Will verify it soon and update.