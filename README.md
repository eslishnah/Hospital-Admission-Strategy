# Hospital Admission — Assignment 5

Strategy Design Pattern project in Java using Maven and JUnit 5.

The project has two strategy families:

- **Bed Selection:** first available bed, nearest bed, least busy ward.
- **Lab Scheduling:** first come, highest urgency, shortest test.

Each family has three strategies. The user can select a strategy manually, or the program can choose one automatically using `HospitalStrategySelector`.

## How to Run

Open the project in IntelliJ IDEA as a Maven project and run `Main.java`.

To run the tests:

```bash
mvn test
```

The program shows the results of different strategies and allows switching between them during runtime.

## Classes and Responsibilities

| Class | Responsibility |
|---|---|
| `Bed` | Stores information about a hospital bed. |
| `LabRequest` | Stores laboratory request details. |
| `BedSelectionStrategy` | Defines the method for selecting a bed. |
| `FirstAvailableBedStrategy` | Finds the first available bed. |
| `NearestBedStrategy` | Finds the closest available bed. |
| `LeastBusyWardStrategy` | Chooses a bed in the least busy ward. |
| `BedAllocationContext` | Runs the selected bed strategy. |
| `BedPlanner` | Provides the bed selection method used by regression tests. |
| `LabSchedulingStrategy` | Defines the method for selecting a laboratory request. |
| `FirstComeLabStrategy` | Chooses the earliest laboratory request. |
| `UrgentLabStrategy` | Chooses the most urgent laboratory request. |
| `ShortestLabStrategy` | Chooses the request with the shortest duration. |
| `LabSchedulingContext` | Runs the selected lab strategy. |
| `WardLoadSource` | Defines how ward occupancy is accessed. |
| `SimulatedWardLoadSource` | Provides simulated ward occupancy data. |
| `HospitalStrategySelector` | Automatically selects strategies based on hospital conditions. |
| `Main` | Runs the console demonstration of both strategy families. |
| `BedPlannerRegressionTest` | Checks bed selection results before and after refactoring. |
| `StrategyTest` | Tests strategies, selectors, context switching, external data handling. |

## Strategy vs State vs Template Method

I used Strategy because the program needs different ways to select beds and schedule lab requests. Each algorithm is in a separate class, so it can be changed while the program is running.

State is used when an object's behavior depends on its internal state. Template Method uses inheritance to define common algorithm steps. My project uses separate strategy classes through interfaces, which makes Strategy more suitable.

## Refactoring History

At first, I implemented bed selection using `if-else` inside `BedPlanner`. After that, I moved the algorithms into separate Strategy classes.

The same regression test passes in both versions.

- [Before — if-else implementation](https://github.com/eslishnah/Hospital-Admission-Strategy/commit/c95c732)
- [After — Strategy Pattern](https://github.com/eslishnah/Hospital-Admission-Strategy/commit/d7e4320)