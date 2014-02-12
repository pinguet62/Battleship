Battleship
==========

## Sequence diagram

URL : https://www.websequencediagrams.com/#

### General

```
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

### Details

```
User->+PlayerTypeChoiceView: Show()
User-->PlayerTypeChoiceView: Choix
PlayerTypeChoiceView->-PlayerTypeChoiceView: Dispose()

PlayerTypeChoiceView->+HostParametersView: Show()
User-->HostParametersView: Choix(Port, Bateaux & Nombre)
HostParametersView-->HostSocketManager: ConnectGuest()
HostParametersView->HostParametersView: Wait()
HostSocketManager-->HostParametersView: GuestConnected()
HostParametersView->-HostParametersView: Dispose()

HostParametersView->+FleetPositioningView: Show(game)\n[PlayerType, Width & Height, BoatEntries]

loop All not placed
    User-->FleetPositioningView: Positioning TODO
end

FleetPositioningView->TODO: TODO
FleetPositioningView->-FleetPositioningView: Dispose()


```