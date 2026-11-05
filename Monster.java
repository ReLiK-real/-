import java.util.Random;
import java.util.Scanner;

public class Monster {
    private String image = "\uD83E\uDDDF";
    private final int x, y;

    Monster(int sizeBoard) {
        Random random = new Random();
        x = 1 + random.nextInt(sizeBoard);
        y = 1 + random.nextInt(sizeBoard); // Исправлено, чтобы монстры могли быть везде
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean conflictPerson(int personX, int personY) {
        return personX == x && personY == y;
    }

    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу (Обычный монстр):");

        Random random = new Random();

        // Чем выше сложность, тем больше числа
        int maxNumber = difficultGame * 20;
        int a = random.nextInt(1, maxNumber);
        int b = random.nextInt(1, maxNumber);

        int trueAnswer = a + b;

        System.out.println("Реши пример: " + a + " + " + b + " = ?");
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();

        if (trueAnswer == answer) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        } else {
            System.out.println("Ты проиграл эту битву!");
            return false;
        }
    }
}