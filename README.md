# cics-java-liberty-springboot-jcics
[![License](https://img.shields.io/badge/License-EPL%202.0-green.svg)](https://www.eclipse.org/legal/epl-2.0/)

This sample provides a Spring Boot application that uses the JCICS TSQ Java API to provide a RESTful CICS temporary storage queue (TSQ) browsing service. The sample demonstrates how to integrate Spring Boot with IBM CICS using the JCICS API on a CICS Liberty JVM server.

## Versions

Select the branch for your target environment:

| Spring Boot | Branch | Min Java | Jakarta EE | Build Status | |
|-------------|--------|----------|------------|--------------|---|
| 4.x | [springboot/v4](../../tree/springboot/v4) | 21 | EE 11 | [![Build](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml/badge.svg?branch=springboot%2Fv4)](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml) | ⚠️ Preview |
| 3.x | [springboot/v3](../../tree/springboot/v3) | 17 | EE 10 | [![Build](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml/badge.svg?branch=springboot%2Fv3)](https://github.com/cicsdev/cics-java-liberty-springboot-jcics/actions/workflows/build.yaml) | |

All branches target CICS TS V6.1 or later.

> ⚠️ **The `springboot/v4` branch is a work-in-progress preview.** It targets Spring Boot 4.x and Jakarta EE 11, which requires Liberty `servlet-6.1` support — not yet available in CICS TS. It is not yet recommended for use.

## License
This project is licensed under [Eclipse Public License - v 2.0](LICENSE).

## Contributing

This sample is maintained by IBM CICS development. We welcome bug reports and feature requests via GitHub Issues. Contributions are welcome and reviewed on a case-by-case basis — please read the [contributing guidelines](https://github.com/cicsdev/.github/blob/main/CONTRIBUTING.md) before opening a pull request. For CICS product questions, contact IBM Support.
