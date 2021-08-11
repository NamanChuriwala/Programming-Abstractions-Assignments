type bool_expr =
        | Lit of string
        | Not of bool_expr
        | And of bool_expr * bool_expr
        | Or of bool_expr * bool_expr

let truth_table l1 l2 arg =
        let rec evalexpr l1 l2 cur1 cur2 expr =
                match expr with
                | And(x,y) -> (evalexpr l1 l2 cur1 cur2 x) && (evalexpr l1 l2 cur1 cur2 y)
                | Or(x,y) -> (evalexpr l1 l2 cur1 cur2 x) || (evalexpr l1 l2 cur1 cur2 y)
                | Not(x) -> if (evalexpr l1 l2 cur1 cur2 x) = true then false else true
                | Lit x -> if x = l1 then cur1 else cur2
        in
        let output = 
                [(true,true,evalexpr l1 l2 true true arg);(true,false,evalexpr l1 l2 true false arg);(false,true,evalexpr l1 l2 false true arg);(false,false,evalexpr l1 l2 false false arg);]
        in output
        ;;
