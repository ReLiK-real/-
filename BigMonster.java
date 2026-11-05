import java.util.Random;
import java.util.Scanner;

public class BigMonster extends Monster {
    BigMonster(int sizeBoard) {
        super(sizeBoard);
        setImage("\uD83D\uDC79");
    }

    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу (Большой монстр):");

        Random random = new Random();

        // Чем выше сложность, тем больше числа (от 10 до 99 при сложности 2 и т.д.)
        int maxNumber = difficultGame * 10;

        int x = random.nextInt(2, maxNumber); // от 2, чтобы не умножать на 0 или 1
        int y = random.nextInt(2, maxNumber);
        int z = random.nextInt(1, maxNumber * 2); // Вычитаемое тоже растет

        int trueAnswer = x * y - z;

        System.out.println("Реши пример: " + x + " * " + y + " - " + z + " = ?");

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