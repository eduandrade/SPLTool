#!/bin/sh

CONFIG="/Users/eduardo/development/workspace-ipt/PROGERAS-SPL/configs/conf.config"
VARIABILITY="/Users/eduardo/development/workspace-github/SPLTool/variability.xml"
INPUT="/Users/eduardo/development/workspace-github/SPLTool/model-modrh.xml"
MODRHWORKSPACE="/Users/eduardo/development/workspace-github/ModRH-Git"

tar czf $MODRHWORKSPACE.tar.gz $MODRHWORKSPACE

rm -rf output/java/*
rm -rf output/pages/*

mkdir -p output/pages/cadastro/
mkdir -p output/pages/relatorios/
mkdir -p output/pages/folhapagamento/
mkdir -p output/pages/folhapagamento/administracao/
mkdir -p output/pages/folhapagamento/processamentopagamento/
mkdir -p output/pages/folhapagamento/historico/

ruby read_modrh.rb $CONFIG $VARIABILITY $INPUT

echo "Copiando classes do Cadastro"
cp -r output/java/Cadastro/* $MODRHWORKSPACE/src/

echo "Copiando classes do Relatorios"
cp -r output/java/Relatorios/* $MODRHWORKSPACE/src/

echo "Copiando classes do FolhaPagamento/Administracao"
cp -r output/java/FolhaPagamento/Administracao/* $MODRHWORKSPACE/src/

echo "Copiando classes do FolhaPagamento/ProcessamentoPagamento"
cp -r output/java/FolhaPagamento/ProcessamentoPagamento/* $MODRHWORKSPACE/src/

echo "Copiando classes do Monitoracao"
cp -r output/java/Monitoracao/* $MODRHWORKSPACE/src/

#echo "Copiando classes do pacote web"
#cp output/java/web/*.java /Users/eduardo/development/workspace-ipt/ModRH/src/br/com/splgenerator/web/

echo "Copiando paginas"
#cp output/pages/*.xhtml /Users/eduardo/development/workspace-ipt/ModRH/WebContent/
cp output/pages/cadastro/*.xhtml $MODRHWORKSPACE/WebContent/cadastro/
cp output/pages/relatorios/*.xhtml $MODRHWORKSPACE/WebContent/relatorios/
cp output/pages/folhapagamento/*.xhtml $MODRHWORKSPACE/WebContent/folhapagamento/
cp output/pages/folhapagamento/administracao/*.xhtml $MODRHWORKSPACE/WebContent/folhapagamento/administracao/
cp output/pages/folhapagamento/processamentopagamento/*.xhtml $MODRHWORKSPACE/WebContent/folhapagamento/processamentopagamento/
cp output/pages/folhapagamento/historico/*.xhtml $MODRHWORKSPACE/WebContent/folhapagamento/historico/
