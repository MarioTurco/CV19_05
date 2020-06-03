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
    queryString = "INSERT INTO UTENTE VALUES("
    queryString +=  "%s, %s, %s, TO_DATE(%s, 'DD/MM/YYYY'), %s, %s, %s, %s, %s)"
    
    params.append(dict["queryStringParameters"]["nome"])
    params.append(dict["queryStringParameters"]["nickname"])
    params.append(dict["queryStringParameters"]["email"])
    params.append(dict["queryStringParameters"]["data_di_nascita"])
    params.append(dict["queryStringParameters"]["recensioniapprovate"])
    params.append(dict["queryStringParameters"]["recensionirifiutate"])
    params.append(dict["queryStringParameters"]["password"])
    params.append(dict["queryStringParameters"]["salt"])
    params.append(dict["queryStringParameters"]["mostra_nickname"])
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
        response["status"] = 'Record inserted successfully'
        
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