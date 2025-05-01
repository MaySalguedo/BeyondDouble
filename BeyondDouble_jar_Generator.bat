@echo off
javac -d build\classes -Xlint:unchecked -Xlint:deprecation .\src\math\core\interfaces\Operable.java .\src\math\core\interfaces\EnhancedOperable.java .\src\Mechanics\Main\MainRunnable.java .\src\math\core\Notationer.java .\src\math\core\Operationer.java .\src\math\core\Digit.java .\src\math\taylor\Util.java
javadoc -d docs -sourcepath src -subpackages math.core math.core.interfaces math.taylor
jar cvfm BeyondDouble_v0.0.14.jar manifest.mf -C build\classes . -C docs .
java -cp BeyondDouble_v0.0.14.jar Mechanics.Main.MainRunnable
pause