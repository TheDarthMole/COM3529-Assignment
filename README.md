# Introduction
Welcome to the "COM3529 Software Testing and Analysis" module at the University
of Sheffield! This repository contains code examples to support the lecture
material, practical sessions, and the assignment.

### Contents

* [Introduction](#introduction)
* [Getting Started](#getting-started)
    * [Using Codio](#using-codio)
    * [Setting Up Your Own Machine](#setting-up-your-own-machine)
    * [Cloning the Repository](#cloning-the-repository)
* [Compiling and Running Code in the
  Repository](#compiling-and-running-code-in-the-repository)
* [Problems or Feedback](#problems-or-feedback)

# Getting Started

In the practical sessions in the Diamond, we will be relying on the virtual
environment provided by [Codio](https://www.codio.com), for which you should
already have a working account. (If you do not have or have forgotten your login
details, click the "sign in" button, and then click the "Lost your password?"
link).

All the required tools are already setup for you on Codio, so there is nothing
to do if you are happy to work with your own virtual box there. We give the
instructions for getting started with this in the [next section](#using-codio).

You might want to set up your own machine at home or your laptop as well. Of
course, you can log into Codio from there too, but sometimes it just feels good
to be using your own preferred IDE and get your hands dirty setting stuff up
from scratch yourself. Read on for more details. 

## Using Codio

Our use of Codio will be limited in that we'll just be using the virtual boxes
for ease of running and using this repository in a standardised setup and
context. To get a box going, go to https://www.codio.com, click the "LOGIN"
button, and login with your university email address and password. Click "My
Projects" under the "BUILD" heading in the left sidebar. Then, click the blue
"New Project" button in the top right. Click "Java 11" as your starting point.
You can name your project whatever you like, but ensure that its visibility
remains "Private". Then click "Create", and you're ready to go. When logging
back into Codio later, you should always see your project listed under "My
Projects".

## Setting Up Your Own Machine

If you intend to use Codio only, you can ignore this section. 

If you intend to set up your own machine for the module, however, you will need
the following:

* __Git__. You will need Git to clone this repository. If you don't already have
  it setup on your machine, you'll need to download and install it. See
  https://git-scm.com/book/en/v2/Getting-Started-Installing-Git.
* __Java 11__. We will be using Java Development Kit (JDK) 11 throughout the
  module, as the most recent long term support (LTS) version of Java released by
  Oracle. However, a more recent JDK should also work fine. Prebuilt OpenJDK
  Binaries are available at https://adoptopenjdk.net. If you already have one or
  more installations of Java on your machine, ensure that a Java 11 installation
  (or later) is the default one used at the command line. For example, some
  browsers will install a Java Runtime Environment (JRE) on your machine, but
  this is insufficient for interacting with the code in this repository.
* __Maven__. Maven is a build automation tool, package and dependency manager
  for Java. It is available at https://maven.apache.org. 

## Cloning the Repository

The easiest way to get a hold of these materials is to clone the repository. You
can do this by opening a terminal and changing directory to somewhere
appropriate on your system (on Codio, the default `workspace` directory is just
fine):

```console
git clone https://github.com/philmcminn/com3529-code.git
```

# Compiling and Running Code in the Repository

The code repository for the module uses [Maven](https://maven.apache.org), a
build automation, package and dependency manager for Java. Maven makes it easy
for us to use different tools and libraries (e.g., JUnit) without needing to
download and install them individually, and having to ensure we have updated the
`CLASSPATH` environment variable correctly each time. 

This section will teach you the basic commands needed to get things compiling
and running. For the interested and curious (which testers should be!), there is
more extensive documentation about all the kinds of things you can do with
Maven. See for example Apache's own tutorials at
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html and
https://maven.apache.org/guides/getting-started/index.html and various
tutorials, for example https://www.tutorialspoint.com/maven/index.htm.

You will notice the repository has a number of top-level directories, e.g.
`lectures`, `practicals`, `assignment`, and so on. Each directory corresponds to
a project, with it's own `pom.xml` file ("pom" stands for "Project Object
Model"). A `pom.xml` contains information about the project and its
dependencies, and includes details about different Maven "plugins" that can be
used to perform specific tasks with the project (e.g., running JUnit).

Each directory, or project, contains code that needs to be built and run using
Maven. This code lives in the `src` directory of each project. The `src`
directory is further subdivided into two further subdirectories: `main` and
`test`. The `main` directory is where the main code of the Java project lives,
and `test` is where the tests reside. Maven keeps the two separate, although the
classes contained in each can belong to the same packages.

You can build these classes via the command line. If you're using your own
machine, you can use an IDE to do this for you (e.g., [IntelliJ
IDEA](https://www.jetbrains.com/idea) or
[Eclipse](https://www.eclipse.org/downloads)). However, these instructions will
assume that you're using the command line on Codio. 

For instance, to build the examples in the lectures directory, you first need to
change to that directory in the terminal. 

```console
cd lectures
```

To compile all the classes in a project, issue the following Maven command. 

```console
mvn compile
```

Note that the first time you run a Maven command, Maven may need to download and
install any relevant dependent libraries from its central repository. You will
see a lot of blurb appearing in your terminal window. Eventually, you should see
a `BUILD SUCCESS` message.

To compile all of the tests, issue the Maven command:

```console
mvn test-compile
```

To run all of the tests in a project, the command is:

```console
mvn test
```

Note that some of the tests fail. This should not be the normal situation, but
it's deliberate for the lecture examples – tune into the lectures to find out
why! So expect some red error messages. On this occasion, you can ignore them.

We may want to run the tests in a specific class only. To do this specify the
test class (`TestIsPositive` in this example) with an additional parameter as
follows:

```console
mvn test -Dtest=TestIsPositive
```

To run a specific test, specify the test class, with the particular method you
want to run (`testGreaterThan1`) separated by a hash symbol:

```console
mvn test -Dtest=TestIsPositive#testGreaterThan1
```

One of the main uses of Maven is to package up a Java application and its
dependencies into a JAR file that can be independently run and distributed
(using the `mvn package` command). However, we can use Maven to run a specific
class – provided it has a `main` method, of course – thanks to a Maven plugin.
The command required requires the fully qualified class name (i.e., the class
name prepended by its package name), as in the following example, from the Week
1 lecture:

```console
mvn exec:java -Dexec.mainClass="uk.ac.shef.com3529.lectures.execution.ExhaustivelyExecuteCalendar"
```

All the above examples are real examples using this repository, and should work
"out of the box" without any problems, assuming you have followed all the
instructions detailed here, and set everything up correctly, if you're using
your own machine.

Maven puts all build files in a subdirectory called `target`. (If you fork this
repository, bear in mind that this directory has been added to the `.gitignore`
file and won't feature as part of any commits unless you remove it from there.)
To reset the project (i.e., clear out the `target` subdirectory), issue the
following command:

```console
mvn clean
```

# Problems or Feedback

We welcome any feedback or constructive criticism that you may have.

If you are experiencing problems with using the repository, please contact the
module lecturer that owns it – [Phil McMinn](https://mcminn.io). Please note
that the teaching team are limited in the help that we can offer on specific
system setups. If you are experiencing significant difficulties, please use
Codio, where we can give you full support if you run into problems. 
