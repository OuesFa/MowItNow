#!/usr/bin/env bash

set -xe

# search and replace any level 1 indent key in cassandra.yaml matching an env var CASSANDRA_*
for yaml in $(cat /etc/cassandra/cassandra.yaml | egrep -e "^[a-z]" -e "^# [a-z_]+:" | cut -d: -f1 | tr -d "# ");
do
  var="CASSANDRA_${yaml^^}"
  val="${!var}"
  if [ "$val" ]; then
    sed "/${yaml}/d" -i /etc/cassandra/cassandra.yaml
    echo "${yaml}: ${val}" >> /etc/cassandra/cassandra.yaml
  fi
done

if [[ -n "${CASSANDRA_DC_SUFFIX}" ]]; then
  sed "/dc_suffix/d" -i /etc/cassandra/cassandra-rackdc.properties
  echo "dc_suffix=${CASSANDRA_DC_SUFFIX}" >> /etc/cassandra/cassandra-rackdc.properties
fi

if [[ -n "${CASSANDRA_JMX_USER}" && -n "${CASSANDRA_JMX_PWD}" ]]; then
  echo "${CASSANDRA_JMX_USER} ${CASSANDRA_JMX_PWD}" > /etc/cassandra/jmxremote.password
fi

docker-entrypoint.sh
