testHash: MyHashMap.class MyHashMapTest.class
	java -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar org.junit.runner.JUnitCore MyHashMapTest

MyHashMap.class: src/MyHashMap.java
	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar src/MyHashMap.java
MyHashMapTest.class: src/MyHashMapTest.java 
	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar src/MyHashMapTest.java 

testFileSystem: FileSystem.class FileSystemTest.class
	java -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar org.junit.runner.JUnitCore FileSystemTest
FileSystem.class: src/FileSystem.java
	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar src/FileSystem.java
FileSystemTest.class: src/FileSystemTest.java 
	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar src/FileSystemTest.java 

testFileData: FileData.class FileDataTest.class
	java -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar org.junit.runner.JUnitCore FileDataTest
FileData.class: src/FileData.java
	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar src/FileData.java
FileDataTest.class: src/FileDataTest.java 
	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar src/FileDataTest.java 



# MyHashMap.class: MyHashMap.java
# 	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar 
# MyHashMapTest.class: MyHashMapTest.java 
# 	javac -cp src/:lib/hamcrest-core-1.3.jar:lib/junit-4.12.jar 
