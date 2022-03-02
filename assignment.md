# COM3529 Assignment, 2022

The objective of this assignment is to assist software testers by relieving some
of the manual effort that goes into deriving test requirements for MCDC coverage
criteria, and writing tests to satisfy them.

It involves writing code to automatically figure out what the test requirements
are, given a program, and then automatically generate JUnit tests for them. You
will do this by writing a Java tool, and you will submit your work via a GitHub
repository.

## 1. The Small Print. (Described Here First, Because It's Not So Small.)

### Submission Intentions – Do These Steps First 

Submission is via a GitHub repository. If you don't have a GitHub account, set
one up (https://github.com/signup). 

Then setup a new repository (click the plus icon once you have logged in).
Ensure that it is private, so that your work cannot be copied by others, and
invite the following GitHub users to be collaborators to this repository (so
that it can be marked).

* `philmcminn` (me)
* `islamelgendy` (Islam Elgendy)
* `obp96` (Owain Parry)

To invite people to your repository, go to "Settings". In the sidebar, click
"Collaborators", then "Add People", then add the usernames above.
 
Then, **you must fill in this form, no later than Monday 28th March**
– https://tinyurl.com/com3529-assignment.

(Note: you need to be signed into your Sheffield computer account, otherwise you
will get a "You need permission" message.)

As part of the form submission, you will need to enter the following
information:

* Your name
* The URL of the GitHub repository required by the hand-in. 

### Deadline

The deadline for your submission is Friday 1st April, 5pm.

### Late Submissions

Work may be submitted up to five days late, with standard University penalties
applied. If you intend on making a late submission, you must let me know in
advance by emailing me (`p.mcminn@sheffield.ac.uk`), so that I don't take the
contents of your repository on the deadline and mark what I find at that
particular time. 

### Plagiarism and Collusion 

Don't do it. (Do I even need to write this?) 

The usual University procedures and measures will be taken and applied
(including giving you a mark of zero) if you are found to have plagiarised your
work, colluded, or if you have sourced someone else to do the work for your
submission. 

## 2. What You Need To Do

### Scope 

The objective is to test simple `static` methods in Java with numerical inputs
(for instance, the `Triangle` example presented in lectures, or the
`BMICalculator` class in the `practicals` package). You do not need to think
about methods that are not static or involve non-numerical parameters, they are
considered out of the scope of this assignment.

You will need some example programs to act as evaluation subjects for your
assignment. `Triangle` is a good one to start with, you can also make up some
programs with complex branch predicates to also evaluate your implementation
with. 

This assignment has two main parts:


### Part 1. Automatic Test Requirement Generation

For this you should **assume the existence of a parser**, although you will not
have one in practice. Do not write one, or use any libraries on the Internet to
achieve this step, it will take too long, and parsing is not the point of the
assignment.

For each branch predicate, this imaginary parser is capable of parsing the
predicate into a data structure. You should design and program this data
structure yourself. It should represent the predicate, including the individual
conditions and its structure in terms of AND, OR, and NOT.

To simulate the parsing step, you need to manually initialise the data structure
with the information for each branch predicate in each of your evaluation
subjects. (Don't throw this manual work away, include it as part of your
submission as evidence of your assignment functioning with different subjects.)

For each branch predicate, your tool needs to figure out what the test
requirements are for both (a) Restricted MCDC and (b) Correlated MCDC. 

It should be able to identify "equivalent conditions" for this purpose, for
instance:

* x == y, y == x
* a == b, a != b 
* a > b, a <= b

The last two cases of "equivalence" are somewhat curious, but are consistent
with applying MCDC, as discussed in the practical class in Week 3.

The information manually parsed and stored in your data structure should reflect
the branch predicate in the original program as far as is possible. That is, you
shouldn't write it in such a way that it side-steps any automated reasoning by
having certain information provided for it, such as equivalence of conditions or
infeasibility described above – this defeats the point of automation, of course.


### Part 2. Automatic Test Case Generation

The final step is then to generate some test cases from the test requirements.
You should **assume you have another tool that is capable of instrumenting the
program** so that it detects when each condition has been evaluated as true or
false. 

To simulate the instrumentation step, you will need to manually produce an
alternative version of each of your evaluation subjects. The lecture material on
Automated Test Case Generation will give you inspiration as to how to do this. 

There are several ways to try and produce inputs to make each condition true or
false. Randomly generating inputs until ones are found that satisfy particular
conditions is the most obvious way.

As soon as your tool has "found" an input to satisfy a test requirement, it
should write a JUnit test case. The output of this step will be a JUnit test
suite. 

At a basic level, this involves writing Java code to a file. This Java code can
be compiled and run independently of your tool. Constructing each test case
involves a call to the method under test with the inputs you have generated. 

You then need to think if/how you will generate assertions to go along with
those inputs. No additional parsing or code analysis should be employed here –
the assertions could simply assert what the code does, to make the test suite a
regression test suite. Or you could get additional input in some shape or form
from the human tester to assist with this step. This part is open-ended.

## 3. Submission and Marking Scheme

### Materials Required For Your Submission

The submitted materials required for this assignment take the form of a GitHub
repository containing:

1. A report in the form of a `README.md` in the root directory of the
   repository.
2. Your Java Code for parts 1 and 2, to include your subjects (and manually
   instrumented versions).
3. A video (of no more than _two_ minutes in length) showing your assignment
   code running with one or two subjects. This can take the form of a simple
   screen capture video with your narration. The idea is not to assess how well
   you can produce a video, but simply to show your assignment executing and
   working. If the video is too large for the repository, please provide a link
   to it (e.g., on YouTube) to it at the end of the `README.md` file.

The `README.md` should describe how someone should install and run your project
for themselves. You should not need to depend heavily on any external libraries.
Everything is possible using standard Java libraries. **For marking purposes,
everything should run with Java 11.**

It should also describe each step you attempted and how you went about doing it. 


### Indicative Marking Scheme

To attain different grades, you need to show the following:

* Pass/3rd: (40-50%) Very basic attempt at fulfilling the requirements of the
  assignment.

* 2:2 (50-60%) Significant aspects of the implementation of the assignment not
  implemented. 

* 2:1 (60-70%) Majority of the assignment's requirements implemented and/or not
  to the highest standard required for a 1st class mark.

* 1st (70%+) All parts of the assignment implemented. Implementation shown to
  work with a variety of subjects.
