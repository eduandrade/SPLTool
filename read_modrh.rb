# File: read_model.rb
# Date: 11/03/2013
# Author: Eduardo Andrade
# Le o XML gerado pelo FeatureIDE e processa somente a parte inserida manualmente, para entao gerar o codigo fonte.

require 'rexml/document'
require "erb"
require 'fileutils'


class Dao
  attr_accessor :interface, :name, :value
end

class Log
  attr_accessor :interface, :name, :value
end

class Page
  attr_accessor :file_name,
                :title,
                :template,
                :links,
                :name
end

class Link
  attr_accessor :id, :label, :value
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

def copy_file( includeFilePath, includeOutputDir, condition )
  if ( condition.to_s == '' or @contentsArray.include? condition )
    puts "Copiando [condicao(#{condition})]: #{includeFilePath} -> #{includeOutputDir}"
    unless File.directory?(includeOutputDir)
      FileUtils.mkdir_p(includeOutputDir)
    end
    FileUtils.cp(includeFilePath, includeOutputDir)
  end
end

def process_features( doc )
  puts ">Processando features..."
  doc.elements.each("featureModel/feature-root/feature[@name='Funcionais']/feature") { |features|
    page = Page.new()
    page.links = []
    page.name = features.attributes["name"]
    page.template = features.attributes["template"]
    page.title = features.attributes["title"]
    page.file_name = features.attributes["page"]

    puts "Processando paginas..."
    features.elements.each("links/link") { |elm_link|
      link = Link.new()
      link.id = elm_link.attributes["id"]
      link.value = elm_link.attributes["value"]
      link.label = elm_link.attributes["label"]
      page.links.push( link )
    }

    if (page.template)
      erb = ERB.new( File.open( page.template ).read )
      new_code = erb.result( binding )
      create_file( "output/pages", "#{page.file_name}", new_code  )
    end

    puts "Processando managed beans..."
    features.elements.each("managed-beans/managed-bean") { |elm_mb|
      mbCondition = elm_mb.attributes["condition"]
      mbTemplate = elm_mb.attributes["template"]
      mbOutputDir = elm_mb.attributes["output-dir"]
      mbFileName = elm_mb.attributes["file-name"]

      if ( mbCondition.to_s == '' or @contentsArray.include? mbCondition )
        erb = ERB.new( File.open( mbTemplate ).read )
        new_code = erb.result( binding )
        create_file( "#{mbOutputDir}", "#{mbFileName}", new_code  )
      end
    }
  }

  puts "Processando includes..."
  doc.elements.each("featureModel/feature-root/feature[@name='Funcionais']/includes/include") { |elm_include|
    copy_file( elm_include.attributes["file-path"], elm_include.attributes["output-dir"], elm_include.attributes["condition"] )
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

  process_features(doc)

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
