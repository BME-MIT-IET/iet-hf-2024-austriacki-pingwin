@echo off

REM Indítunk egy PowerShell-t
start powershell.exe -Command "Get-Content .\logs\tmp.log -Wait -Encoding UTF8"


REM Elindítunk egy Java programot két argumentummal, majd várunk, hogy befejeződjön
java -jar ad4b_proto.jar -loadgreat -test/*

pause
