@echo off
"C:\Program Files (x86)\Java\jdk1.7.0_25\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/com/rs/game/npc/combat/impl/*.java
echo Compiled all Successfully
pause