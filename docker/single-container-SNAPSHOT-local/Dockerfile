FROM asqatasun/asqatasun:pre-requisites_with-empty-DB
MAINTAINER Matthieu Faure <mfaure@asqatasun.org>

# #################################################################
#
#                      DISCLAIMER
#
# This is a fat container, that is absolutly not compliant to Docker best-practices
# https://docs.docker.com/engine/userguide/eng-image/dockerfile_best-practices/
# Don't use it for production as all data are wiped out at reboot / rebuild
# BUT for quick testing, that does the job :)
#
# #### FROM  ######################################################
#   Ubuntu:14.04
#   |-- asqatasun/asqatasun:base_wget
#       |-- asqatasun/asqatasun:only-DB
#           |-- asqatasun/asqatasun:pre-requisites_without-DB
#               |-- asqatasun/asqatasun:pre-requisites_with-empty-DB  
###################################################################

USER root
ENV  WWWPORT="8080"                                    \
     DATABASE_DB="asqatasun"                           \
     DATABASE_HOST="localhost"                         \
     DATABASE_USER="asqatasun"                         \
     DATABASE_PASSWD="asqaP4sswd"                      \
     TOMCAT_WEBAPP_DIR="/var/lib/tomcat7/webapps"      \
     TOMCAT_USER="tomcat7"                             \
     ASQA_URL="http://localhost:8080/asqatasun/"       \
     ASQA_ADMIN_EMAIL="me@my-email.org"                \
     ASQA_ADMIN_PASSWD="myAsqaPassword"                \
     ASQA_RELEASE="4.1.0-SNAPSHOT"

EXPOSE $WWWPORT

# ##########################################################
#
# Asqatasun installation
# cf http://doc.asqatasun.org/en/10_Install_doc/
#
# ##########################################################

# Add Asqatasun
ADD ./asqatasun-${ASQA_RELEASE}.i386.tar.gz /root/

# Install Asqatasun
RUN  cd  /root                                      && \
     mv   asqatasun*/    ./asqatasun/               && \
     service mysql start                            && \
     sleep 5                                        && \
     cd   /root/asqatasun/install/                  && \
     echo "yes\n" | ./install-SQL.sh           \
        --database-db       $DATABASE_DB       \
        --database-host     $DATABASE_HOST     \
        --database-user     $DATABASE_USER     \
        --database-passwd   $DATABASE_PASSWD        && \
     echo "yes\n" | ./install.sh               \
        --database-db       $DATABASE_DB       \
        --database-host     $DATABASE_HOST     \
        --database-user     $DATABASE_USER     \
        --database-passwd   $DATABASE_PASSWD   \
        --asqatasun-url     $ASQA_URL          \
        --tomcat-webapps    $TOMCAT_WEBAPP_DIR \
        --tomcat-user       $TOMCAT_USER       \
        --asqa-admin-email  $ASQA_ADMIN_EMAIL  \
        --asqa-admin-passwd $ASQA_ADMIN_PASSWD \
        --firefox-esr-binary-path /opt/firefox/firefox \
        --display-port      :99                     && \
     rm -rf  /root/asqatasun*

CMD  service mysql start                            && \
     sleep   5                                      && \
     service xvfb start                             && \
     service tomcat7 start                          ;  \
     tail -f /var/log/tomcat7/catalina.out     \
             /var/log/asqatasun/asqatasun.log


