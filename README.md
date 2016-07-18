# utils4j
A must have utility package for java


[![Build Status](https://travis-ci.org/varra4u/utils4j.svg)](https://travis-ci.org/varra4u/utils4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.varra4u/utils4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.varra4u/utils4j)
[![JavaDoc](https://javadoc-emblem.rhcloud.com/doc/com.github.varra4u/utils4j/badge.svg)](http://www.javadoc.io/doc/com.github.varra4u/utils4j)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Stack Overflow](https://img.shields.io/:stack%20overflow-utils4j-brightgreen.svg)](http://stackoverflow.com/questions/tagged/utils4j)

Utils4J is a very intellegent, flexible and generic library, built to solve the developer's day to day problems and most importantly it removes the burden of rewriting the same redundency code in all the projects. For more details, see our [API docs][javadoc] for the latest release.

### You can find this artifact details in Maven artifactory at:
```xml 
<!-- https://mvnrepository.com/artifact/com.github.varra4u/utils4j -->
<dependency>
    <groupId>com.github.varra4u</groupId>
    <artifactId>utils4j</artifactId>
    <version>1.0</version>
</dependency>
```

### Getting a property (could be environment, properties files or Spring Context)

Utils4J provides a very flexible and convenient interface to retrive the properties from any source, the beauty is no need to define the source to get the property from, as given below:

```java
final Object value = VarraProperties.getProperty("env"+ StringUtils.DOT + "property" + StringUtils.DOT + "name");
final String value = VarraProperties.getPropertyAsGeneric("file"+ StringUtils.DOT + "property" + StringUtils.DOT + "name");
final Student student = VarraProperties.getWrapperProperty("spring"+ StringUtils.DOT + "student", Student.class);
```

#### Features at a Glance

Utils4J provides flexible construction to create a property repository with a combination of the following features:

 * Automatic way of loading the properties, optionally asynchronously.
 * Time based eviction of properties, if you prefer to unload the entries.
 * Flexible way to load the spring context, and make them available as part of repo.
 * Automatic loading of system environment's properties and make them available as part of repo to an entire application.
 * asynchronous way of refreshing/reloading the properties.
 * Property values automatically wrapped in to the desired types.
 * Provides an interface for storing the todo tasks.
 * Asynchronous way of executing the tasks, maintains an in-memory based queue with fixed or unlimted based on configuration.
 * Evicts the tasks if the queue is full and notifys the evicted tasks to the listener thread in an asynchrounous way.
 * Provides an interface to perform some household work when the task is completed/failed.
 * Misc utilities for day to day developer's usage.


[javadoc]: https://www.javadoc.io/doc/com.github.varra4u/utils4j
