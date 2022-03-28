#!/usr/bin/env bash

(mvn test && git commit -am "$1") || git reset --hard