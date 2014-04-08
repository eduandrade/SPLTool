
CONFIG="/Users/eduardo/development/workspace-ipt/PROGERAS-SPL/configs/conf.config"
VARIABILITY="/Users/eduardo/Dropbox/Mestrado/TEMA/2-SPL-CODEGEN/dev/variability.xml"
INPUT="/Users/eduardo/Dropbox/Mestrado/TEMA/2-SPL-CODEGEN/dev/model-gerador.xml"

ruby read_model.rb $CONFIG $VARIABILITY $INPUT

echo "Copiando classes do pacote model"
#rm -f /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/model/*.java
cp output/java/model/*.java /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/model/

echo "Copiando classes do pacote web"
#rm -f /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/web/*.java
cp output/java/web/*.java /Users/eduardo/development/workspace-ipt/Playground/src/br/com/splgenerator/web/

echo "Copiando paginas"
cp output/pages/*.xhtml /Users/eduardo/development/workspace-ipt/Playground/WebContent/
