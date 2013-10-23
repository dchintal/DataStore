(ns TestLisp.core)
;(def hashdata (hash-map :a 1 :b 2))
(defn parentCheck
  "Test Sample"
  [x y] 
  ;(println x)
  (def getChild (hashdata (keyword x))) 
  ;(println getChild)
  (if (= getChild y) (def val "true") (def val "false"))
  (println x " parent of " y "is" val))
;(def d '(1 2 3)) (foo (first d))
(def hashdata (hash-map :bob 'mary 
                        :jane 'mary :mary 'peter :paul 'peter :peter 'john))

(parentCheck 'bob 'mary)

;(parentCheck 'bob 'y)

;(def dat 12)


 