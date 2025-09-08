@echo off
:: ===== TCS BusBuddy Feature Folder Generator =====
:: Usage: generate-feature-folders FeatureName

if "%~1"=="" (
    echo ERROR: Missing Feature name.
    echo Usage: generate-feature-folders FeatureName
    exit /b
)

set FEATURE=%1
set BASEPATH=src\main\java\com\tcs\busbuddy

:: Create feature package structure
mkdir %BASEPATH%\%FEATURE%\model
mkdir %BASEPATH%\%FEATURE%\repository
mkdir %BASEPATH%\%FEATURE%\service
mkdir %BASEPATH%\%FEATURE%\controller
mkdir %BASEPATH%\%FEATURE%\dto

echo ✅ Feature folder structure created for '%FEATURE%'.
echo Path: %BASEPATH%\%FEATURE%
