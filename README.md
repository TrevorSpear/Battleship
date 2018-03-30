# Battleship
Java code -> src/main/java/edu/oregonstate/trevorspear/battleship/  
HTML/JS code -> src/main/resources/public/


##MVC
This Battleship project is made with a purpose to show off mvc architecture. The view will call the Java project to receive the model. The model contains all the ships and if the ships have been hit and where.  Based on the model the view will change it's display. Once user places a ship or fires at the opponent the view will send the model to the controller. The controller will then change the model and send it back to the view.
