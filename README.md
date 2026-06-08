# cics-java-liberty-springboot-jcics
[![Build](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml)

## Overview

This sample provides a Spring Boot application that uses the JCICS TSQ Java API to provide a RESTful CICS temporary storage queue (TSQ) browsing service. The sample demonstrates how to integrate Spring Boot with IBM CICS using the JCICS API on a CICS Liberty JVM server.

The sample is structured as a multi-module project with:
- **cics-java-liberty-springboot-jcics-app** - The Spring Boot application module
- **cics-java-liberty-springboot-jcics-cicsbundle** - The CICS bundle module for deployment

For further details about the development of this sample refer to the tutorial [Spring Boot Java applications for CICS, Part 1: JCICS, Gradle, and Maven](https://developer.ibm.com/tutorials/spring-boot-java-applications-for-cics-part-1-jcics-maven-gradle/)

---

## Requirements

### Workstation Requirements
- **Java:** Java SE 17 or later (required for Spring Boot 3.x)
- **Build Tools:**
  - **Gradle:** Version 7.3+ (Java 17 support) - Recommended: 8.0+ - included via wrapper
  - **Maven:** Version 3.8.1+ (Java 17 support) - Recommended: 3.9.0+ - included via wrapper
- **IDE (Optional):**
  - Eclipse with IBM CICS SDK for Java EE, Jakarta EE and Liberty
  - IntelliJ IDEA, VS Code, or any IDE with Gradle/Maven support
  - Command line (no IDE required if using wrappers)

### z/OS Requirements
- **CICS TS:** V6.1 or later
- **WebSphere Liberty:** Included with CICS
- **Java:** IBM Semeru Runtime 17 or later on z/OS
- **Jakarta EE:** 10 or later

---



## Downloading

- Clone the repository using your IDEs support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/archive/main.zip) and unzip onto the workstation

>*Tip: Eclipse Git provides an 'Import existing Projects' check-box when cloning a repository.*


### Check dependencies
 
Before building this sample, you should verify that the correct CICS TS bill of materials (BOM) is specified for your target release of CICS. The BOM specifies a consistent set of artifacts, and adds information about their scope. In the example below the version specified is compatible with CICS TS V6.1 with JCICS APAR PH63856, or newer. That is, the Java byte codes built by compiling against this version of JCICS will be compatible with later CICS TS versions and subsequent JCICS APARs. 
You can browse the published versions of the CICS BOM at [Maven Central.](https://mvnrepository.com/artifact/com.ibm.cics/com.ibm.cics.ts.bom)
 
Gradle (build.gradle): 

`compileOnly enforcedPlatform("com.ibm.cics:com.ibm.cics.ts.bom:6.1-20250812133513-PH63856")`   

Maven (POM.xml):

``` xml	
<dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>com.ibm.cics</groupId>
        <artifactId>com.ibm.cics.ts.bom</artifactId>
        <version>6.1-20250812133513-PH63856</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
  ```

  
## Building the Sample

You can build using Gradle, Maven, or Eclipse. The wrappers are pre-configured with compatible versions.

### Option 1: Building with Gradle

**From the root directory:**

Linux/Mac:
```bash
./gradlew clean build
```

Windows:
```cmd
gradlew.bat clean build
```

**Output:**
- WAR file: `cics-java-liberty-springboot-jcics-app/build/libs/cics-java-liberty-springboot-jcics-app-0.1.0.war`
- CICS bundle ZIP: `cics-java-liberty-springboot-jcics-cicsbundle/build/distributions/cics-java-liberty-springboot-jcics-cicsbundle-0.1.0.zip`

**Note:**
- In Eclipse, the `build` directory may be hidden. To view it: Package Explorer → ⋮ menu → Filters → Uncheck "Gradle build folder"

---

### Option 2: Building with Maven

**From the root directory:**

Linux/Mac:
```bash
./mvnw clean package
```

Windows:
```cmd
mvnw.cmd clean package
```

**Output:**
- WAR file: `cics-java-liberty-springboot-jcics-app/target/cics-java-liberty-springboot-jcics-app-0.1.0.war`
- CICS bundle ZIP: `cics-java-liberty-springboot-jcics-cicsbundle/target/cics-java-liberty-springboot-jcics-cicsbundle-0.1.0.zip`

---

### Option 3: Building with Eclipse

1. **Clone and Import Repository:**
   - File → Import → Git → Projects from Git → Clone URI
   - Enter the repository URL
   - Ensure "Import existing Eclipse projects" box is checked
   - Complete the wizard to clone and import the projects

2. **Resolve Build Path (if needed):**
   - Right-click project → Properties → Java Build Path → Libraries
   - Add Library → CICS with Enterprise Java and Liberty
   - Select appropriate CICS and Java EE versions

3. **Build the Project:**
   - Right-click on root project → Run As → Gradle Build (or Maven Build)
   - Goals: `clean build` (Gradle) or `clean package` (Maven)

**Notes:**
- When building a WAR file for deployment to Liberty it is good practice to exclude Tomcat from the final runtime artifact. We demonstrate this in the pom.xml with the *provided* scope, and in build.gradle with the *providedRuntime()* dependency.
- If you import the project to your IDE, you might experience local project compile errors. To resolve these errors you should run a tooling refresh on that project. For example, in Eclipse: right-click on "Project", select "Gradle -> Refresh Gradle Project", **or** right-click on "Project", select "Maven -> Update Project...".

>Tip: *In Eclipse, Gradle (buildship) is able to fully refresh and resolve the local classpath even if the project was previously updated by Maven. However, Maven (m2e) does not currently reciprocate that capability. If you previously refreshed the project with Gradle, you'll need to manually remove the 'Project Dependencies' entry on the Java build-path of your Project Properties to avoid duplication errors when performing a Maven Project Update.*

---



## Deploying to CICS

### Prerequisites

Ensure you have the following features defined in your Liberty `server.xml`:
- `<feature>servlet-6.0</feature>` or later depending on the version of Jakarta EE in use
- `<feature>cicsts:security-1.0</feature>` if CICS security is enabled

---

### Method 1: CICS Bundle Deployment (Gradle/Maven)

This is the **recommended** deployment method as it uses the CICS bundle generated during the build process.

**Before deploying, configure your JVM server name:**

Edit the CICS bundle configuration files to specify your Liberty JVM server:

**Gradle** (`cics-java-liberty-springboot-jcics-cicsbundle/build.gradle`):
```gradle
cics.jvmserver = 'YOUR_JVMSERVER_NAME'  // e.g., 'DFHWLP'
```

**Maven** (`cics-java-liberty-springboot-jcics-cicsbundle/pom.xml`):
```xml
<cics.jvmserver>YOUR_JVMSERVER_NAME</cics.jvmserver>  <!-- e.g., DFHWLP -->
```

**Deploy the bundle:**

1. Upload the CICS bundle ZIP file to zFS:
   - Gradle: `cics-java-liberty-springboot-jcics-cicsbundle/build/distributions/cics-java-liberty-springboot-jcics-cicsbundle-0.1.0.zip`
   - Maven: `cics-java-liberty-springboot-jcics-cicsbundle/target/cics-java-liberty-springboot-jcics-cicsbundle-0.1.0.zip`

2. Unzip the bundle on zFS

3. Create a CICS BUNDLE resource definition:
   ```
   CEDA DEFINE BUNDLE(JCICS) GROUP(MYGROUP) BUNDLEDIR(/path/to/bundle)
   ```

4. Install the bundle:
   ```
   CEDA INSTALL BUNDLE(JCICS) GROUP(MYGROUP)
   ```

---

### Method 2: CICS Explorer Deployment

1. Copy the built WAR from your *target* or *build/libs* directory into an Eclipse CICS bundle project
2. Create a new WAR bundlepart that references the WAR file
3. Deploy the CICS bundle project from CICS Explorer using the **Export Bundle Project to z/OS UNIX File System** wizard

---

### Method 3: Direct Liberty Application Deployment

Manually upload the WAR file to zFS and add an `<application>` element to the Liberty server.xml:

```xml
<application id="cics-java-liberty-springboot-jcics-app-0.1.0"
    location="${server.config.dir}/springapps/cics-java-liberty-springboot-jcics-app-0.1.0.war"
    name="cics-java-liberty-springboot-jcics-app-0.1.0" type="war">
    <application-bnd>
        <security-role name="cicsAllAuthenticated">
            <special-subject type="ALL_AUTHENTICATED_USERS"/>
        </security-role>
    </application-bnd>
</application>
```

---


## Running the Sample

The example application is divided into four services which perform actions on a CICS temporary storage queue (TSQ). Each with their own REST service suffix as follows:

- **info** - query information about the TSQ
- **write** - write information to a TSQ
- **browse** - read items from the TSQ
- **delete** - delete the TSQ

### Testing the Application

1. **Verify Deployment:**
   
   Ensure the web application started successfully in Liberty by checking for msg `CWWKT0016I` in the Liberty messages.log:
   ```
   CWWKT0016I: Web application available (default_host): http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics-app-0.1.0
   SRVE0292I: Servlet Message - [cics-java-liberty-springboot-jcics-app-0.1.0]:.Initializing Spring embedded WebApplicationContext
   ```

2. **Write to a TSQ:**
   
   To write the string `ILOVECICS` to a TSQ called `SPGJCICS`:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics-app-0.1.0/write?tsq=SPGJCICS&item=ILOVECICS
   ```

3. **Verify the TSQ Content:**
   
   Check if the specified TSQ has the information you expected by either:
   - Executing the CICS command `CEBR SPGJCICS` on a 3270 terminal. You should see `ILOVECICS` in TSQ SPGJCICS, or
   - Using the browse TSQ service URL:
     ```
     http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics-app-0.1.0/browse?tsq=SPGJCICS
     ```

4. **Query TSQ Information:**
   
   To see basic information for TSQ SPGJCICS:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics-app-0.1.0/info?tsq=SPGJCICS
   ```

5. **Delete the TSQ:**
   
   To delete the TSQ:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics-app-0.1.0/delete?tsq=SPGJCICS
   ```

---


## License
This project is licensed under [Eclipse Public License - v 2.0](LICENSE). 
