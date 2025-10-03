@echo off
cls
javac -d target\classes -Xmaxwarns -Xlint:unchecked -Xlint:deprecation ".\src\main\java\math\core\interfaces\*.java" ".\src\main\java\math\core\exceptions\*.java" ".\src\main\java\math\core\*.java" ".\src\main\java\math\taylor\*.java" ".\src\main\java\BeyondDouble\*.java"
javac -d target\test-classes -cp "target\classes;lib\*" -Xmaxwarns -Xlint:unchecked -Xlint:deprecation ".\src\test\java\BeyondDouble\*.java" ".\src\test\java\math\core\*.java" ".\src\test\java\math\taylor\*.java"
java -cp "target\classes;target\test-classes;lib\*" org.junit.platform.console.ConsoleLauncher execute --scan-classpath --class-path "target\test-classes"
pause