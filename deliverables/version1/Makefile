SHELL = /bin/sh
SBT = sbt

TARGET = version1

.SUFFIXES:
.PHONY: all $(TARGET) run clean

all: $(TARGET)

$(TARGET):
	@$(SBT) compile

run:
	@$(SBT) run

clean:
	@$(SBT) clean
