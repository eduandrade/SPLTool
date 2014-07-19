#!/bin/sh

CONFIG="/Users/eduardo/development/workspace-ipt/PROGERAS-SPL/configs/conf.config"
VARIABILITY="/Users/eduardo/development/workspace-github/SPLTool/variability.xml"
INPUT="/Users/eduardo/development/workspace-github/SPLTool/model-modrh.xml"
MODRHWORKSPACE="output/target"

tar czf $MODRHWORKSPACE.tar.gz $MODRHWORKSPACE

rm -rf output/java/*
rm -rf output/pages/*
rm -rf $MODRHWORKSPACE/*

mkdir -p output/pages/cadastro/
mkdir -p output/pages/relatorios/
mkdir -p output/pages/folhapagamento/
mkdir -p output/pages/folhapagamento/administracao/
mkdir -p output/pages/folhapagamento/processamentopagamento/
mkdir -p output/pages/folhapagamento/historico/

mkdir -p $MODRHWORKSPACE/src/main/java
mkdir -p $MODRHWORKSPACE/src/main/resources
mkdir -p $MODRHWORKSPACE/src/main/webapp

ruby read_modrh.rb $CONFIG $VARIABILITY $INPUT

echo "Copiando classes..."
cp -R output/java/* $MODRHWORKSPACE/src/main/java/

echo "Copiando resources..."
cp -R output/resources/* $MODRHWORKSPACE/src/main/resources/

echo "Copiando paginas..."
cp -R output/pages/* $MODRHWORKSPACE/src/main/webapp/

echo "Empacotando..."
/Users/eduardo/development/tools/apache-maven-3.2.2/bin/mvn -f output/target/pom.xml clean package

echo "Fim!"
