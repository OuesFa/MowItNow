FROM cassandra:4.0.1

ADD custom-entrypoint.sh /custom-entrypoint.sh

USER 999:999

ENTRYPOINT ["/custom-entrypoint.sh"]

LABEL version="4.0.1"
LABEL name="cassandra"
