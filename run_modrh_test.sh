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

echo "Teste finalizado!"
