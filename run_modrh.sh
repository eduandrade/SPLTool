#!/bin/sh

CONFIG="/Users/eduardo/development/workspace-ipt/PROGERAS-SPL/configs/conf.config"
VARIABILITY="/Users/eduardo/development/workspace-github/SPLTool/variability.xml"
INPUT="/Users/eduardo/development/workspace-github/SPLTool/model-modrh.xml"
#MODRHWORKSPACE="/Users/eduardo/development/workspace-github/ModRH-Git"
MODRHWORKSPACE="/Users/eduardo/development/workspace-ipt/ModRH-Git-Tests"

tar czf $MODRHWORKSPACE.tar.gz $MODRHWORKSPACE

rm -rf output/java/*
rm -rf output/pages/*
rm -rf $MODRHWORKSPACE/src/*
rm -rf $MODRHWORKSPACE/WebContent/*

mkdir -p output/pages/cadastro/
mkdir -p output/pages/relatorios/
mkdir -p output/pages/folhapagamento/
mkdir -p output/pages/folhapagamento/administracao/
mkdir -p output/pages/folhapagamento/processamentopagamento/
mkdir -p output/pages/folhapagamento/historico/

ruby read_modrh.rb $CONFIG $VARIABILITY $INPUT

echo "Copiando classes..."
cp -R output/java/* $MODRHWORKSPACE/src/

#echo "Copiando classes do pacote web"
#cp output/java/web/*.java /Users/eduardo/development/workspace-ipt/ModRH/src/br/com/splgenerator/web/

echo "Copiando paginas"
#cp output/pages/*.xhtml /Users/eduardo/development/workspace-ipt/ModRH/WebContent/
cp -R output/pages/* $MODRHWORKSPACE/WebContent/
