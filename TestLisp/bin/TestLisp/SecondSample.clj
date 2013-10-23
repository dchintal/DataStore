(ns SecondSample)
;(def hashdata (hash-map :a 1 :b 2))

(defn parent
  "Test Sample"
  [x y]
  (def hashdata {'bob 'mary, 'jane 'mary, 'mary 'peter, 'paul 'peter, 'peter 'john} )
  println x y
  (def getChild (hashdata x)) 
  (if (and (= x 'a) (= y 'b))
    (doseq [[k v] (map vector (keys hashdata) (vals hashdata))]
    (println k "->" v) 
    )
    ):else(if (and (not(= x 'a)) (= y 'b))  (     
         ;((def getChild (hashdata (keyword x)))
           ;println getChild
         println "The child of " x "is" getChild   
        )) :else(if (and (not(= x 'a)) (not(= y 'b)))          
        (println x
        (if (= getChild y) (def vald "true") (def vald "false"))
        (println x " parent of " y "is" vald))
    )
 ) 

 (parent 'a 'b)