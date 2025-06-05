@echo off
cls

	setlocal enabledelayedexpansion

	javac -d build\classes -Xlint:unchecked -Xlint:deprecation ".\src\math\core\interfaces\*.java" ".\src\math\core\exceptions\*.java" ".\src\math\core\*.java" ".\src\math\taylor\*.java" ".\src\Mechanics\Main\*.java"

	echo Runtime started:

	for /f "tokens=2 delims==" %%a in ('wmic os get localdatetime /value 2^>NUL') do set "start_datetime=%%a"
	if not defined start_datetime (

		echo [ERROR] Failed to get start time
		set "start_time=00:00:00.00"

	) else (

		set "start_time=!start_datetime:~8,2!:!start_datetime:~10,2!:!start_datetime:~12,2!.!start_datetime:~15,2!"

	)

	java -cp build\classes; Mechanics.Main.MainRunnable

	for /f "tokens=2 delims==" %%a in ('wmic os get localdatetime /value 2^>NUL') do set "end_datetime=%%a"
	if not defined end_datetime (

		echo [ERROR] Failed to get end time
		set "end_time=00:00:00.00"

	) else (

		set "end_time=!end_datetime:~8,2!:!end_datetime:~10,2!:!end_datetime:~12,2!.!end_datetime:~15,2!"

	)

	call :time_diff "%start_time%" "%end_time%" duration

	echo.
	echo Duration: !duration! - [%start_time%] and [%end_time%]
	pause
	exit /b

	:time_diff

		setlocal
		set "start=%~1"
		set "end=%~2"

		if "%start%"=="." goto time_error
		if "%end%"=="." goto time_error

		for /f "tokens=1-4 delims=:.," %%a in ("%start%") do (

			set /a "h1=1%%a - 100, m1=1%%b - 100, s1=1%%c - 100, cs1=1%%d - 100"
			set /a "start_centisec=((h1 * 360000) + (m1 * 6000) + (s1 * 100) + cs1)"

		)

		for /f "tokens=1-4 delims=:.," %%a in ("%end%") do (

			set /a "h2=1%%a - 100, m2=1%%b - 100, s2=1%%c - 100, cs2=1%%d - 100"
			set /a "end_centisec=((h2 * 360000) + (m2 * 6000) + (s2 * 100) + cs2)"

		)

		set /a "diff_centisec=end_centisec - start_centisec"

		set /a "hours=diff_centisec / 360000"
		set /a "diff_centisec=diff_centisec %% 360000"

		set /a "minutes=diff_centisec / 6000"
		set /a "diff_centisec=diff_centisec %% 6000"

		set /a "seconds=diff_centisec / 100"
		set /a "centiseconds=diff_centisec %% 100"

		set "hours=00%hours%"
		set "minutes=00%minutes%"
		set "seconds=00%seconds%"
		set "centiseconds=00%centiseconds%"

	endlocal & set "%3=%hours:~-2%:%minutes:~-2%:%seconds:~-2%.%centiseconds:~-2%"

	goto :eof

	:time_error
	endlocal & set "%3=ERROR:INVALID_TIME_FORMAT"

goto :eof