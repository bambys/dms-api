-- create database
IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'DMS')
BEGIN
	CREATE DATABASE DMS
END