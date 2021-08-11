let rec pow x n =
        if n=0 then 1 else x * pow x (n-1);;

let rec float_pow x n =
        if n=0 then 1.0 else x *. float_pow x (n-1);;

let rec compress = function 
        | [] -> [] 
        | x :: [] -> x :: []
        | x :: (y::_ as t) -> if x=y then compress t else x :: compress t;;

let rec remove_if list f =
        match list with
        | [] -> []
        | h :: t -> if f h = false then h :: remove_if t f else remove_if t f;;

let slice list i j =
        let rec helper i j index cur = function
                | [] -> cur
                | h::t -> if i>j then cur else if index<i then helper i j (index+1) cur t else if index>=i && index<j then helper i j (index+1) (cur@[h]) t else cur
        in
        helper i j 0 [] list;;

let equivs func list =
        let rec singlelistmatch element = function
                | h::t -> func h element
                | [] -> false
        in
        let rec fulllistmatch value result =
                match result with
                        | [[]] -> [[value]]
                        | [] -> [[value]]
                        | h :: t -> if singlelistmatch value h = true then [h@[value]]@t else [h]@(fulllistmatch value t)
        in
        let rec iterate res = function
                | [] -> res
                | h::t -> let tmp = fulllistmatch h res in iterate tmp t
        in
        iterate [[]] list;;

let goldbachpair n = 
        let rec loop n i =
                if n = i then true else if n mod i = 0 then false else loop n (i+1)
        in
        let isprime n = loop n 2
        in
        let rec divide a b =
                if (isprime a=true) && (isprime b=true) then (a,b) else divide (a+1) (b-1)
        in
        divide 2 (n-2);;

let rec equiv_on f g = function
        | [] -> true
        | h :: t -> if f h = g h then equiv_on f g t else false;;

let rec pairwisefilter f = function
        | [] -> []
        | x :: [] -> x :: []   
        | x :: y :: t -> f x y :: pairwisefilter f t;;

let polynomial list = 
        let rec power x y = if y = 0 then 1 else x*power x (y-1)
        in
        let f x a b = power x a * b
        in
        let rec func res num = function
                | [] -> res
                | (x,y)::t -> f num y x + func res num t
        in
        let poly list = fun x -> func 0 x list in
        poly list;; 

let powerset l =
        let rec combine l1 l2 =
                match l1 with
                        | [] -> [l2]
                        | [[]] -> [l2]
                        | h::t -> [h]@(combine t l2)
        in
        let rec subsets arr n tmp res =
                if n=0 then combine res tmp else if List.length arr = 0 then res else match arr with
                        | h::t -> let tmpres = (subsets t (n-1) (h::tmp) res) in (subsets t n tmp tmpres)
        in
        let rec run tmplist i =
                if i=0 then [[]] else (run tmplist (i-1)) @ (subsets tmplist i [] [[]])
        in
        let rec length = function
                | [] -> 0
                | h::t -> 1 + length t
        in
        run l (length l);;
