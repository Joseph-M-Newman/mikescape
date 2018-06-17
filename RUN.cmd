@echo off
title runserver
"C:\Program Files\Java\jre-10.0.1\bin\java.exe" -Xmx815m -cp bin;lib/*; com.rs.Launcher true true false
pause