	
# Maven
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>ca.tweetzy</groupId>
    <artifactId>funds</artifactId>
    <version>ENTER-RELEASE-VERSION</version>
</dependency>
```
# Gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'ca.tweetzy:funds:-SNAPSHOT'
}
```