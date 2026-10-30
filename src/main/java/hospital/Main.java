package hospital;

import hospital.bed.*;
import hospital.lab.*;
import hospital.model.Bed;
import hospital.model.LabRequest;
import hospital.selector.HospitalStrategySelector;
import hospital.source.SimulatedWardLoadSource;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Bed> beds = List.of(
                new Bed("B1", 1, 8, true),
                new Bed("B2", 2, 2, true),
                new Bed("B3", 3, 5, true),
                new Bed("B4", 3, 1, false));
        List<LabRequest> requests = List.of(
                new LabRequest("L1", 1, 2, 10),
                new LabRequest("L2", 2, 5, 8),
                new LabRequest("L3", 3, 3, 3),
                new LabRequest("L4", 4, 1, 6));
        HospitalStrategySelector selector = new HospitalStrategySelector(new SimulatedWardLoadSource());
        BedPlanner planner = new BedPlanner(new SimulatedWardLoadSource());

        System.out.println("--- BED STRATEGIES: SAME INPUT ---");
        for (String mode : List.of("FIRST", "NEAREST", "LEAST_BUSY")) {
            System.out.println(mode + ": " + planner.choose(beds, mode).getId());
        }
        System.out.println("--- LAB STRATEGIES: SAME INPUT ---");
        for (String mode : List.of("FIRST", "URGENT", "SHORTEST")) {
            LabSchedulingContext context = new LabSchedulingContext(selector.labByName(mode));
            System.out.println(mode + ": " + context.choose(requests).getId());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("--- WRAPPED STRATEGIES (MAX DISTANCE 5) ---");

        for (String mode : List.of("FIRST", "NEAREST", "LEAST_BUSY")) {
            BedSelectionStrategy original = selector.bedByName(mode);

            BedAllocationContext context = new BedAllocationContext(
                    new MaxDistanceBedStrategy(original, 5));

            Bed result = context.choose(beds);

            System.out.println(mode + ": " +
                    (result == null ? "No suitable bed" : result.getId()));
        }

        System.out.print("Manual bed strategy (FIRST/NEAREST/LEAST_BUSY): ");
        String bedMode = scanner.nextLine().trim();
        System.out.print("Manual lab strategy (FIRST/URGENT/SHORTEST): ");
        String labMode = scanner.nextLine().trim();
        BedAllocationContext bedContext = new BedAllocationContext(selector.bedByName(bedMode));
        LabSchedulingContext labContext = new LabSchedulingContext(selector.labByName(labMode));
        System.out.println("Manual bed: " + bedContext.choose(beds).getId());
        System.out.println("Manual lab: " + labContext.choose(requests).getId());

        System.out.print("Patient urgency (1-5): ");
        int urgency = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Hospital busy? (y/n): ");
        boolean busy = scanner.nextLine().trim().equalsIgnoreCase("y");
        System.out.print("Waiting lab requests count: ");
        int waitingCount = Integer.parseInt(scanner.nextLine().trim());

        bedContext.setStrategy(selector.selectBed(urgency, busy));
        labContext.setStrategy(selector.selectLab(urgency, waitingCount));
        System.out.println("Automatic bed: " + bedContext.choose(beds).getId());
        System.out.println("Automatic lab: " + labContext.choose(requests).getId());
        System.out.println("Both contexts switched strategies at runtime.");
    }
}
