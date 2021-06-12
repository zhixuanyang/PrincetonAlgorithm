import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class BaseballElimination {
    private int size;
    private int[] wins;
    private int[] loses;
    private int[] remaining;
    private boolean marked;
    private ArrayList<String> teams;
    private int[][] eachother;
    private Map<String, Integer> teamlist;
    private FordFulkerson alog;
    private FlowNetwork network;
    private ArrayList<String> result;
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        In input = new In(filename);
        size = input.readInt();
        teamlist = new HashMap<>();
        wins = new int[size];
        loses = new int[size];
        teams = new ArrayList<>();
        remaining = new int[size];
        eachother = new int[size][size];
        for (int i = 0; i < size; i++) {
            String name = input.readString();
            teamlist.put(name, i);
            teams.add(name);
            wins[i] = input.readInt();
            loses[i] = input.readInt();
            remaining[i] = input.readInt();
            for (int j = 0; j < size; j++) {
                eachother[i][j] = input.readInt();
            }
        }
        result = new ArrayList<>();
        marked = false;
    }

    public int numberOfTeams() {
        return size;
    }

    public Iterable<String> teams() {
        return teamlist.keySet();
    }

    public int wins(String team) {
        if (team == null || !teamlist.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return wins[teamlist.get(team)];
    }

    public int losses(String team) {
        if (team == null || !teamlist.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return loses[teamlist.get(team)];
    }

    public int remaining(String team) {
        if (team == null || !teamlist.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return remaining[teamlist.get(team)];
    }

    public int against(String team1, String team2) {
        if (team1 == null || team2 == null
                || !teamlist.containsKey(team1)
                || !teamlist.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        return eachother[teamlist.get(team1)][teamlist.get(team2)];
    }

    public boolean isEliminated(String team) {
        if (team == null || !teamlist.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        marked = false;
        int temp = teamlist.get(team);
        for (String name : teams) {
            if (name == team) {
                continue;
            }
            int id = teamlist.get(name);
            if (wins[temp] + remaining[temp] < wins[id]) {
                result.add(name);
                marked = true;
            }
        }
        if (marked) {
            return false;
        } else {
            return ffalog(temp);
        }
    }

    private boolean ffalog(int team) {
        FlowEdge edge;
        FlowEdge first;
        FlowEdge second;
        FlowEdge third;
        int tempsize = calculate(size - 1 - 1);
        int count = 0;
        int total = 0;
        network = new FlowNetwork(2 + size + tempsize);
        for (int i = 0; i < size; i++) {
            if (i == team) {
                continue;
            }
            for (int j = i + 1; j < size; j++) {
                if (j == team) {
                    continue;
                }
                count += 1;
                edge = new FlowEdge(0, count, eachother[i][j]);
                total += eachother[i][j];
                first = new FlowEdge(count, tempsize + i + 1, Double.POSITIVE_INFINITY);
                second = new FlowEdge(count, tempsize + j + 1, Double.POSITIVE_INFINITY);
                network.addEdge(edge);
                network.addEdge(first);
                network.addEdge(second);
            }
        }
        for (int i = 0; i < size; i++) {
            if (i == team) {
                continue;
            }
            third = new FlowEdge(tempsize + i + 1, 1 + size + tempsize,
                    wins[team] + remaining[team] - wins[i]);
            network.addEdge(third);
        }
        alog = new FordFulkerson(network, team, 2 + size + tempsize);
        return !(total == (int) alog.value());
    }

    private int calculate(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n + calculate(n - 1);
        }
    }
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || !teamlist.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        if (!isEliminated(team)) {
            return null;
        } else {
            for (String name : teamlist.keySet()) {
                if (name == team) {
                    continue;
                }
                int id = teamlist.get(name);
                if (alog.inCut(id)) {
                    result.add(name);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams5.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
