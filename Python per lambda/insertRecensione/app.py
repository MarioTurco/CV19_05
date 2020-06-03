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
    print("SUCCESS: Connection to postgresql instance succeeded")

   


except psycopg2.Error as e:
    logger.error("ERROR: Unexpected error: Could not connect to PostgreSQL instance.")
    logger.error(e)
    sys.exit()


def buildQueryString(params, dict):
    queryString = "INSERT INTO RECENSIONE VALUES("
    queryString +=  "%s, TO_DATE(%s, 'DD/MM/YYYY'), %s, %s, default, %s, 'In attesa', %s)"
    
    params.append(dict["queryStringParameters"]["testo"])
    params.append(dict["queryStringParameters"]["datarecensione"])
    params.append(dict["queryStringParameters"]["titolo"])
    params.append(dict["queryStringParameters"]["valutazione"])
    params.append(dict["queryStringParameters"]["struttura"])
    params.append(dict["queryStringParameters"]["autore"])
    
    return queryString



def handler(event, context):
    
    logger.info(event)
    responseObject = {}
    response = []
    try:
        response = {}
        cur = conn.cursor()
        params = []
            
        queryString = buildQueryString(params, event)
        logger.info(queryString)
        logger.info(params)
        cur.execute(queryString, params)
        conn.commit() 
        count = cur.rowcount
        response["status"] = 'Record inserted successfully into table: ' + str(count)
        
    except psycopg2.Error as e:
        response["status"] = str(e)
        logger.info(e)
        conn.rollback()
    finally:
        responseObject['statusCode'] = 200
        responseObject['headers'] = {}
        responseObject['headers']['Content-Type'] = 'application/json'
        responseObject['body'] = json.dumps(response)
	
    return responseObject