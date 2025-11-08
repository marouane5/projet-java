# Makefile pour compiler/exec les simulateurs avec gui.jar
# Vous pouvez compléter ce makefile, mais étant donnée la taille du projet,
# il est FORTEMENT recommandé d'utiliser un IDE!

# Organisation:
#  1) Les sources (*.java) se trouvent dans le répertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) sont générés dans le répertoire bin
#     La hiérarchie des sources (par package) est conservée.
#
#  3) Une librairie gui.jar est distribuée pour l'interface grapique. 
#     Elle se trouve dans le sous-répertoire lib.
#
# Compilation:
#  Options de javac:
#   -d : répertoire dans lequel sont générés les .class compilés
#   -sourcepath : répertoire dans lequel sont cherchés les .java
#   -classpath : répertoire dans lequel sont cherchées les classes compilées (.class et .jar)

JAVAC = javac
JAVA = java
SRC_DIR = src
BIN_DIR = bin
CLASSPATH = $(BIN_DIR):lib/gui.jar

SOURCES := $(shell find $(SRC_DIR) -name '*.java')

.PHONY: all clean compile runTestInvader runBalls runLife runImmigration runSchelling runBoids

all: compile

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

compile: $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -sourcepath $(SRC_DIR) -classpath lib/gui.jar $(SOURCES)

runTestInvader: compile
	$(JAVA) -classpath $(CLASSPATH) TestInvader

runBalls: compile
	$(JAVA) -classpath $(CLASSPATH) sim.balls.TestBallsSimulator

runLife: compile
	$(JAVA) -classpath $(CLASSPATH) sim.cellular.TestLife

runImmigration: compile
	$(JAVA) -classpath $(CLASSPATH) sim.cellular.TestImmigration

runSchelling: compile
	$(JAVA) -classpath $(CLASSPATH) sim.cellular.TestSchelling

runBoids: compile
	$(JAVA) -classpath $(CLASSPATH) sim.boids.TestBoids

clean:
	rm -rf $(BIN_DIR)/
