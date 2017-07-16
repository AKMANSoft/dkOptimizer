import java.util.Comparator;

public class Player {

    private Position position;

    private String name;

    private int salary;

    private double projectedPoints;

    public Player() {

    }

    public Player(Player player) {
        this.name = player.getName();
        this.salary = player.getSalary();
        this.projectedPoints = player.getProjectedPoints();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public double getProjectedPoints() {
        return projectedPoints;
    }

    public void setProjectedPoints(double projectedPoints) {
        this.projectedPoints = projectedPoints;
    }
}
