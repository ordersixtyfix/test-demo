FROM ubuntu:latest
LABEL authors="boragul"

ENTRYPOINT ["top", "-b"]