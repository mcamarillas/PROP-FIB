JC = javac
FLAGS = -d ./EXE -classpath ./EXE

all: main drivers

main: Main.class

Main.class: 
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Main.java

Atribute.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Atribute.java

Cluster.class: User.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Cluster.java

Conjunt_Items.class: Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Conjunt_Items.java

Item.class: TipusItem.class Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Item.java


K_Neareast_Neightbour.class: Conjunt_Items.class Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/K_Neareast_Neightbour.java

Kmeans.class: Cluster.class User.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Kmeans.java

myPair.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/myPair.java

Ranged_Atribute.class: Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/Ranged_Atribute.java

RateRecomendation.class: myPair.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/RateRecomendation.java

SlopeOne.class: User.class myPair.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/SlopeOne.java

TipusItem.class: Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/TipusItem.java

TipusRol.class:
	$(JC) $(FLAGS) ./FONTS/src/domini/model/TipusRol.java

User.class: TipusRol.class valoratedItem.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/User.java

valoratedItem.class: Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/model/valoratedItem.java

HybridApproach.class: myPair.class
    $(JC) $(FLAGS) ./FONTS/src/domini/model/HybridApproach.java

ControladorDomini.class: Atribute.class Cluster.class Conjunt_Items.class HybridApproach.class Item.class K_Neareast_Neightbour.class Kmeans.class myPair.class Ranged_Atribute.class RateRecomendation.class SlopeOne.class TipusItem.class TipusRol.class User.class valoratedItem.class ControladorPersistenciaItem.class ControladorPersistenciaRatings.class ControladorPersistenciaRecomendation.class
    $(JC) $(FLAGS) ./FONTS/src/domini/controladors/ControladorDomini.java

ControladorPersistenciaItem.class: Conjunt_Items.class
    $(JC) $(FLAGS) ./FONTS/src/domini/persistencia/ControladorPersistenciaItem.java

ControladorPersistenciaRatings.class: User.class
    $(JC) $(FLAGS) ./FONTS/src/domini/persistencia/ControladorPersistenciaRatings.java

ControladorPersistenciaRecomendation.class: myPair.class
    $(JC) $(FLAGS) ./FONTS/src/domini/persistencia/ControladorPersistenciaRecomendation.java

ControladorPresentacion.class: ControladorDomini.class
    $(JC) $(FLAGS) ./FONTS/src/domini/persistencia/ControladorPersistenciaRecomendation.java



drivers: DriverCluster.class DriverKmeans.class DriverKND.class DriverLectorCSV2.class DriverRateRecomendation.class DriverValoratedItem.class DriverSlopeOne.class DriverMyPair.class DriverRanged_Atribute.class DriverConjuntItems.class DriverTipusItem.class DriverAtribute.class DriverRanged_Atribute.class 

DriverCluster.class: User.class Cluster.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverCluster.java

DriverKmeans.class: User.class Kmeans.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverKmeans.java

DriverKND.class: Atribute.class Ranged_Atribute.class TipusItem.class Conjunt_Items.class K_Neareast_Neightbour.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverKND.java

DriverMyPair.class: myPair.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverMyPair.java

DriverRateRecomendation.class: RateRecomendation.class Item.class User.class SlopeOne.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverRateRecomendation.java

DriverSlopeOne.class: User.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverSlopeOne.java

UserTest.class:
	javac FONTS/src/domini/Drivers/UserTest.java

DriverValoratedItem.class: valoratedItem.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverValoratedItem.java
	
DriverItem.class: Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverItem.java
	
DriverConjuntItems.class: Item.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverConjuntItems.java

DriverTipusItem.class: Item.class Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverTipusItem.java
	
DriverAtribute.class: Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverAtribute.java
	
DriverRanged_Atribute.class: Ranged_Atribute.class
	$(JC) $(FLAGS) ./FONTS/src/domini/Drivers/DriverRanged_Atribute.java

Suite_Distance_Item.class:
	javac FONTS/src/domini/Drivers/Suite_Distance_Item.java

Suite_Resto_Item.class:
	javac FONTS/src/domini/Drivers/Suite_Resto_Item.java



RunMain:
	java -cp ./EXE FONTS.src.domini.model.Main

DriverCluster:
	java -cp ./EXE FONTS.src.domini.drivers.DriverCluster

DriverKmeans:
	java -cp ./EXE FONTS.src.domini.drivers.DriverKmeans

DriverKND:
	java -cp ./EXE FONTS.src.domini.drivers.DriverKND

DriverMyPair:
	java -cp ./EXE FONTS.src.domini.drivers.DriverMyPair

DriverRateRecomendation:
	java -cp ./EXE FONTS.src.domini.drivers.DriverRateRecomendation

DriverSlopeOne:
	java -cp ./EXE FONTS.src.domini.drivers.DriverSlopeOne

UserTest:
	java org.junit.runner.JUnitCore FONTS.src.domini.drivers.UserTest

Suite_Distance_Item:
	java org.junit.runner.JUnitCore FONTS.src.domini.drivers.Suite_Distance_Item

Suite_Resto_Item:
	java org.junit.runner.JUnitCore FONTS.src.domini.drivers.Suite_Resto_Item


DriverValoratedItem:
	java -cp ./EXE FONTS.src.domini.drivers.DriverValoratedItem
	
/*DriverItem:
	java -cp ./EXE FONTS.src.domini.drivers.DriverItem*/
	
DriverTipusItem:
	java -cp ./EXE FONTS.src.domini.drivers.DriverTipusItem
	
DriverConjuntItems:
	java -cp ./EXE FONTS.src.domini.drivers.DriverConjuntItems
	
	
DriverAtribute:
	java -cp ./EXE FONTS.src.domini.drivers.DriverAtribute
	
DriverRanged_Atribute:
	java -cp ./EXE FONTS.src.domini.drivers.DriverRanged_Atribute
	


