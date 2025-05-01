@echo off

	cd C:\Users\felip\Documents\BeyondDouble

	setlocal enabledelayedexpansion

	javac -d build\classes -Xlint:unchecked -Xlint:deprecation .\src\math\core\interfaces\Operable.java .\src\math\core\interfaces\EnhancedOperable.java .\src\math\core\Notationer.java .\src\math\core\Operationer.java .\src\math\core\Digit.java .\src\math\taylor\Util.java .\src\Mechanics\Main\MainRunnable.java

	echo Runtime start:

	for /f "tokens=2 delims==" %%a in ('wmic os get localdatetime /value') do set "start_datetime=%%a"
	set start_time=%start_datetime:~8,2%:%start_datetime:~10,2%:%start_datetime:~12,2%.%start_datetime:~15,2%

	java -cp build\classes; Mechanics.Main.MainRunnable

	for /f "tokens=2 delims==" %%a in ('wmic os get localdatetime /value') do set "end_datetime=%%a"
	set end_time=%end_datetime:~8,2%:%end_datetime:~10,2%:%end_datetime:~12,2%.%end_datetime:~15,2%

	call :time_diff "%start_time%" "%end_time%" duration

	echo.
	echo Duration: !duration!
	pause
	exit /b

	:time_diff

		setlocal
		set start=%~1
		set end=%~2

		for /f "tokens=1-4 delims=:.," %%a in ("%start%") do (

			set /a "start_centisec=(%%a * 360000) + (%%b * 6000) + (%%c * 100) + %%d"

		)

		for /f "tokens=1-4 delims=:.," %%a in ("%end%") do (

			set /a "end_centisec=(%%a * 360000) + (%%b * 6000) + (%%c * 100) + %%d"

		)

		set /a "diff_centisec=end_centisec - start_centisec"

		set /a "hours=diff_centisec / 360000"
		set /a "diff_centisec=diff_centisec %% 360000"

		set /a "minutes=diff_centisec / 6000"
		set /a "diff_centisec=diff_centisec %% 6000"

		set /a "seconds=diff_centisec / 100"
		set /a "centiseconds=diff_centisec %% 100"

		if %hours% lss 10 set "hours=0%hours%"
		if %minutes% lss 10 set "minutes=0%minutes%"
		if %seconds% lss 10 set "seconds=0%seconds%"
		if %centiseconds% lss 10 set "centiseconds=0%centiseconds%"

	endlocal & set "%3=%hours%:%minutes%:%seconds%.%centiseconds%"

goto :eof