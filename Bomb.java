import java.util.*;
import java.util.stream.Collectors;

public class Bomb {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayDeque<Integer> effects = Arrays.stream(scanner.nextLine().split(", "))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        ArrayDeque<Integer> casings = new ArrayDeque<>();

        Arrays.stream(scanner.nextLine().split(", "))
                .mapToInt(Integer::parseInt)
                .forEach(casings::push);

        TreeMap<String, Integer> bombs = new TreeMap<>();

        bombs.put("Cherry Bombs", 0);
        bombs.put("Datura Bombs", 0);
        bombs.put("Smoke Decoy Bombs", 0);

        while (!effects.isEmpty() && !casings.isEmpty()) {
            if (hasAllBombs(bombs)) {
                break;
            }
            int effect = effects.peek();
            int casing = casings.pop();

            int sum = effect + casing;

            if (sum == 40) {
                effects.poll();
                bombs.put("Datura Bombs", bombs.get("Datura Bombs") + 1);
            } else if (sum == 60) {
                effects.poll();
                bombs.put("Cherry Bombs", bombs.get("Cherry Bombs") + 1);
            } else if (sum == 120) {
                effects.poll();
                bombs.put("Smoke Decoy Bombs", bombs.get("Smoke Decoy Bombs") + 1);
            } else {
                casings.push(casing - 5);
            }
        }

        if (hasAllBombs(bombs)) {
            System.out.println("Bene! You have successfully filled the bomb pouch!");
        } else {
            System.out.println("You don't have enough materials to fill the bomb pouch.");
        }

        String effectsOutput = "Bomb Effects: "
                + (effects.isEmpty() ? "empty" :
                effects.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")));

        System.out.println(effectsOutput);

        String casingsOutput = "Bomb Casings: "
                + (casings.isEmpty() ? "empty" :
                casings.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")));

        System.out.println(casingsOutput);

        for (Map.Entry<String, Integer> entry : bombs.entrySet()) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue());
        }
    }

    private static boolean hasAllBombs(TreeMap<String, Integer> bombs) {
        return bombs.get("Cherry Bombs") >= 3
                && bombs.get("Datura Bombs") >= 3
                && bombs.get("Smoke Decoy Bombs") >= 3;
    }
}
