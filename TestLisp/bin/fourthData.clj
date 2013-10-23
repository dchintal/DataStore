(ns fourthData)
(def data 2)
(defn dataIterat "samp data" [] (
    (def hashdat {'bob 'mary,'jane 'mary,'mary 'peter,'paul 'peter,'peter 'john })
  (doseq [[k v] (map vector (keys hashdat) (vals hashdat))]
      
       (def a v)
      (doseq [[t u] (map vector (keys hashdat) (vals hashdat))]
        ;(println "t val" t "u val" a)
        (println "lll")
        (def data 2)
        (if (= t a)
               (def getParent u)(def getParent 1))
        (if (not(= 1 getParent))       
          ((println "+++++++++++")
            (def b getParent)
            (while (> data 1) 
       (
        (doseq [[p q] (map vector (keys hashdat) (vals hashdat))]
         (if (= p b)
               (def getParent2 u)(def getParent2 1))
        (if (not(= 1 getParent2))
        (println k "is +++ ansector of" getParent2)(def data 1))
           )
        )
       )( def b getParent2) )
      (println k "is ++ ansector of" v)) 
        (println "end loop")
      )            
     ;(println k "is ansector of" v)    
      )   
  ) )