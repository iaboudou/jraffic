### Traffic Simulation


    Java traffic simulation with cars and traffic lights. 
    The simulation manages car movement, traffic light changes, intersections, and different driving directions. 
    Cars stop at red lights, move at green lights, and follow different routes through the intersection.

### Structure

```text
jraffic
    ├── Readme.md
    └── src
        ├── car
        ├── intersection
        ├── input
        ├── simulation
        ├── ui
        ├── Main.java
        └── app
```

### Team

* **iaboudou** : ```Intersection & traffic lights```
* **mohnouri** : ```Car movement```
* **Ybourazz** : ```UI```

### Features

* Cars move through the intersection
* Traffic lights change automatically
* Cars stop at red lights
* Cars move when the light is green
* Cars follow different routes
* Cars avoid collisions

### Architecture

1. **Main :**
   ```Starts the application.```
2. **Car :**
   ```Manages cars, their movement, states, routes, and directions.```
3. **Intersection :**
   ```Controls traffic lights and decides which direction can pass.```
4. **Simulation :**
   ```Connects the car system, intersection, and UI, and updates the simulation.```
5. **UI :**
   ```Displays the road, cars, traffic lights, and simulation state.```
6. **Input :**
   ```Handles keyboard input, including spawning cars and closing the application.```



### Run

```bash
chmod +x app.sh
./app.sh install
./app.sh run
```
