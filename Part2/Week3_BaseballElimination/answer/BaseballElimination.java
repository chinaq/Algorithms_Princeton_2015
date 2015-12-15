import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import java.util.ArrayList;


public class BaseballElimination {
    
    private int total;
    private LinearProbingHashST<String, Integer> setTeams;
    private String[] strTeams;
    private int[] wins;
    private int[] loss;
    private int[] left;
    private int[][] games;
    
    private boolean[] isEliminated;
    private ArrayList<ArrayList<String>> certificate;
    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {          
        setData(filename);            
        doAllFordFulkerson();        
    }                
    
    
    private void setData(String filename) {
        In in = new In(filename);
        total = in.readInt();
        
        setTeams = new LinearProbingHashST<String, Integer>(total);
        strTeams = new String[total];
        wins = new int[total];
        loss = new int[total];
        left = new int[total];
        games = new int[total][total];
        isEliminated = new boolean[total];
        //certificate = (ArrayList<String>[]) new Object[total];
        certificate =  new ArrayList<ArrayList<String>>();
        for (int i = 0; i < total; i++) {
            certificate.add(null);
        }
        
        for (int i = 0; i < total; i++) {
            String team = in.readString();
            setTeams.put(team, i);
            strTeams[i] = team;
            wins[i] = in.readInt();
            loss[i] = in.readInt();
            left[i] = in.readInt();
            for (int j = 0; j < total; j++) {
                games[i][j] = in.readInt();
            }
        }   
    }
    
    
    
    private void doAllFordFulkerson() {  
        for (int x = 0; x < total; x++) {
            for (int i = 0; i < total; i++) {
                if (wins[x] + left[x] < wins[i]) {
                    isEliminated[x] = true;
                    certificate.set(x, new ArrayList<String>());
                    certificate.get(x).add(strTeams[i]);
                    break;
                }
            }
            if (!isEliminated[x]) 
                doFordFulkerson(x);
        }
    }
    
    
    
    private void doFordFulkerson(int x) {
        //构建一个net             
        int netTeamsCount = total - 1;
        int netGamesCount = 
            ((netTeamsCount - 1) + 1) * (netTeamsCount - 1) / 2;        
        int netTotalCount = netTeamsCount + netGamesCount + 2; 
        FlowNetwork net = new FlowNetwork(netTotalCount);   
        
        int wGame = 0;        
        for (int i = 0; i < total; i++) {            
            for (int j = i + 1; j < total; j++) {
                if (i != x && j != x) {
                    //添加Game的Edge
                    wGame++;
                    int capacity = games[i][j];
                    FlowEdge edgeGame = new FlowEdge(0, wGame, capacity);
                    net.addEdge(edgeGame);
                    
                    //添加Team的Edge
                    int wTeam1 = netGamesCount + 1 + i;                    
                    int wTeam2 = netGamesCount + 1 + j;      
                    if (i > x) wTeam1--;
                    if (j > x) wTeam2--;                                 
                    FlowEdge edgeTeam1 = 
                        new FlowEdge(wGame, wTeam1, Double.POSITIVE_INFINITY);
                    FlowEdge edgeTeam2 = 
                        new FlowEdge(wGame, wTeam2, Double.POSITIVE_INFINITY);
                    net.addEdge(edgeTeam1);
                    net.addEdge(edgeTeam2);                   
                }
            }
        }        
        
        
        for (int i = 0; i < total; i++) {        
            if (i != x) {
                //添加Team到结束点的Edge
                int vTeam = netGamesCount + 1 + i;
                if (i > x) vTeam--;
                int cap = wins[x] + left[x] - wins[i];
                FlowEdge edgeEnd = new FlowEdge(vTeam, netTotalCount - 1, cap);
                net.addEdge(edgeEnd); 
            }
        }
        
        
        
        //StdOut.println(strTeams[x] + " " + x);
        //StdOut.println(net.toString());        
        
        //计算ff
        FordFulkerson ff = new FordFulkerson(net, 0, netTotalCount - 1);
        
        //获取mincut
        int allNetGamesExpected = 0;
        for (int i = 0; i < total; i++) {            
            for (int j = i + 1; j < total; j++) {
                if (i != x && j != x) {
                    allNetGamesExpected += games[i][j];
                }
            }
        }
                
        if (ff.value() < allNetGamesExpected) {            
            isEliminated[x] = true;            
            certificate.set(x, new ArrayList<String>()); 
            for (int i = netGamesCount + 1; i < netTotalCount - 1; i++) {
                if (ff.inCut(i)) {
                    int teamNo = i - (netGamesCount + 1);
                    if (teamNo >= x) teamNo++;                    
                    certificate.get(x).add(strTeams[teamNo]);
                }
            }
        }
    }
    
        
    // number of teams
    public int numberOfTeams() {
        return total;
    }                        
        
    // all teams
    public Iterable<String> teams() {
        ArrayList<String> teams = new ArrayList<String>();
        for (int i = 0; i < total; i++) {
            teams.add(strTeams[i]);
        }
        return teams;
    }            
    
    // number of wins for given team
    public int wins(String team) {        
        ifNotTeamThrowEx(team);
        return wins[setTeams.get(team)];
    }
    
    // number of losses for given team
    public int losses(String team) {  
        ifNotTeamThrowEx(team);
        return loss[setTeams.get(team)];
    } 
    
    // number of remaining games for given team
    public int remaining(String team) {
        ifNotTeamThrowEx(team);
        return left[setTeams.get(team)];    
    } 

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {  
        ifNotTeamThrowEx(team1);
        ifNotTeamThrowEx(team2);
        return games[setTeams.get(team1)][setTeams.get(team2)];    
    }    

    // is given team eliminated?
    public boolean isEliminated(String team) {
        ifNotTeamThrowEx(team);
        return isEliminated[setTeams.get(team)];
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        ifNotTeamThrowEx(team);
        return certificate.get(setTeams.get(team));
    }  
    
    private void showString() {
       for (int i = 0; i < total; i++) {
            StdOut.print(i +  ": " + strTeams[i] + " | " 
                             + wins[i] + " " + loss[i] + " " + left[i] + " | ");
            for (int j = 0; j < total; j++) {
                StdOut.print(games[i][j] + " ");
            }
            StdOut.print("\n");
        }
    }
    
    
    
    private void ifNotTeamThrowEx(String team) {        
        if (!setTeams.contains(team)) {
            throw new java.lang.IllegalArgumentException();
        }
    }
    
    
    
    
    
    //main
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
        
//        BaseballElimination division = new BaseballElimination(args[0]);
//        division.showString();
        
    }
    
}