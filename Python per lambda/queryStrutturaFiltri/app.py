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


def buildQueryString(paramString, dict):
    queryString = "SELECT * FROM STRUTTURA"
    queryString += " WHERE 1=1 "
    if dict["queryStringParameters"]:
        for key in dict["queryStringParameters"]:
            if key == 'valutazione_media':
                queryString += " AND " + key + " >= %s AND " + key + " < %s+1" 
                paramString.append(dict["queryStringParameters"][key])
                paramString.append(dict["queryStringParameters"][key])
            
            elif key == 'radius':
                queryString += " AND ACOS(SIN(RADIANS(%s))*SIN(RADIANS(LATITUDINE)) + COS(RADIANS(%s))*COS(RADIANS(LATITUDINE))*COS(RADIANS(%s) - RADIANS(LONGITUDINE)))*6371 <= %s"
                paramString.append(dict["queryStringParameters"]["x"])
                paramString.append(dict["queryStringParameters"]["x"])
                paramString.append(dict["queryStringParameters"]["y"])
                paramString.append(dict["queryStringParameters"]["radius"])
            
            elif ((key != 'x') and (key != 'y')):
                if((key == 'id_struttura')):
                    queryString += " AND " + key + " = %s"
                    paramString.append(dict["queryStringParameters"][key])
                else:
                    queryString += " AND UPPER(" + key + ") LIKE UPPER(%s)"
                    paramString.append(dict["queryStringParameters"][key])
            
        queryString += " ORDER BY VALUTAZIONE_MEDIA DESC"
    else:
        queryString = ""
        
    return queryString



def handler(event, context):
    
    logger.info(event)
    responseObject = {}
    response = []
    try:
        with conn.cursor() as cur:
            responseObject = {}
            
            
            #valori inseriti dall'utente per la ricerca
            params = []
            
            queryString = buildQueryString(params, event)
            logger.info(queryString)
            if (queryString != ""):
                cur.execute(queryString, params)
                columns = [desc[0] for desc in cur.description]
                for row in cur:
                    i = 0
                    rowObj = {}
                    for col in columns:
                        rowObj[col] = row[i].__str__()
                        i=i+1
                        
                    response.append(rowObj)
        conn.commit() 
    except psycopg2.Error as e:
        conn.rollback()
    finally:
        responseObject['statusCode'] = 200
        responseObject['headers'] = {}
        responseObject['headers']['Content-Type'] = 'application/json'
        responseObject['body'] = json.dumps(response)
	
    return responseObject