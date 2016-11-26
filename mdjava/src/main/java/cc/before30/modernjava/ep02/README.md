* Identity Type 같은 Type Return
 

Functional Interface


T -> R (Mapper)

T -> T (Identity Function)

Identity는 같은 타입만을 두는 것은 아니다

(X)
public String f(String vlue) {
   return "value is " + value;
}

(O)
public String identity(String vluae) {
  return value;
}

왜 같은 값을 주는 function을 만들어 쓰는가?
왜 중요한지? 왜 쓰이는지?

