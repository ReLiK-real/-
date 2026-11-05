import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");
        String answer = scanner.nextLine();
        System.out.println("Ваш ответ:\t" + answer);

        if (answer.equals("ДА")) {
            System.out.println("Выбери сложность игры (от 1 до 5):");
            int difficultGame = scanner.nextInt();
            System.out.println("Выбранная сложность:\t" + difficultGame);

            scanner.nextLine();
            System.out.println("Хотите ли вы взять упрощение игры? (ДА или НЕТ)");
            String cheatAnswer = scanner.nextLine();

            int extraLives = 3;
            if (cheatAnswer.equals("ДА")) {
                System.out.println("Сколько жизней вы хотите себе взять?");
                extraLives = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Ваши жизни изменены на: " + extraLives);
            } else {
                System.out.println("Упрощение не взято. Играем по стандартным правилам!");
            }

            while (true) {
                boolean isWon = playGame(difficultGame, extraLives, scanner);

                if (isWon) {
                    // «Съедаем» Enter, оставшийся от nextInt() в playGame
                    scanner.nextLine();

                    System.out.println("Вы дошли до замка! Хотите продолжить игру? (ДА или НЕТ)");
                    String continueAnswer = scanner.nextLine();

                    if (continueAnswer.equals("ДА")) {
                        if (difficultGame >= 5) {
                            System.out.println("Поздравляем! Вы прошли игру на максимальной сложности! Игра окончена.");
                            break;
                        }
                        difficultGame++;
                        System.out.println("\n=== Переход на сложность " + difficultGame + " ===");
                    } else {
                        System.out.println("Спасибо за игру! До встречи.");
                        break;
                    }
                } else {
                    System.out.println("Закончились жизни. Итог: Вы проиграли.");
                    break;
                }
            }
        } else {
            System.out.println("Жаль, приходи ещё!");
        }
    }

    static boolean playGame(int difficultGame, int initialLives, Scanner scanner) {
        String castle = "\uD83C\uDFF0";
        int sizeBoard = 5;
        int step = 0;

        Person person = new Person(sizeBoard);
        person.setLive(initialLives);

        Random random = new Random();
        int castleX = 1 + random.nextInt(sizeBoard);
        int castleY = 1;

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 1; y <= sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                board[y - 1][x - 1] = "  ";
            }
        }

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 1;
        Monster[] monsters = new Monster[countMonster];
        int count = 0;

        while (count < countMonster) {
            Monster monster;
            if (random.nextBoolean()) {
                monster = new Monster(sizeBoard);
            } else {
                monster = new BigMonster(sizeBoard);
            }

            int row = monster.getY() - 1;
            int column = monster.getX() - 1;

            if (board[row][column].equals("  ")) {
                board[row][column] = monster.getImage();
                monsters[count] = monster;
                count++;
            }
        }

        board[castleY - 1][castleX - 1] = castle;

        while (true) {
            board[person.getY() - 1][person.getX() - 1] = person.getImage();
            outputBoard(board, person.getLive());

            System.out.println("Введите, куда будет ходить персонаж "
                    + "(ход возможен только по вертикали или горизонтали на одну клетку)"
                    + "\nКоординаты персонажа — (x: " + person.getX()
                    + ", y: " + person.getY() + ")");

            int x = scanner.nextInt();
            int y = scanner.nextInt();
            System.out.println(x + ", " + y);

            if (person.isMoveCorrect(x, y)) {
                if (board[y - 1][x - 1].equals("  ")) {
                    board[person.getY() - 1][person.getX() - 1] = "  ";
                    person.move(x, y);
                    step++;

                    System.out.println("Ход корректный; новые координаты: "
                            + person.getX() + ", " + person.getY()
                            + "\nХод номер: " + step);
                } else if (board[y - 1][x - 1].equals(castle)) {
                    System.out.println("Вы прошли игру!");
                    return true;
                } else {
                    for (Monster monster : monsters) {
                        if (monster.conflictPerson(x, y)) {
                            if (monster.taskMonster(difficultGame)) {
                                board[person.getY() - 1][person.getX() - 1] = "  ";
                                person.move(x, y);
                                step++;
                            } else {
                                person.downLive();
                            }
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Некорректный ход");
            }

            if (person.getLive() <= 0) {
                return false;
            }
        }
    }

    static void outputBoard(String[][] board, int live) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        for (String[] row : board) {
            System.out.println(wall);
            for (String col : row) {
                System.out.print(leftBlock + col + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);

        System.out.println("Количество жизней:\t" + live + "\n");
    }
}