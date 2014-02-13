Battleship
==========

## Sequence diagram

URL : https://www.websequencediagrams.com/#

### General

```
title Exchanges between host and guest

note over Host, Guest: Parameters
Host-->Host: choice:\n[Port, Size, Boats]
note left of Host: Parameters\n[Port, Width/Height, Boats]
Guest-->Guest: choice:\n[Port]
Host->Host: wait()
Guest->Host: onGuestConnected()
Host->Guest: send(:ParametersDto)
note right of Guest: Parameters\n[Port, Width/Height, Boats]

note over Host, Guest: Positioning
Host-->Host: choice:\nBoats positioning
Guest-->Guest: choice:\nBoats positioning
Host->Host: wait()
Guest->Guest: wait()
Host->Guest: send(:PositioningDto)
note right of Guest: Positioning
Guest->Host: send(:PositioningDto)
note left of Host: Positioning

note over Host, Guest: Game
loop Jeu en cours
    note over Host, Guest: Lap 1
    Guest->Guest: wait()
    Host-->Host: choice\n[Coordinates]
    Host->Host: refresh()
    Host->Guest: send(:AttackDto)
    Host->Host: wait()
    Guest->Guest: refresh()
    Guest-->Guest: choice\n[Coordinates]
    Guest->Guest: refresh()
    Guest->Host: send(:AttackDto)
    Guest->Guest: wait()
    Host->Host: refresh()
    note over Host, Guest: Lap 2
    Host-->Host: choice\n[Coordinates]
    Host->Host: refresh()
    Host->Guest: send(:AttackDto)
end
```

### Details

#### Host

```
note over User, PlayerTypeView: Choice Host
User->+PlayerTypeView: show()
User-->PlayerTypeView: choice
PlayerTypeView->-PlayerTypeView: dispose()

note over User, HostParametersView: Parameters
PlayerTypeView->+HostParametersView: show()
User-->HostParametersView: Choice\nPort, Size, Boats
HostParametersView-->HostSocketManager: init()
HostParametersView->HostParametersView: wait()
HostSocketManager->HostSocketManager: guestConnected()
HostSocketManager-->HostParametersView: continue()
HostParametersView->-HostParametersView: dispose()

note over User, FleetPositioningView: Positioning
HostParametersView->+FleetPositioningView: show(Game)\n[PlayerType, Width & Height, BoatEntries]

loop All not placed
    User-->FleetPositioningView: choiceBoat
    loop Boat placed
        User-->FleetPositioningView: selectCase()
        FleetPositioningView->FleetPositioningView: refresh()
    end
end

FleetPositioningView->-FleetPositioningView: dispose()
```

#### Guest

```
note over User, PlayerTypeView: Choice Guest
User->+PlayerTypeView: show()
User-->PlayerTypeView: choice
PlayerTypeView->-PlayerTypeView: dispose()

note over User, GuestParametersView: Parameters
PlayerTypeView->+GuestParametersView: show()
User-->GuestParametersView: Choice\nPort
GuestParametersView-->GuestSocketManager: init()
GuestParametersView->GuestParametersView: wait()
GuestSocketManager->GuestSocketManager: hostConnecting()
GuestSocketManager->GuestSocketManager: initGame()
GuestSocketManager-->GuestParametersView: continue()
GuestParametersView->-GuestParametersView: dispose()

note over User, FleetPositioningView: Positioning
GuestParametersView->+FleetPositioningView: show(Game)\n[PlayerType, Width & Height, BoatEntries]

loop All not placed
    User-->FleetPositioningView: choiceBoat
    loop Boat placed
        User-->FleetPositioningView: selectCase()
        FleetPositioningView->FleetPositioningView: refresh()
    end
end

FleetPositioningView->-FleetPositioningView: dispose()
```