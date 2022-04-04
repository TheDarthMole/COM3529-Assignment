# Static Analyser - Yellow Sticker
Assignment by Nicholas Ruffles. Please let the yellow sticker rules apply.

## Setup
This project was made using IntelliJ using JDK 11. All code and tests can be run from within the IDE. 

A Maven pom.xml file has been created, however has not been tested running using maven as I have java 17 on my laptop.

For running the project, open the `StaticAnalyser.java` file in the `src` folder. There you should see
a main method, which calls `test1` and `test2`. These are both the test cases that I have been using that evaluate
different expressions. Simply click `Run StaticAnalyser` next to the main method (Using IntelliJ).

The main method should evaluate the two test cases. For each test case the Restricted MCDC and also Correlated MCDC will
be evaluated

This code base makes use of ScriptEngine, which works in my version of Java 11, however it should be noted that it is
is deprecated and will be removed from future versions of Java.

## Data Structure

I chose to use a tree as the data structure, as I fet it worked in terms of expression's inherent tree structure.

Each Predicate can either contain two or one other predicate, or it can contain a single condition. 

There are evaluation functions that evaluate both a single condition, and a full predicate tree. These use the 
javascript script engine for evaluation. 

## Part 1

There has been an attempt at making a small set of test cases for the `Condition` class, you can find them 
in `StaticAnalyser/src/test/java/ConditionTest.java`.

### Correlated MC/DC

In order to generate correlated MC/DC, the unique conditions are found using a recursive function to find all conditions,
then they are checked against each other to check for equivalence. Equivalence also means if the conditions are the 
inverse of each other.

After finding unique conditions, the test cases are generated for them, using a simple number to binary generator. 

Looping over each unique condition, we find a case where there is a true and false evaluation, where the major condition
is both true and false. The minor conditions do not matter what state they are in.

This unique set of correlated MC/DC conditions are then displayed to the console then returned from the function.

For the evaluations I make sure to use a lookup table for the input/output mapping where possible, to make sure we are 
not evaluating the exact same statement multiple times.

### Restricted MC/DC

Similar to Correlated MC/DC, we find the unique conditions as well as the potential inputs, as well as generate a lookup
table.

For every major operator, we toggle the major condition and see if the output toggles. If it doesn't, then we find a new
input. We make use of a lookup table again to make sure that identical expressions are not evaluated more than once.

The set of Restricted MC/DC values are output then returned from the function.

## Part 2

Didn't have time to complete this :(

Please find the video demonstrating the code in the root of this repository.