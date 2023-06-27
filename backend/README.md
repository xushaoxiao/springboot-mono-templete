# How to Build

### Build the API server

Run build:
```sh
mvn package
```

# Development Guide

## IDE Setup
Use IntelliJ.

## Coding Style
We follow [Google's style guide for Java](https://google.github.io/styleguide/javaguide.html)
except we use 4-space indentation instead of 2-space.

You can run
```sh
mvn checkstyle:checkstyle
```
to generate a Checkstyle report. The report is in `target/site/checkstyle.html`.

