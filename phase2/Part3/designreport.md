# Design Report

> Inception: clarify the details of the problem:

* a. What will a jumper do if the location in front of it is empty, but the location two cells in front contains a flower or a rock?

  ***Answer:*** 

  * If the location two cells in front contains a flower, it can jump.
  * If the location two cells in front contains a rock, it turns 45 degrees to the right.

* b. What will a jumper do if the location two cells in front of the jumper is out of the grid?

  ***Answer:*** Turn 45 degrees to the right.

* c. What will a jumper do if it is facing an edge of the grid?

  ***Answer:*** Turn 45 degrees to the right.

* d. What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?

  ***Answer:*** Turn 45 degrees to the right.

* e. What will a jumper do if it encounters another jumper in its path?

  ***Answer:*** Turn 45 degrees to the right.

* f. Are there any other tests the jumper needs to make?

  ***Answer:*** 

  * What will a jumper do if another actor (not a flower or a rock) is in the cell that is one cells in front of the jumper?

    (Answer: Turn 45 degrees to the right.)

  * What will a jumper do if it can jump?

    (Answer: Move the jumper directly to the cell that is two cells in front of the jumper.)