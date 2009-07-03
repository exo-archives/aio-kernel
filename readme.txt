eXo Kernel v.2.1.1.

Release Notes
=============

Bug

    * [KER-61] - jgroups 2.5.0 & 2.5.2 in dependency declarations
    * [KER-62] - Wrong jgroups groupId and artifactId used in pom.xml
    * [KER-63] - "container/jmx" should not be inside "container" module
    * [KER-70] - Configuration unmarshalling fails under some circumstances
    * [KER-83] - IOUtil has an infinite loop when the provided input stream its available() method returns 0

Doc

    * [KER-49] - HOW-TO use component plugins include new features.

Improvement

    * [KER-86] - IOUtil improvements

New Feature

    * [KER-37] - Ability to provide some configuration to a given portal instance from the other war (not portal.war)

Task

    * [KER-9] - Need to review some tests in kernel project
    * [KER-73] - TestConcurrentCache fails
    * [KER-74] - TestRootContainerManagedIntegration fails
    * [KER-79] - Migrate module configuration to use kernel_1_0.xsd
    * [KER-80] - Remove unnecessary configuration from all the JARs' configuration.xml
    * [KER-82] - Decouple management metadata from JMX
    * [KER-85] - Kernel issues management
    * [KER-88] - Use parent pom 1.1.1 in trunk
