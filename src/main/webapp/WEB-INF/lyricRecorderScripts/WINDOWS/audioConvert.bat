@echo off
ECHO Started Audio Convert Script

SET AUDIO_CONVERT_EXECUTABLE=%1
SET SOURCE_AUDIO_FILE=%2
SET TARGET_AUDIO_FILE=%3

%AUDIO_CONVERT_EXECUTABLE% -y -i %SOURCE_AUDIO_FILE% %TARGET_AUDIO_FILE%
ECHO Finished Audio Convert Script
exit;
