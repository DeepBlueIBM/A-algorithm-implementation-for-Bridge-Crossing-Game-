import java.util.ArrayList;

enum Position {RIGHT, LEFT}

public class State implements Comparable<State>{

    private int cannibalLeft;
    private int missionaryLeft;
    private int cannibalRight;
    private int missionaryRight;
    private Position boat;
    private int boatCapacity;

    private State father= null;

    private int score;
    private int g;

    public State(int cannibalLeft, int missionaryLeft, Position boat,
                 int cannibalRight, int missionaryRight, int boatCapacity, int g) {
        this.cannibalLeft = cannibalLeft;
        this.missionaryLeft = missionaryLeft;
        this.boat = boat;
        this.cannibalRight = cannibalRight;
        this.missionaryRight = missionaryRight;
        this.boatCapacity = boatCapacity;
        this.g = g;
    }

    void print(){
        if (boat == Position.LEFT) {
            System.out.println( "(" + cannibalLeft + "," + missionaryLeft + ",L,"
                    + cannibalRight + "," + missionaryRight + ")");
        } else {
            System.out.println( "(" + cannibalLeft + "," + missionaryLeft + ",R,"
                    + cannibalRight + "," + missionaryRight + ")");
        }
    }

    public ArrayList<State> getChildren(){
        ArrayList<State> children = new ArrayList<>();
        if (boat == Position.LEFT) {
            for (int i = 0; i <= boatCapacity+1; i++) {
                for (int j = 0; j <= boatCapacity+1; j++) {
                    if(i+j<boatCapacity+1 && !(i==0 && j==0) && (j==0 | i<=j)) {
                        testAndAdd(children, new State(cannibalLeft - i, missionaryLeft - j, Position.RIGHT,
                                cannibalRight + i, missionaryRight + j, boatCapacity, 0));
                    }
                }
            }
        }else{
            for (int i = 0; i <= boatCapacity+1; i++) {
                for (int j = 0; j <= boatCapacity+1; j++) {
                    if(i+j<boatCapacity+1 && !(i==0 && j==0) && (j==0 | i<=j)) {
                        testAndAdd(children, new State(cannibalLeft + i, missionaryLeft + j, Position.LEFT,
                               cannibalRight - i, missionaryRight - j, boatCapacity, 0));
                    }
                }
            }
        }
        return children;
    }


    public boolean isValid() {
        return missionaryLeft >= 0 && missionaryRight >= 0 && cannibalLeft >= 0 && cannibalRight >= 0
                && (missionaryLeft == 0 || missionaryLeft >= cannibalLeft)
                && (missionaryRight == 0 || missionaryRight >= cannibalRight);
    }

    private void testAndAdd(ArrayList<State> successors, State stateChildren) {
        if (stateChildren.isValid()) {
            stateChildren.heuristic();
            stateChildren.g+=1;
            stateChildren.setFather(this);
            successors.add(stateChildren);
        }
    }

    public int getCannibalLeft() {
        return cannibalLeft;
    }

    public void setCannibalLeft(int cannibalLeft) {
        this.cannibalLeft = cannibalLeft;
    }

    public int getMissionaryLeft() {
        return missionaryLeft;
    }

    public void setMissionaryLeft(int missionaryLeft) {
        this.missionaryLeft = missionaryLeft;
    }

    public int getCannibalRight() {
        return cannibalRight;
    }

    public void setCannibalRight(int cannibalRight) {
        this.cannibalRight = cannibalRight;
    }

    public int getMissionaryRight() {
        return missionaryRight;
    }

    public void setMissionaryRight(int missionaryRight) {
        this.missionaryRight = missionaryRight;
    }

    public int getBoatCapacity() {
        return boatCapacity;
    }

    public void setBoatCapacity(int boatCapacity) {
        this.boatCapacity = boatCapacity;
    }

    public void goToLeft() {
        boat = Position.LEFT;
    }

    public void goToRight() {
        boat = Position.RIGHT;
    }

    public boolean isOnLeft() {
        return boat == Position.LEFT;
    }

    public boolean isOnRight() {
        return boat == Position.RIGHT;
    }

    public State getFather() {
        return father;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getG(){
        return this.g;
    }

    public boolean isFinal() {
        return this.cannibalLeft == 0 && this.missionaryLeft == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) {
            return false;
        }
        State s = (State) obj;
        return (s.cannibalLeft == cannibalLeft && s.missionaryLeft == missionaryLeft
                && s.boat == boat && s.cannibalRight == cannibalRight
                && s.missionaryRight == missionaryRight);
    }

    public int hashCode(){
        return this.cannibalLeft + this.cannibalRight + this.missionaryLeft + this.missionaryRight + this.identifier();
    }

    int identifier(){

        int result = 0;
        result += Math.pow(cannibalLeft, 2);
        result += Math.pow(missionaryLeft, 3);
        result += Math.pow(cannibalRight, 4);
        result += Math.pow(missionaryRight, 5);

        return result;
    }

    private void heuristic(){

        int passengers = this.cannibalLeft + this.missionaryLeft; //2*N
        while(passengers>0){
            if(passengers>this.boatCapacity){
                passengers -= this.boatCapacity;
                this.score += 2;
            }else{
                passengers = 0;
                this.score += 1;
            }
        }
        this.score+=getG();
    }

    @Override
    public int compareTo(State s){

        return Double.compare(this.score, s.score);
    }
}
