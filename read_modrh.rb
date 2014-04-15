# File: read_model.rb
# Date: 11/03/2013
# Author: Eduardo Andrade
# Le o XML gerado pelo FeatureIDE e processa somente a parte inserida manualmente, para entao gerar o codigo fonte.

require 'rexml/document'
require "erb"
require 'fileutils'

class Field
  attr_reader :name, :type, :getter_setter, :value

  def initialize(name, type, getter_setter, value)
      @name = name
      @type = type
      #@getter_setter = getter_setter
      @value = value

      @getter_setter = true
      if ( getter_setter == "false" )
        @getter_setter = false
      end
  end
end

class Entity
  attr_reader :name, :package, :template, :persist

  def initialize(name, package, template, persist)
      @name = name
      @package = package
      @template = template
      @persist = persist
  end
end

class ManagedBean
  attr_accessor :name, :package, :template, :fields, :operations, :dao, :log
end

class Dao
  attr_accessor :interface, :name, :value
end

class Log
  attr_accessor :interface, :name, :value
end

class Operation
  attr_accessor :return_type, :name, :body
end

class Page
  attr_accessor :file_name,
                :title,
                :managed_bean_name,
                :template,
                :form_id,
                :table_var,
                :table_border,
                :table_operation_list,
                :columns,
                :table_edit_label,
                :table_edit_action,
                :table_remove_label,
                :table_remove_action,
                :add_page,
                :add_label,
                :input_fields,
                :save_action,
                :save_label,
                :links
end

class Column
  attr_accessor :label, :property
end

class InputField
  attr_accessor :id, :backing_bean_property, :label, :backing_bean, :required, :required_message, :size, :converter
end

class Link
  attr_accessor :id, :label, :value
end

def process_entities ( doc )

  puts "Processando Entidades..."

  doc.elements.each("featureModel/struct/and/and/feature") { |feature|

    name = feature.attributes["name"]
    puts ">Processando feature '#{name}'..."
    entity_element = feature.elements["entity"]

    if (entity_element)

      #dados da entidade
      entity = Entity.new(entity_element.attributes["name"], entity_element.attributes["package"], entity_element.attributes["template"], entity_element.attributes["persist"])

      field_name = ""
      field_getter_setter = ""
      fields = []
      entity_element.elements.each("fields/field") { |field_element|
        field = Field.new(field_element.attributes["name"], field_element.attributes["type"], field_element.attributes["getter-and-setter"], field_element.attributes["value"])
        fields.push( field )
      }

      # process template
      erb = ERB.new( File.open( entity.template() ).read )
      new_code = erb.result( binding )

      # Create the new Java File
      new_class_name = entity.name()
      create_file( "output/java/model", "#{new_class_name}.java", new_code  )

    else
      puts "ERRO! Feature '#{name}' esta mal formatada!"
    end

  }

end

def create_file( dir, file_name, content )
  print "Criando arquivo #{dir}/#{file_name}\n"

  unless File.directory?(dir)
    FileUtils.mkdir_p(dir)
  end

  @contentsArray.each { |line|
    @variability_doc.elements.each("variation-points/variation") { |elm_variation|
      name = elm_variation.attributes["name"]
      value = elm_variation.attributes["value"]
      holder = elm_variation.attributes["holder"]
      if ( line == name )
      #if ( line.start_with?( "VP-DAO" ) )
        content.sub! holder, value
        #content.sub! "VP-DAO", value
      end
    }
  }

  File.open( "#{dir}/#{file_name}", "w" ).write( content )
end

def process_managed_beans( doc )

  puts ">Processando Managed Beans..."

  doc.elements.each("featureModel/struct/and/and/managed-beans/managed-bean") { |elm_managed_bean|

    managed_bean = ManagedBean.new()
    managed_bean.name = elm_managed_bean.attributes["name"]
    managed_bean.package = elm_managed_bean.attributes["package"]
    managed_bean.template = elm_managed_bean.attributes["template"]

    fields = []
    elm_managed_bean.elements.each("fields/field") { |elm_field|
      field = Field.new(elm_field.attributes["name"], elm_field.attributes["type"], nil, elm_field.attributes["value"])
      fields.push( field )
    }
    managed_bean.fields = fields

    dao = Dao.new()
    dao.name = elm_managed_bean.elements["dao"].attributes["name"]
    dao.value = elm_managed_bean.elements["dao"].attributes["value"]
    dao.interface = elm_managed_bean.elements["dao"].attributes["interface"]
    managed_bean.dao = dao

    log = Log.new()
    log.name = elm_managed_bean.elements["log"].attributes["name"]
    log.value = elm_managed_bean.elements["log"].attributes["value"]
    log.interface = elm_managed_bean.elements["log"].attributes["interface"]
    managed_bean.log = log

    operations = []
    elm_managed_bean.elements.each("operations/operation") { |elm_operation|
      operation = Operation.new()
      operation.name = elm_operation.attributes["name"]
      operation.return_type = elm_operation.attributes["return-type"]
      operation.body = elm_operation.text()
      operations.push( operation )
    }
    managed_bean.operations = operations

    # process template
    erb = ERB.new( File.open( managed_bean.template ).read )
    new_code = erb.result( binding )

    # Create the new Java File
    new_class_name = managed_bean.name
    create_file( "output/java/web", "#{new_class_name}.java", new_code  )

  }

end

