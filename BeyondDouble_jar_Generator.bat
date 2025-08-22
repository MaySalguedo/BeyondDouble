@echo off
cls
javac -d target\classes -Xlint:unchecked -Xlint:deprecation ".\src\main\java\math\core\interfaces\*.java" ".\src\main\java\math\core\exceptions\*.java" ".\src\main\java\math\core\*.java" ".\src\main\java\math\taylor\*.java" ".\src\main\java\Mechanics\Main\*.java"
javadoc -d docs -sourcepath ".\src\main\java" -subpackages math.core math.core.interfaces math.taylor
jar cvfm BeyondDouble_v0.0.17.jar manifest.mf -C target\classes . -C docs .
java -cp BeyondDouble_v0.0.17.jar Mechanics.Main.MainRunnable
pause