package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {
    public static void main(String[] args) {
    	//재시작/종료
    	  while (true) {
              playGame();
              System.out.print("게임을 다시 시작하시겠습니까? (1: 재시작, 2: 종료): ");
              int choice = Integer.parseInt(Console.readLine());
              if (choice == 2) {
                  System.out.println("게임을 종료합니다.");
                  break;
              }
          }
      }
    	//게임시작
      private static void playGame() {
          int[] computerNumbers = RandomNumbers(1, 9, 3);

          System.out.println("게임을 시작합니다. 3자리 숫자를 맞혀보세요!");
          System.out.println("컴퓨터의 번호" + computerNumbers[0]);
          System.out.println("컴퓨터의 번호" + computerNumbers[1]);
          System.out.println("컴퓨터의 번호" + computerNumbers[2]);

          int attempts = 0;

          while (true) {
              System.out.print("3자리 숫자를 입력하세요: ");
              int userGuess = Integer.parseInt(Console.readLine());

              if (!isValidGuess(userGuess)) {
                  throw new IllegalArgumentException("올바른 범위의 3자리 숫자를 입력하세요.");
              }

              int[] userDigits = getDigits(userGuess);

              int strikes = countStrikes(userDigits, computerNumbers);
              int balls = countBalls(userDigits, computerNumbers);

              if (strikes == 3) {
                  System.out.println("3스트라이크, 3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                  break;
              } else if (strikes == 0 && balls == 0) {
                  System.out.println("낫싱");
              } else {
                  System.out.println(strikes + "스트라이크 " + balls + "볼");
              }

              attempts++;
          }

          System.out.println("게임 종료! 시도 횟수: " + attempts);
      }
      // 숫자 생성
      private static int[] RandomNumbers(int min, int max, int count) {
          int[] numbers = new int[count];
          for (int i = 0; i < count; i++) {
              int num;
              do {
                  num = Randoms.pickNumberInRange(min, max);
              } while (contains(numbers, num));
              numbers[i] = num;
          }
          return numbers;
      }
      // 컴퓨터의 숫자 중복 확인
      private static boolean contains(int[] array, int value) {
          for (int num : array) {
              if (num == value) {
                  return true;
              }
          }
          return false;
      }
      // 사용자의 입력 유효성검사(중복,자리수)
      private static boolean isValidGuess(int guess) {
          if (guess < 100 || guess > 999) {
              return false;
          }

          int[] digits = getDigits(guess);
          return digits[0] != digits[1] && digits[1] != digits[2] && digits[0] != digits[2];
      }
      // 숫자 분리
      private static int[] getDigits(int number) {
          int[] digits = new int[3];
          //백의 자리
          digits[0] = number / 100;
          //십의 자리
          digits[1] = (number % 100) / 10;
          //일의 자리
          digits[2] = number % 10;
          return digits;
      }
      // 스트라이크 개수
      private static int countStrikes(int[] guess, int[] answer) {
          int strikes = 0;
          for (int i = 0; i < guess.length; i++) {
              if (guess[i] == answer[i]) {
                  strikes++;
              }
          }
          return strikes;
      }
      // 볼 개수
      private static int countBalls(int[] guess, int[] answer) {
          int balls = 0;
          for (int i = 0; i < guess.length; i++) {
              for (int j = 0; j < answer.length; j++) {
                  if (i != j && guess[i] == answer[j]) {
                      balls++;
                  }
              }
          }
          return balls;
      }
  }