# Hospital Admission — Assignment 5 (Strategy Pattern)

Java 17, Maven, JUnit 5. Two strategy families:

- Bed selection: first available, nearest, least-busy ward.
- Lab scheduling: arrival order, highest urgency, shortest expected test.

`HospitalStrategySelector` selects automatically from urgency, hospital load, and queue size. Both contexts can switch with `setStrategy()`. `WardLoadSource` isolates simulated ward occupancy. The tests inject a fixed fake source.

## Run

From IntelliJ: open `pom.xml` as a Maven project, run `hospital.Main`.

Terminal:

```bash
mvn test
mvn -q -DskipTests package
java -cp target/classes hospital.Main
```

## Demo

The program prints the same bed and lab inputs processed by all three strategies. Enter a manual choice (FIRST/NEAREST/LEAST_BUSY and FIRST/URGENT/SHORTEST). Then enter urgency, busy status, and lab queue length. It prints automatic results after replacing strategies with `setStrategy()`.

Sample inputs:

```text
FIRST
FIRST
5
y
4
```

Expected automatic choices: nearest bed B2, urgent lab L2.

## Classes and responsibilities (SRP)

| Class | Responsibility |
|---|---|
| `Bed` | Stores one hospital bed's data. |
| `LabRequest` | Stores one laboratory request's data. |
| `BedSelectionStrategy` | Defines the bed choice contract. |
| `FirstAvailableBedStrategy` | Selects the first available bed. |
| `NearestBedStrategy` | Selects the nearest available bed. |
| `LeastBusyWardStrategy` | Selects an available bed using ward occupancy. |
| `BedAllocationContext` | Delegates bed choice to the active strategy. |
| `BedPlanner` | Provides a stable bed choice entry point for regression tests. |
| `LabSchedulingStrategy` | Defines the laboratory request choice contract. |
| `FirstComeLabStrategy` | Selects the earliest arrived laboratory request. |
| `UrgentLabStrategy` | Selects the highest urgency laboratory request. |
| `ShortestLabStrategy` | Selects the shortest laboratory request. |
| `LabSchedulingContext` | Delegates laboratory scheduling to the active strategy. |
| `WardLoadSource` | Defines access to ward occupancy information. |
| `SimulatedWardLoadSource` | Supplies simulated ward occupancy values. |
| `HospitalStrategySelector` | Selects strategies using user choices, patient urgency, hospital activity, queue length. |
| `Main` | Demonstrates manual selection, automatic selection, runtime switching. |
| `BedPlannerRegressionTest` | Checks unchanged bed choice results across refactoring. |
| `StrategyTest` | Verifies strategies, selector rules, data source replacement, context switching. |

## Strategy vs State vs Template Method

This is Strategy because the program chooses different algorithms for the same task. The context holds a strategy interface, and the selected object can be replaced at runtime. A patient's state does not control which algorithm gets executed, so this is not State. Template Method defines steps of an algorithm in a parent class using inheritance. Here, each complete algorithm lives in a separate implementation of an interface, and the contexts use composition.

## Refactoring history

**Before commit:** ADD_BEFORE_COMMIT_URL

**After commit:** ADD_AFTER_COMMIT_URL

The unchanged `BedPlannerRegressionTest` must pass before and after refactoring. The first commit puts all three bed algorithms inside one `BedPlanner` class with `if/else`. The second replaces that implementation with Strategy while preserving `BedPlanner.choose(...)` behavior. The old implementation is provided in `refactoring-guide/BEFORE_BedPlanner.txt` for reference only; the evaluated code is under `src/`.
