MACHINE RecTest3
SETS
 ID={aa,bb}
VARIABLES xx, bank, total, allxx
INVARIANT
 xx : struct(name : ID, balance : NAT) & bank : struct(name : ID) & total:struct(money:NAT) &
 allxx : POW(struct(name : ID, balance : NAT)) 
INITIALISATION bank := rec(name:bb) || xx:= rec(name:aa, balance:2) ||
    total := rec(money:3) || allxx :: POW(struct(name : ID, balance : NAT))
OPERATIONS
  positive = PRE xx'balance > 0 THEN skip END;
  ok_withrdaw(amt) = PRE amt:NAT1 & (xx'balance >= amt) THEN
     skip
  END;
  withrdaw(amt) = PRE amt:NAT1 & (xx'balance >= amt) THEN
     xx := rec(name:xx'name, balance:(xx'balance - amt))
  END;
  unsafe_dec = BEGIN
     xx := rec(name:xx'name, balance:(xx'balance - 1))
  END;
  set_xx = BEGIN xx :: allxx END
END
