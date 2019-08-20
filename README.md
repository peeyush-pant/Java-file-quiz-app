# Java-file-quiz-app

### Running the application

Run the application using:

```sh
java Design
```
Or 

By double-clicking the 'quiz.jar' file.

### Requirements

The system should have **JAVA** installed on it.


#### Using the application

When the user opens the application, the first view shows the information regarding the quiz. For ex. the number of questions, the time allotted, score for the correct answer and for the wrong answer.
![Initial](https://https://github.com/peeyush-pant/Java-file-quiz-app/screenshots/initial.JPG)
The number of digits should be equal to the number of questions.

The next screen will show question one by one to the user.
User can skip to a particular question number using the numbers panel on the left-hand side.
![Quiz](https://https://github.com/peeyush-pant/Java-file-quiz-app/screenshots/quiz.JPG)

Initially, all the numbers on the left are yellow which means unanswered.
Once the user answers a question and moves to the next question the answered one turned green.
![Answers](https://https://github.com/peeyush-pant/Java-file-quiz-app/screenshots/answers.JPG)

The user can submit the quiz when all questions are answered. The final score will be displayed to the user.
![Result](https://https://github.com/peeyush-pant/Java-file-quiz-app/screenshots/result.JPG)

If the user closes the quiz when it is running the final score will be displayed to the user.


## Information
This application reads questions and answers from files and displays it in a beautiful UI to the user.
There are 2 files that are used to read data from:
1. info.txt
2. answers.txt

#### Info
Info file stores all the information regarding the test except the answers.
It has to be present in a special format.

The first four lines have to be:
```
Size=10
Time:3
Correct Answer:10
Wrong Answer:-1
```
"Size" is the number of questions.
"Time" is the number of minutes available to the user.
"Correct Answer" is the total score given for each correct answer.
"Wrong Answer" is the total score to be deducted for each wrong answer. This has to be a negative number or zero.

After this, Questions have to added with it's options.
The question should be enclosed in asterisks (*) and options should be enclosed in forward slashes (/).

```
*Entomology is the science that studies*
/Behavior of human beings/
/Insects/
/The origin and history of technical and scientific terms/
/The formation of rocks/
```
The number of questions should be equal to the "Size" mentioned in the very first line of the file.

#### Answers
The answers file should have the correct option number one after the other without any space.
```
2441314443
```

### Todos

 - Add encryption to info and answers file
 - Read questions from DB instead of files

License
----

MIT
