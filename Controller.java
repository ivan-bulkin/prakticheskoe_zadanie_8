package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;//надо сделать так, чтобы не подтягивалась куча дополнительных строк

import java.util.Random;

public class Controller {

    int zagadannoeChislo;//В этой переменной храним загаданное число
    int kolichestvoPopitok = 5;//В этой переменной храним загаданное число, 5 - означает, что игра сыграна и можно начать новую игру
    int polzovatelVvel;//В этой переменной хранится число, которое ввёл пользователь
    int kolichestvoPobed = 0;//Переменная, в которой храниться количество Побед
    int kolichestvoProigrishei = 0;//Переменная, в которой храниться количество Проигрышей

    @FXML
    private TextField numberInputField;

    @FXML
    private Label kolichestvoPobedLabel;

    @FXML
    private Label kolichestvoProigrisheiLabel;

    @FXML
    private Label viVveliLabel;

    @FXML
    public void initialize() {
        kolichestvoPobedLabel.setText(String.valueOf(kolichestvoPobed));
        kolichestvoProigrisheiLabel.setText(String.valueOf(kolichestvoProigrishei));
        viVveliLabel.setText("");
    }

    @FXML
        //Выбор в верхнем меню Файл-->Выход Ctrl+Q
    void doExit() {
        System.exit(0);//Выходим из программы
    }

    @FXML
        //Выбор в верхнем меню Помощь-->О программе
        //Выводит подсказку к программе
    void doAboutProgramm() {
        errorInputNumber(Alert.AlertType.INFORMATION, "О программе", "Игра Угадай число от 0 до 9-ти.\n---===Иван Булкин продакшн===---\nКомпьютер загадывает число от 0 до 9-ти.\nВы должны за три попытки угадать загаданное число.");
    }

    @FXML
        //Нажатие кнопки Новая игра - случайным образом загадывается число от 0 до 9-ти
    void doButtonNewGame() {
        if (kolichestvoPopitok == 5) {//Можем начать новую игру
            zagadannoeChislo = (int) (Math.random() * 10);
            kolichestvoPopitok = 4;
            numberInputField.clear();
            viVveliLabel.setText("");
//            System.out.println("Загаданное число: " + zagadannoeChislo);//Тестовая строка, потом закомментировать
        } else {//Не даём начать новую игру, надо попробовать угадать предыдущее загаданное число
            errorInputNumber(Alert.AlertType.ERROR, "Надо доиграть игру", "Число уже загадано, Вы должны сперва его угадать.");
        }
    }

    @FXML
    void doRamdomAction() {

        if (kolichestvoPopitok == 5) {//Если kolichestvoPopitok=5, то это означает, что число ещё не загадано и игра не начата
            errorInputNumber(Alert.AlertType.ERROR, "Число ещё не загадано", "Чтобы начать новую игру нажмите кнопку Новая игра.");
            return;
        }

        String message = numberInputField.getText();
        numberInputField.clear();

        if (!message.isBlank()) {

            try {
                polzovatelVvel = Integer.parseInt(message);
                if (polzovatelVvel < 0 || polzovatelVvel > 9) {
                    errorInputNumber(Alert.AlertType.ERROR, "Ошибка ввода числа", "Введите целое число от 0 до 9-ти.");
                    return;
                }
//                System.out.println("Введено число: " + polzovatelVvel);//Тестовая строка, потом закомментировать
                if (polzovatelVvel == zagadannoeChislo) {
                    if (kolichestvoPopitok == 4) {
                        viVveliLabel.setText("Поздравляем !!!\nВы угадали с первой попытки\n" + zagadannoeChislo + " = " + polzovatelVvel);
                        kolichestvoPopitok = 5;
                        kolichestvoPobed++;
                        kolichestvoPobedLabel.setText(String.valueOf(kolichestvoPobed));
                        return;
                    }
                    if (kolichestvoPopitok == 3) {
                        viVveliLabel.setText("Поздравляем !!!\nВы угадали со второй попытки\n" + zagadannoeChislo + " = " + polzovatelVvel);
                        kolichestvoPopitok = 5;
                        kolichestvoPobed++;
                        kolichestvoPobedLabel.setText(String.valueOf(kolichestvoPobed));
                        return;
                    }
                    if (kolichestvoPopitok == 2) {
                        viVveliLabel.setText("Поздравляем !!!\nВы угадали с третьей попытки\n" + zagadannoeChislo + " = " + polzovatelVvel);
                        kolichestvoPopitok = 5;
                        kolichestvoPobed++;
                        kolichestvoPobedLabel.setText(String.valueOf(kolichestvoPobed));
                        return;
                    }
                } else {
                    if (zagadannoeChislo > polzovatelVvel & kolichestvoPopitok != 1) {
                        viVveliLabel.setText("Вы ввели: " + polzovatelVvel + "\nНаше число больше" + "\nПопыток осталось: " + (kolichestvoPopitok - 2));
                    }
                    if (zagadannoeChislo < polzovatelVvel & kolichestvoPopitok != 1) {
                        viVveliLabel.setText("Вы ввели: " + polzovatelVvel + "\nНаше число меньше" + "\nПопыток осталось: " + (kolichestvoPopitok - 2));
                    }
                    if (kolichestvoPopitok == 2) {
                        viVveliLabel.setText("Вы проиграли.\nЗагаданное число было: " + zagadannoeChislo);
                        kolichestvoPopitok = 5;
                        kolichestvoProigrishei++;
                        kolichestvoProigrisheiLabel.setText(String.valueOf(kolichestvoProigrishei));
                        return;
                    }

                    kolichestvoPopitok--;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorInputNumber(Alert.AlertType.ERROR, "Ошибка ввода числа", "Введите целое число от 0 до 9-ти.");
                return;
            }
        }
    }

    //Вот этот метод сделала сама Идея, когда я нажал на блоке кода Ctrl+Alt+M круто, в принципе
    private void errorInputNumber(Alert.AlertType error, String s, String s2) {
        var alert = new Alert(error);
        alert.setTitle(s);
        alert.setResizable(false);
//                alert.setContentText("Не верное число!");
        alert.setHeaderText(s2);
        alert.show();
    }

}
