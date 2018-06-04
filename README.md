# CLI-RPG
A Role Playing Game for the command line.

The game is inspired by two episodes of Star Wars:    
*Episode IV: A New Hope*    
*Episode V: The Empire Strikes Back*    

The player can choose between two characters; *Darth Vader* or *Luke Skywalker*.    
Based on the storylines, the character should reach a target and avoid enemies on the way.    
In case the character crosses an enemy, the fight begins.
Both the enemy and the character can attack each other.    
Everytime the character or the enemy is attacked, they lose 1 point of health.    
The fight will continue until the health of the enemy or the character becomes 0.     
In case the character dies, the game is over. 
In case the enemy dies, the character gains 1 point of experience.      
The character can keep on exploring the map until their health becomes 0 or they reach the target.

## Requirements
Java version : 1.8    
Maven version : 3.5.0

## Run the project
````    
cd path/to/folder

// Clone the project
git clone https://github.com/Jouda-Hidri/CLI-RPG.git

// Build and test the application
mvn clean install

// Test the application
mvn test

// run
cd target
java -jar rpg-1.0-SNAPSHOT.jar
````    

## Controls
N : start a new game     
R : resume the previous saved game     
W : move up     
A : move left     
S : move down     
D : move right     
F : fight     
Q : save and quit     
