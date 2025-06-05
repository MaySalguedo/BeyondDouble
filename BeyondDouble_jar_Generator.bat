@echo off
cls
javac -d build\classes -Xlint:unchecked -Xlint:deprecation ".\src\math\core\interfaces\*.java" ".\src\math\core\exceptions\*.java" ".\src\math\core\*.java" ".\src\math\taylor\*.java" ".\src\Mechanics\Main\*.java"
javadoc -d docs -sourcepath src -subpackages math.core math.core.interfaces math.taylor
jar cvfm BeyondDouble_v0.0.16.jar manifest.mf -C build\classes . -C docs .
java -cp BeyondDouble_v0.0.16.jar Mechanics.Main.MainRunnable
pause