(ns image-gen.core
  (:require
    [clojure.math :as math]
    [quil.core :as q]))


(defn -hex->rgb
  [hex]
  (let [hex-str (subs hex 1)
        hex-len (count hex-str)
        r (Integer/parseInt
            (subs hex-str 0 (/ hex-len 3)) 16)
        g (Integer/parseInt
            (subs hex-str (/ hex-len 3) (* 2 (/ hex-len 3))) 16)
        b (Integer/parseInt
            (subs hex-str (* 2 (/ hex-len 3))) 16)]
    [r g b]))


(defn hex->rgb
  ([hex]
   (-hex->rgb hex))
  ([hex alpha]
   (conj (-hex->rgb hex) alpha)))


(defn eq-triangle
  [x y r theta]
  (let
    [x1 (+ x (* r (math/cos theta)))
     x2 (+ y (* r (math/sin theta)))
     y1 (+ x (* r (math/cos (+ theta (/ (* 2 Math/PI) 3)))))
     y2 (+ y (* r (math/sin (+ theta (/ (* 2 Math/PI) 3)))))
     z1 (+ x (* r (math/cos (+ theta (/ (* 4 Math/PI) 3)))))
     z2 (+ y (* r (math/sin (+ theta (/ (* 4 Math/PI) 3)))))]
    [x1 x2 y1 y2 z1 z2]))


(defn gen-color
  []
  (->> ["#ff7eb6" "#33b1ff" "#be95ff"]
       #_["#728CB1" "#6BBC7C" "#E4A4F9" "#5841BB"]
       (map hex->rgb)
       rand-nth))


(defn draw
  []
  (apply q/stroke (hex->rgb "#ffffff" 0))
  (q/stroke-weight 1)
  (apply q/fill (conj (gen-color) (q/random 150 255)))
  (apply q/triangle
         (eq-triangle
           (q/random (q/width))
           (q/random (q/height))
           (q/random 10 100)
           (q/random 0 (/ (* 2 Math/PI) 3)))))


(defn setup
  []
  (q/frame-rate 0)
  (apply q/background (hex->rgb "#161616"))
  (dotimes [_ 30]
    (draw)))


(defn sketch
  []
  (q/defsketch
    proj
    :title "the"
    :settings #(q/smooth 2)
    :setup setup
    :draw draw
    :size [900 480]))


(defn -main
  []
  (sketch))


(comment (sketch))
