#!/bin/sh

echo "Desligando Glassfish..."
/Users/eduardo/development/tools/glassfish-eclipse/glassfishv3/glassfish/bin/asadmin stop-domain
echo "Desligando banco de dados..."
/Users/eduardo/development/tools/glassfish-eclipse/glassfishv3/glassfish/bin/asadmin stop-database
