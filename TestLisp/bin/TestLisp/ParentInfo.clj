(ns TestLisp.ParentInfo)

(defn parent
  "Test Sample"
  [a b]
  (def hashdata {'bob 'mary, 'jane 'mary, 'mary 'peter, 'paul 'peter, 'peter 'john} )
  println a b
  (def getChild (hashdata a)) 
  (if (and (= a 'x) (= b 'y))
    (doseq [[k v] (map vector (keys hashdata) (vals hashdata))]
    (println k "->" v) 
    )
    ):else(if (and (not(= a 'x)) (= a 'y))  (     
         ;((def getChild (hashdata (keyword x)))
           ;println getChild
         println "The child of " a "is" getChild   
        )) :else(if (and (not(= a 'x)) (not(= b 'y)))          
        (println a)
        (if (= getChild y) (def vald "true") (def vald "false"))
        (println a " parent of " b "is" vald))
    )
 ) 

 