def process_pages( doc )

  puts ">Processando paginas..."

  doc.elements.each("featureModel/struct/and/and/pages/page") { |elm_page|
    page = Page.new()
    page.file_name = elm_page.attributes["file-name"]
    page.title = elm_page.attributes["title"]
    page.managed_bean_name = elm_page.attributes["managed-bean"]
    page.template = elm_page.attributes["template"]

    if ( elm_page.elements["add"] )
      page.add_page = elm_page.elements["add"].attributes["page"]
      page.add_label = elm_page.elements["add"].attributes["label"]
    end

    links = []
    if ( elm_page.elements["links/link"] )
     elm_page.elements.each("links/link") { |elm_link|
        link = Link.new()
        link.value = elm_link.attributes["value"]
        link.label = elm_link.attributes["label"]
        links.push( link )
      }
    end
    page.links = links

    elm_container = elm_page.elements["container"]
    page.form_id = elm_container.attributes["id"]

    if ( elm_container.elements["table"] )
      elm_table = elm_container.elements["table"]
      page.table_var = elm_table.attributes["var"]
      page.table_border = elm_table.attributes["border"]
      page.table_operation_list = elm_table.attributes["operation-list"]
      page.table_edit_label = elm_table.elements["edit"].attributes["label"]
      page.table_edit_action = elm_table.elements["edit"].attributes["action"]
      page.table_remove_label = elm_table.elements["remove"].attributes["label"]
      page.table_remove_action = elm_table.elements["remove"].attributes["action"]

      columns = []
       elm_table.elements.each("columns/column") { |elm_column|
         column = Column.new()
         column.label = elm_column.attributes["label"]
         column.property = elm_column.attributes["property"]
         columns.push( column )
       }
       page.columns = columns
    end

    input_fields = []
    elm_container.elements.each("input_fields/input_field") { |elm_input_field|
      input_field = InputField.new()
      input_field.id = elm_input_field.attributes["id"]
      input_field.backing_bean_property = elm_input_field.attributes["backing-bean-property"]
      input_field.label = elm_input_field.attributes["label"]
      input_field.backing_bean = elm_input_field.attributes["backing-bean"]
      input_field.required = elm_input_field.attributes["required"]
      input_field.required_message = elm_input_field.attributes["required-message"]
      input_field.size = elm_input_field.attributes["size"]
      input_field.converter = elm_input_field.attributes["converter"]
      input_fields.push( input_field )
    }
    page.input_fields = input_fields

    if ( elm_container.elements["save"] )
      page.save_action = elm_container.elements["save"].attributes["action"]
      page.save_label = elm_container.elements["save"].attributes["label"]
    end

    # process template
    erb = ERB.new( File.open( page.template ).read )
    new_code = erb.result( binding )

    # Create the new Java File
    create_file( "output/pages", "#{page.file_name}", new_code  )
  }

end

def process_features( doc )
  puts ">Processando features..."
  doc.elements.each("featureModel/feature-root/feature[@name='Funcionais']") { |features|

    @contentsArray.each { |config_line|
      features.elements.each("feature[@name='#{config_line}']") { |feat_child|
        if feat_child
          name = feat_child.attributes["name"]
          puts "> Processando feature '#{name}'"
          is_parent = feat_child.attributes["is-parent"]
          if is_parent == "true"
            #puts feat_child
            feat_child.each_recursive do |childElement|
				          #puts "[", childElement.name.to_s, "]"
                  child_name = childElement.attributes["name"]
                  #TODO se feature existir na configuracao, entao adiciona
                  puts child_name
			      end
          end
        end
      }
    }

  }

end

def process_features2( doc )
  puts ">Processando features..."
  doc.elements.each("featureModel/feature-root/feature[@name='Funcionais']") { |features|
    page_name = ""
    page_features = []

    features.each_recursive do |child_element|
          child_name = child_element.attributes["name"]
          #is_parent = child_element.attributes["is-parent"]
          is_parent = false
          if child_element.attributes["is-parent"] == "true"
            is_parent = true
          end
          #puts "> #{child_name} / #{is_parent}"

          if is_parent == true
            if page_features.empty?
              page_name = child_name
              #puts "DEBUG1 -  #{page_name} / #{is_parent} / #{page_features}"
            else
              puts ">>Criando pagina #{page_name} com as features #{page_features}"
              page_features.clear()
              page_name = child_name
              #puts "DEBUG2 -  #{page_name} / #{is_parent} / #{page_features}"
            end
          else
            #puts ">>Adicionando #{child_name}."
            page_features.push(child_name)
            #puts "DEBUG3 -  #{page_name} / #{is_parent} / #{page_features}"
          end
    end
  }
end

def process_config( file )

  puts "Processando o arquivo [#{file}]..."
  f = File.open( file )
  f.each_line { |line|
      @contentsArray.push( line.chomp() )
  }
  @contentsArray.each { |x| puts "--> #{x}"}

end

def process_model( file )

  puts "Processando o arquivo [#{file}]..."
  doc = REXML::Document.new( File.open( file ) )

  #process_entities( doc )
  #process_managed_beans( doc )
  #process_pages( doc )
  process_features2 (doc)

  puts "Finalizado a geracao de codigo!"

end

def load_variability( file )
  puts "Processando o arquivo [#{file}]..."
  @variability_doc = REXML::Document.new( File.open( file ) )
end

unless ARGV.length == 3
  puts "Favor informar todos os parametros."
  exit -1
end

@contentsArray = []
@variability_doc = nil

puts "============================================================="
process_config( ARGV[0] )
load_variability( ARGV[1] )
process_model( ARGV[2] )
puts "============================================================="
