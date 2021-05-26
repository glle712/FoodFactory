# FoodFactory

FoodFactory is a code challenge exercise on concurrent process design

## Installation

Use [maven](https://maven.apache.org/download.cgi) to build FoodFactory.

```bash
cd FoodFactory
mvn clean install
```

## Usage

Your can also directly run FoodFactory using [maven](https://maven.apache.org/download.cgi).


```bash
cd FoodFactory
mvn spring-boot:run
```

## Notes
There are no JUnit tests of any sort.
The program just runs, to exit use CTRL+C.

To configure the quantity the Stores and the Ovens, as well as their capacities, you can edit the file [application.properties](src/main/resources/application.properties) and then run.

You can modify the test case editing the `init()` method of the class `Application.java`

## License
you are not licensed to use this code anywhere