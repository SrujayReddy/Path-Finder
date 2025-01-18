run:
	javac Frontend.java
	java Frontend

runBDTests:
	javac -cp .:../junit5.jar *.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests


runFDTests:
	javac -cp .:../junit5.jar *.java
	java -jar ../junit5.jar -cp . -c FrontendTests

clean:
	rm *.class
