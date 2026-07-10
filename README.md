# cics-java-liberty-springboot-jcics
[![Build](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml/badge.svg)](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml)
[![License](https://img.shields.io/badge/License-EPL%202.0-green.svg)](https://www.eclipse.org/legal/epl-2.0/)

## Overview

This sample provides a Spring Boot application that uses the JCICS TSQ Java API to provide a RESTful CICS temporary storage queue (TSQ) browsing service. The sample demonstrates how to integrate Spring Boot with IBM CICS using the JCICS API on a CICS Liberty JVM server.

**Key Features:**
- **JCICS API Integration**: Direct use of CICS Java APIs for TSQ operations
- **RESTful Services**: Spring Boot REST endpoints for TSQ management
- **Multi-Module Project**: Separate application and CICS bundle modules
- **Multi-Build Support**: Compatible with both Gradle and Maven
- **CICS Bundle Deployment**: Automated deployment using CICS bundle plugins

## Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Reference](#reference)
4. [Downloading](#downloading)
5. [Check Dependencies](#check-dependencies)
6. [Building the Sample](#building-the-sample)
7. [Deploying to a CICS Liberty JVM server](#deploying-to-a-cics-liberty-jvm-server)
8. [Running the Sample](#running-the-sample)
9. [Troubleshooting](#troubleshooting)
10. [License](#license)
11. [Additional Resources](#additional-resources)
12. [Contributing](#contributing)

## Prerequisites

- CICS TS V6.1 or later (required for Spring Boot 3.x and Jakarta EE 10 support)
- A configured Liberty JVM server in CICS
- Java SE 17 or later on the workstation
- An Eclipse development environment on the workstation (optional)
- Either Gradle or Apache Maven on the workstation (optional if using Wrappers)

## Reference

For more information about the development of this sample, see [Spring Boot Java applications for CICS, Part 1: JCICS, Gradle, and Maven](https://developer.ibm.com/tutorials/spring-boot-java-applications-for-cics-part-1-jcics-maven-gradle/).

## Downloading

- Clone the repository using your IDEs support, such as the Eclipse Git plugin
- **or**, download the sample as a [ZIP](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/archive/main.zip) and unzip onto the workstation

**Importing into Eclipse:**
1. In the **Git Repositories** view, right-click the repository → **Import as Project** (imports the root project)
2. Switch to the **Java EE** perspective
3. In the **Project Explorer**, right-click the `cics-java-liberty-springboot-jcics-app` folder → **Import as Project**
4. Right-click the `cics-java-liberty-springboot-jcics-cicsbundle` folder → **Import as Project**
5. Right-click the `cics-java-liberty-springboot-jcics-cicsbundle-eclipse` folder → **Import as Project**
6. Right-click the root project → **Gradle → Refresh Gradle Project** or **Maven → Update Project...** to resolve dependencies


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

You can build the sample using an IDE of your choice, or you can build it from the command line. For both approaches, using the supplied Gradle or Maven wrapper is the recommended way to get a consistent version of build tooling.

On the command line, you simply swap the Gradle or Maven command for the wrapper equivalent, `gradlew` or `mvnw` respectively.

For an IDE, taking Eclipse as an example, the plug-ins for Gradle *buildship* and Maven *m2e* will integrate with the "Run As..." capability, allowing you to specify whether you want to build the project with a Wrapper, or a specific version of your chosen build tool.

The required build-tasks are `clean build` for Gradle and `clean verify` for Maven. Once run, Gradle will generate a WAR file in the `cics-java-liberty-springboot-jcics-app/build/libs` directory, while Maven will generate it in the `cics-java-liberty-springboot-jcics-app/target` directory.

**Note:** When building a WAR file for deployment to Liberty it is good practice to exclude Tomcat from the final runtime artifact. We demonstrate this in the pom.xml with the *provided* scope, and in build.gradle with the *providedRuntime()* dependency.

**Note:** If you import the project to your IDE, you might experience local project compile errors. To resolve these errors you should run a tooling refresh on that project. For example, in Eclipse: right-click on "Project", select "Gradle → Refresh Gradle Project", **or** right-click on "Project", select "Maven → Update Project...".

### Gradle Wrapper (command line)

Run the following in a local command prompt:

On Linux or Mac:

```shell
./gradlew clean build
```

On Windows:

```shell
gradlew.bat clean build
```

This creates a WAR file inside the `cics-java-liberty-springboot-jcics-app/build/libs` directory.

> **Note:** In Eclipse, the `build` directory may be hidden by default. To view it: **Package Explorer → ⋮ → Filters and Customization → uncheck "Gradle build folder"**. For Maven, the `target` directory is visible by default.

### Maven Wrapper (command line)

Run the following in a local command prompt:

On Linux or Mac:

```shell
./mvnw clean verify
```

On Windows:

```shell
mvnw.cmd clean verify
```

This creates a WAR file inside the `cics-java-liberty-springboot-jcics-app/target` directory.

## Deploying to a CICS Liberty JVM server

Ensure you have the following features defined in your Liberty `server.xml`:

- `servlet-6.0` (required for Spring Boot 3.x and Jakarta EE 10)
- `cicsts:security-1.0` if CICS security is enabled

A template `server.xml` is provided [here](./etc/config/liberty/server.xml).

### CICS Bundle Plugin Deployment (Gradle/Maven)

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

### CICS Explorer SDK Deployment

This repository includes a pre-configured Eclipse CICS bundle project `cics-java-liberty-springboot-jcics-cicsbundle-eclipse` that can be used directly with CICS Explorer SDK.

1. Right-click the `cics-java-liberty-springboot-jcics-cicsbundle-eclipse` project → **Export Bundle Project to z/OS UNIX File System** and follow the wizard

> **Note**: The bundle project is pre-configured so that the Eclipse WTP export automatically packages the application WAR with all dependencies. This relies on the `-app` project being open in the same Eclipse workspace. If you have not yet imported the project, follow step 5 of the [Importing into Eclipse](#downloading) instructions first.

---

### Direct Liberty Application Deployment

1. Manually upload the WAR file to zFS
2. Add an `<application>` element to the Liberty server.xml to define the web application with access to all authenticated users. For example:

```xml
<application id="cics-java-liberty-springboot-jcics"
    location="${server.config.dir}/springapps/cics-java-liberty-springboot-jcics.war"
    name="cics-java-liberty-springboot-jcics" type="war">
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
   CWWKT0016I: Web application available (default_host): http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics
   SRVE0292I: Servlet Message - [cics-java-liberty-springboot-jcics]:.Initializing Spring embedded WebApplicationContext
   ```

2. **Write to a TSQ:**
   
   To write the string `ILOVECICS` to a TSQ called `SPGJCICS`:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics/write?tsq=SPGJCICS&item=ILOVECICS
   ```

3. **Verify the TSQ Content:**
   
   Check if the specified TSQ has the information you expected by either:
   - Executing the CICS command `CEBR SPGJCICS` on a 3270 terminal. You should see `ILOVECICS` in TSQ SPGJCICS, or
   - Using the browse TSQ service URL:
     ```
     http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics/browse?tsq=SPGJCICS
     ```

4. **Query TSQ Information:**
   
   To see basic information for TSQ SPGJCICS:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics/info?tsq=SPGJCICS
   ```

5. **Delete the TSQ:**
   
   To delete the TSQ:
   ```
   http://myzos.mycompany.com:httpPort/cics-java-liberty-springboot-jcics/delete?tsq=SPGJCICS
   ```

---

## Troubleshooting

**Issue: Application fails to start**
- Check Liberty messages.log for errors
- Verify `servlet-6.0` is enabled in `server.xml`
- Confirm CICS TS version supports Spring Boot 3.x (V6.1+)

**Issue: Red error markers in Eclipse after Maven Update Project**
- This is a known false-positive from WTP's deployment assembly validator
- The project includes `org.eclipse.wst.validation.prefs` to suppress these markers
- Command-line builds (`./mvnw clean verify`) are not affected

## License
This project is licensed under [Eclipse Public License - v 2.0](LICENSE).

## Additional Resources

- [CICS TS for z/OS Documentation](https://www.ibm.com/docs/en/cics-ts)
- [Spring Boot Java applications for CICS, Part 1: JCICS, Gradle, and Maven](https://developer.ibm.com/tutorials/spring-boot-java-applications-for-cics-part-1-jcics-maven-gradle/)
- [CICS Java Development](https://www.ibm.com/docs/en/cics-ts/latest?topic=programming-java-applications)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## Contributing

This sample is maintained by IBM CICS development. We welcome bug reports and feature requests via GitHub Issues. Contributions are welcome and reviewed on a case-by-case basis — please read the [contributing guidelines](https://github.com/cicsdev/.github/blob/main/CONTRIBUTING.md) before opening a pull request. For CICS product questions, contact IBM Support.
