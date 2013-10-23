(ns TestLisp.fiveDat)
(def data 2)
(defn dataIterate "samp data" [] (
    (def hashdatas {'bob 'mary,'jane 'mary,'mary 'peter,'paul 'peter,'peter 'john })
  (doseq [[k v] (map vector (keys hashdatas) (vals hashdatas))]
     (def data 2)    
       (def a v)    
      (doseq [[t u] (map vector (keys hashdatas) (vals hashdatas))]

      (if (= t a)
               (def getParent u)(def getParent 1))

        (if (not(= 1 getParent))
          (println k "is ++ ansector of" getParent (def data 2)                                  
             (doseq [[p q] (map vector (keys hashdatas) (vals hashdatas))]
                (if (= p getParent)
               (def getParent2 q)(def getParent2 1))
                (if (not(= 1 getParent2))
          (println k "is ++ ansector of" getParent2 (def data 2)
                )   
           )(def data 1))
       ) 
     )
     
     
      )
      (println k "is ansector of" v)
  ) )
 ) 