@echo off
title Drop Editor
echo starting...
"C:/Program Files/Java/jre7/bin/java.exe" -Xmx512m -cp bin;lib/*; com.rs.tools.dropEditor true true false
pause