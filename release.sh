#!/bin/sh
mvn clean compile deploy -P release-sign-artifacts
