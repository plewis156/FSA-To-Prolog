# FSA-To-Prolog
A Java program which will take a string representing a finite state automaton, check the validity of the FSA, then output the FSA as working Prolog code.

The FSA is represented as a string in the following format A;B;C;D;E; where A is the number of states as an integer, B is the alphabet, C is the transitions between each state, D is the start state represented as an integer,  and E is the finishing states represented as integers.

Example:

5;x,y,z,a;(0:0:x),(0:1:y),(1:2:x),(2:2:x),(2:3:y),(3:3:x),(3:4:z),(4:4:x),(4:1:a);0;1,3;

In this case 5 is the number of states; x,y,z,a is the alphabet; (0:0:x),(0:1:y),(1:2:x),(2:2:x),(2:3:y),(3:3:x),(3:4:z),(4:4:x),(4:1:a) are the transitions; 0 is the start state; 1,3 are the finishing states.

An example input might be:

x,x,x,y,x,x,x,y,x,x,x,z,x,x,x,a
