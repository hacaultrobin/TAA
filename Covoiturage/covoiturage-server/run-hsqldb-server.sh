mvn dependency:copy-dependencies
cd data
java -cp ../target/dependency/hsqldb-2.3.2.jar org.hsqldb.Server
