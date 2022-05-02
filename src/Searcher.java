import java.util.*;

public class Searcher {

    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    Searcher(){

        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }

    State Astar(State initialState)     //Astar algorithm with closed set
    {
        if(initialState.isFinal()) return initialState;
        this.frontier.add(initialState);
        while(this.frontier.size() > 0)
        {
            State currentState = this.frontier.remove(0);
            if(currentState.isFinal()) return currentState;

            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
                Collections.sort(this.frontier);
            }
        }
        return null;
    }
}
