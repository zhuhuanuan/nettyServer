#!/bin/bash
protoc --java_out=../src --proto_path=../proto/ ../proto/*.proto
