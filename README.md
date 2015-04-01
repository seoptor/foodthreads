# foodthreads
Java multithreading sample project.

This project is dedicated to producer-consumer concurrency paradigm visualisation using java multithreading for my studying purposes and as an example for the concerning parties.

Application simulates a pizzeria fridge with concurrent access by cooks and suppliers. Fridge contains some predefined ammount of pizza toppings that can be restocked by suppliers. Supplys doing restocks after predefined pause for some random ammount and types of products with random quantity. Cooks on the other hand use those toppings to prepare different pizzas using predefined simplified recipes with different times to cook. Recipes are grouped in recipes book, that is used by cooks, and each cook randomly selects one recipe to try after some predefined pause. If fridge does not contain enough products to cook selected pizza, then cook is just pausing in a hope that fridge will be restocked by suppliers.

Because of the random recipe selections and random restocks there is a huge possibility of overstocking on one product and shortage on another (cheese usually). It is acceptable for now and will be updated someday with additional actor, that will be responsible for supply ordering, and cooks will recieve pizza orders from clients.

---
Copyright (c) 2015, Sergiy Germash <s.germash@gmail.com>

All rights reserved.
