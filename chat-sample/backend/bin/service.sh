#!/bin/bash

APP_HOME=../
APP_NAME=chat-sample
LOG_PATH=${APP_HOME}/logs
CMD="java -Xbootclasspath/a:${APP_HOME}/conf/ -jar ${APP_HOME}/chat-sample.jar --spring.config.name=application --spring.config.location=${APP_HOME}/conf/"

safe_mkdir(){
  if [[ ! -d $1 ]] ; then
    mkdir -p $1
  fi
}

safe_mkdir ${LOG_PATH}
safe_mkdir ${APP_HOME}/.pid/

hint="Usage: ./service.sh start|stop|log|status"
PID_FILE=${APP_HOME}/.pid/${APP_NAME}.pid

START_LOG=${LOG_PATH}/start.log

started="#################################################################################\n
           Application [${APP_NAME}] Started At  $(date)                                 \n
        ##################################################################################\n"

stopped="#################################################################################\n
                Application [${APP_NAME}] Stopped At  $(date)                             \n
        ##################################################################################\n"

if [[ "$1" == "start" ]]; then
    if [[ ! -f "$PID_FILE" ]]; then
        echo -e ${started} | tee -a ${START_LOG}
        nohup ${CMD} 1>> ${START_LOG} 2>> ${START_LOG} 2>&1 &echo $! > ${PID_FILE}

        tail -f ${START_LOG}
    else
        echo "${APP_NAME} service already started, please stop first and try again"
    fi
elif [[ "$1" == "stop" ]]; then
    if [[ -f "$PID_FILE" ]]; then
        echo -e ${stopped} | tee -a ${START_LOG}
        cat ${PID_FILE} | xargs kill -9
        rm ${PID_FILE}
        echo "$APP_NAME stop : [OK]"
    else
        echo "$APP_NAME service not started yet, please start first and try again"
    fi
elif [[ "$1" == "log" ]]; then
    tail -n 300 ${START_LOG}
elif [[ "$1" == "version" ]]; then
    cat ${APP_HOME}/version
elif [[ "$1" == "status" ]]; then
    pid=`cat ${PID_FILE}`
    if [[ $? -eq 0 ]]; then
        p_status=`ps -p ${pid}`
        if [[ $? -eq 0 ]]; then
            echo "Application [${APP_NAME}] is running, pid : ${pid}."
        else
            echo "Application [${APP_NAME}] is not running."
        fi
    else
        echo "Application [${APP_NAME}] is not running."
    fi
else
    echo ${hint}
fi