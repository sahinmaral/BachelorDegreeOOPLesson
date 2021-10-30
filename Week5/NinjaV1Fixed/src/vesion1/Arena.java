package vesion1;

public class Arena {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Ninja blue = new Ninja("Blue_Maroon");
        Ninja yellow = new Ninja("Yellow_DarkBlue");
        int round = 1;
        while (blue.getHealthPoint() > 0 && yellow.getHealthPoint() > 0) {
            System.out.println("\nRound ::>>" + round);
            if (blue.Attack(blue, yellow)) {
                System.out.println(blue.getName() + " successfully attacked to the " + yellow.getName());
                if (yellow.getHealthPoint() == 0) {
                    ResultInfos(blue,yellow);
                    break;
                }
            }
            if (yellow.Attack(yellow, blue)) {
                System.out.println(yellow.getName() + " successfully attacked to the " + blue.getName());
                if (blue.getHealthPoint() == 0) {
                    ResultInfos(blue,yellow);
                    break;
                }
            }
            ResultInfos(blue,yellow);
            round++;
        }
    }

    public static void ResultInfos(Ninja ninja1, Ninja ninja2){
        System.out.print(ninja1.toString());
        System.out.print(ninja2.toString());
    }

}
