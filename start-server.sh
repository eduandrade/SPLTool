#!/bin/sh

echo "Iniciando Glassfish..."
/Users/eduardo/development/tools/glassfish-eclipse/glassfishv3/glassfish/bin/asadmin start-domain
echo "Iniciando banco de dados..."
/Users/eduardo/development/tools/glassfish-eclipse/glassfishv3/glassfish/bin/asadmin start-database --dbhome /Users/eduardo/development/workspace-github/SPLTool/resources/setup/db
