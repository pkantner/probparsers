MODEL
   M10
VARIABLES x
INVARIANT x:INTEGER & x<10
INITIALISATION x:=0
OPERATIONS
 inc = PRE x<9 THEN x:=x+1 END
END