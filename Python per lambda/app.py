import sys
import logging
import rds_config
import psycopg2
import json
#rds settings

rds_host  = "database-1.cn8hhgibnvsj.eu-central-1.rds.amazonaws.com"
name = rds_config.db_username
password = rds_config.db_password
db_name = rds_config.db_name

logger = logging.getLogger()
logger.setLevel(logging.INFO)

try:
    conn = psycopg2.connect(user=name, password=password, host=rds_host, port="5432", database="postgres")
except psycopg2.Error as e:
    logger.error("ERROR: Unexpected error: Could not connect to PostgreSQL instance.")
    logger.error(e)
    sys.exit()


def handler(event, context):
    
    table = event['queryStringParameters']['table']
     with conn.cursor() as cur:
        cur.execute("select * from " + table)
        for row in cur:
            logger.info(row)
    #conn.commit()

    #return "Success"
