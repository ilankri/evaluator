SHELL = /bin/sh
SBT = sbt

TARGET = skeleton

.SUFFIXES:
.PHONY: all $(TARGET) run clean

all: $(TARGET)

$(TARGET):
	@$(SBT) compile

run:
	@$(SBT) run

clean:
	@$(SBT) clean
