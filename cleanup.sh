#!/usr/bin/env bash


docker stop grafana
docker stop prometheus
docker stop service
docker system prune