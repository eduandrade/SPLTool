
CONFIG="/Users/eduardo/development/workspace-ipt/PROGERAS-SPL/configs/conf.config"
VARIABILITY="/Users/eduardo/development/workspace-github/SPLTool/variability.xml"
INPUT="/Users/eduardo/development/workspace-github/SPLTool/model-gerador-reembolso.xml"

ruby read_model_reembolso.rb $CONFIG $VARIABILITY $INPUT

echo "Copiando classes do pacote model"
#rm -f /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/model/*.java
cp output/java/model/*.java /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/model/

echo "Copiando classes do pacote web"
#rm -f /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/web/*.java
cp output/java/web/*.java /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/web/

echo "Copiando paginas"
cp output/pages/*.xhtml /Users/eduardo/development/workspace-ipt/Playground/WebContent/
