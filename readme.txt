eXo Kernel v.2.0.8.

Release Notes
=============

Bug

    * [KER-61] - jgroups 2.5.0 & 2.5.2 in dependency declarations
    * [KER-62] - Wrong jgroups groupId and artifactId used in pom.xml
    * [KER-76] - J2EEServerInfo creates its own mbean server in standalone mode
    * [KER-83] - IOUtil has an infinite loop when the provided input stream its available() method returns 0
    * [KER-94] - Can not compress some files into a zip file

Improvement

    * [KER-86] - IOUtil improvements
    * [KER-109] - Improve the CacheService to allow complex implementation of ExoCache

New Feature

    * [KER-110] - Provide an implementation of ExoCache for JBoss Cache 3
    * [KER-111] - Provide an implementation of ExoCache for JBoss Cache 1

Task

    * [KER-9] - Need to review some tests in kernel project
    * [KER-85] - Kernel issues management
    * [KER-104] - JiBXException: No unmarshaller for element "{http://www.exoplaform.org/xml/ns/kernel_1_0.xsd}configuration"
    * [KER-105] - To add back CacheService for src/main/java/conf/configuration.xml
