import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.HashMap;
import java.util.Map;
public class BaseballElimination {

    private int size;
    private int[] wins;
    private int[] loses;
    private int[] remaining;
    private int[][] eachother;
    private Map<String, Integer> teamlist;
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        In input = new In(filename);
        size = input.readInt();
        teamlist = new HashMap<>();
        wins = new int[size];
        loses = new int[size];
        remaining = new int[size];
        eachother = new int[size][size];
        for (int i = 0; i < size; i++) {
            String name = input.readString();
            teamlist.put(name, i);
            wins[i] = input.readInt();
            loses[i] = input.readInt();
            remaining[i] = input.readInt();
            for (int j = 0; j < size; j++) {
                eachother[i][j] = input.readInt();
            }
        }
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
        if (team == null) {
            throw new IllegalArgumentException();
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }
        return null;
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
