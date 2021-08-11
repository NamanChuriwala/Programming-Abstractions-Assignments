type expr = 
          | Const of int 
          | Var of string
          | Plus of args
          | Mult of args
          | Minus of args
          | Div of args    
      and args = {arg1 : expr ; arg2 : expr};;

let evaluate arg =
        let rec evalexpr = function
                | Const x -> x
                | Plus {arg1=x;arg2=y} -> evalexpr(x) + evalexpr(y)
                | Mult {arg1=x;arg2=y} -> evalexpr(x) * evalexpr(y)
                | Minus {arg1=x;arg2=y} -> evalexpr(x) - evalexpr(y)
                | Div {arg1=x;arg2=y} -> evalexpr(x) / evalexpr(y)
        in evalexpr arg;;
