# Overview

Common algorithms and logics for GAME

To calculate the movable range of the character with a moving power on the
2-dimension array list based Map in the kind of Simulation Game,Strategic
Simulation Game


![demo](https://riversun.github.io/img/moving_area_demo_.jpg
 "demo")


It is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

-----
# How to Use
## Calculate movable area
1. To calculate movable area,create MovableAreaHelper instance.
2. Call #setMap to set map(contains hurdle value) data
3. Call #getMovableArea to calculate movable area.

Example code
```java
MovableAreaHelper mah = new MovableAreaHelper();

    //character position
		int characterX = 6;
		int characterY = 6;

    //moving-power means the steps that character can move forward.
		int characterMovingPower = 4;

    //the number of each value means the hurdle.
    //The hurdle consumes moving-power.
		int[][] map = new int[][] {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 9, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 9, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 9, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 9, 9, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		};

		mah.setMap(map);

    //returns the array of movable area.
    int[][] movableArea = mah.getMovableArea(characterX, characterY, characterMovingPower);

```
Example Result

 "*" means character position,0 means unmovable,1 means movable
![demo](https://riversun.github.io/img/moving_area_demo.jpg
 "demo")
