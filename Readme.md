# 🚦 Traffic Simulation

## 📌 Description

This project is a **Java Traffic Simulation** that models cars moving through an intersection controlled by traffic lights.

The application is divided into three main responsibilities:

* 🎨 **Graphical User Interface**
* 🚗 **Car Movement**
* 🚦 **Intersection & Traffic Light Logic**

The goal is to keep these responsibilities separated so that each part of the application is easy to understand, test, and maintain.

---

# 🏗️ Project Structure

```text
TrafficSimulation/
│
├── README.md
│
└── src/
    │
    ├── Main.java
    │
    ├── ui/
    │   ├── SimulationWindow.java
    │   ├── SimulationPanel.java
    │   └── Renderer.java
    │
    ├── car/
    │   ├── Car.java
    │   ├── Direction.java
    │   └── CarMovement.java
    │
    ├── intersection/
    │   ├── Intersection.java
    │   ├── TrafficLight.java
    │   └── IntersectionLogic.java
    │
    └── simulation/
        ├── Simulation.java
        └── SimulationConfig.java

```

---

# 👥 Team Responsibilities

## 🎨 Ybourazz — Graphical UI

Responsible for the **Graphical User Interface**.

### Responsibilities

* Create the application window
* Draw the roads
* Draw the intersection
* Draw cars
* Draw traffic lights
* Update the screen
* Handle graphical components
* Display the current simulation state

---

# 🚗 mohnouri — Car Movement

Responsible for the **cars and their movement**.

### Responsibilities

* Create cars
* Store car position
* Store car speed
* Store car direction
* Move cars
* Stop cars
* Resume cars
* Detect whether a car can enter the intersection
* Make cars react to traffic lights

---

# 🚦 iaboudou — Intersection Logic

Responsible for the **intersection and traffic-light logic**.

### Responsibilities

* Manage traffic lights
* Define traffic-light states
* Change lights
* Control which direction can pass
* Prevent conflicting cars from entering
* Define intersection rules


---

# 🔄 How The Components Communicate

The architecture should be approximately:

```text
                    ┌─────────────┐
                    │    Main     │
                    └──────┬──────┘
                           │
                           ▼
                    ┌─────────────┐
                    │ Simulation  │
                    └──────┬──────┘
                           │
             ┌─────────────┼─────────────┐
             │             │             │
             ▼             ▼             ▼
          ┌─────┐       ┌─────┐      ┌────────────┐
          │ UI  │       │ Car │      │Intersection│
          └─────┘       └─────┘      └────────────┘
             │             │             │
             ▼             ▼             ▼
          Display       Movement     Traffic Logic
```

---

# 🧩 Main Classes

## `Car`

Represents a car in the simulation.

Example:

```java
public class Car {

    private double x;
    private double y;
    private double speed;
    private Direction direction;

}
```

The `Car` object contains the state of a vehicle.

---

## `Direction`

Represents the direction of a car.

```java
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}
```

---

## `TrafficLight`

Represents a traffic light.

```java
public enum TrafficLightState {
    RED,
    YELLOW,
    BLUE
}
```

The traffic light changes according to the intersection logic.

---

## `Intersection`

Represents the physical intersection.

It contains the information necessary to determine what is happening at the crossing.

```text
             NORTH
               ↓
               │
               │
WEST ──────────┼────────── EAST
               │
               │
               ↑
             SOUTH
```

---

## `IntersectionLogic`

Responsible for deciding:

* Which light is green
* Which cars can pass
* Which cars must stop
* When traffic lights change

This class should contain the **rules**, not the graphical code.

---

## `Simulation`

Controls the global simulation.

It connects:

```text
Cars
Traffic Lights
Intersection
UI
```

The simulation repeatedly updates the state of the application.

---

# 🔁 Simulation Loop

The application works approximately like this:

```text
             START
               │
               ▼
       Update traffic lights
               │
               ▼
          Update cars
               │
               ▼
      Check intersection
               │
               ▼
         Render the UI
               │
               ▼
            Repeat
```

For example:

```text
1. Traffic light = RED
2. Car approaches intersection
3. Car checks the light
4. Car stops
5. Light changes to GREEN
6. Car starts moving
7. UI displays the new position
```

---

# 🧠 Design Principles

The project should follow **Separation of Concerns**.

Each component should have one main responsibility:

```text
UI
 ↓
Display things

Car
 ↓
Manage car state/movement

Intersection
 ↓
Represent intersection

IntersectionLogic
 ↓
Manage traffic rules

Simulation
 ↓
Coordinate everything
```

Avoid putting everything inside `Main.java`.

---

# 📦 Packages

### `ui`

Contains everything related to the graphical interface.

```text
ui/
├── SimulationWindow.java
├── SimulationPanel.java
└── Renderer.java
```

### `car`

Contains everything related to vehicles.

```text
car/
├── Car.java
├── Direction.java
└── CarMovement.java
```

### `intersection`

Contains intersection and traffic-light logic.

```text
intersection/
├── Intersection.java
├── TrafficLight.java
└── IntersectionLogic.java
```

### `simulation`

Contains the global simulation management.

```text
simulation/
├── Simulation.java
└── SimulationConfig.java
```

---

# 🚀 Running the Project

If using Maven:

```bash
mvn clean install
```

Then:

```bash
mvn exec:java
```

Or run `Main.java` directly from your IDE.

---

# 🎯 Final Goal

The final application should show a working traffic simulation where:

* 🚗 Cars move
* 🚦 Traffic lights change
* 🛑 Cars stop at red lights
* 🟢 Cars move at green lights
* 🚧 Cars respect the intersection
* 🎨 Everything is displayed through the graphical interface


