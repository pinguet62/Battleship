https://www.websequencediagrams.com/#

```
title Battleship

note over A,B: Initialisation

A->A: Saisie paramètres :\nport, taille & bateaux
B->B: Saisie paramètres :\nport

A->A: Attente de connexion

B->A: Connexion

A->B: Envoie paramètres


note over A,B: Positionnement

A->A: Positionnement bateaux
B->B: Positionnement bateaux

A->A: Attente positions

B->A: Envoie positions

B->B: Attente positions

A->B: Envoie positions


note over A,B: Jeu

loop Jeu en cours
    B->B: Attente
    A->B: Attaque
    A->A: Attente
    B->A: Attaque
end
```