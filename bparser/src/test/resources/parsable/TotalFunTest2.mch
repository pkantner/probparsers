MACHINE TotalFunTest2
SETS
 ID={aa,bb,cc,dd,ee,ff,gg} 
CONSTANTS f
PROPERTIES
  f: ID >-> POW(ID) & !yy.(yy:ID => f(yy) = {yy})
VARIABLES xx
INVARIANT
 xx:ID 
INITIALISATION xx::dom(f)
OPERATIONS
  Set(yy) = PRE yy:ID & yy: dom(f)  THEN xx:: f(yy) END;
  is_xx(yy) = PRE yy:ID & yy=xx THEN skip END
END

