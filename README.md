# MinigameFixes

some workarounds and features for bugs in minigame plugins for minecraft-kids.online

* when logging in or tepleporting to the lobby area, the inventory of non-op plyers is cleared
* cannot move below Y=1 when in spectator mode
* forbid opening of containers in playing Village Defense
* in parkour courses, en enchanting table will give players elytras, and walking on red nether bricks will remove them. 
* prevent opening of player inventory in Parkour (because it is not required anyway, and that'S the easiest to prevent players from putting elytras somewhere else :P )
* add a worldguard region flag that allows to configure the explosion radius of TNT.


To build, village defense must be put in the local maven repository. 
Since atm it can't be built, you need to download the jar (version 4.5.1),
change to the folder containing it and run the following command:

`mvn install:install-file -Dfile=villagedefense-4.5.1.jar -DgroupId=plugily.projects -DartifactId=villagedefense -Dversion=4.5.1 -Dpackaging=jar`