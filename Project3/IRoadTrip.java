//Yifan Wan
//12/6/2023

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class IRoadTrip {

    private Map<String, Map<String, Integer>> borders; // Country name -> (Neighbor, Border length)
    private Map<String, String> countryCodes; // Country name -> Country code
    private Map<String, Integer> distances; // Country code pairs -> Distance

    public IRoadTrip(String[] args) {
        borders = new HashMap<>();
        countryCodes = new HashMap<>();
        distances = new HashMap<>();
        readStateNameFile(args[2]);
        readCapDistFile(args[1]);
        readBordersFile(args[0]);
    }

    private void readBordersFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" = ");
                String country = parts[0].trim();
                Map<String, Integer> neighbors = new HashMap<>();
                if (parts.length > 1) {
                    for (String borderInfo : parts[1].split(";")) {
                        String[] borderDetails = borderInfo.trim().split(" ");
                        neighbors.put(borderDetails[0], Integer.parseInt(borderDetails[1].split(" ")[0].replaceAll("[^\\d.]", "")));
                    }
                }
                borders.put(country, neighbors);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readStateNameFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length > 2) {
                    countryCodes.put(parts[2], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCapDistFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4) {
                    String key = parts[1] + "-" + parts[3];
                    distances.put(key, Integer.parseInt(parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getDistance(String country1, String country2) {
        String code1 = countryCodes.getOrDefault(country1, "");
        String code2 = countryCodes.getOrDefault(country2, "");
        return distances.getOrDefault(code1 + "-" + code2, -1);
    }

    public List<String> findPath(String country1, String country2) {
        if (!borders.containsKey(country1) || !borders.containsKey(country2)) {
            return Collections.emptyList();
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue));

        for (String country : borders.keySet()) {
            distances.put(country, Integer.MAX_VALUE);
            previous.put(country, null);
        }

        distances.put(country1, 0);
        queue.add(new AbstractMap.SimpleEntry<>(country1, 0));

        while (!queue.isEmpty()) {
            String currentCountry = queue.poll().getKey();

            if (currentCountry.equals(country2)) {
                break;
            }

            for (Map.Entry<String, Integer> neighborEntry : borders.get(currentCountry).entrySet()) {
                String neighbor = neighborEntry.getKey();
                int distance = neighborEntry.getValue();

                int totalDistance = distances.get(currentCountry) + distance;
                if (totalDistance < distances.get(neighbor)) {
                    distances.put(neighbor, totalDistance);
                    previous.put(neighbor, currentCountry);
                    queue.add(new AbstractMap.SimpleEntry<>(neighbor, totalDistance));
                }
            }
        }

        return reconstructPath(previous, country1, country2);
    }

    private List<String> reconstructPath(Map<String, String> previous, String start, String end) {
        LinkedList<String> path = new LinkedList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.addFirst(at);
        }

        if (path.getFirst().equals(start)) {
            return formatPath(path);
        } else {
            return Collections.emptyList();
        }
    }

    private List<String> formatPath(List<String> path) {
        List<String> formattedPath = new ArrayList<>();
        for (int i = 1; i < path.size(); i++) {
            String startCountry = path.get(i - 1);
            String endCountry = path.get(i);
            int distance = borders.get(startCountry).get(endCountry);
            formattedPath.add(startCountry + " --> " + endCountry + " (" + distance + " km)");
        }
        return formattedPath;
    }

    public void acceptUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the name of the first country (type EXIT to quit): ");
            String firstCountry = scanner.nextLine();
            if ("EXIT".equalsIgnoreCase(firstCountry)) break;

            System.out.print("Enter the name of the second country (type EXIT to quit): ");
            String secondCountry = scanner.nextLine();
            if ("EXIT".equalsIgnoreCase(secondCountry)) break;

            List<String> path = findPath(firstCountry, secondCountry);
            if (path.isEmpty()) {
                System.out.println("No path found.");
            } else {
                System.out.println("Route from " + firstCountry + " to " + secondCountry + ":");
                path.forEach(System.out::println);
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        IRoadTrip a3 = new IRoadTrip(args);
        a3.acceptUserInput();
    }
}
