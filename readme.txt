eXo Kernel v.2.1.2.

Release Notes
=============

Bug

    * [KER-91] - JMX integration code does not compile fine under Java 6 due to changes in the JMX signatures
    * [KER-94] - Can not compress some files into a zip file

Improvement

    * [KER-87] - Remove obsolete distributed/replicated cache features
    * [KER-97] - Apply slf4j logger to ExoLogger

New Feature

    * [KER-58] - support system properties extrapolation in xml configuration
    * [KER-92] - Reimplement ExoLogger + commons logging facade with SLF4J
    * [KER-96] - Release Kernel 2.1.2

Task

    * [KER-54] - Use generics for eXoCache
    * [KER-89] - ExoCache improvements
    * [KER-90] - Remove legacy JMX layer framework
    * [KER-93] - Move detachable portal classes to portal and portlet container
    * [KER-102] - Apply Maven dependency management

Sub-task

    * [KER-98] - Test slf4j configuration in runtime
    * [KER-99] - ExoLogger wrapper around slf4j Logger
    * [KER-100] - Adopt LogConfigurationInitializer for slf4j logger system
    * [KER-103] - Source code migration to eXo Log
