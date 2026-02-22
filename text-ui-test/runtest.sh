#!/usr/bin/env bash

# Create bin directory if it doesn't exist
if [ ! -d "../bin" ]; then
    mkdir ../bin
fi

# Delete output from previous run
if [ -e "./ACTUAL.TXT" ]; then
    rm ACTUAL.TXT
fi

# Delete data file to start fresh
if [ -e "./data/alfred.txt" ]; then
    rm ./data/alfred.txt
fi

# Compile the code into the bin folder
if ! find ../src/main/java -name "*.java" | xargs javac -d ../bin -cp ../bin; then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# Run the program, feed commands from input.txt and redirect output to ACTUAL.TXT
java -classpath ../bin alfred.Alfred < input.txt > ACTUAL.TXT

# Convert line endings for comparison
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT 2>/dev/null || true

# Compare the output
if diff ACTUAL.TXT EXPECTED-UNIX.TXT > /dev/null 2>&1; then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    echo ""
    echo "===== DIFFERENCES ====="
    diff ACTUAL.TXT EXPECTED-UNIX.TXT
    exit 1
fi