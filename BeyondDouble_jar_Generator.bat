@echo off
javac -d build\classes -Xlint:unchecked .\src\Mechanics\Main\MainRunnable.java .\src\math\core\Notationer.java .\src\math\core\Operationer.java .\src\math\core\Digit.java
jar cvfm BeyondDouble_v0.0.9.jar manifest.mf -C build\classes . -C docs .
java -cp BeyondDouble_v0.0.9.jar Mechanics.Main.MainRunnable
pause