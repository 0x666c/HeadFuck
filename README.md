# HeadFuck
A brainfuck-based esoteric programming language

Language specs:

## Old operators (brainfuck)

\> - increment pointer

\< - decrement pointer

\+ - increment cell value

\- - decrement cell value

\* - write byte from attached input into current cell

\^ - write byte to attached output

\[ - loop while current cell != 0 open

\] - loop closing

## New operators: \[Done: 9.5/14\]
 
\. - reset pointer

\_ - set cell value to 0

\! - terminate program with exit code equal to current cell

\@*or^a-z - attach stream to input/output, if self passed as argument, then default value is assigned

a\-z - call a function (argument is current cell, return gets placed in the same cell)

Preallocation:

\#a-z - put value/reference from preallocated memory into current cell (variables)

\: - preallocated code section open

\; - preallocated code section close

\, - next declaration

\{ - function scope open

\} - function scope close
		
a-z\{\<code\>\} - declare a function

a-z"\<string\>"

a-z'\<signed long long int\>'

`<comment>` - comment


## STD functions:
 ### (f)
   Call: creates a file with either a numeric name if argument is a byte value, or alphabetic name if argument is a string reference.
	
   @ operator: opens handle to file with specified name and assigns it to chosen stream. If file does not exist, do nothing.
		
 ### (m)
   Call: shows an info message box with either number represented by current byte value or string.
    
   
 ## Sample programs:
  ### hello world:
    >+++++++++[<++++++++>-]<^>+++++++[<++++>-]<+^+++++++^^+++^[-]>++++++++[<++++>-]<^>+++++++++++[<++++++++>-]<-^--------^+++^------^--------^[-]>++++++++[<++++>-]<+^[-]++++++++++^
  ### Write "hello world" to a file:
    f@^f>+++++++++[<++++++++>-]<^>+++++++[<++++>-]<+^+++++++^^+++^[-]>++++++++[<++++>-]<^>+++++++++++[<++++++++>-]<-^--------^+++^------^--------^[-]>++++++++[<++++>-]<+^[-]++++++++++^
	
